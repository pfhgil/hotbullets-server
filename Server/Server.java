package Server;

import Client.Client;
import Main.Settings;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{
    private ServerSocket serverSocket;

    private ClientsHandler clientsHandler;

    private Client[] clients;

    private int playersNum;

    private boolean serverCreated;

    private UID uid;

    public Server()
    {
        clients = new Client[Settings.ServerSettings.MAX_PLAYERS];

        playersNum = 0;

        uid = new UID(Settings.ServerSettings.MAX_PLAYERS);
        try {
            serverSocket = new ServerSocket(Settings.ServerSettings.SERVER_PORT);

            serverCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Start()
    {
        if(serverCreated) {
            clientsHandler = new ClientsHandler(this);

            clientsHandler.StartHandling();

            System.out.println("Server started!");
        } else {
            System.out.println("Can not start server: server has not been created!");
        }
    }

    public ServerSocket getServerSocket() { return serverSocket; }

    public Client[] getClients() { return clients; }

    public int getPlayersNum() { return playersNum; }
    public void setPlayersNum(int playersNum) { this.playersNum = playersNum; }

    public UID getUid() { return uid; }
}
