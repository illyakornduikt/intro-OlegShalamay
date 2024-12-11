package entity;



// import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import main.KeyHandler;



// import java.awt.Graphics2D; 
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public int hasKeys = 0;


    public final int screenX;
    public final int screenY;
    

    public Player(GamePanel gp, main.KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.titleSize/2);
        screenY = gp.screenHeight/2 - (gp.titleSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        worldX = gp.titleSize * 23;
        worldY = gp.titleSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_up_1.png"));
            up2 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_up_2.png"));
            down1 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_down_1.png"));
            down2 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_down_2.png"));
            left1 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_left_1.png"));
            left2 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_left_2.png"));
            right1 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_right_1.png"));
            right2 = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
    public void update(){

        if (keyH.upPressed == true || keyH.downPressed == true || 
        keyH.leftPressed == true || keyH.rightPressed == true){

            if (keyH.upPressed == true){
                direction = "up";
                
            }
            else if (keyH.downPressed == true){
                direction = "down";
                
            }
            else if (keyH.leftPressed == true){
                direction = "left";
                
            }
            else if (keyH.rightPressed == true){
                direction = "right";
                
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);


            if (collisionOn == false){
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    
                }
            }
    
            spriteCounter++;
            if(spriteCounter > 10){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
    
        }


        
    }

    public void pickUpObject(int i ){
        if (i != 999){

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKeys++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("you got a key!");
                    break;

                case "Door":
                    
                    if (hasKeys > 0){
                        gp.playSE(4);
                        gp.obj[i] = null;
                        hasKeys--;
                        gp.ui.showMessage("you opened the door!");}
                    else{
                        gp.ui.showMessage("you need a key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(3);
                    speed += 8;
                    gp.obj[i] = null;
                    break;

                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
                // case "key":
                //     hasKeys++;
                //     gp.obj[i] = null;
                //     System.out.println("Key: " + hasKeys);
                //     break;
                    
                // default:
                //     break;
            }

        }}

    


    public void draw(Graphics2D g2){
        
        // g2.setColor(Color.white);

        // g2.fillRect(x, y, gp.titleSize, gp.titleSize);
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
    }
    
}
