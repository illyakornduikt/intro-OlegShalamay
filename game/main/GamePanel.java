package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int titleSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = titleSize * maxScreenCol;
    public final int screenHeight = titleSize * maxScreenRow;


    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    // public final int worldWidth = titleSize * maxWorldCol;
    // public final int worldHeight = titleSize * maxWorldRow;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music  = new Sound();
    Sound se  = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);

    Thread gameThread;


    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    

    int FPS = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // public void run() {

    //     double drawInterwal = 1000000000/FPS;
    //     double nextDrawTime = System.nanoTime() + drawInterwal;

    //     while (gameThread != null) {
    //         // System.out.println("The game loop is running!");
    //         update();

    //         repaint();

    //         // double remainingTime = nextDrawTime - System.nanoTime();

    //         try {
    //             double remainingTime = nextDrawTime - System.nanoTime();
    //             remainingTime = remainingTime/1000000;

    //             if (remainingTime<0){
    //                 remainingTime = 0;
    //             }

    //             Thread.sleep((long) remainingTime);
    //             nextDrawTime += drawInterwal;
    //         } catch (InterruptedException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //     }
    // }

    public void run(){
        double drawInterwal = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterwal;
            timer += (currentTime - lastTime);
            lastTime = currentTime;


            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update(){

        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics g2 = (Graphics2D)g;


        tileM.draw((Graphics2D) g2);

        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw((Graphics2D) g2, this);
            }
        }

        player.draw((Graphics2D) g2);

        ui.draw((Graphics2D) g2);

        g2.dispose();

    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
