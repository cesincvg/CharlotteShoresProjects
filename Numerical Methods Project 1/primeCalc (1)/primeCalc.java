import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

//created a GUI that enables the user to enter a number between 2 and 1,000,000 and the GUI
//will show if the number is prime or not. I experimented with a Pop Up browser for this assignment
public class primeCalc extends JFrame implements TextListener,ActionListener

{
    JLabel label1; //This label will display a text to enter a number
    TextField text1; //Will be where the user inputs the number
    JButton submit; //submit button once the user types in a number
    String numstring; //the "string" that the user enters
    int userEnterednum,ctr = 0,n;

    public primeCalc()
    {
        super(" Is That A Prime Number? "); //makes the title
        // setBackground(Color.CYAN);
        setLayout(new FlowLayout());   //
        label1 = new JLabel(" Enter A Number between 2 and 1,000,000: ");
        add(label1);
        text1 = new TextField(10); //the size of textField is = to 10
        add(text1); //text field is added to the GUI
        submit = new JButton(" Submit ");  //adds the submit button next to the JLabel
        add(submit); //submit is added to the GUI
        text1.addTextListener(this);
        submit.addActionListener(this); //adds an event listener object to the submit button
    }


    public void textValueChanged(TextEvent newEvent){ //when the user inputs a new text nu,ber
        if (newEvent.getSource() == text1) {
            numstring = text1.getText();
            userEnterednum = Integer.parseInt(numstring); //turn the string numstring into int variable num
        }
    }

    public void actionPerformed(ActionEvent ae ) {
        if(ae.getSource() == submit) { //when submit is clicked
            //The next lines of code are from SIEVEHISTOGRAM
            int n = 1000000;
            Boolean[] boolArray = new Boolean[n+1]; //create boolean array
            for (int i = 0; i <= n; i++){
                boolArray[i] = true; //assume everything is prime
            }

            int startingVal = 0;
            int primes = 0; // set number of primes equal to zero

            for (int i =2; i*i < n; i++){ //start loop at 2
                if (boolArray[i] == true) { //when the array is true (meaning we dont know if its prime or not
                    for (int j = i*i; j <= n; j+=i) {
                        boolArray[j] = false;
                    }
                }
            }

            // each of the statements below gives a pop out message depending on if the number entered by the user was prime, invalid, or not prime
            if (userEnterednum > 1000000 || userEnterednum < 2){
                JOptionPane.showMessageDialog(null,userEnterednum + " is an invalid number" ,"INVALID NUMBER",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if (boolArray[userEnterednum] ) {
                JOptionPane.showMessageDialog(null,userEnterednum + " is a prime number","Prime Number",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,userEnterednum + " is not a prime number" ,"Not a Prime Number",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
       System.exit(0); // after the user closes the message tab they will have to re-run the program to enter another number
    }

    public static void main (String args[])
    {
        primeCalc theGui = new primeCalc(); //creates new GUI
        theGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program ends once closed
        theGui.setSize(330,200);
        theGui.setLocation(400,350);
        theGui.setVisible(true); //make sure user can see the GUI
    }
}