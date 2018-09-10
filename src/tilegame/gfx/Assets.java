package tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 32;
    private static final int height = 32;
    public static BufferedImage grass, dirt, player, tree, stone;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        grass = sheet.crop(0,0,width,height);
        dirt = sheet.crop(0,(height*1)-1,width,height);
        player = sheet.crop(0,(height*2)-1,width,height);
        tree = sheet.crop(0,(height*3)-1,width,height);
        stone = sheet.crop(0,(height*4)-1,width,height);
    }
}
