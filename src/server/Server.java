package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;

    public Server(int _porta) {
        try {
            serverSocket = new ServerSocket(_porta);
            Thread thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Aguardando Conexão");
            while (true) {
                Socket socket = serverSocket.accept();
                treatConnection(socket);
                System.out.println("Server Ligado");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
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
}
