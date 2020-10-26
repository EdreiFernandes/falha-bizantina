package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import client.UserConfig;

public class Server implements Runnable {
    private ServerSocket serverSocket;

    public Server(int _address) {
        int id = new Random().nextInt(15);
        UserConfig.getInstance().setUsername("User_" + id);
        turnServerOn(_address);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(UserConfig.getInstance().getAddress());
            System.out.println("Aguardando Conexão");

            while (true) {
                Socket socket = serverSocket.accept();
                treatConnection(socket);
                System.out.println("Server Ligado");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println("Erro: " + e.getMessage());
            if (e.getMessage().equals("Address already in use: NET_Bind")) {
                // Testar próxima porta
                turnServerOn(UserConfig.getInstance().getAddress() + 1);
            }
        }
    }

    private void turnServerOn(int _address) {
        UserConfig.getInstance().setStatus(Status.DISCONNECTED);
        if (UserConfig.isInDomainRange(_address)) {
            UserConfig.getInstance().setAddress(_address);
            UserConfig.getInstance().setStatus(Status.CONNECTED);
            Thread thread = new Thread(this);
            thread.start();
        } else {
            UserConfig.getInstance().setAddress(0);
            UserConfig.getInstance().setStatus(Status.OUT_OF_DOMAIN);
            System.out.println("Porta fora das dependências da aplicação");
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
