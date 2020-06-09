package sample;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;

public class Main extends Application {

    private Group root = new Group();
    private TextArea mTextArea = new TextArea();
    private Button mStart = new Button("Start");
    private TextField mTypo = new TextField();
    private Pane mPane = new Pane();
    private Label spectateLabel = new Label();
    private Label readyLabel = new Label();



    List<String> lobbyList = new ArrayList<>();
    private Map<String, List<String>> stateOfThePlayers;


    private PlayersState playersState = new PlayersState();
    private Pack packOfCards = new Pack();

    private boolean isPlaying = false;


    TextArea mListLobby = new TextArea();
    TextArea mListViewReady = new TextArea();
    ArrayList<String> lobbyPlayers = new ArrayList<>();
    ArrayList<String> readyPlayers = new ArrayList<>();

    public ArrayList<ObjectOutputStream> clientOutputStreams;
    public ArrayList<String> users;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MacBear Server");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> System.exit(0));
        setup();
    }

    private void setup() {




        root.getChildren().add(mPane);
        mPane.setPrefWidth(500);
        mPane.setPrefHeight(500);
        mPane.setStyle("-fx-background-color: #" + "1899C4");

        mPane.getChildren().add(mTextArea);
        mTextArea.setEditable(false);
        mTextArea.setLayoutX(0);
        mTextArea.setPrefHeight(460);
        mTextArea.setPrefWidth(250);
        mTextArea.setStyle("-fx-background-color: #" + "1899C4");

        mPane.getChildren().add(mTypo);
        mTypo.setLayoutX(0);
        mTypo.setLayoutY(460);
        mTypo.setPrefHeight(40);
        mTypo.setPrefWidth(250);
        mTypo.setPromptText("Type a message...");
        mTypo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if(keyEvent.getCode() == KeyCode.ENTER)  {
                String text = mTypo.getText();
                if(text != null && !text.trim().isEmpty())
                    sendToAll(text);
                mTypo.setText("");
            }
        });

        mPane.getChildren().add(mStart);
        mStart.setLayoutX(285);
        mStart.setLayoutY(400);
        mStart.setPrefWidth(180);
        mStart.setPrefHeight(80);
        mStart.setFont(Font.font("",30));
        mStart.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                sendToAll("&$start");
                isPlaying = true;
            });
            Pack newPack = new Pack();

            packOfCards = newPack;
            packOfCards.setListOfAvaibleCards(packOfCards.getListOfCards());
            packOfCards.shuffle();

            sendToAll(packOfCards);
            packOfCards.getListOfAvaibleCards().removeAll(packOfCards.getListOfAvaibleCards().subList(0,playersState.getReadyPlayer().size()*6));
            Platform.runLater(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                Card card = packOfCards.getACard(0);
                Order order = new Order("all",0,0,card,packOfCards);
                packOfCards.getListOfAvaibleCards().add(card);

                sendToAll(order);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }


                Order startOrder = new Order(playersState.getReadyPlayer().get(0),0,0,null, packOfCards);
                sendToAll(startOrder);

            });
        });


        mPane.getChildren().add(mListLobby);
        mListLobby.setLayoutX(285);
        mListLobby.setLayoutY(40);
        mListLobby.setPrefHeight(150);
        mListLobby.setPrefWidth(180);
        mListLobby.setEditable(false);
        mListLobby.setText("");

        mPane. getChildren().add(mListViewReady);
        mListViewReady.setLayoutX(285);
        mListViewReady.setLayoutY(230);
        mListViewReady.setPrefHeight(150);
        mListViewReady.setPrefWidth(180);
        mListViewReady.setEditable(false);
        mListViewReady.setText("");

        mPane.getChildren().add(spectateLabel);
        spectateLabel.setLayoutX(285);
        spectateLabel.setLayoutY(20);
        spectateLabel.setPrefHeight(20);
        spectateLabel.setPrefWidth(290);
        spectateLabel.setText("Players in Lobby: (0/100)");
        spectateLabel.setTextFill(Color.web("#ffffff"));

        mPane.getChildren().add(readyLabel);
        readyLabel.setLayoutX(285);
        readyLabel.setLayoutY(210);
        readyLabel.setPrefHeight(20);
        readyLabel.setPrefWidth(290);
        readyLabel.setText("Ready Players: (0/8)");
        readyLabel.setTextFill(Color.web("#ffffff"));

        new Thread(new startServer(3000)).start();


    }

    private void sendToAll(String message){
        showMessage(message);
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext())
            try
            {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.writeObject(message);
                writer.flush();
            }
            catch (Exception ex) {}

    }

    private void sendToAll(PlayersState playersState){
        resetLists(playersState.getLobbyPlayers(),playersState.getReadyPlayer());
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext())
            try {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.reset();
                writer.writeObject(playersState);
                writer.flush();
            } catch (Exception ex) {
            }
    }

    private void sendToAll(Order order){
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext())
            try {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.reset();
                writer.writeObject(order);
                writer.flush();
            } catch (Exception ex) {
            }
    }

    private void sendToAll(Pack pack){
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext())
            try {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.reset();
                writer.writeObject(pack);
                writer.flush();
            } catch (Exception ex) {
            }
    }


    private void resetLists(List<String> lobbyPlayers, List<String> readyPlayer) {
        Platform.runLater(() -> {
            spectateLabel.setText("Players in Lobby: ("+String.valueOf(playersState.getLobbyPlayers().size())+"/100)");
            readyLabel.setText(("Ready Players: ("+String.valueOf(playersState.getReadyPlayer().size())+"/8)"));

        });
        mListLobby.clear();
        mListViewReady.clear();

        for(String person: lobbyPlayers){
            mListLobby.appendText(person + "\n");
        }

        for(String person: readyPlayer){
            mListViewReady.appendText(person + "\n");
        }
    }

    private void showMessage(final String text){
        mTextArea.appendText("\n" + text);

    }
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    public class startServer implements Runnable{


        private int mPortNumber;

        public startServer(int portNumber){
            mPortNumber = portNumber;
        }

        @Override
        public void run() {
            clientOutputStreams = new ArrayList<>();
            users = new ArrayList<>();

            try{

                ServerSocket socket = new ServerSocket(mPortNumber);

                while(true){
                    Socket clientSocket = socket.accept();
                    ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                    clientOutputStreams.add(writer);
                    new Thread(new newClient(clientSocket, writer)).start();

                }

            }catch(Exception e){System.exit(0);}

        }
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    public class newClient implements Runnable {

        ObjectInputStream reader;
        Socket sock;
        ObjectOutputStream client;
        String nume;

        public newClient(Socket clientSocket, ObjectOutputStream writer) {
            sock = clientSocket;
            client = writer;
            try {
                reader = new ObjectInputStream(sock.getInputStream());
            } catch (Exception ex) {

            }

        }

        @Override
        public  void run() {

            String message = " ";
            try {
                Object object = new Object();
                while (true){
                    object = reader.readObject();

                    if(object instanceof String){
                        message = (String) object;
                        if(message.startsWith("&nume")) {
                            nume = message.substring(5);
                            sendToAll(nume + " connected");
                            playersState.addTo(nume,playersState.getLobbyPlayers());
                            sendToAll(playersState);

                        }
                        else if(message.startsWith("/left")){
                            playersState.removeFrom(nume,playersState.getLobbyPlayers());
                            playersState.removeFrom(nume,playersState.getReadyPlayer());
                            sock.close();
                            sendToAll(playersState);
                            sendToAll(nume + " left");
                            break;
                        }
                        else sendToAll(nume + " - " +message);
                    }
                    else if(object instanceof PlayersState){
                        playersState = (PlayersState) object;
                        sendToAll(playersState);

                        if(playersState.getReadyPlayer().size() == 1 && isPlaying){
                            sendToAll("/end");
                            isPlaying = false;
                        }
                    }
                    else if(object instanceof Order){
                        if(((Order) object).getName().equals("server")){
                            packOfCards = ((Order) object).getPackOfCards();
                            System.out.println(packOfCards.getListOfAvaibleCards().size());
                            sendToAll(packOfCards);
                        } else {
                            packOfCards = ((Order) object).getPackOfCards();
                            sendToAll((Order) object);
                        }
                    }



                }

            } catch (IOException ex) {
                try {
                    sock.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.exit(0);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }



    public static void main(String[] args) {
        launch(args);
    }
}