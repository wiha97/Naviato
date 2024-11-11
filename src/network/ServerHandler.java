package network;

import models.GameBoard;
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
    private final GameBoard gameBoard;

    public ServerHandler(int port, ServerView serverView) {
        this.port = port;
        this.serverView = serverView;
        this.gameBoard = new GameBoard();
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
                System.out.println("Server: Client connected: " + clientSocket.getInetAddress());
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
                String randomShot = gameBoard.printRandomCoordinate();
                System.out.println("Server sending shot: " + randomShot);
                writer.println(randomShot);

                String incomingShot = reader.readLine();
                if (incomingShot != null) {
                    System.out.println("Client: " + incomingShot);
                    String response = handleShot(incomingShot);
                    writer.println(response);
                } else {
                    System.out.println("No feed from client.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Communication error: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.out.println("Error stopping the server: " + e.getMessage());
        }
    }

    public void startServer() {
        Thread serverThread = new Thread(this);
        serverThread.start();
    }

    public String handleShot(String coordinates) {
        boolean isHit = isHit(coordinates);
        if (isHit) {
            boolean isSunk = checkIfShipSunk(coordinates);
            if (isSunk) {
                return "s " + coordinates;
            } else {
                return "h " + coordinates;
            }
        } else {
            return "m " + coordinates;
        }
    }

    private boolean isHit(String coordinates) {
        return false;
    }

    private boolean checkIfShipSunk(String coordinates) {
        return false;
    }
}
