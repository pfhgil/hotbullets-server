package Server;

import java.io.Serializable;

public class ServerPacket implements Serializable
{
    private Object packet;

    private int playerID;

    public ServerPacket(Object _packet, int _playerID)
    {
        packet = _packet;

        playerID = _playerID;
    }

    public Object getPacket() { return packet; }
    public void setPacket(Object packet) { this.packet = packet; }

    public int getPlayerID() { return playerID; }
    public void setPlayerID(int playerID) { this.playerID = playerID; }
}