package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    public OBJ_Boots(){
        name = "Boots";
        try {
            image = ImageIO.read(new File("/Users/drake/Desktop/javagame/res/objects/boots.png"));

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
