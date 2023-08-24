package com.zetcode; //it said I needed this when I made the private class Board
//I looked it up online, and it is used when you want to name two classes the same
//https://www.geeksforgeeks.org/packages-in-java/


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Board extends JPanel implements ActionListener {
    private Container c;

    private JMenu menu, submenu;
    private JMenuItem i1, i2, i3, i4, i5;

    private final int number_images = 13;
    private final int size_of_cell = 15;

    private final int cell_cover = 10;
    private final int mark_for_the_cell = 10;
    private  int empty_cell = 0;
    private  int cell_is_mine = 9;
    private  int covered_cell_is_mine = cell_is_mine + cell_cover;
    private  int marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;

    private int draw_the_mine = 9;
    private int draw_the_cover = 10;
    private int draw_the_mark = 11;
    private int draw_the_wrong_mark = 12;

    private int number_mines = 10;
    private int num_rows = 16;
    private int num_cols = 16;

    private int width_of_board = num_cols * size_of_cell + 350;
    private int height_of_board = num_rows * size_of_cell + 6;

    private int[] cell_field;
    private boolean inGame;
    private int minesLeft;
    private Image[] image;

    private int allCells;
    private  JLabel statusbar; // will let the user know how many_value flags are left



    private JButton button[];
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton customButton;
    private JButton resetButton;
    private JPanel buttonPanel1;

    private JTextField customRowText;
    private JTextField customColText;
    private JTextField customMinesText;

    public Board(JLabel statusbar, JButton easyButton, JButton mediumButton, JButton hardButton, JButton customButton) {

        this.statusbar = statusbar; //set up the status bar for which the number of flags left will be shown

        initBoard();
        //creates new buttons
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Expert");
        customButton = new JButton("Custom");
        resetButton = new JButton("Reset");

        //creates new text fields
        customRowText = new JTextField("# Row: ");
        customColText = new JTextField("# Col: ");
        customMinesText = new JTextField("# Mines: ");


        //allocate a panel which will be used to hold the button
        buttonPanel1 = new JPanel(new GridLayout(7,0)); //the 7 buttons/text field will eache be under one another

        //creates location for buttonPanel and sets background color to gray
        add(buttonPanel1, BorderLayout.SOUTH);
        buttonPanel1.setBackground(Color.lightGray);

        //the code below sets an action listener for each button and JTextField
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Easy_value Game");
                num_cols = 9;
                num_rows = 9;
                number_mines = 10;
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;

                initBoard();
                repaint();


            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Medium Game");
                num_cols = 16;
                num_rows = 16;
                number_mines = 40;
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                initBoard();
                repaint();

            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hard Game");
                num_cols = 24;
                num_rows = 20;
                number_mines = 99;
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                setPreferredSize(new Dimension(width_of_board +500, height_of_board));
                initBoard();
                repaint();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset Game");
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                setPreferredSize(new Dimension(width_of_board +500, height_of_board));
                repaint();
                initBoard();
                create_New_Game();
            }
        });

        customRowText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom Row Text");
                System.out.println("Text=" + customRowText.getText());
                num_rows = Integer.parseInt(customRowText.getText().split(":")[1].strip());
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                repaint();
                initBoard();
            }
        });

        customColText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom Col Text");
                System.out.println("Text=" + customColText.getText());
                num_cols = Integer.parseInt(customColText.getText().split(":")[1].strip());
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                repaint();
                initBoard();
            }
        });

        customMinesText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom Col Text");
                System.out.println("Text=" + customColText.getText());
                number_mines = Integer.parseInt(customColText.getText().split(":")[1].strip());
                covered_cell_is_mine = cell_is_mine + cell_cover;
                marked_cell_is_mine = covered_cell_is_mine + mark_for_the_cell;
                repaint();
                initBoard();
            }
        });
        //adds each of the buttons and Jtext to the panel
        buttonPanel1.add(easyButton);
        buttonPanel1.add(mediumButton);
        buttonPanel1.add(hardButton);
        buttonPanel1.add(resetButton);
        buttonPanel1.add(customRowText);
        buttonPanel1.add(customColText);
        buttonPanel1.add(customMinesText);

        //makes sure everything is visible
        setVisible(true);


    }

    private void initBoard() { //every_value time a button is clicked it will instatiate the board again


        setPreferredSize(new Dimension(width_of_board +350, height_of_board + 75)); //creates the width and height of the board

        image = new Image[number_images];  //inputs the images

        for (int i = 0; i < number_images; i++) { //this for loop inputs the images so they can be seen

            var path =  i + ".png";
            image[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter()); //adds a mouse listener
        create_New_Game();
    }

    private void create_New_Game() {

        int cell; //creates a cell int

        var random = new Random(); //creating a random variable called VAR
        inGame = true;
        minesLeft = number_mines;

        allCells = num_rows  * num_cols; //total number of cells based
        cell_field = new int[allCells];  //array based on number of total cells

        for (int i = 0; i < allCells; i++) {

            cell_field[i] = cell_cover;  //cover all of the cells


        }


        statusbar.setText(Integer.toString(minesLeft)); // this will show how many_value mines are left during the game

        int i = 0;

        while (i < number_mines) { //while we don't have all the mines set up

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)  && (cell_field[position] != covered_cell_is_mine)) {  //if the position is not a mine, and position is less than total amount of cells

                int current_col = position % num_cols; //what column we are in currently
                cell_field[position] = covered_cell_is_mine; //this is where the mine will be placed in the cell field
                i++;

                //the next if statements look at the specific cell in cell_field (if it is not a covered mine, and add one to the value)
                //By adding one it is able to determine what the number image will be on the grid displayed to the user
                if (current_col > 0) {  //if our current column is not zero
                    cell = position - 1 - num_cols;  //the cell value (which we look at in the cell_field) is equal to its position subtracted by the number of columns and 1
                    if (cell >= 0) { //the cells value is the number of bombs that it touches
                        if (cell_field[cell] != covered_cell_is_mine) {  //if the array value is not a covered mine, if it was a mine it would not need a number
                            cell_field[cell] += 1; //add one to the cell_field
                        }
                    }
                  cell = position - 1; // find a new position of the cell by subtracting one
                    if (cell >= 0) {
                        if (cell_field[cell] != covered_cell_is_mine) { //if not a covered cell
                            cell_field[cell] += 1; //add one to the cell field (will increase the number of mines that the cell touches
                        }
                    }

                    cell = position + num_cols - 1;
                    if (cell < allCells) {//the cell value (which we look at in the cell_field) is equal to its position subtracted by the number of columns and 1
                        if (cell_field[cell] != covered_cell_is_mine) {
                            cell_field[cell] += 1;
                        }
                    }
                }

                cell = position - num_cols;
                if (cell >= 0) { //cell is a real value
                    if (cell_field[cell] != covered_cell_is_mine) { //not a covered mine
                        cell_field[cell] += 1; // add 1
                    }
                }

                cell = position + num_cols;
                if (cell < allCells) {
                    if (cell_field[cell] != covered_cell_is_mine) { // cell_field of cell is not a covered mine
                        cell_field[cell] += 1; // add 1
                    }
                }
                //similiar arithmatics occur below in order to determine what number will be printed at each spot of the grid
                if (current_col < (num_cols - 1)) {
                    cell = position - num_cols + 1;
                    if (cell >= 0) {
                        if (cell_field[cell] != covered_cell_is_mine) {
                            cell_field[cell] += 1;
                        }
                    }
                    cell = position + num_cols + 1;
                    if (cell < allCells) {
                        if (cell_field[cell] != covered_cell_is_mine) {
                            cell_field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (cell_field[cell] != covered_cell_is_mine) {
                            cell_field[cell] += 1;
                        }
                    }
                }
                System.out.println("Boooooooooooooo. inGame:"+inGame);
            }else{
                System.out.println("Hello World. inGame:"+inGame);
            }
        }


    }

    private void find_empty_cells(int j) {
        //this is the method that finds what cells are empty
        //empty cells are found by considering that the bordering 8 cells are all empty
        //this means that no number or mine is in any of the surrounding cells
        int current_col = j % num_cols;
        int cell;

        if (current_col > 0) {
            cell = j - num_cols - 1;
            if (cell >= 0) {
                if (cell_field[cell] > cell_is_mine) {
                    cell_field[cell] -= cell_cover; //subtracting the cell cover to get new image
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell); //we repeat the process to see if there are other empty cells surrounding the new one found
                    }
                }
            }
            //similiar arithmatic occurs below in order to see what cells do not have any value
            //there will be 8 different functions because that is how many surrounding squares there are from the original cell
            cell = j - 1; //we are subtracting one in order to move to a new surrounding cell of the original
            if (cell >= 0) {
                if (cell_field[cell] > cell_is_mine) { //this shows the cell chosen is not a mine
                    cell_field[cell] -= cell_cover; //
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + num_cols - 1; //move to new surrounding cell
            if (cell < allCells) {
                if (cell_field[cell] > cell_is_mine) {
                    cell_field[cell] -= cell_cover;
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - num_cols;
        if (cell >= 0) {
            if (cell_field[cell] > cell_is_mine) {
                cell_field[cell] -= cell_cover;
                if (cell_field[cell] == empty_cell) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + num_cols;
        if (cell < allCells) {
            if (cell_field[cell] > cell_is_mine) {
                cell_field[cell] -= cell_cover;
                if (cell_field[cell] == empty_cell) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (num_cols - 1)) {
            cell = j - num_cols + 1;
            if (cell >= 0) {
                if (cell_field[cell] > cell_is_mine) {
                    cell_field[cell] -= cell_cover;
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + num_cols + 1;
            if (cell < allCells) {
                if (cell_field[cell] > cell_is_mine) {
                    cell_field[cell] -= cell_cover;
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (cell_field[cell] > cell_is_mine) {
                    cell_field[cell] -= cell_cover;
                    if (cell_field[cell] == empty_cell) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        //this function is able to make the images appear on the screen for the user to see
        int uncover = 0;

        for (int i = 0; i < num_rows; i++) {

            for (int j = 0; j < num_cols; j++) {
                //these two for loops will go through each cell

                int cell = cell_field[(i * num_cols) + j];

                if (inGame && cell == cell_is_mine) { //if the cell is a mine then the game is over

                    inGame = false;
                }

                if (!inGame) { //when The game ends

                    if (cell == covered_cell_is_mine) {
                        cell = draw_the_mine;  //make all of the covered mines visible
                    } else if (cell == marked_cell_is_mine) {
                        cell = draw_the_mark; //if you flag somewhere that is not a mine it will show a crossed of flag
                    } else if (cell > covered_cell_is_mine) {
                        cell = draw_the_wrong_mark; //will show an image that is bomb
                    } else if (cell > cell_is_mine) {
                        cell = draw_the_cover;
                    }

                }
                else {
                    if (cell > covered_cell_is_mine) { //if cell is greater than the covered mine cell
                        cell = draw_the_mark;  //redraw
                    } else if (cell > cell_is_mine) {
                        cell = draw_the_cover; //will make the cover image appear again when you thought there was a flag but now dont
                        uncover++; //add one to the number of mines uncovered
                    }
                }
                g.drawImage(image[cell], (j * size_of_cell),
                        (i * size_of_cell), this);
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            statusbar.setText("Game won");

        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }

    public void actionPerformed(ActionEvent e) {
            //unused action listener
    }

    private class MinesAdapter extends MouseAdapter {
        //this function is able to know where the mouse was clicked on the cells
        @Override
        public void mousePressed(MouseEvent e) {

            int x_value = e.getX(); //gets the x calue of mouse event
            int y_value = e.getY(); // gets the y value of the mouse event

            int cCol = x_value / size_of_cell; //determine was column the mouse event was in
            int cRow = y_value / size_of_cell; //determines was row the column was in

            boolean doRepaint = false;

            if (!inGame) {



//                create_New_Game(); this is uneeded
                repaint();
            }

            if ((x_value < (num_cols * size_of_cell)) && (y_value < (num_rows * size_of_cell))) {

                if (e.getButton() == MouseEvent.BUTTON3) {   //I found online that when a right click is performed this is considered Button3 https://stackoverflow.com/questions/20990463/right-click-mouse-event

                    if (cell_field[(cRow * num_cols) + cCol] > cell_is_mine) {

                        doRepaint = true; //repaint

                        if (cell_field[(cRow * num_cols) + cCol] <= covered_cell_is_mine) {

                            if (minesLeft > 0) { //user has mines lefT
                                cell_field[(cRow * num_cols) + cCol] += mark_for_the_cell;
                                minesLeft--; //subtarct the mines left once a flag has been clicked
                                String message = Integer.toString(minesLeft);
                                statusbar.setText(message); //change the message to how many mines are left
                            } else {
                                statusbar.setText("No marks left"); //if we have got rid of all the mines this means that all flags have been placed
                            }
                        } else {

                            cell_field[(cRow * num_cols) + cCol] -= mark_for_the_cell; //if the flag is clicked again
                            minesLeft++; //must increase number of mines left
                            String message = Integer.toString(minesLeft); //print out new mines if left in the status bar
                            statusbar.setText(message);
                        }
                    }

                } else {


                    if ((cell_field[(cRow * num_cols) + cCol] > cell_is_mine) && (cell_field[(cRow * num_cols) + cCol] < marked_cell_is_mine)) {
                        cell_field[(cRow * num_cols) + cCol] -= cell_cover;
                        doRepaint = true;

                        if (cell_field[(cRow * num_cols) + cCol] == cell_is_mine) {
                            inGame = false; //if user clicks on a mine Game Over
                        }

                        if (cell_field[(cRow * num_cols) + cCol] == empty_cell) {
                            find_empty_cells((cRow * num_cols) + cCol); //if user clicks on an empty cell call empty cell function again to make sure the empty cell
                            // is not surrounded by empty cells because most likely it will be
                        }
                    }
                    if (cell_field[(cRow * num_cols) + cCol] > covered_cell_is_mine) {
                        return; //if the number on the cell clicked is not a mine then the user is free to continue
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
