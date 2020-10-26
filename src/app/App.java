package app;

import client.Client;
import client.UserConfig;
import server.Server;
import server.Status;

public class App {
    public static void main(String[] args) {
        new Server(4240);
        AppLayout layout = new AppLayout();

        Object[] data = { UserConfig.getInstance().getUsername(), UserConfig.getInstance().getStatus(),
                UserConfig.getInstance().getAddress() };
        layout.addToUsersTable(data);

        if (UserConfig.getInstance().getStatus() != Status.OUT_OF_DOMAIN) {
            Client client = new Client();
            client.IAmAlive();
        }
    }
}
