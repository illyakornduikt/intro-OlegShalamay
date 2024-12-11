package tile;


import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/Users/drake/Desktop/javagame/res/maps/world01.txt");
    }
    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/water.png"));
            tile[2].collision = true;


            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/tiles/sand.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void loadMap(String filePath){
        try {
            // InputStream is = getClass().getResourceAsStream("res/maps/map01.txt");
            File is = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(is));
            System.out.println(is);
            System.out.println(br);
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                System.out.println(line);

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
            
        } catch (Exception e) {
            // TODO: handle exception

        }
    }
    public void draw(Graphics2D g2){
        int WorldCol = 0;
        int WorldRow = 0;
        


        while (WorldCol < gp.maxWorldCol && WorldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[WorldCol][WorldRow];

            int worldX = WorldCol * gp.titleSize;
            int worldY = WorldRow * gp.titleSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.titleSize> gp.player.worldX - gp.player.screenX && 
                worldX - gp.titleSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.titleSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.titleSize< gp.player.worldY + gp.player.screenY){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.titleSize, gp.titleSize, null);
                }



            WorldCol++;
            

            if (WorldCol == gp.maxWorldCol){
                WorldCol = 0;
                
                WorldRow++;
               
            }
            
        }
    }
    
}
