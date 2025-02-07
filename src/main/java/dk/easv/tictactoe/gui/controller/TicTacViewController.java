
package dk.easv.tictactoe.gui.controller;

// Java imports
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.List;
import java.util.Random;


// Project imports
import dk.easv.tictactoe.bll.GameBoard;
import dk.easv.tictactoe.bll.IGameBoard;

/**
 *
 * @author EASV
 */
public class TicTacViewController implements Initializable
{
    @FXML
    private Label lblPlayer;

    @FXML
    private Button btnNewGame;

    @FXML
    private GridPane gridPane;
    
    private static final String TXT_PLAYER = "Player: ";
    private IGameBoard game;

    private Image oImage = new Image(getClass().getResourceAsStream("/images/O.png"));
    private Image xImage = new Image(getClass().getResourceAsStream("/images/X.png"));

    /**
     * Event handler for the grid buttons
     *
     * @param event
     */
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        if (!game.isGameOver()){

            try {
                Integer row = GridPane.getRowIndex((Node) event.getSource());
                Integer col = GridPane.getColumnIndex((Node) event.getSource());
                int r = (row == null) ? 0 : row;
                int c = (col == null) ? 0 : col;
                int player = game.getNextPlayer();
                setPlayer();

                if (game.play(c, r)) {
                    Button btn = (Button) event.getSource();

                    //String xOrO = player == 2 ? "X" : "O";
                    //btn.setText(xOrO);
                    ImageView imageView;
                    if (player == 1) {
                        imageView = new ImageView(oImage);
                    } else {
                        imageView = new ImageView(xImage);
                    }

                    imageView.setFitWidth(btn.getWidth()-40);
                    imageView.setFitHeight(btn.getHeight()-40);
                    btn.setGraphic(imageView);
                    setPlayer();
                    playClickSound();

                    if (game.isGameOver()) {
                        int winner = game.getWinner();
                        displayWinner(winner);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private MediaPlayer backgroundPlayer;
    private MediaPlayer newGameSoundPlayer;
        private List<String> soundFiles = List.of(
             "src/main/resources/sounds/BackgroundMusic/AnotherRoundForEveryone.mp3",
             "src/main/resources/sounds/BackgroundMusic/AStoryYouWontBelieve.mp3",
             "src/main/resources/sounds/BackgroundMusic/BackOnThePath.mp3",
             "src/main/resources/sounds/BackgroundMusic/CleverClogs.mp3",
             "src/main/resources/sounds/BackgroundMusic/TheMustyScentofFreshPate.mp3",
             "src/main/resources/sounds/BackgroundMusic/TheNightingale.mp3",
             "src/main/resources/sounds/BackgroundMusic/TheWitcher3UnreleasedGwentTrack.mp3"
             );

    private final String drawSoundPath = "src/main/resources/sounds/DrawSoundEffect.mp3";
    private final String winSoundPath = "src/main/resources/sounds/WinSoundEffect.mp3";
    private final String newGameSoundPath = "src/main/resources/sounds/NewGameSoundEffect.mp3";
    private final String clickSoundPath = "src/main/resources/sounds/ClickButtonSoundEffect.mp3";

    /**
     * Event handler for starting a new game
     */
    
         
    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
         if (backgroundPlayer != null) {
                backgroundPlayer.stop();
            }
         playNewGameSound();
        //setPlayer();
        clearBoard();
    }
    
    private void playRandomBackgroundSound() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(soundFiles.size());
        String soundFile = soundFiles.get(randomIndex);

        Media media = new Media(new File(soundFile).toURI().toString());
        backgroundPlayer = new MediaPlayer(media);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the sound if needed
        backgroundPlayer.play();
    }

    private void playNewGameSound() {
        // Stop any existing background music
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
        Media media = new Media(new File(newGameSoundPath).toURI().toString());
        newGameSoundPlayer = new MediaPlayer(media);
        newGameSoundPlayer.play();

        // Set an event to play background music after the new game sound finishes
        newGameSoundPlayer.setOnEndOfMedia(() -> {
            playRandomBackgroundSound();
        });
    }

    private void playClickSound() {
        Media media = new Media(new File(clickSoundPath).toURI().toString());
        MediaPlayer clickSoundPlayer = new MediaPlayer(media);
        clickSoundPlayer.play();
    }

    /**
     * Initializes a new controller
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        game = new GameBoard();
        setPlayer();
        playRandomBackgroundSound();
    }

    /**
     * Set the next player
     */
    public void setPlayer()
    {
        lblPlayer.setText(TXT_PLAYER + game.getNextPlayer());
    }


    /**
     * Finds a winner or a draw and displays a message based
     */
    private void displayWinner(int winner)
    {
        String message = "";
        switch (winner)
        {
            case -1:
                message = "It's a draw :-(";
                playDrawSound();
                break;
            default:
                message = "Player " + winner + " wins!!!";
                playWinSound();
                break;
        }
        lblPlayer.setText(message);
    }

    /**
     * Clears the game board in the GUI
     */

    private void playDrawSound() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
        Media media = new Media(new File(drawSoundPath).toURI().toString());
        MediaPlayer drawSoundPlayer = new MediaPlayer(media);
        drawSoundPlayer.play();
    }

    private void playWinSound() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
        Media media = new Media(new File(winSoundPath).toURI().toString());
        MediaPlayer winSoundPlayer = new MediaPlayer(media);
        winSoundPlayer.play();
    }

    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
            btn.setGraphic(null);
            setPlayer();
        }
    }

}
