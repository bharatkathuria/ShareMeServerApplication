package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPMessageServer {

    private ServerSocket serverSocket=null;
    private Socket socket = null;
    public DataStructure dataStructure;
    TCPMessageServer(){
        dataStructure = new DataStructure();
    }

    public void startTCPServerService(Stage stage){
        try{
            System.out.println("TCP Server trying connection");
            serverSocket = new ServerSocket(2222);

        }catch (IOException e){

            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, stage, "Error!",
                            "FATAL ERROR.....");
                }
            });
            System.exit(0);
        }
        System.out.println("Connection Established");
        while (true){
            try{
                System.out.println("Listening on TCP server.......");
                socket=serverSocket.accept();
            }catch (IOException e){
                e.printStackTrace();
            }
            dataStructure.add(socket);
            new SeperateServiceForEveryClient(socket).start();
        }
    }

    private class SeperateServiceForEveryClient extends Thread{

        Socket socketReferenceToCurrentClient=null;
        public SeperateServiceForEveryClient(Socket socketReferenceToCurrentClient){
            this.socketReferenceToCurrentClient=socketReferenceToCurrentClient;
        }

        @Override
        public void run(){
            System.out.println("Started.......w");
            String message="";
            DataInputStream dataInputStream = null;
            try{
                dataInputStream = new DataInputStream(socketReferenceToCurrentClient.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String userName="";
            try{
                userName=dataInputStream.readUTF();
                dataStructure.setNameOfClient(socketReferenceToCurrentClient,userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataStructure.sendNamesOfActiveClientsToParticularClient(socketReferenceToCurrentClient);

            dataStructure.sendNotificationToAllClientsExcept(socketReferenceToCurrentClient,userName+" joined");

            dataStructure.sendCommandToAllClientsExcept(socketReferenceToCurrentClient,"INSERT@"+userName);

            while (true){
                try{
                    message = dataInputStream.readUTF();
                } catch (SocketException |NullPointerException e) {
                    System.err.println(e);
                    dataStructure.delete(socketReferenceToCurrentClient);
                    dataStructure.sendNotificationToAllClients(userName+" left");
                    dataStructure.sendCommandToAllClients("REMOVE@"+userName);
                    try{
                        socketReferenceToCurrentClient.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    dataStructure.distributeMessage(message.trim());
            }
        }
    }
}
