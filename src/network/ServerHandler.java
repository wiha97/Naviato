package network;

import managers.GameManager;
import models.GameBoard;
import models.Ship;
import models.Square;
import util.Print;
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
            Print.line("Server started on port " + port);
//            System.out.println("Server started on port " + port);

            while (running) {
                clientSocket = serverSocket.accept();
                Print.line("Client connected: " + clientSocket.getInetAddress());
                serverView.updateConnectionStatus("Client connected!");

                handleClientCommunication(clientSocket);
            }
        } catch (IOException e) {
            Print.line("Error: " + e.getMessage());
//            System.out.println("Error: " + e.getMessage());
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
//                    System.out.println("Received: " + incomingMessage);
                    Print.line("Received: " + incomingMessage);
                    String response = GameManager.gameMessage(incomingMessage);
                    Print.line("Sent: " + response);
                    writer.println(response);
//                    String rc = GameManager.randomCoordinate();
//                    writer.println(rc);
//                    Print.line("Sent: " + rc);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            Print.line("Communication error: " + e.getMessage());
//            System.out.println("Communication error: " + e.getMessage());
        }
    }

    private String handleShotMessage(String message) {
        if (!gameInitialized && message.startsWith("i shot")) {
            gameInitialized = true;
            String coordinates = message.substring(7);
            Print.line("Game initialized by client at: " + coordinates);
//            System.out.println("Game initialized by client at: " + coordinates);
            return handleGameShot(coordinates);
        }

        if (gameInitialized) {
            String[] arr = message.split(" ");
            String coordinates = "";
            if (arr.length > 2) {
                coordinates = arr[2];
//            String coordinates = message.substring(7);
            }
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

        return code + " shot " + coordinates;
    }

    private boolean isHit(String coordinates) {

        return false;
        //logik för att kontrollera hit
    }

    private boolean checkIfShipSunk(String coordinates) {
        return false;
        //logik för att kontrollera om skepp sjunkit
    }

    public void stopServer() {
        running = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            Print.line("Server stopped.");
//            System.out.println("Server stopped.");
        } catch (IOException e) {
            Print.line("Error stopping the server: " + e.getMessage());
//            System.out.println("Error stopping the server: " + e.getMessage());
        }
    }

    public void startServer() {
        new Thread(this).start();
    }
}
