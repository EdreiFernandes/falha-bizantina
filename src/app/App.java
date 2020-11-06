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

    public static void main(String[] args) {
        new Server(AddressConfig.getInstance().getFirstAddress());
        layout = new AppLayout();

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
