package network;

import managers.GameManager;
import models.GameBoard;
import models.Square;
import views.ClientView;

import java.io.*;
import java.net.Socket;
//JJ
public class ClientHandler implements Runnable{

    //private final GameManager gameManager = new GameManager();
    private Square square;
    private String ip;
    private int port;


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

                writer.println(GameManager.firstShot());

                while (true) {
                    int sliderSleep = (int) (ClientView.getSliderValue()*1000);
                    Thread.sleep(sliderSleep);

                    String incomingShot = reader.readLine();
                    System.out.println("Server: "+incomingShot);
                    if (incomingShot != null) {
                        String reply = GameManager.gameMessage(incomingShot);
                        writer.println(reply);
                        System.out.println("Client: "+reply);
                    } else {
                        System.out.println("No more shots");
                        break;

                    }



                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }





}
