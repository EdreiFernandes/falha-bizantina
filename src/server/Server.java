package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int address;
    private String status = "Conectado";
    private ServerSocket serverSocket;

    public Server(int _address) {
        address = _address;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(address);
            System.out.println("Aguardando Conexão");

            while (true) {
                Socket socket = serverSocket.accept();
                treatConnection(socket);
                System.out.println("Server Ligado");
            }
        } catch (Exception e) {
            status = "Desconectado";
            System.out.println("Erro: " + e.getMessage());
            if (e.getMessage().equals("Address already in use: NET_Bind")) {
                System.out.println("Porta em uso");
            }
        }
    }

    private void treatConnection(Socket _socket) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(_socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(_socket.getInputStream());

            // TODO tratamento
            System.out.println("Tratado");

            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public int getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
