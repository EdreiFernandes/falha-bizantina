package app;

import server.Server;

public class App {
    public static void main(String[] args) {
        Server server = new Server(4240);
        AppLayout layout = new AppLayout();

        Object[] data = { server.getUsername(), server.getStatus(), server.getAddress() };
        layout.addToUsersTable(data);
    }
}
