import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class cardshuffle extends JFrame implements ActionListener {

    // array of Icons
    private ImageIcon spades[];
    private ImageIcon hearts[];
    private ImageIcon clubs[];
    private ImageIcon diamonds[];


    // array of Buttons
    private JButton button[];
    private JButton quitButton;
    private JButton shuffleButton;
    private JButton resetButton;
    private JButton[][] newArray = new JButton[4][13]; //this will create the grid layout

    // layout objects:  the container, and panel within the container
    private Container c;
    private JPanel imageBoard;
    private JPanel buttonPanel1;

    public cardshuffle() {
        super("Shuffle the Cards");

        // Allocate a panels to hold a button
        imageBoard = new JPanel();        //used to hold the button
        imageBoard.setLayout(new GridLayout(4,13));


        // get the content pane, onto which everything is eventually added
        c = getContentPane();


        //we are now going to set the images to each suit array
        spades = new ImageIcon[13];
        spades[0] = new ImageIcon("ace_of_spades_icon.png");
        spades[0].setDescription("ace_of_spades_icon.png");
        spades[1] = new ImageIcon("2_of_spades_icon.png");
        spades[1].setDescription("2_of_spades_icon.png");
        spades[2] = new ImageIcon("3_of_spades_icon.png");
        spades[2].setDescription("3_of_spades_icon.png");
        spades[3] = new ImageIcon("4_of_spades_icon.png");
        spades[3].setDescription("4_of_spades_icon.png");
        spades[4] = new ImageIcon("5_of_spades_icon.png");
        spades[4].setDescription("5_of_spades_icon.png");
        spades[5] = new ImageIcon("6_of_spades_icon.png");
        spades[5].setDescription("6_of_spades_icon.png");
        spades[6] = new ImageIcon("7_of_spades_icon.png");
        spades[6].setDescription("7_of_spades_icon.png");
        spades[7] = new ImageIcon("8_of_spades_icon.png");
        spades[7].setDescription("8_of_spades_icon.png");
        spades[8] = new ImageIcon("9_of_spades_icon.png");
        spades[8].setDescription("9_of_spades_icon.png");
        spades[9] = new ImageIcon("10_of_spades_icon.png");
        spades[9].setDescription("10_of_spades_icon.png");
        spades[10] = new ImageIcon("jack_of_spades_icon.png");
        spades[10].setDescription("jack_of_spades_icon.png");
        spades[11] = new ImageIcon("queen_of_spades_icon.png");
        spades[11].setDescription("queen_of_spades_icon.png");
        spades[12] = new ImageIcon("king_of_spades_icon.png");
        spades[12].setDescription("king_of_spades_icon.png");

        hearts = new ImageIcon[13];
        hearts[0] = new ImageIcon("ace_of_hearts_icon.png");
        hearts[0].setDescription("ace_of_hearts_icon.png");
        hearts[1] = new ImageIcon("2_of_hearts_icon.png");
        hearts[1].setDescription("2_of_hearts_icon.png");
        hearts[2] = new ImageIcon("3_of_hearts_icon.png");
        hearts[2].setDescription("3_of_hearts_icon.png");
        hearts[3] = new ImageIcon("4_of_hearts_icon.png");
        hearts[3].setDescription("4_of_hearts_icon.png");
        hearts[4] = new ImageIcon("5_of_hearts_icon.png");
        hearts[4].setDescription("5_of_hearts_icon.png");
        hearts[5] = new ImageIcon("6_of_hearts_icon.png");
        hearts[5].setDescription("6_of_hearts_icon.png");
        hearts[6] = new ImageIcon("7_of_hearts_icon.png");
        hearts[6].setDescription("7_of_hearts_icon.png");
        hearts[7] = new ImageIcon("8_of_hearts_icon.png");
        hearts[7].setDescription("8_of_hearts_icon.png");
        hearts[8] = new ImageIcon("9_of_hearts_icon.png");
        hearts[8].setDescription("9_of_hearts_icon.png");
        hearts[9] = new ImageIcon("10_of_hearts_icon.png");
        hearts[9].setDescription("10_of_hearts_icon.png");
        hearts[10] = new ImageIcon("jack_of_hearts_icon.png");
        hearts[10].setDescription("jack_of_hearts_icon.png");
        hearts[11] = new ImageIcon("queen_of_hearts_icon.png");
        hearts[11].setDescription("queen_of_hearts_icon.png");
        hearts[12] = new ImageIcon("king_of_hearts_icon.png");
        hearts[12].setDescription("king_of_hearts_icon.png");

        clubs = new ImageIcon[13];
        clubs[0] = new ImageIcon("ace_of_clubs_icon.png");
        clubs[0].setDescription("ace_of_clubs_icon.png");
        clubs[1] = new ImageIcon("2_of_clubs_icon.png");
        clubs[1].setDescription("2_of_clubs_icon.png");
        clubs[2] = new ImageIcon("3_of_clubs_icon.png");
        clubs[2].setDescription("3_of_clubs_icon.png");
        clubs[3] = new ImageIcon("4_of_clubs_icon.png");
        clubs[3].setDescription("4_of_clubs_icon.png");
        clubs[4] = new ImageIcon("5_of_clubs_icon.png");
        clubs[4].setDescription("5_of_clubs_icon.png");
        clubs[5] = new ImageIcon("6_of_clubs_icon.png");
        clubs[5].setDescription("6_of_clubs_icon.png");
        clubs[6] = new ImageIcon("7_of_clubs_icon.png");
        clubs[6].setDescription("7_of_clubs_icon.png");
        clubs[7] = new ImageIcon("8_of_clubs_icon.png");
        clubs[7].setDescription("8_of_clubs_icon.png");
        clubs[8] = new ImageIcon("9_of_clubs_icon.png");
        clubs[8].setDescription("9_of_clubs_icon.png");
        clubs[9] = new ImageIcon("10_of_clubs_icon.png");
        clubs[9].setDescription("10_of_clubs_icon.png");
        clubs[10] = new ImageIcon("jack_of_clubs_icon.png");
        clubs[10].setDescription("jack_of_clubs_icon.png");
        clubs[11] = new ImageIcon("queen_of_clubs_icon.png");
        clubs[11].setDescription("queen_of_clubs_icon.png");
        clubs[12] = new ImageIcon("king_of_clubs_icon.png");
        clubs[12].setDescription("king_of_clubs_icon.png");

        diamonds = new ImageIcon[13];
        diamonds[0] = new ImageIcon("ace_of_diamonds_icon.png");
        diamonds[0].setDescription("ace_of_diamonds_icon.png");
        diamonds[1] = new ImageIcon("2_of_diamonds_icon.png");
        diamonds[1].setDescription("2_of_diamonds_icon.png");
        diamonds[2] = new ImageIcon("3_of_diamonds_icon.png");
        diamonds[2].setDescription("3_of_diamonds_icon.png");
        diamonds[3] = new ImageIcon("4_of_diamonds_icon.png");
        diamonds[3].setDescription("4_of_diamonds_icon.png");
        diamonds[4] = new ImageIcon("5_of_diamonds_icon.png");
        diamonds[4].setDescription("5_of_diamonds_icon.png");
        diamonds[5] = new ImageIcon("6_of_diamonds_icon.png");
        diamonds[5].setDescription("6_of_diamonds_icon.png");
        diamonds[6] = new ImageIcon("7_of_diamonds_icon.png");
        diamonds[6].setDescription("7_of_diamonds_icon.png");
        diamonds[7] = new ImageIcon("8_of_diamonds_icon.png");
        diamonds[7].setDescription("8_of_diamonds_icon.png");
        diamonds[8] = new ImageIcon("9_of_diamonds_icon.png");
        diamonds[8].setDescription("9_of_diamonds_icon.png");
        diamonds[9] = new ImageIcon("10_of_diamonds_icon.png");
        diamonds[9].setDescription("10_of_diamonds_icon.png");
        diamonds[10] = new ImageIcon("jack_of_diamonds_icon.png");
        diamonds[10].setDescription("jack_of_diamonds_icon.png");
        diamonds[11] = new ImageIcon("queen_of_diamonds_icon.png");
        diamonds[11].setDescription("queen_of_diamonds_icon.png");
        diamonds[12] = new ImageIcon("king_of_diamonds_icon.png");
        diamonds[12].setDescription("king_of_diamonds_icon.png");

        for(int i =0; i < 13; i++ ){
            newArray[0][i] = new JButton();
            newArray[1][i] = new JButton();
            newArray[2][i] = new JButton();
            newArray[3][i] = new JButton();


        }
        for(int i =0; i < 13; i++ ){
            newArray[0][i].setIcon(diamonds[i]);
            newArray[1][i].setIcon(clubs[i]);
            newArray[2][i].setIcon(hearts[i]);
            newArray[3][i].setIcon(spades[i]);


        }

        resetIcons();

        for (int i = 0; i <4; i++){
            for (int j = 0; j <13; j++){
                imageBoard.add(newArray[i][j]);
            }
        }

        //instantiate and set up the buttons in the panel



        quitButton = new JButton("Quit");
        shuffleButton = new JButton("Shuffle");
        resetButton = new JButton("Reset");
        quitButton.addActionListener(this);
        shuffleButton.addActionListener(this);
        resetButton.addActionListener(this);

        buttonPanel1 = new JPanel();
        buttonPanel1.add(shuffleButton);
        buttonPanel1.add(resetButton);
        buttonPanel1.add(quitButton);


        setSize(1200, 1000);


        Container c = getContentPane();
        c.add(imageBoard, BorderLayout.NORTH);
        c.add(buttonPanel1, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }



    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == resetButton){
            System.out.println("Reset Event");
            resetIcons();
        }
        else if (e.getSource() == quitButton){
            System.out.println("Quit Event");
            System.exit(0);
        }
        else if (e.getSource() == shuffleButton){
            System.out.println("Shuffle Event");
            shuffleIcons();
        }
    }

    private void shuffleIcons() {
        ArrayList <ImageIcon> iconCandidates = new ArrayList <ImageIcon> ();
        boolean array[] = new boolean[13];
        for (int i =0; i < 13; i ++){

        }
        for (int j=0; j <13; j++){
            iconCandidates.add((clubs[j]));
            iconCandidates.add((diamonds[j]));
            iconCandidates.add((hearts[j]));
            iconCandidates.add((spades[j]));
        }
        for (int i = 0; i < 4; i++){
            for (int j =0; j<13; j++){
                int choice = (int) ((Math.random() * iconCandidates.size()));

                newArray[i][j].setIcon(iconCandidates.get(choice));
                iconCandidates.remove(choice);
            }
        }
    }

    private void resetIcons() {
        for(int i =0; i < 13; i++ ){
            newArray[0][i].setIcon(diamonds[i]);
            newArray[1][i].setIcon(clubs[i]);
            newArray[2][i].setIcon(hearts[i]);
            newArray[3][i].setIcon(spades[i]);
        }
        for (int i = 0; i <4; i++){
            for (int j = 0; j <13; j++){
                imageBoard.add(newArray[i][j]);
            }
        }

    }

    public static void main(String args[])
    {
        cardshuffle M = new cardshuffle();
        M.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e)
                    {
                        System.exit(0);
                    }
                }
        );
    }
}
