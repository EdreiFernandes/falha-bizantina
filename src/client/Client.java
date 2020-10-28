package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.Operations;

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

        output.writeObject(sendMessage);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");
        System.out.println(msg);

        input.close();
        output.close();
        socket.close();
    }

}
