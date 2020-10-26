package app;

import client.UserConfig;
import server.Server;

public class App {
    public static void main(String[] args) {
        new Server(4240);
        AppLayout layout = new AppLayout();

        Object[] data = { UserConfig.getInstance().getUsername(), UserConfig.getInstance().getStatus(),
                UserConfig.getInstance().getAddress() };
        layout.addToUsersTable(data);
    }
}
