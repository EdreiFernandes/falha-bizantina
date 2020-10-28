package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import app.App;
import server.Operations;
import server.Status;

public class Client {

    public void IAmAlive() {
        int address = 4240;
        while (UserConfig.isInDomainRange(address)) {
            try {
                if (UserConfig.getInstance().getAddress() != address) {
                    SendMessage(address);
                }
            } catch (Exception e) {
                System.out.println("Error " + address);
            }
            address++;
        }
    }

    public void SendMessage(int _address) throws Exception {
        Socket socket = new Socket("localhost", _address);

        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        // TODO enviar mensagem
        Message sendMessage = new Message(Operations.ALIVE);
        sendMessage.setParameters("msg", "I am alive");
        sendMessage.setParameters("address", UserConfig.getInstance().getAddress());
        sendMessage.setParameters("username", UserConfig.getInstance().getUsername());
        sendMessage.setParameters("status", UserConfig.getInstance().getStatus());

        output.writeObject(sendMessage);
        output.flush();

        Message reply = (Message) input.readObject();
        if (reply.getStatus() == Status.OK) {
            String msg = (String) reply.getParameters("msg");
            int address = (int) reply.getParameters("address");
            String username = (String) reply.getParameters("username");
            Status status = (Status) reply.getParameters("status");

            Object[] data = { username, status, address };
            App.updateUsersTable(data);
            System.out.println(_address + " says: " + msg);
        }

        input.close();
        output.close();
        socket.close();
    }

}
