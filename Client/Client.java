package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// класс клиента
public class Client
{
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(Socket _socket)
    {
        socket = _socket;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() { return socket; }

    public ObjectOutputStream getOut() { return out; }

    public ObjectInputStream getIn() { return in; }
}
