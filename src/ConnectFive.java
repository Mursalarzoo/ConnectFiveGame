/*

This program is written by Mursal Hosseini - December 14th 2022

Requirements for the game:
- A game board consisting of 9 columns and 6 rows.
- Two players taking turns placing a piece by indicating a column.
- A number of pieces to be placed in the columns.
- A routine that automatically fills the two outer columns at the beginning of a game

Reference:
- https://stackoverflow.com/questions/35686362/how-to-create-a-board-game-in-java
- Chapters 10 & 11 -  A. Downey, C. Mayfield: Think Java - How to Think Like a Computer Scientist, 2nd Edition. Green Tea Press, 2020.
- Part of a sample solution to Exercises 14.1-14.2 - A. Downey, C. Mayfield: Think Java - How to Think Like a Computer Scientist, 2nd Edition. Green Tea Press, 2020.
- https://ssaurel.medium.com/creating-a-connect-four-game-in-java-f45356f1d6ba
- https://www.youtube.com/watch?v=gQb3dE-y1S4
- https://www.techiedelight.com/generate-random-character-java/
- https://www.codingame.com/playgrounds/38574/6x7-connect-4-multiplayer-and-singleplayer-with-special-moves
- https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm  (Check for winning method)
- https://www.baeldung.com/java-check-string-number

 */
import java.util.Arrays;
import java.util.Random;                                  // java.util = package which consists of a number of classes
import java.util.Scanner;                                 // allow the use of all classes in java.util = import only the Scanner class.

