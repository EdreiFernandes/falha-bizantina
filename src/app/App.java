package app;

import server.Server;

public class App {
    public static void main(String[] args) {
        new Server(4240);
        new AppLayout();
    }
}
