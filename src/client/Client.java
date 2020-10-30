package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import app.App;
import server.AddressConfig;
import server.Operations;
import server.Status;

public class Client {

    public void SendMessage() {
        int address = AddressConfig.getInstance().getFirstAddress();

        while (AddressConfig.isInDomainRange(address)) {
            try {
                if (UserConfig.getInstance().getAddress() != address) {
                    IAmAlive(address);
                }
            } catch (Exception e) {
                System.out.println("Error " + address);
            }
            address++;
        }
    }

    public void IAmAlive(int _address) throws Exception {
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
        String msg = (String) reply.getParameters("msg");
        if (reply.getStatus() == Status.OK) {
            int address = (int) reply.getParameters("address");
            String username = (String) reply.getParameters("username");
            Status status = (Status) reply.getParameters("status");

            Object[] data = { username, status, address };
            App.updateUsersTable(data);
        }
        System.out.println("Op: " + reply.getOperation() + " - " + _address + " says: " + msg);

        input.close();
        output.close();
        socket.close();
    }

}
