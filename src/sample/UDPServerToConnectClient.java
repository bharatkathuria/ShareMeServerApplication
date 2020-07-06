package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerToConnectClient {

    private DatagramSocket udpServerSocket = null;

    public void startUDPServerService(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("connectingServer.fxml"));
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    stage.setScene(new Scene(root,300,200));
                }
            });
            System.out.println("Starting UDP server.....");
            udpServerSocket = new DatagramSocket(1234, InetAddress.getByName("0.0.0.0"));
            udpServerSocket.setBroadcast(true);
            Parent root2 = FXMLLoader.load(getClass().getResource("serverRunningGUI.fxml"));
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    stage.setScene(new Scene(root2,300,200));
                }
            });
            System.out.println("Connected UDP server.....");
        }
        catch (Exception e) {

            System.out.println("Failed to start UDP server.....");
            e.printStackTrace();
            System.exit(0);
        }

        byte[] recievedBuffer = new byte[100000];
        DatagramPacket recievedPacket = new DatagramPacket(recievedBuffer,recievedBuffer.length);

        while (true){

            try{
                System.out.println("Listening Clients..........");
                udpServerSocket.receive(recievedPacket); //server waits here till it recieve a packet
                String recievedDataString = new String(recievedPacket.getData(),0,recievedPacket.getLength());
                recievedDataString=recievedDataString.trim();
                if(recievedDataString.equals("CLIENT_HERE")){
                String serverString = "SERVER_HERE";
                byte[] serverStringByte = serverString.getBytes();
                DatagramPacket sendingPacket = new DatagramPacket(serverStringByte,serverStringByte.length,recievedPacket.getAddress(),recievedPacket.getPort());
                udpServerSocket.send(sendingPacket);
                System.out.println("Sending server command");

                }
            }catch (IOException e){

                System.out.println("error listening clients");
                e.printStackTrace();
            }
        }
    }
}