public class ConnectFive {                                 // Class (uppercase letter) - collection of methods
    public static void main(String[] args) {               // main function, which is the one which is called first when we run the program.
        //Welcoming print line
        System.out.println(" ");
        System.out.println(" Welcome to Connect Five Game");
        System.out.println(" ");
        System.out.println(" Start by choosing who is going to be player one and player two");

        //declaring the Scanner variable in and create a Scanner that reads input from System.in +
        Scanner in = new Scanner(System.in);            // Define a scanner of type scanner, Scanner instantiated with keyword 'new' + we give system.in as an input.
        System.out.println(" ");
        System.out.println(" Are you ready to start the game?");

        in.nextLine();                                  //Require some kind of input from the user before the Game starts.

        //Lots of defining here
        String playerInputString;                       //Define playerInputString of type string.
        int playerInput;
        boolean gameOver = false;                       // Type is declared with the Boolean keyword and can only take values/returns true or false.
        char playerType = 'o';                          //o is player one og x is player 2 + Defining player type og type char

        //2-Dimensional-array: represent a grid of cells, we specify number of rows and columns
        int rows = 6;                                   //define rows
        int cols = 9;                                   //define column
        char[][] array = new char[rows][cols];          //create 2-Dimensional-array of type char, instantiate and create empty (initially) rows and columns
        //Arrays are created (instantiated) by using the 'new' operator, specifying the size in square brackets


        for (int r = 0; r < rows; r++) {                //Create double for-loop, start by going through the rows and then columns at each row. Row 0 then 1 etc.
            for (int c = 0; c < cols; c++) {
                array[r][c] = '.';                      //Array=Game board and assign it to '.' (dots) which basically is empty places. Here the player can place x or o.
            }                                           // Go through all the elements in our array
        }

        //prefill the outer edges with random pieces
        array = prefillEdge(array, rows, 0);    //Calling a function called prefill edge (to gange), and assign it to array, 'array=' means that our function is returning an array.
        array = prefillEdge(array, rows, 8);    // Another input inside our function is the rows, and column index, due to we need to prefill two outer columns 0 and 8.


        drawBoard(array);                                   // show board - Here we draw the Game board, as the 'array' is giving as input.


        // GAME STARTS
        //While-loop as long as our condition is false, our while loop will continue + used to iterate a part of the program repeatedly until the condition is true.
        while (!gameOver) {                                 //while the game is not finish, continue the loop.
            System.out.println(" ");                        //Printing empty string

            if (playerType == 'o')                          //if sentence, so it will be clear which players turn it is.
                System.out.println("Player one select between 1-7 columns: ");
            else
                System.out.println("Player two select between 1-7 columns: ");

            playerInputString = in.nextLine();              //Here taken in an input and output it inside playerInputString

            if (!isNumeric(playerInputString)) {            //Call the function 'isNumeric', to check if the input we get from the player is a number. Function is on line 163.
                System.out.println(" Please enter a number between 1-7");       //if it is not a number, then we print the message and called the 'continue'.
                continue;       //Keyword means we start from the begging of the while loop.
            }

            playerInput = Integer.valueOf(playerInputString);   //convert String to number.

            // Check the player input for errors
            if (playerInput < 1 || playerInput > 7) {        //Check if the player input is below one or above 7 then the message will be printed out.
                System.out.println(" Please enter a number between 1-7");
                continue;
            }
                    // Reverse = The loop iterates the indexes in reverse order.
            boolean inserted = false;                        // create a boolean - start a the 9' index and move up.
            for (int i = rows - 1; i > -1; i--) {            // Reverse for-loop, start at the bottom of the board and go up. Check if the space the player has put their piece is empty, if not moving to the next row.
                if (array[i][playerInput] == '.') {             // if it is a '.' that means it is an empty space,
                    array[i][playerInput] = playerType;         // playerType will be inserted (x or o).
                    inserted = true;                            // assign inserted value to true
                    break;                                      // then break (go out of) the for loop
                }
            }



            // check the whole board, see if there are connect 5 - winning condition - iteration

            // Vertical check - double for loop - initializing a variable, condition that depends on the variable, update the variable
            // initializer runs once - condition is checked each time through the loop, false=loop ends - update og back to step 2.
            int count = 0;                                 //create a counter = ligesom en tæller/timer
            for (int r = 0; r < rows; r++) {               //Going through each row and inside each row going through each column. r=index 0 first.
                for (int c = 0; c < cols; c++) {
                    if (array[r][c] == playerType)          //checking if the spaces in rows and columns are the same as player type=the last piece our player placed. fx if there is a 'o' we count how many 'o's is next to each other.
                        count++;                            // increase a value each time the loop has been executed.
                    else
                        count = 0;                          //if o's is not next to each other the counter is reset.

                    if (count >= 5) {                       //if we have 5 pieces next to each other, then Game over is true, the program will stop, and we break our first for-loop.
                        gameOver = true;
                        break;
                    }
                }

                if (gameOver)                               //if we have a winning condition, we will break our second for-loop.
                    break;
            }

            //Horizontal check
            count = 0;
            for (int r = 0; r < rows; r++) {                //Here we don't need a double for-loop because we know the column index and therefor only loop through the rows.
                if (array[r][playerInput] == playerType)
                    count++;
                else
                    count = 0;

                if (count >= 5) {
                    gameOver = true;
                    break;
                }

            }

            drawBoard(array);                               //Draw out board


            if (gameOver)                                   //check for winning condition, if there is a winning condition then we break our main while loop.
                break;

            // If no winning - check if switch is happening between players
            if (inserted && playerType == 'o') {            //checking if player has inserted the piece and if the player type is o.
                playerType = 'x';                           //Switching to the next player which is x.
            } else if (inserted) {                          //if the player type is x, and we switch back to o.
                playerType = 'o';
            }
        }


        if (gameOver) {                                     //if gamerOver is true, then we print out the winner.
            if (playerType == 'o')
                System.out.println("Congrats player one has won!");
            else
                System.out.println("Congrats player two has won!");
        }
    }
            //public=it can be used in other classes.
    public static boolean isNumeric(String strNum) {        // Reference link above - Code from the net. strNum = variable name.
        try {                                               // Try & catch: trying to part the playerInputString to double if that fails, we know we did not get a decimal number from the user.
            double d = Double.parseDouble(strNum);          // if that fails, we catch the error and return false.
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;                                         //If try&catch does not fail then return true.
    }                                                        //Try&catch make sure that the program does not crash.


    //This function returns a 2-dimension char, then takes a char of 2-dimensional input, then two int with rows and columnIndex
    private static char[][] prefillEdge(char[][] array, int rows, int columnIndex) {
         for (int i=0; i < rows; i++){                      // Going through all the rows in our array
             // generate a random piece
             String alphabet = "ox";                        //on each iteration, in our for-loop, we create an alphabet which contain two character
             Random r = new Random();                       //The 'new' operator create a Random generator
             char randomPiece = alphabet.charAt(r.nextInt(alphabet.length()));          //The random will help us choose a random character, which in our case is o and x. Then characters will be placed in the row and correct column.

             array[i][columnIndex] = randomPiece;           //As we see here, it places random piece in all the columns (0 and 8)
         }

         return array;                                      //When we are finished with assigning random pieces, it returns the array.
    }


    //This function does not return anything = void. It takes an array of two-dimensional type char as an input
    private static void drawBoard(char[][] array){
        int rows = 6;
        int cols = 9;

        System.out.println(" ");
        String[] gameBoard = {"","","1", "2", "3", "4", "5", "6", "7", "", ""};     //Create the columns, so it is visible for the user.
        for (int r = 0; r < gameBoard.length; r++) {                                // Going through our label (gameBoard) via for loop , length=size of array
            System.out.print(gameBoard[r]);                                         // print them with spacing
            System.out.print(' ');                                                  // spacing
        }
            System.out.println(" ");

        for (int r = 0; r < rows; r++) {                      //Creating a double for-loop and go through the rows and columns
            for (int c = 0; c < cols; c++) {
                System.out.print(array[r][c]);
                System.out.print(' ');                        //At each row we print our all the columns with spacing
            }

            System.out.println();                             //If we don't print line, all the columns will be next to each other.
        }
    }
}

