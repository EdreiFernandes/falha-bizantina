package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import helper.AddressConfig;
import helper.Operations;
import helper.Status;
import helper.UserConfig;
import rsa.PublicKey;

public class Client {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void SendMessage(String _operationString) {
        int address = AddressConfig.getInstance().getFirstAddress();

        while (AddressConfig.isInDomainRange(address)) {
            try {
                if (UserConfig.getInstance().getAddress() != address) {
                    socket = new Socket("localhost", address);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());

                    Operations operation = Operations.valueOf(_operationString);
                    Message sendMessage = new Message(operation);

                    switch (operation) {
                        case ALIVE:
                            IAmAlive(sendMessage);
                            break;

                        case ENTRY:
                            IWouldLikeToUseWC(sendMessage);
                            break;

                        default:
                            System.out.println(_operationString + " doesn't exist in the current context!");
                            break;
                    }

                    input.close();
                    output.close();
                    socket.close();
                }
            } catch (Exception e) {
                System.out.println("Error " + address);
            }
            address++;
        }
    }

    private void IAmAlive(Message _sendMessage) throws Exception {
        _sendMessage.setParameters("msg", "I am alive");
        _sendMessage.setParameters("address", UserConfig.getInstance().getAddress());
        _sendMessage.setParameters("username", UserConfig.getInstance().getUsername());
        _sendMessage.setParameters("status", UserConfig.getInstance().getStatus());

        output.writeObject(_sendMessage);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");

        if (reply.getStatus() == Status.OK) {
            int address = (int) reply.getParameters("address");
            String username = (String) reply.getParameters("username");
            Status status = (Status) reply.getParameters("status");

            Object[] data = { username, status, address };
            App.updateUsersTable(data);
            System.out.println("Op: " + reply.getOperation() + " - " + address + " says: " + msg);
        } else {
            System.out.println("Op: " + reply.getOperation() + " says: " + msg);
        }
    }

    private void IWouldLikeToUseWC(Message _sendMessage) throws Exception {
        Message askKeys = new Message(Operations.PUBKEY);
        askKeys.setParameters("msg", "Can i have your keys?");

        output.writeObject(askKeys);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");
        System.out.println(msg);

        if (reply.getStatus() == Status.OK) {
            PublicKey publicKey = (PublicKey) reply.getParameters("pubkeys");

            msg = App.getRsa().EncryptMessage("I would like to use the toilet, ok?", publicKey);
            _sendMessage.setParameters("msg", msg);

            output.writeObject(_sendMessage);
            output.flush();

            reply = (Message) input.readObject();
            msg = (String) reply.getParameters("msg");
            System.out.println(msg);

            if (reply.getStatus() == Status.OK) {
                msg = (String) reply.getParameters("res");
                System.out.println(msg);
            }
        }
    }
}
