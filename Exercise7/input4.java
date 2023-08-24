import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class input4 {

    public  static void main(String args[]){
        int xs[] = new int[4],
                ys[] = new int[4];
        xs[0] = 50;
        ys[0] = 225;
        xs[1] = 150;
        ys[1] = 125;
        xs[2] = 250;
        ys[2] = 125;
        xs[3] = 350;
        ys[3] = 225;
        new DancingLoop(xs,ys);

    }
}
