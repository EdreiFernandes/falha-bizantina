package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import app.App;
import client.Message;
import client.UserConfig;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Server(int _address) {
        int id = new Random().nextInt(15);
        UserConfig.getInstance().setUsername("User_" + id);
        turnServerOn(_address);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(UserConfig.getInstance().getAddress());
            System.out.println("Waiting conections");

            while (true) {
                Socket socket = serverSocket.accept();
                treatConnection(socket);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println("Error: " + e.getMessage());
            if (e.getMessage().equals("Address already in use: NET_Bind")) {
                System.out.println("Let's try to use the next address");
                turnServerOn(UserConfig.getInstance().getAddress() + 1);
            }
        }
    }

    private void turnServerOn(int _address) {
        UserConfig.getInstance().setStatus(Status.DISCONNECTED);
        if (AddressConfig.isInDomainRange(_address)) {
            UserConfig.getInstance().setAddress(_address);
            UserConfig.getInstance().setStatus(Status.CONNECTED);
            Thread thread = new Thread(this);
            thread.start();
        } else {
            UserConfig.getInstance().setAddress(0);
            UserConfig.getInstance().setStatus(Status.OUT_OF_DOMAIN);
            System.out.println("Address outside the application domain");
        }
    }

    private void treatConnection(Socket _socket) {
        try {
            output = new ObjectOutputStream(_socket.getOutputStream());
            input = new ObjectInputStream(_socket.getInputStream());

            Message received = (Message) input.readObject();
            Operations operation = received.getOperation();
            Operations replyOperation = Operations.valueOf(operation.toString() + "_REPLY");
            Message reply = new Message(replyOperation);

            switch (operation) {
                case ALIVE:
                    someoneAlive(received, reply);
                    break;

                default:
                    reply.setStatus(Status.ERROR);
                    reply.setParameters("msg", "I couldn't find this operation, Sorry");
                    break;
            }

            output.writeObject(reply);
            output.flush();

            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void someoneAlive(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            int address = (int) _received.getParameters("address");
            String username = (String) _received.getParameters("username");
            Status status = (Status) _received.getParameters("status");

            Object[] data = { username, status, address };
            App.updateUsersTable(data);
            System.out.println(address + " says: " + msg);

            _reply.setStatus(Status.OK);
            _reply.setParameters("msg", "Ok, i heard you");
            _reply.setParameters("address", UserConfig.getInstance().getAddress());
            _reply.setParameters("username", UserConfig.getInstance().getUsername());
            _reply.setParameters("status", UserConfig.getInstance().getStatus());
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }
}
