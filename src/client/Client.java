package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public void SendMessage() throws Exception {
        Socket socket = new Socket("localhost", 4242);

        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        // TODO enviar mensagem

        input.close();
        output.close();
        socket.close();
    }

}
