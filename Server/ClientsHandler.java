package Server;

import Client.Client;
import Main.Settings;

import java.io.IOException;
import java.net.Socket;

public class ClientsHandler
{
    private Thread clientsWaitingThread;
    private Thread clientsDisconnectCheckThread;

    private boolean handlingActive;

    public ClientsHandler(Server server)
    {
        clientsWaitingThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(handlingActive && server.getPlayersNum() < Settings.ServerSettings.MAX_PLAYERS) {
                    try {
                        Socket newClientSocket = server.getServerSocket().accept();
                        Client newClient = new Client(newClientSocket);

                        System.out.println("Подключился игрок: " + newClientSocket.getInetAddress().getCanonicalHostName());

                        server.getClients().add(newClient);
                        server.setPlayersNum(server.getPlayersNum() + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        clientsDisconnectCheckThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(handlingActive) {
                    for(int i = 0; i < server.getClients().size(); i++) {
                        if(!server.getClients().get(i).getSocket().isConnected()) {
                            try {
                                server.getClients().get(i).getIn().close();

                                server.getClients().get(i).getOut().flush();
                                server.getClients().get(i).getOut().close();

                                server.getClients().get(i).getSocket().close();

                                System.out.println("Отключился игрок: " + server.getClients().get(i).getSocket().getInetAddress().getCanonicalHostName());

                                server.getClients().remove(i);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void StartHandling()
    {
        handlingActive = true;
        
        clientsWaitingThread.start();
        clientsDisconnectCheckThread.start();
    }

    public void StopHandling()
    {
        handlingActive = false;
    }
}
