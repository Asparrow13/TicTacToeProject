
package dk.easv.tictactoe.bll;

/**
 *
 * @author EASV
 */
public class GameBoard implements IGameBoard
{
    int [][] board= new int[3][3];

    /**
     * Returns 0 for player 0, 1 for player 1.
     * Returns int ID of the next player.
     */
        private int currentPlayer=0;

    public int getNextPlayer()
    {

        if (currentPlayer==1){
            currentPlayer=2;
        }
        else {
            currentPlayer = 1;
        }
       return currentPlayer;
    }

    /**
     * Attempts to let the current player play at the given coordinates. If the
     * attempt is successful the current player has ended his turn, and it is the
     * next player's turn.

     * //@param col column to place a marker in.
     * //@param row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean emptySquare(int col, int row){

        return board[col][row] == 0;

    }

    public boolean play(int col, int row)
    {
        //TODO Implement this method
        if(!emptySquare(col,row)||isGameOver())
            return false;
        board[col][row] = currentPlayer;


        return true;

    }

    /**
     * Tells us if the game has ended either by draw or by meeting the winning
     * condition.
     * Returns true if the game is over, else it will return false.
     */
    //public boolean gameOver = false;

    public boolean isGameOver() {
        //TODO Implement this method

        if (getWinner() > 0) {
            return true;

        }
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                if (emptySquare(col, row)) {
                    return false;
                }
            }
        }
       // gameOver = true;
        return true;
    }

    /**
     * Gets the id of the winner, -1 if it's a draw.
     *
     * @return int id of winner, or -1 if you draw.
     */
    public int getWinner()
    {
        for (int col = 0; col < 3; col++) {
            if (board[col][0] != 0 && board[col][0] == board[col][1] && board[col][1] == board[col][2]) {
                return board[col][0]; // Return the player who wins (1 or 2)
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] != 0 && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                return board[0][col]; // Return the player who wins (1 or 2)
            }
        }

        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return board[0][0]; // Return the player who wins (1 or 2)
            }

        if (board[2][0] != 0 && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            return board[2][0]; // Return the player who wins (1 or 2)

        }

        return -1;
    }

    /**
     * Resets the game to a new game state.
     */
    public int startingPlayer = 1;

    public void newGame()
    {

        // Set all squares to 0 (empty)
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                board[col][row] = 0;
            }
        }

        currentPlayer = startingPlayer;
        if (startingPlayer == 1) {
            startingPlayer = 2;
        } else {
            startingPlayer = 1;



        }
    }
}
