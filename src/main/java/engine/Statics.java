package engine;

import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Created by MajronMan on 06.01.2017.
 */
public class Statics {
    private static Image sword;
    private static Image rat;
    private static Image squid;
    private static Image potion;

    public static Image swordImage(){
        if (sword == null) {
            String name="sword.jpg";
            URL url = getSystemResource(name);
            try {
                sword = new Image(url.openStream());
            } catch (IOException e) {
                System.out.println("No such image: "+ name);
            }
        }
        return sword;
    }

    public static Image ratImage(){
        if (rat == null) {
            String name="enemy.jpg";
            URL url = getSystemResource(name);
            try {
                rat = new Image(url.openStream());
            } catch (IOException e) {
                System.out.println("No such image: "+ name);
            }
        }
        return rat;
    }

    public static Image squidImage(){
        if (squid == null) {
            String name="enemy2.jpg";
            URL url = getSystemResource(name);
            try {
                squid = new Image(url.openStream());
            } catch (IOException e) {
                System.out.println("No such image: "+ name);
            }
        }
        return squid;
    }
    public static Image potionImage(){
        if (potion == null) {
            String name="potion.jpg";
            URL url = getSystemResource(name);
            try {
                potion = new Image(url.openStream());
            } catch (IOException e) {
                System.out.println("No such image: "+ name);
            }
        }
        return potion;
    }
}
