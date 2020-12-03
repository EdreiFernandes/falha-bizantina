package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import helper.AddressConfig;
import helper.Operations;
import helper.Status;
import helper.UserConfig;
import rsa.PublicKey;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Server(int _address) {
        turnServerOn(_address);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(UserConfig.getInstance().getAddress());
            System.out.println("Running server");

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
            App.writeConsole("System", 0, "Address outside the application domain");
        }
    }

    private void treatConnection(Socket _socket) {
        try {
            boolean talking = true;
            output = new ObjectOutputStream(_socket.getOutputStream());
            input = new ObjectInputStream(_socket.getInputStream());

            while (talking) {
                Message received = (Message) input.readObject();
                Operations operation = received.getOperation();
                Operations replyOperation = Operations.valueOf(operation.toString() + "_REPLY");
                Message reply = new Message(replyOperation);

                switch (operation) {
                    case ALIVE:
                        someoneAlive(received, reply);
                        break;

                    case PUBKEY:
                        askingForKeys(received, reply);
                        break;

                    case ENTRY:
                        askingForWC(received, reply);
                        break;

                    case CONFIRM:
                        changingWCBusy(received, reply);
                        break;

                    case EXIT:
                        someoneExitingWC(received, reply);
                        break;

                    case ENDCON:
                        endConnection(received, reply);
                        talking = false;
                        break;

                    default:
                        reply.setStatus(Status.ERROR);
                        reply.setParameters("msg", "I couldn't find this operation, Sorry");
                        break;
                }

                output.writeObject(reply);
                output.flush();
            }

            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void askingForKeys(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            System.out.println(msg);

            _reply.setStatus(Status.OK);
            _reply.setParameters("msg", "Sure, take my keys!");
            PublicKey publicKey = App.getRsa().GetPublicKey();
            _reply.setParameters("pubkey", publicKey);
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }

    private void someoneAlive(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            String address = (String) _received.getParameters("address");
            String username = (String) _received.getParameters("username");
            String status = (String) _received.getParameters("status");

            msg = App.getRsa().DecryptMessage(msg);
            address = App.getRsa().DecryptMessage(address);
            username = App.getRsa().DecryptMessage(username);
            status = App.getRsa().DecryptMessage(status);

            System.out.println(msg);

            Object[] data = { username, status, address };
            App.updateUsersTable(data);
            App.writeConsole(username, Integer.valueOf(address), status);

            PublicKey publicKey = (PublicKey) _received.getParameters("pubkey");
            msg = App.getRsa().EncryptMessage("Ok, i heard you", publicKey);
            address = App.getRsa().EncryptMessage(Integer.toString(UserConfig.getInstance().getAddress()), publicKey);
            username = App.getRsa().EncryptMessage(UserConfig.getInstance().getUsername(), publicKey);
            status = App.getRsa().EncryptMessage(UserConfig.getInstance().getStatus().toString(), publicKey);

            _reply.setStatus(Status.OK);
            _reply.setParameters("msg", msg);
            _reply.setParameters("address", address);
            _reply.setParameters("username", username);
            _reply.setParameters("status", status);
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }

    private void askingForWC(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            PublicKey publicKey = (PublicKey) _received.getParameters("pubkey");
            msg = App.getRsa().DecryptMessage(msg);
            System.out.println(msg);

            _reply.setStatus(Status.OK);
            if (!App.getClient().getisWCBusy()) {
                msg = "Ok, You can use the WC now";
            } else {
                msg = "Wait a minute";
            }
            msg = App.getRsa().EncryptMessage(msg, publicKey);
            String answer = App.getRsa().EncryptMessage(App.getClient().getisWCBusy().toString(), publicKey);

            _reply.setParameters("msg", msg);
            _reply.setParameters("answer", answer);
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }

    private void changingWCBusy(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            System.out.println(msg);
            App.getClient().setWCBusy(true);

            _reply.setStatus(Status.OK);
            _reply.setParameters("msg", "The 'isWCBusy' has been changing");
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }

    private void endConnection(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            System.out.println(msg);

            _reply.setStatus(Status.OK);
            _reply.setParameters("msg", "See you");
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }

    private void someoneExitingWC(Message _received, Message _reply) {
        try {
            String msg = (String) _received.getParameters("msg");
            msg = App.getRsa().DecryptMessage(msg);
            System.out.println(msg);

            App.getClient().setWCBusy(false);

            _reply.setStatus(Status.OK);
            PublicKey publicKey = (PublicKey) _received.getParameters("pubkey");
            msg = App.getRsa().EncryptMessage("All rigth, The toilet is no longer busy", publicKey);
            _reply.setParameters("msg", msg);
        } catch (Exception e) {
            _reply.setStatus(Status.ERROR);
            _reply.setParameters("msg", e.getMessage());
        }
    }
}
