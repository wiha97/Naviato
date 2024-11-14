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
    private boolean isFirstShot = false;

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

    public String handleShot(String shotMessage) {
        if (shotMessage.startsWith("i")) {
            System.out.println("Game initialization shot received: " + shotMessage);
            String coordinates = shotMessage.substring(6);
            return handleGameShot(coordinates);
        }

        String coordinates = shotMessage.substring(6);
        return handleGameShot(coordinates);
    }

    public String handleGameShot(String coordinates) {
        boolean isHit = isHit(coordinates);
        String result = "";

        if (isHit) {
            boolean isSunk = checkIfShipSunk(coordinates);
            if (isSunk) {
                result = "s shot " + GameManager.printRandomCoordinate();
            } else {
                result = "h shot " + GameManager.printRandomCoordinate();
            }
        } else {
            result = "m shot " + GameManager.printRandomCoordinate();
        }

        return result;
    }

    private boolean isHit(String coordinates) {
        Square targetSquare = gameBoard.getSquare(coordinates);
        if (targetSquare != null && targetSquare.getShip() != null) {
            targetSquare.setHit(true);
            return true;
        }
        return false;
    }

    private boolean checkIfShipSunk(String coordinates) {
        Square targetSquare = gameBoard.getSquare(coordinates);
        Ship ship = targetSquare.getShip();

        if (ship != null) {
            return ship.isSunk();
        }
        return false;
    }
}
