package Server;

import Client.Client;
import Main.Settings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    private ServerSocket serverSocket;

    private PacketsHandler packetsHandler;
    private ClientsHandler clientsHandler;

    private List<Client> clients;

    private List<Object> packets;

    private int playersNum;

    private boolean serverCreated;

    public Server()
    {
        clients = new ArrayList<>();

        packets = new ArrayList<>();

        playersNum = 0;
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

    public List<Client> getClients() { return clients; }

    public List<Object> getPackets() { return packets; }

    public int getPlayersNum() { return playersNum; }
    public void setPlayersNum(int playersNum) { this.playersNum = playersNum; }
}
