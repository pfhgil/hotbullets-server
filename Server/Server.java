package Server;

import Main.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    private ServerSocket serverSocket;

    private PacketsHandler packetsHandler;
    private ClientsHandler clientsHandler;

    private List<Socket> clients;

    private String serverIP;

    private boolean serverCreated;

    public Server()
    {
        serverIP = "";
        //serverIP = Utils.Utils.getCurrentIP();
        serverIP = "31.173.84.102";

        clients = new ArrayList<>();

        try {
            System.out.println(serverIP);

            /*
            String str1 = serverIP;
            String str2 ="254.250.252.0";
            String[] command1 = { "netsh", "interface", "ip", "set", "address",
                    "name=", "Local Area Connection" ,"source=static", "addr1=" + str1,
                    "mask=" + str2};
            Process pp = java.lang.Runtime.getRuntime().exec(command1);

             */

            serverSocket = new ServerSocket(Settings.ServerSettings.SERVER_PORT);

            serverCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Start()
    {
        if(serverCreated) {
            packetsHandler = new PacketsHandler(this);
            clientsHandler = new ClientsHandler(this);

            clientsHandler.StartHandling();

            packetsHandler.StartInHandling();
            packetsHandler.StartOutHandling();

            System.out.println("Server started!");
        } else {
            System.out.println("Can not start server: server has not been created!");
        }
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    public String getServerIP() { return serverIP; }

    public List<Socket> getClients() { return clients; }
}
