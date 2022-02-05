package Server;

import Main.Settings;

import java.io.IOException;
import java.net.Socket;

public class ClientsHandler
{
    private Thread clientsWaitingThread;
    private Socket newClientSocket;

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
                if(handlingActive) {
                    try {
                        newClientSocket = server.getServerSocket().accept();

                        System.out.println("Подключился пользователь: " + newClientSocket.getInetAddress().getHostAddress());

                        server.getClients().add(newClientSocket);
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
