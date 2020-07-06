package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class DataStructure {

    private class Node{
        Socket socket=null;
        DataOutputStream dataOutputStream =null;
        String userName="";
        public Node(Socket socket){
            this.socket=socket;
        }
    }
    LinkedList<Node> list = new LinkedList<>();

    public void add(Socket socket){
        Node newNode = new Node(socket);
        try{
            newNode.dataOutputStream=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {

        }
        list.add(newNode);
    }

    public void delete(Socket socket){
        System.out.println("delete error");
        for(Node temp:list)
        {
            if(temp.socket==socket){
               list.remove(temp);
               break;
            }
        }
    }

    public void distributeMessage(String message){

        for(Node temp:list){
            if(temp.dataOutputStream!=null){
                try{
                    System.out.println("Sending M"+message);
                    temp.dataOutputStream.writeUTF("------------------------------------------------------------------------\n" + message + "\n------------------------------------------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void setNameOfClient(Socket socket,String userName){
        for(Node temp:list)
        {
            if(temp.socket==socket){
                temp.userName=userName;
            }
        }
    }

    String getNameOfClient(Socket socket){
        for(Node temp:list)
        {
            if(temp.socket==socket){
                return temp.userName;
            }
        }
        return "NULL";
    }
    public void sendNamesOfActiveClientsToParticularClient(Socket socket){
        String userNamesOfActiveClients="";
        Node client=null;
        for(Node temp:list){

            if(temp.socket==socket){
                client=temp;
                continue;
                         }
            userNamesOfActiveClients+=(temp.userName+"+");

        }
        try {
            if(client!=null)
                client.dataOutputStream.writeUTF(userNamesOfActiveClients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNotificationToAllClients(String message){
        for(Node temp:list){
            if (temp.dataOutputStream != null) {
                try {
                    System.out.println("Sending MALL"+message);
                    temp.dataOutputStream.writeUTF("\n----------------------------------" + message + "--------------------------------------\n" + "------------------------------------------------------------------------\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendNotificationToAllClientsExcept(Socket socket,String message){

        for(Node temp:list){
            if (temp.dataOutputStream != null && temp.socket != socket) {
                try {
                    temp.dataOutputStream.writeUTF("\n----------------------------------" + message + "--------------------------------------\n" + "------------------------------------------------------------------------\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendCommandToAllClientsExcept(Socket socket,String message) {
        for(Node temp:list){
            if (temp.dataOutputStream != null && temp.socket != socket) {
                try {
                    temp.dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendCommandToParticularClient(Socket socket,String message){
        Node client=null;
        for(Node temp:list) {
            if (temp.socket == socket) {
                client = temp;
            }
        }
        try{
            if(client!=null){
                client.dataOutputStream.writeUTF(message);
            }
        }catch (IOException e){

        }
    }

    //for file transfer tcp..............

    public void sendCommandToAllClients(String message) {

        for(Node temp:list){
            if (temp.dataOutputStream != null) {
                try {
                    temp.dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
