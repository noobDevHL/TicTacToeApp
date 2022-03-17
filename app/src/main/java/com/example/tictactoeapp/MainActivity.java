package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

/**
 * Classic Tic Tac Toe game for one player against the computer
 * There is a winner if three of the same symbols are in one row, column or diagonal
 * displays amount of wins for player, computer or the amount of ties
 *
 * based on the Video-Tutorial by CodeWithMazn https://www.youtube.com/watch?v=CCQTD7ptYqY&t=1823s
 *
 * @author Aline
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, tieScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;
    private Button nextRound;

    private int playerOneScoreCount, playerTwoScoreCount, tieScoreCount;
    boolean playerOneActive;

    // button status: p1 => 1, p2 => 2, empty => 0
    int[] gamestate = {0,0,0,0,0,0,0,0,0};
    int gameStatePointer;
    int winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // connecting variables with textviews
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        tieScore = (TextView) findViewById(R.id.tieScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);
        nextRound = (Button) findViewById(R.id.nextRound);

        // Click-Listener for buttons
        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        // initializing variables
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        tieScoreCount = 0;
        playerOneActive = true;
        winner = 0;
    }

    /**
     * function after player clicks on button
     * @param view Button which was clicked
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
    if(!((Button)view).getText().toString().equals("")){
        return;
    }
    String buttonID = view.getResources().getResourceEntryName(view.getId());
    gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1));

    // if still 1 button free, player1 turn
    if(checkGamestate()) {
       ((Button) view).setText("X");
       ((Button) view).setTextColor(Color.parseColor("#94D2BD"));
       gamestate[gameStatePointer] = 1; // changing gamestate to p1
       playerOneActive = false;
    } else {
       Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
       tieScoreCount++;
       tieScore.setText(Integer.toString(tieScoreCount));
    }

   // checking for winner, if so update score
   winner = checkWinner();
        switch (winner) {
            case 1: // player1 wins
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();
                return; // if there is a winner, leave this function

            case 2: // computer wins
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
                return; // if there is a winner, leave this function

            // else change active player
            default:
                playerOneActive = false;
                break;
        }

    // still 1 button free and player1 inactive, computers turn
    if(checkGamestate() && !playerOneActive) {
        computersTurn();
    } else { // else no winner
        Toast.makeText(this, "It's a tie!", Toast.LENGTH_SHORT).show();
        tieScoreCount++;
        tieScore.setText(Integer.toString(tieScoreCount));
    }

    // checking for winner, if so update score
    winner = checkWinner();
        switch (winner) {
            case 1: // player1 wins
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();
                break;

            case 2: // computer wins
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
                break;

            // else change active player
            default:
                playerOneActive = true;
                break;
        }

    // reset game by clicking button
        resetGame.setOnClickListener(view1 -> {
        playAgain();
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        tieScoreCount = 0;
        playerStatus.setText("");
        updatePlayerScore();
        });

    // start new round and set player status
        nextRound.setOnClickListener(view1 -> {
            playAgain();
            if(playerOneScoreCount > playerTwoScoreCount) {
                playerStatus.setText("You are winning!");
            } else if (playerTwoScoreCount > playerOneScoreCount) {
                playerStatus.setText("The Computer is winning!");
            } else {
                playerStatus.setText("Everything is possible!");
            }
        });
    }


    /**
     * function for computers turn by checking gamestate
     */
    public void computersTurn(){
        if(gamestate[0] == gamestate[1] && gamestate[0] != 0 && gamestate[2] == 0) {
            buttons[2].setText("O");
            buttons[2].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[2] = 2;
        } else if(gamestate[0] == gamestate[2] && gamestate[0] != 0 && gamestate[1] == 0) {
            buttons[1].setText("O");
            buttons[1].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[1] = 2;
        } else if(gamestate[1] == gamestate[2] && gamestate[1] != 0 && gamestate[0] == 0) {
            buttons[0].setText("O");
            buttons[0].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[0] = 2;
        } else if(gamestate[3] == gamestate[4] && gamestate[3] != 0 && gamestate[5] == 0) {
            buttons[5].setText("O");
            buttons[5].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[5] = 2;
        } else if(gamestate[3] == gamestate[5] && gamestate[3] != 0 && gamestate[4] == 0) {
            buttons[4].setText("O");
            buttons[4].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[4] = 2;
        } else if(gamestate[4] == gamestate[5] && gamestate[4] != 0 && gamestate[3] == 0) {
            buttons[3].setText("O");
            buttons[3].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[3] = 2;
        } else if(gamestate[6] == gamestate[7] && gamestate[6] != 0 && gamestate[8] == 0) {
            buttons[8].setText("O");
            buttons[8].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[8] = 2;
        } else if(gamestate[6] == gamestate[8] && gamestate[6] != 0 && gamestate[7] == 0) {
            buttons[7].setText("O");
            buttons[7].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[7] = 2;
        } else if(gamestate[7] == gamestate[8] && gamestate[7] != 0 && gamestate[6] == 0) {
            buttons[6].setText("O");
            buttons[6].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[6] = 2;
        } else if(gamestate[0] == gamestate[3] && gamestate[0] != 0 && gamestate[6] == 0) {
            buttons[6].setText("O");
            buttons[6].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[6] = 2;
        } else if(gamestate[0] == gamestate[6] && gamestate[0] != 0 && gamestate[3] == 0) {
            buttons[3].setText("O");
            buttons[3].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[3] = 2;
        } else if(gamestate[3] == gamestate[6] && gamestate[3] != 0 && gamestate[0] == 0) {
            buttons[0].setText("O");
            buttons[0].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[0] = 2;
        } else if(gamestate[1] == gamestate[4] && gamestate[1] != 0 && gamestate[7] == 0) {
            buttons[7].setText("O");
            buttons[7].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[7] = 2;
        } else if(gamestate[1] == gamestate[7] && gamestate[1] != 0 && gamestate[4] == 0) {
            buttons[4].setText("O");
            buttons[4].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[4] = 2;
        } else if(gamestate[4] == gamestate[7] && gamestate[4] != 0 && gamestate[1] == 0) {
            buttons[1].setText("O");
            buttons[1].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[1] = 2;
        } else if(gamestate[2] == gamestate[5] && gamestate[2] != 0 && gamestate[8] == 0) {
            buttons[8].setText("O");
            buttons[8].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[8] = 2;
        } else if(gamestate[2] == gamestate[8] && gamestate[2] != 0 && gamestate[5] == 0) {
            buttons[5].setText("O");
            buttons[5].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[5] = 2;
        } else if(gamestate[5] == gamestate[8] && gamestate[5] != 0 && gamestate[2] == 0) {
            buttons[2].setText("O");
            buttons[2].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[2] = 2;
        } else if(gamestate[0] == gamestate[4]  && gamestate[0] != 0 && gamestate[8] == 0) {
            buttons[8].setText("O");
            buttons[8].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[8] = 2;
        } else if(gamestate[0] == gamestate[8] && gamestate[0] != 0 && gamestate[4] == 0) {
            buttons[4].setText("O");
            buttons[4].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[4] = 2;
        } else if(gamestate[4] == gamestate[8] && gamestate[4] != 0 && gamestate[0] == 0) {
            buttons[0].setText("O");
            buttons[0].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[0] = 2;
        } else if(gamestate[2] == gamestate[4] && gamestate[2] != 0 && gamestate[6] == 0) {
            buttons[6].setText("O");
            buttons[6].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[6] = 2;
        } else if(gamestate[4] == gamestate[6] && gamestate[4] != 0 && gamestate[2] == 0) {
            buttons[2].setText("O");
            buttons[2].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[2] = 2;
        } else {   // else choose random button
            do {
                Random random = new Random();
                gameStatePointer = random.nextInt(8);
            } while (gamestate[gameStatePointer] != 0);
            buttons[gameStatePointer].setText("O");
            buttons[gameStatePointer].setTextColor(Color.parseColor("#E9D8A6"));
            gamestate[gameStatePointer] = 2;
        }
    }

    /**
     * function to check if at least one button empty
     * @return buttonFree if at least one button free
     */
    public boolean checkGamestate(){
        boolean buttonFree = false;
        for (int i : gamestate) {
            if (i == 0) {
                buttonFree = true;
                break;
            }
        }
        return buttonFree;
    }

    /**
     * function to check for a winner by comparing gamestates
     * @return winner  1 if player1 won, 2 if computer won, 0 if no winner
     */
    public int checkWinner(){
        if(gamestate[0] == gamestate[1] && gamestate[0] == gamestate[2]) {
               winner = gamestate[0];
        } else if(gamestate[3] == gamestate[4] && gamestate[3] == gamestate[5]) {
            winner = gamestate[3];
        } else if(gamestate[6] == gamestate[7] && gamestate[6] == gamestate[8]) {
            winner = gamestate[6];
        } else if(gamestate[0] == gamestate[3] && gamestate[0] == gamestate[6]) {
            winner = gamestate[0];
        } else if(gamestate[1] == gamestate[4] && gamestate[1] == gamestate[7]) {
            winner = gamestate[1];
        } else if(gamestate[2] == gamestate[5] && gamestate[2] == gamestate[8]) {
            winner = gamestate[2];
        } else if(gamestate[0] == gamestate[4] && gamestate[0] == gamestate[8]) {
            winner = gamestate[0];
        } else if(gamestate[2] == gamestate[4] && gamestate[2] == gamestate[6]) {
            winner = gamestate[2];
        } else winner = 0;
    return winner;
    }

    /**
     * function to update player/tie scores
     */
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
        tieScore.setText(Integer.toString(tieScoreCount));
    }

    /**
     * function to animate winner row/column/diagonal
     */
    public void winningAnimation(){
        // TODO
        if(winner != 0) {

        }
        // getWinningPositions
        // buttons[winningPosition].setAlpha(0f)
        // buttons[winningPosition].animate().alpha(1f).setDuration(1500)
    }

    /**
     * start a new game by resetting everything
     */
    public void playAgain(){
        playerOneActive = true;

        for(int i = 0; i < buttons.length; i++) {
            gamestate[i] = 0;
            buttons[i].setText("");
        }
    }
}