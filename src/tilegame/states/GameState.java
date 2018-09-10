package tilegame.states;

import tilegame.gfx.Assets;

import java.awt.*;

public class GameState extends State {

    public GameState(){

    }

    public void tick(){

    }

    public void render(Graphics g) {
        g.drawImage(Assets.dirt,0,0,null);
    }
}
