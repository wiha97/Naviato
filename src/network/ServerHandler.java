package network;

import javafx.application.Platform;
import managers.GameManager;
import managers.ViewManager;
import util.App;
import util.Print;
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

    public ServerHandler(int port, ServerView serverView) {
        this.port = port;
        this.serverView = serverView;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setReuseAddress(true);
                running = true;
                Print.line("Server started on port " + port);

                Platform.runLater(() -> ViewManager.planView());
                while (GameManager.isRunning()) {
                    App.sleep(200);
                    Print.line("Waiting...");
                }


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
                String incomingMessage = reader.readLine();

                if (incomingMessage != null) {


                    if (incomingMessage.equals("game over"))
                        break;
                    if (incomingMessage.startsWith("i") || incomingMessage.length() < 4) {
                        String reply = GameManager.gameMessage(incomingMessage);
                        Print.line("Client: " + reply);
                        writer.println(reply);
                        Thread.sleep(sliderSleep);
                        String rc = GameManager.randomCoordinate();
                        writer.println(rc);
                        Print.line("client: " + rc);
                    } else {
                        // Update board with new info (hit, miss, etc.)
                        GameManager.receiveStatus(incomingMessage);
                    }
                } else {
                    break;
                }
                Platform.runLater(() -> ViewManager.getBattleView().update());
            }
        } catch (IOException | InterruptedException e) {
            Print.line("Communication error: " + e.getMessage());
        }
        stopServer();
        Platform.runLater(() -> ViewManager.getBattleView().update());
        App.sleep(3000);
        Platform.runLater(() -> ViewManager.gameOverView());
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
