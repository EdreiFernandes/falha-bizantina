package app;

import javax.swing.table.DefaultTableModel;

import client.Client;
import client.UserConfig;
import server.AddressConfig;
import server.Server;
import server.Status;

public class App {
    private static AppLayout layout;

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
            Client client = new Client();
            client.SendMessage("ALIVE");
        }
    }
}
