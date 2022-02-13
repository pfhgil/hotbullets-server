package Client;

import General.ServerPacket;
import Main.Main;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import Server.UID;

// класс клиента
public class Client
{
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Thread inThread;

    private boolean packetsHandlingActive;

    private UID.ID id;

    private Object inPacket;

    public Client(Socket _socket)
    {
        socket = _socket;
        id = Main.server.getUid().GetFreeAndEmploy();

        try {
            out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
            out.writeObject(new ServerPacket("тест 1", id.getID()));
            out.flush();
            in = new ObjectInputStream(new DataInputStream(socket.getInputStream()));

            inThread = new Thread(() -> {
                while(true) {
                    if(packetsHandlingActive) {
                        /*
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                         */

                        if(in != null) {
                            try {
                                try {
                                    inPacket = in.readObject();
                                } catch (EOFException | ClassNotFoundException | SocketException e ) {
                                    e.printStackTrace();
                                    Disconnect();
                                    break;
                                }

                                if(inPacket == null) {
                                    Disconnect();
                                    break;
                                } else {
                                    for(Client client : Main.server.getClients()) {
                                        if(client != null && client.getOut() != null && client != this) {
                                            try {
                                                ServerPacket serverPacket = new ServerPacket(inPacket, id.getID());
                                                client.getOut().writeObject(serverPacket);
                                                client.getOut().flush();
                                            } catch (SocketException e) {
                                                Disconnect();
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartPacketsHandling()
    {
        packetsHandlingActive = true;

        inThread.start();
    }

    public void StopPacketsHandling()
    {
        packetsHandlingActive = false;
    }

    public void Disconnect()
    {
        try {
            StopPacketsHandling();

            System.out.println("Отключился игрок: " + socket.getInetAddress().getCanonicalHostName() + ". ID: " + id.getID());

            id.setEmployed(false);

            inThread.interrupt();
            in.close();

            out.flush();
            out.close();

            out = null;
            in = null;

            socket.close();
            socket = null;

            Main.server.getClients()[id.getID()] = null;
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public Socket getSocket() { return socket; }

    public ObjectOutputStream getOut() { return out; }

    public ObjectInputStream getIn() { return in; }

    public UID.ID getId() { return id; }
}
