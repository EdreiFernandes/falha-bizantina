package app;

import javax.swing.table.DefaultTableModel;

import helper.AddressConfig;
import helper.Status;
import helper.UserConfig;
import rsa.RSAManager;

public class App {
    private static AppLayout layout;
    private static Client client;
    private static RSAManager rsa;

    public static RSAManager getRsa() {
        return rsa;
    }

    public static Client getClient() {
        return client;
    }

    public static void updateUsersTable(Object[] _data) {
        DefaultTableModel model = (DefaultTableModel) layout.getUsersTable().getModel();
        model.addRow(_data);
    }

    public static void writeConsole(String _username, int _userId, String _log) {
        String consoleLogs = layout.getConsole().getText();
        consoleLogs += _username + "_" + _userId + ": " + _log + "\n";
        layout.getConsole().setText(consoleLogs);
    }

    public static void usingTheWC() {
        UserConfig.getInstance().setImIntheWC(true);

        Thread countThread = new Thread() {
            @Override
            public void run() {
                try {
                    int timer = 30; // seconds

                    while (timer > 0) {
                        timer--;
                        Thread.sleep(1000);
                    }
                    UserConfig.getInstance().setImIntheWC(false);

                    writeConsole("System", 0, "Leaving the WC");
                    App.getClient().SendMessage("EXIT");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        countThread.start();
    }

    public static void main(String[] args) {
        new Server(AddressConfig.getInstance().getFirstAddress());
        layout = new AppLayout();

        UserConfig.getInstance().setUsername(layout.askForUsername());

        Object[] data = { UserConfig.getInstance().getUsername(), UserConfig.getInstance().getStatus(),
                UserConfig.getInstance().getAddress() };
        updateUsersTable(data);

        if (UserConfig.getInstance().getStatus() != Status.OUT_OF_DOMAIN) {
            rsa = new RSAManager();
            client = new Client();

            client.SendMessage("ALIVE");
        }
    }
}
