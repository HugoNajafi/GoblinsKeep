package objects;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends SuperObject{
    public Key() {
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("insertkey.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        collision = true;
    }
}
