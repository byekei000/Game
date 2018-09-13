package tilegame;

import tilegame.display.Display;
import tilegame.gfx.Assets;
import tilegame.input.KeyManager;
import tilegame.states.GameState;
import tilegame.states.MenuState;
import tilegame.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private Display display;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;

    private boolean running = false;
    public String title;
    public int width, height;

    //States
    private State gameState;
    private State menuState;

    //Input
    private KeyManager keyManager;

    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
    }

    private void initiate(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    private void tick(){
        keyManager.tick();

        if(State.getState() != null)
            State.getState().tick();
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0,0,width,height);
        //draw here
        if(State.getState() != null){
            State.getState().render(g);
        }
        //
        bs.show();
        g.dispose();
    }

    public void run(){
        initiate();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public KeyManager getKeyManager(){
        return keyManager;
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
