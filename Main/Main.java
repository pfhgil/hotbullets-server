package Main;

import Server.Server;

public class Main
{
    public static Server server;

    public static void main(String[] args)
    {
        System.out.println("Program started.");

        server = new Server();
        server.Start();
    }
}
