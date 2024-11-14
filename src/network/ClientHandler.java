package network;

import managers.GameManager;
import models.GameBoard;
import models.Square;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{


    private GameBoard gameBoard = GameManager.getGameBoard();
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

            gameBoard.generateField();
            try {
                socket = new Socket(ip, port);
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                GameManager.firstShot();
                while (true) {
                    //"millis" ska vara värdet från slider
                    Thread.sleep(5000);

                    String incomingShot = reader.readLine();
                    if (incomingShot != null) {
                        String reply = GameManager.gameMessage(incomingShot);
                        writer.println(reply);
                    } else {
                        System.out.println("no feed");
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
