

public class Game implements Runnable{

    private Display display;
    private Thread thread;

    private boolean running = false;
    public String title;
    public int width, height;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void initiate(){
        display = new Display(title, width, height);
    }

    private void tick(){

    }

    private void render(){

    }

    public void run(){
        initiate();
        while(running){
            tick();
            render();
        }
        stop();
    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop() {
        if(!running){
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
