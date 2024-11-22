package network;

import javafx.application.Platform;
import managers.GameManager;
import managers.ViewManager;
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
            synchronized (this) {


                try {
                    socket = new Socket(ip, port);
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    Platform.runLater(() -> ViewManager.planView());
                    while (GameManager.isRunning()) {
                        App.sleep(200);
                        Print.line("Waiting...");
                    }
                    Platform.runLater(() -> ViewManager.getBattleView().start(ViewManager.getBaseStage()));
                    App.sleep(200);

                    String fs = GameManager.firstShot();
                    Print.line("Sent: " + fs);
                    writer.println(fs);

                    while (running) {
//                        Platform.runLater(() -> ViewManager.getBattleView().loop());
                        int sliderSleep = (int) (ClientView.getSliderValue() * 1000);
                        String incomingShot = reader.readLine();

                        if (incomingShot != null) {
                            Print.line("Received: " + incomingShot);
                            if (incomingShot.equals("game over"))
                                break;
                            if (incomingShot.length() > 2) {
                                // Update board with new info (hit, miss, etc.)
                                GameManager.receiveStatus(incomingShot);
                            } else {
                                String reply = GameManager.gameMessage(incomingShot);
                                Print.line("Sent: " + reply);
                                writer.println(reply);

                                Thread.sleep(sliderSleep);

                                String rc = GameManager.randomCoordinate();
                                if (rc.length() > 2)
                                    break;
                                writer.println(rc);
                                Print.line("Sent: " + rc);
                            }
                        }

                        Platform.runLater(() -> ViewManager.getBattleView().update());

                    }


                } catch (IOException | InterruptedException e) {
                    Print.line("Error: " + e.getMessage());

                } finally {
                    disconnect();
                }
                Platform.runLater(() -> ViewManager.getBattleView().update());
                App.sleep(3000);
                Platform.runLater(() -> ViewManager.gameOverView());
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
