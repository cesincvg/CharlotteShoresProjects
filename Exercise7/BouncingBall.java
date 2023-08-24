import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;



public class BouncingBall extends JPanel {

    JButton reset = new JButton("Reset"),
            quit  = new JButton("Quit"),
            pointsButton = new JButton("Points"),
            hoopsButton = new JButton("Hoops");

    int xs[] = new int[4],
            ys[] = new int[4];
    private Ball b1;
    private Ball b2;
    private Ball b3;
    private Ball b4;
    private int delay=10;
    private double speed=10.0;
    private static int panelWidth=800;
    private static int panelHeight=800;
    private DancingLoop dancingloop;
    private boolean showBall=true;
    private boolean showSpline=true;

    public void toggleSpline(){
        this.showSpline=!this.showSpline;
    }

    public void toggleBall(){
        this.showBall=!this.showBall;
    }


    public static void main (String[]args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Bouncing Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(panelWidth, panelHeight);
        frame.setContentPane(new BouncingBall());
        frame.setVisible(true);
    }

    public BouncingBall() {
        // Set up the bouncing ball with random speeds and colors



        b1 = new Ball(panelWidth, panelHeight,
                (float) speed,
                (float) speed,
                new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

        b2 = new Ball(panelWidth, panelHeight,
                (float) (speed * Math.random()),
                (float) (speed * Math.random()),
                new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

        b3 = new Ball(panelWidth, panelHeight,
                (float) (speed * Math.random()),
                (float) (speed * Math.random()),
                new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

        b4 = new Ball(panelWidth, panelHeight,
                (float) (speed * Math.random()),
                (float) (speed * Math.random()),
                new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

        // Create a thread to update the animation and repaint
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    // Ask the ball to move itself and then repaint
                    b1.moveBall();
                    b2.moveBall();
                    b3.moveBall();
                    b4.moveBall();

                    repaint();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
   //     xs[0] = b1.getX();
   //     ys[0] = b1.getY();
    //    xs[1] = b2.getX();
   //     ys[1] = b2.getY();
    //    xs[2] = b3.getX();
    //    ys[2] = b3.getY();
    //    xs[3] = b4.getX();
     //   ys[3] = b4.getY();

        thread.start();


        JPanel controls = new JPanel();
        add(controls, BorderLayout.NORTH);


        /* quit button */
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        controls.add(quit);
        hoopsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                toggleSpline();

            }
        });
        controls.add(hoopsButton);
        pointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean animating = false;
                toggleBall();
            }
        });
        controls.add(pointsButton);

        // create a slider
        JSlider b = new JSlider(0, 1000, 0);
        b.setMajorTickSpacing(250);
        b.setMinorTickSpacing(100);

        b.setPaintTrack(true);
        b.setPaintTicks(true);
        b.setPaintLabels(true);


        JLabel l = new JLabel();

        // add slider to panel
        controls.add(b);
        controls.add(l);
        b.setMinimum(5);
        b.setMaximum(1000);
        l.setText("Value of Slider is = " + b.getValue());

        b.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                l.setText("value of Slider is =" + b.getValue());

                speed = b.getValue();

                b1.setDx((float) (speed * Math.random()));
                b1.setDy((float) (speed * Math.random()));

                b2.setDx((float) (speed * Math.random()));
                b2.setDy((float) (speed * Math.random()));

                b3.setDx((float) (speed * Math.random()));
                b3.setDy((float) (speed * Math.random()));

                b4.setDx((float) (speed * Math.random()));
                b4.setDy((float) (speed * Math.random()));


//
//                b1 = new Ball(panelWidth, panelHeight,
//                        (float) (speed * Math.random()),
//                        (float) (speed * Math.random()),
//                        new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
//
//                b2 = new Ball(panelWidth, panelHeight,
//                        (float) (speed * Math.random()),
//                        (float) (speed * Math.random()),
//                        new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
//
//                b3 = new Ball(panelWidth, panelHeight,
//                        (float) (speed * Math.random()),
//                        (float) (speed * Math.random()),
//                        new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
//
//                b4 = new Ball(panelWidth, panelHeight,
//                        (float) (speed * Math.random()),
//                        (float) (speed * Math.random()),
//                        new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
            }
        });

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the ball

        if(showBall) {
            b1.paintBall(g);
            b2.paintBall(g);
            b3.paintBall(g);
            b4.paintBall(g);
        }
        xs[0] = b1.getX();
        xs[1] = b2.getX();
        xs[2] = b3.getX();
        xs[3] = b4.getX();

        ys[0] = b1.getY();
        ys[1] = b2.getY();
        ys[2] = b3.getY();
        ys[3] = b4.getY();

        this.dancingloop = new DancingLoop(this.xs, this.ys);
        if(showSpline) {
            this.dancingloop.drawMe(g);
        }
    }
}
