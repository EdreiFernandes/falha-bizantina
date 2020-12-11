package app;

import javax.swing.table.DefaultTableModel;

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

    public static void writeConsole(String _log) {
        String consoleLogs = layout.getConsole().getText();
        consoleLogs += "System: " + _log + "\n";
        layout.getConsole().setText(consoleLogs);
    }

    public static void usingTheWC() {
        Thread countThread = new Thread() {
            @Override
            public void run() {
                try {
                    int timer = 30; // seconds
                    layout.toggleTimer(true);

                    while (timer > 0) {
                        Thread.sleep(1000);
                        layout.getTimer().setText(Integer.toString(timer));
                        timer--;
                    }
                    layout.toggleTimer(false);

                    if (UserConfig.getInstance().getImIntheWC()) {
                        UserConfig.getInstance().setImIntheWC(false);
                        writeConsole("You are leaving the toilet");
                        App.getClient().SendMessage("EXIT");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
        countThread.start();
    }

    public static void main(String[] args) {
        layout = new AppLayout();
        new Server(UserConfig.getInstance().getAddress());
        UserConfig.getInstance().setUsername(layout.askForUsername());

        Object[] data = { UserConfig.getInstance().getUsername(), UserConfig.getInstance().getStatus(),
                UserConfig.getInstance().getAddress() };
        updateUsersTable(data);

        if (UserConfig.getInstance().getStatus() != Status.OUT_OF_DOMAIN) {
            writeConsole("You are connected");

            rsa = new RSAManager();
            client = new Client();

            client.SendMessage("ALIVE");
        }
    }
}
