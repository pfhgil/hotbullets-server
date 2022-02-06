package Server;

public class UID
{
    public static class ID
    {
        private boolean employed;
        private int ID;

        public ID(int _ID)
        {
            ID = _ID;
        }

        public boolean isEmployed() { return employed; }
        public void setEmployed(boolean employed) { this.employed = employed; }

        public int getID() { return ID; }
    }

    private int maxIDs;
    private ID[] IDs;

    public UID(int _maxIDs)
    {
        maxIDs = _maxIDs;

        Init();
    }

    private void Init()
    {
        IDs = new ID[maxIDs];

        for(int i = 0; i < IDs.length; i++) {
            IDs[i] = new ID(i);
        }
    }

    public ID GetFreeAndEmploy()
    {
        for(ID id : IDs) {
            if(!id.isEmployed()) {
                id.setEmployed(true);
                return id;
            }
        }

        return null;
    }
}
