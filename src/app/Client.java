package app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import helper.AddressConfig;
import helper.Operations;
import helper.Status;
import helper.UserConfig;
import rsa.PublicKey;

public class Client {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Boolean isWCBusy;
    private List<Boolean> votingList;

    public Client() {
        isWCBusy = false;
        votingList = new ArrayList<Boolean>();
    }

    public void SendMessage(String _operationString) {
        int address = AddressConfig.getInstance().getFirstAddress();
        Operations operation = Operations.valueOf(_operationString);

        while (AddressConfig.isInDomainRange(address)) {
            try {
                if (UserConfig.getInstance().getAddress() != address) {
                    socket = new Socket("localhost", address);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());

                    Message sendMessage = new Message(operation);

                    switch (operation) {
                        case ALIVE:
                            IAmAlive(sendMessage);
                            break;

                        case ENTRY:
                            if (!isWCBusy) {
                                PublicKey publicKey = askForPublicKeys();
                                if (publicKey != null) {
                                    IWouldLikeToUseWC(sendMessage, publicKey);
                                }
                            } else {
                                System.out.println("The WC is busy. Wait a minute");
                            }
                            break;

                        case CONFIRM:
                            ConfirmingUse(sendMessage);
                            break;

                        default:
                            System.out.println(_operationString + " doesn't exist in the current context!");
                            break;
                    }

                    endConnection();
                    input.close();
                    output.close();
                    socket.close();
                }
            } catch (Exception e) {
                System.out.println("Error_" + address + ": " + e.getMessage());
            }
            address++;
        }

        if (operation.equals(Operations.ENTRY)) {
            countingVotes();
        }
    }

    public PublicKey askForPublicKeys() throws Exception {
        Message askKeys = new Message(Operations.PUBKEY);
        askKeys.setParameters("msg", "Can i have your keys?");

        output.writeObject(askKeys);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");
        System.out.println(msg);

        if (reply.getStatus() == Status.OK) {
            PublicKey publicKey = (PublicKey) reply.getParameters("pubkeys");
            return publicKey;
        }
        return null;
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

    private void IWouldLikeToUseWC(Message _sendMessage, PublicKey _publicKey) throws Exception {
        String msg = App.getRsa().EncryptMessage("I would like to use the toilet, ok?", _publicKey);
        _sendMessage.setParameters("msg", msg);
        _sendMessage.setParameters("pubkey", App.getRsa().GetPublicKey());

        output.writeObject(_sendMessage);
        output.flush();

        Message reply = (Message) input.readObject();
        msg = (String) reply.getParameters("msg");
        System.out.println(App.getRsa().DecryptMessage(msg));

        if (reply.getStatus() == Status.OK) {
            votingList.add(false);
        } else if (reply.getStatus() == Status.WAIT) {
            votingList.add(true);
        }
    }

    private void endConnection() throws Exception {
        Message endConnection = new Message(Operations.ENDCON);
        endConnection.setParameters("msg", "Thanks, that's all");

        output.writeObject(endConnection);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");
        System.out.println(msg);
    }

    private void countingVotes() {
        int canNotUse = 0;
        int canUse = 0;
        for (Boolean vote : votingList) {
            if (vote) {
                canNotUse++;
            } else {
                canUse++;
            }
        }

        if (canUse > canNotUse) {
            isWCBusy = true;
            SendMessage("CONFIRM");
        }

        votingList.clear();
    }

    private void ConfirmingUse(Message _sendMessage) throws Exception {
        _sendMessage.setParameters("msg", "I will use the toilet");

        output.writeObject(_sendMessage);
        output.flush();

        Message reply = (Message) input.readObject();
        String msg = (String) reply.getParameters("msg");
        System.out.println(msg);
    }

    public Boolean getisWCBusy() {
        return isWCBusy;
    }

    public void setWCBusy(Boolean isWCBusy) {
        this.isWCBusy = isWCBusy;
    }
}
