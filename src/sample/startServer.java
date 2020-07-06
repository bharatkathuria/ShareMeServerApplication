package sample;

import javafx.stage.Stage;

public class startServer {


    public  void startAllservers(Stage stage){

        Thread udpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                new UDPServerToConnectClient().startUDPServerService(stage);

            }
        });
        //for terminating thread with window close
        udpThread.setDaemon(true);
        udpThread.start();
        final TCPMessageServer tcpServer = new TCPMessageServer();
        Thread tcpThread=new Thread(new Runnable() {
            @Override
            public void run() {
                tcpServer.startTCPServerService(stage);
            }
        });
        //for terminating thread with window close
        tcpThread.setDaemon(true);
        tcpThread.start();

    }
}
