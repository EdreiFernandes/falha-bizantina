package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    public void SendMessage(int _address) throws Exception {
        Socket socket = new Socket("localhost", _address);

        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        // TODO enviar mensagem
        System.out.println("Enviando para " + _address);

        input.close();
        output.close();
        socket.close();
    }

}
