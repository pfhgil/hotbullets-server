package Server;

public class PacketsHandler
{
    private Thread inThread;
    private Thread outThread;

    private boolean inHandlingActive;
    private boolean outHandlingActive;

    public PacketsHandler(Server server)
    {
        inThread = new Thread(() -> {
            while(true) {
                if(inHandlingActive) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        outThread = new Thread(() -> {
            while(true) {
                if(outHandlingActive) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void StartInHandling()
    {
        inHandlingActive = true;
        inThread.start();
    }

    public void StopInHandling()
    {
        inHandlingActive = false;
    }

    public void StartOutHandling()
    {
        outHandlingActive = true;
        outThread.start();
    }

    public void StopOutHandling()
    {
        outHandlingActive = false;
    }
}
