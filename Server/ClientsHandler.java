package Server;

import Client.Client;
import Main.Settings;

import java.io.IOException;
import java.net.Socket;

public class ClientsHandler
{
    private Thread clientsWaitingThread;

    private boolean handlingActive;

    public ClientsHandler(Server server)
    {
        clientsWaitingThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(handlingActive && server.getPlayersNum() < Settings.ServerSettings.MAX_PLAYERS) {
                    try {
                        Socket newClientSocket = server.getServerSocket().accept();
                        Client newClient = new Client(newClientSocket);

                        server.setPlayersNum(server.getPlayersNum() + 1);
                        server.getClients()[newClient.getId().getID()] = newClient;

                        server.getClients()[newClient.getId().getID()].StartPacketsHandling();

                        System.out.println("Подключился игрок: " + newClientSocket.getInetAddress().getCanonicalHostName() + ". ID: " + server.getClients()[newClient.getId().getID()].getId().getID());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void StartHandling()
    {
        handlingActive = true;
        
        clientsWaitingThread.start();
    }

    public void StopHandling()
    {
        handlingActive = false;
    }
}
