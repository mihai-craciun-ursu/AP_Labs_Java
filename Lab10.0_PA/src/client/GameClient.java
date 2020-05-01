package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient extends Application{

    public static InputThread inputThread;

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int PORT = 8000;

        try{
            Socket socket = new Socket(serverAddress, PORT);

            inputThread = new InputThread(socket);
            inputThread.start();

            Application.launch(args);

        }catch (UnknownHostException e){
            System.err.println("No server listening..." + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Scene scene = new Scene(new Group(), 720, 920);

    private static final Scene gameScene = new Scene(new Group(), 720, 920);

    private static Stage stage;

    private final Button createButton = new Button("Create");
    private static final GridPane grid = new GridPane();
    private static final GridPane gameGrid = new GridPane();
    private static final Button[] gameButtons = new Button[225];
    private static int x = 7;
    private static int y = 15;
    private static int room;
    private static int turn;

    @Override
    public void start(Stage stageL) throws Exception {
        stage = stageL;
        stage.setTitle("5 nopti pierdute in a row");

        //////////////////////////GAME SCENE /////////////////////////////////////////////
        gameGrid.setVgap(3);
        gameGrid.setHgap(3);

        for(int index = 0; index< 225; index++) {
            gameButtons[index] = new Button();
            gameButtons[index].setMinWidth(45);
            gameButtons[index].setMinHeight(45);
            gameGrid.add(gameButtons[index], index % 15, index / 15);

        }

        for(int index=0; index < 225; index++){
            int finalIndex = index;
            gameButtons[index].setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    inputThread.sendToServer("put " + finalIndex/15 + "|" + finalIndex%15 + "|" + room + "|" + turn);
                }
            });
        }

        Group gameRoot = (Group)gameScene.getRoot();
        gameRoot.getChildren().add(gameGrid);
        //////////////////////////////////////////////////////////////////////////////////
        grid.setVgap(5);
        grid.setHgap(11);
        grid.add(createButton,5, 5, 1, 1);

        createButton.setOnAction(e -> {
            inputThread.sendToServer("create");
        });

        createButton.setMinHeight(50);
        createButton.setMinWidth(150);


        stage.setOnCloseRequest(event -> {
            inputThread.sendToServer("closing");
        });

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }

    public static final void addRoom(int gameId){
        Platform.runLater(() -> {
            Button button = new Button("join " + gameId);
            button.setOnAction(e -> {
                inputThread.sendToServer("join " + gameId);
            });

            grid.add(button, x, y, 1, 1);
            x++;
            if(x==11){
                y++;
                x=7;
            }
        });

    }

    public static final void changeToGameMode(int gameId, int gameTurn){
        Platform.runLater(() -> {
            stage.setScene(gameScene);
            turn = gameTurn;
            room = gameId;
        });
    }

    public static final void colorPiece(int i, int j, int turn){
        Platform.runLater(() -> {
            if(turn == 1){
                gameButtons[i*15 + j].setStyle("-fx-background-color: blue");
            }else{
                gameButtons[i*15 + j].setStyle("-fx-background-color: red");
            }
        });
    }
}
