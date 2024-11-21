package network;

import javafx.application.Platform;
import managers.GameManager;
import managers.ViewManager;
import models.GameBoard;
import models.Square;
import util.App;
import util.Print;
import views.ClientView;

import java.io.*;
import java.net.Socket;

//JJ
public class ClientHandler implements Runnable {

    //private final GameManager gameManager = new GameManager();
    private Square square;
    private String ip;
    private int port;
    private boolean running = true;


    public ClientHandler(String ip, int port) {
        this.ip = ip;
        this.port = port;

    }

    Socket socket;

    @Override
    public void run() {
        {


            try {
                socket = new Socket(ip, port);
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                Platform.runLater(() -> ViewManager.planView());
                while (GameManager.isRunning()){
                    App.sleep(3000);
                    Print.line("Waiting...");
                }

                writer.println(GameManager.firstShot());

                while (running) {
                    int sliderSleep = (int) (ClientView.getSliderValue() * 1000);
                    Thread.sleep(sliderSleep);

                    String incomingShot = reader.readLine();
//                    System.out.println("Server: "+incomingShot);
                    Print.line("Received: " + incomingShot);
                    if (incomingShot != null) {
                        String reply = GameManager.gameMessage(incomingShot);
                        writer.println(reply);
                        Print.line("Client: " + reply);
                        if (reply.length() > 2) {
                            String rc = GameManager.randomCoordinate();
                            writer.println(rc);
                            Print.line("client: " + rc);
                        }
//                        String rc = GameManager.randomCoordinate();
//                        writer.println(rc);
//                        Print.line("Client: "+rc);
//                        System.out.println("Client: "+reply);
                    } else {
                        Print.line("No more shots");
//                        System.out.println("No more shots");
                        break;

                    }


                }


            } catch (IOException | InterruptedException e) {
                Print.line("Error: " + e.getMessage());

            } finally {
                disconnect();
            }
        }
    }

    public void disconnect() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            Print.line("Client disconnect");
        } catch (IOException e) {
            Print.line("Error: " + e.getMessage());
        }
    }


}
