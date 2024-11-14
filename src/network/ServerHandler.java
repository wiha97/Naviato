package network;

import managers.GameManager;
import models.GameBoard;
import models.Ship;
import models.Square;
import views.ServerView;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler implements Runnable {

    private final int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean running;
    private final ServerView serverView;
    private final GameBoard gameBoard = GameManager.getGameBoard();
    private boolean gameInitialized = false;

    public ServerHandler(int port, ServerView serverView) {
        this.port = port;
        this.serverView = serverView;
        gameBoard.generateField();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            running = true;
            System.out.println("Server started on port " + port);

            while (running) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                serverView.updateConnectionStatus("Client connected!");

                handleClientCommunication(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    private void handleClientCommunication(Socket socket) {
        try (
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input))
        ) {
            while (running) {
                String incomingMessage = reader.readLine();
                if (incomingMessage != null) {
                    System.out.println("Received: " + incomingMessage);
                    String response = handleShotMessage(incomingMessage);
                    writer.println(response);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Communication error: " + e.getMessage());
        }
    }

    private String handleShotMessage(String message) {
        if (!gameInitialized && message.startsWith("i shot")) {
            gameInitialized = true;
            String coordinates = message.substring(7);
            System.out.println("Game initialized by client at: " + coordinates);
            return handleGameShot(coordinates);
        }

        if (gameInitialized) {
            String coordinates = message.substring(7);
            return handleGameShot(coordinates);
        }

        return "Invalid message";
    }

    private String handleGameShot(String coordinates) {
        boolean isHit = isHit(coordinates);
        String code;

        if (isHit) {
            boolean isSunk = checkIfShipSunk(coordinates);
            code = isSunk ? "s" : "h";
        } else {
            code = "m";
        }

        return code + " shot " + GameManager.printRandomCoordinate();
    }

/*    private boolean isHit(String coordinates) {
       //logik för att kontrollera hit
            return true;
        }
        return false;
    }

    private boolean checkIfShipSunk(String coordinates) {
        //logik för att kontrollera om skepp sjunkit
    }*/

    public void stopServer() {
        running = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.out.println("Error stopping the server: " + e.getMessage());
        }
    }

    public void startServer() {
        new Thread(this).start();
    }
}
