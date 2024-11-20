package network;

import managers.GameManager;
import models.GameBoard;
import models.Ship;
import models.Square;
import util.Print;
import views.ClientView;
import views.ServerView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//Fredrik med lite korrigering av Wilhelm

public class ServerHandler implements Runnable {

    private final int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean running;
    private final ServerView serverView;
    private final GameBoard gameBoard = GameManager.getGameBoard();

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

            while (running) {
                clientSocket = serverSocket.accept();
                Print.line("Client connected: " + clientSocket.getInetAddress());
                serverView.updateConnectionStatus("Client connected!");

                handleClientCommunication(clientSocket);
            }
        } catch (IOException e) {
            Print.line("Error: " + e.getMessage());
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
                int sliderSleep = (int) (ServerView.getSliderValue() * 1000);
                try {
                    Thread.sleep(sliderSleep);
                } catch (InterruptedException e) {
                    Print.line("Sleep interrupted: " + e.getMessage());
                    break;
                }

                String incomingMessage = reader.readLine();
                if (incomingMessage != null) {
                    Print.line("Received: " + incomingMessage);
                    String response = GameManager.gameMessage(incomingMessage);
                    Print.line("Sent: " + response);
                    writer.println(response);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            Print.line("Communication error: " + e.getMessage());
        }
    }


    public void stopServer() {
        running = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            Print.line("Server stopped.");
        } catch (IOException e) {
            Print.line("Error stopping the server: " + e.getMessage());
        }
    }

    public void startServer() {
        new Thread(this).start();
    }
}
