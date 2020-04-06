package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private final Button addButton = new Button ("Add player");
    private final Button startButton = new Button ("Start Game");


    private final ComboBox<String> player = new ComboBox<>();
    private final Label timeLabel = new Label();
    private static Label[] playerLabels = new Label[10];
    private static Button[] gameButtons = new Button[100];
    private static Token selectedToken;
    private final TextField playerName = new TextField ();
    private int numberOfPlayers = 0;
    private String[] listOfColors = {"coral", "lightBlue", "seagreen", "purple", "yellow"};

    private static List<Player> listOfPlayers = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 720, 920);


        player.getItems().addAll(
                "Human",
                "Bot"
        );



        player.setValue("Human");



        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(11);

        grid.setStyle("-fx-background-color: lightgrey");

        grid.setPadding(new Insets(220, 10, 10, 10));


        for(int index = 0; index< 100; index++) {
            gameButtons[index] = new Button();
            gameButtons[index].setMinWidth(60);
            gameButtons[index].setMinHeight(60);
            grid.add(gameButtons[index], index % 10, index / 10);

        }



        GridPane grid2 = new GridPane();
        grid2.setVgap(5);
        grid2.setHgap(11);

        grid2.setStyle("-fx-background-color: whitesmoke");
        grid2.setMinHeight(200);
        grid2.setMinWidth(720);
        grid2.add(addButton, 0,5);
        grid2.add(player, 0,6);
        grid2.add(playerName, 0 ,7);
        grid2.add(startButton, 0, 9);
        grid2.setPadding(new Insets(10, 10, 10, 10));

        Board board = new Board(100, 100, 5);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(numberOfPlayers < 5) {
                    numberOfPlayers++;
                    playerLabels[numberOfPlayers] = new Label(player.getValue() + ": " + playerName.getText());
                    grid2.add(playerLabels[numberOfPlayers], 30, numberOfPlayers + 4);

                    if(player.getValue() == "Human"){
                        listOfPlayers.add(new HumanPlayer(playerName.getText(), board, numberOfPlayers, listOfColors[numberOfPlayers-1]));
                    }else if(player.getValue() == "Bot"){
                        listOfPlayers.add(new RandomPlayer(playerName.getText(), board, numberOfPlayers, listOfColors[numberOfPlayers-1]));
                    }
                }
            }
        });



        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        root.getChildren().add(grid2);
        stage.setScene(scene);
        stage.show();





        for(int index=0; index < 100; index++){
            int finalIndex = index;
            gameButtons[index].setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    if(board.getListOfAvailableTokens().contains(board.getListOfTokens().get(finalIndex))){
                        selectedToken = board.getListOfTokens().get(finalIndex);
                        System.out.println(selectedToken.getNumber());
                    }
                }
            });
        }

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(numberOfPlayers >= 2){
                    board.setNumberOfPlayers(numberOfPlayers);
                    Game game = new Game(listOfPlayers, board);
                    game.start();
                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void setTextToButton(int index, String text){
        gameButtons[index].setText(text);
    }
    public static void setColorToButton(int index, String color){
        gameButtons[index].setStyle("-fx-background-color: " + color +";");
    }

    public static Token getSelectedToken() {
        return selectedToken;
    }

    public static void resetSelectedToken(){
        selectedToken = null;
    }

    public static void setCurrentPlayer(int index){
        playerLabels[index].setText(">"+playerLabels[index].getText());
    }
    public static void resetCurrentPlayer(int index){
        playerLabels[index].setText(playerLabels[index].getText().substring(1));
    }

}
