package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.scene.paint.Color.web;


public class Main extends Application {


    private final String serverIP = "localhost";
    public ArrayList<Socket> serverSockets;
    ObjectOutputStream output;
    ObjectInputStream input;
    String message = "";
    ArrayList<String> items = new ArrayList<>();
    ArrayList<Pane> labelsOfCards = new ArrayList<>();
    TextArea mListLobby = new TextArea();
    TextArea mListViewReady = new TextArea();
    //elemente
    private Button readyButton = new Button();
    private Button spectateButton = new Button();
    private Button finishTurnButton = new Button("End Turn");
    private Button macauaButton = new Button("Macaua");


    private TextArea mTextArea = new TextArea();
    private TextField mTypo = new TextField();
    private Label readyLabel = new Label();
    private Label spectateLabel = new Label();

    private Pane mPane = new Pane();
    private Pane getCardsPane = new Pane();
    private Pane downCardPane = new Pane();
    private Pane changeToClubs = new Pane();
    private Pane changeToDiamonds = new Pane();
    private Pane changeToHears = new Pane();
    private Pane changeToSpades = new Pane();

    private Group root = new Group();

    private String nume = "TEST";
    private PlayersState playersState = new PlayersState();
    private Pack packOfCards = new Pack();
    private List<Card> handOfCards = new ArrayList<>();
    private Card downCard;
    private boolean isTurn = false;
    private boolean firstcard = true;
    private boolean hasCardsToDraw = false;
    private boolean hasTurnsToStay = false;
    private boolean isMacaua = false;
    private boolean isPlaying = false;
    private int cardsToPass = 0;
    private int turnsToPass = 0;
    private int turnsToStay = 0;
    private String signToChange = "";
    private Socket connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.setTitle("Mac&Bear");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(t -> {
            try {
                packOfCards.getListOfAvaibleCards().addAll(handOfCards);
                Order order = new Order("server",0,0,null,packOfCards);
                if(isTurn)
                    endTurn();

                sendMessage(order);
                sendMessage("/left");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        setup();
    }

    private void setup() throws IOException {



        Controller controller = new Controller();

        URL main = Main.class.getResource("Main.class");
        if (!"file".equalsIgnoreCase(main.getProtocol()))
            throw new IllegalStateException("Main class is not stored in a file.");
        String path = new File(main.getPath()).toString();

        root.getChildren().add(mPane);
        mPane.setPrefWidth(1100);
        mPane.setPrefHeight(600);
        mPane.setStyle("-fx-background-color: #" + "008a54");

        mPane.getChildren().add(mTextArea);
        mTextArea.setEditable(false);
        mTextArea.setLayoutX(550);
        mTextArea.setLayoutY(10);
        mTextArea.setPrefHeight(450);
        mTextArea.setPrefWidth(250);
        mTextArea.setStyle("-fx-background-color: #" + "008a54");

        mPane.getChildren().add(mTypo);
        mTypo.setLayoutX(550);
        mTypo.setLayoutY(460);
        mTypo.setPrefHeight(40);
        mTypo.setPrefWidth(250);
        mTypo.setPromptText("Type a message...");
        mTypo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if(keyEvent.getCode() == KeyCode.ENTER)  {
                String text = mTypo.getText();
                if(text != null && !text.trim().isEmpty())
                    try {
                        sendMessage(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                mTypo.setText("");
            }
        });



        mPane.getChildren().add(mListLobby);
        mListLobby.setLayoutX(810);
        mListLobby.setLayoutY(50);
        mListLobby.setPrefHeight(150);
        mListLobby.setPrefWidth(180);
        mListLobby.setEditable(false);
        mListLobby.setText("");

        mPane.getChildren().add(mListViewReady);
        mListViewReady.setLayoutX(810);
        mListViewReady.setLayoutY(300);
        mListViewReady.setPrefHeight(150);
        mListViewReady.setPrefWidth(180);
        mListViewReady.setEditable(false);
        mListViewReady.setText("");


        readyButton = new Button();
        mPane.getChildren().add(readyButton);
        readyButton.setLayoutX(845);
        readyButton.setLayoutY(225);
        readyButton.setPrefWidth(30);
        readyButton.setPrefHeight(30);
        readyButton.setDisable(true);
        ImageView backReadyButton = new ImageView(getClass().getResource("Resized_signs/arrow_down.png").toExternalForm());
        readyButton.setGraphic(backReadyButton);
        readyButton.setOnAction((ActionEvent event) ->
                {
                    spectateButton.setDisable(false);
                    playersState.removeFrom(nume,playersState.getLobbyPlayers());
                    playersState.addTo(nume, playersState.getReadyPlayer());
                    try {
                        sendMessage(playersState);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );



        spectateButton = new Button();
        mPane.getChildren().add(spectateButton);
        spectateButton.setLayoutX(915);
        spectateButton.setLayoutY(225);
        spectateButton.setPrefWidth(30);
        spectateButton.setPrefHeight(30);
        spectateButton.setDisable(true);
        ImageView backSpectateButton = new ImageView(getClass().getResource("Resized_signs/arrow_up.png").toExternalForm());
        spectateButton.setGraphic(backSpectateButton);
        spectateButton.setOnAction((ActionEvent event) ->
                {

                    spectateButton.setDisable(true);
                    playersState.addTo(nume,playersState.getLobbyPlayers());
                    playersState.removeFrom(nume, playersState.getReadyPlayer());
                    try {
                        sendMessage(playersState);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        mPane.getChildren().add(getCardsPane);
        getCardsPane.setLayoutX(400);
        getCardsPane.setLayoutY(100);
        getCardsPane.setPrefWidth(90);
        getCardsPane.setPrefHeight(131);
        ImageView bk = new ImageView(getClass().getResource("Resized_cards/back.jpg").toExternalForm());
        getCardsPane.getChildren().add(bk);
        getCardsPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(cardsToPass != 0) {
                addCard(cardsToPass);
                cardsToPass = 0;
            }else
                addCard(1);
            Platform.runLater(()-> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            endTurn();
        });
            });
        getCardsPane.setDisable(true);

        mPane.getChildren().add(downCardPane);
        downCardPane.setLayoutX(200);
        downCardPane.setLayoutY(100);
        downCardPane.setPrefHeight(131);
        downCardPane.setPrefWidth(90);
        ImageView back = new ImageView(getClass().getResource("Resized_cards/back.jpg").toExternalForm());
        downCardPane.getChildren().add(back);

        mPane.getChildren().add(finishTurnButton);
        finishTurnButton.setLayoutX(400);
        finishTurnButton.setLayoutY(275);
        finishTurnButton.setPrefHeight(55);
        finishTurnButton.setPrefWidth(90);
        finishTurnButton.setOnAction((ActionEvent event) -> {
            endTurn();
        });
        finishTurnButton.setDisable(true);

        mPane.getChildren().add(macauaButton);
        macauaButton.setLayoutX(200);
        macauaButton.setLayoutY(275);
        macauaButton.setPrefSize(90,55);
        macauaButton.setOnAction((ActionEvent event) -> {
            if(handOfCards.size() == 1)
                isMacaua = true;
        });


        mPane.getChildren().add(readyLabel);
        readyLabel.setLayoutX(810);
        readyLabel.setLayoutY(280);
        readyLabel.setPrefHeight(20);
        readyLabel.setPrefWidth(290);
        readyLabel.setText("Ready Players: (" + String.valueOf(items.size()) + "/8)");
        readyLabel.setTextFill(web("#ffffff"));

        mPane.getChildren().add(spectateLabel);
        spectateLabel.setLayoutX(810);
        spectateLabel.setLayoutY(30);
        spectateLabel.setPrefHeight(20);
        spectateLabel.setPrefWidth(290);
        spectateLabel.setText("Players in Lobby: (" + String.valueOf(items.size()) + "/100)");
        spectateLabel.setTextFill(web("#ffffff"));

        mPane.getChildren().add(changeToClubs);
        changeToClubs.setLayoutY(100);
        changeToClubs.setLayoutX(131);
        changeToClubs.setPrefWidth(40);
        changeToClubs.setPrefHeight(40);
        ImageView backClubs = new ImageView(getClass().getResource("Resized_signs/clubs.png").toExternalForm());
        changeToClubs.getChildren().add(backClubs);
        changeToClubs.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            signToChange = "clubs";
            isTurn = true;
            try {
                sendMessage(new Order("all", 0, 0, new Card(downCard.getNumber(), signToChange, downCard.getUrl()), packOfCards));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //CELE 4 BUTOANE DISPAR
            changeToSpades.setVisible(false);
            changeToDiamonds.setVisible(false);
            changeToClubs.setVisible(false);
            changeToHears.setVisible(false);
        });
        changeToClubs.setVisible(false);

        mPane.getChildren().add(changeToDiamonds);
        changeToDiamonds.setLayoutX(35);
        changeToDiamonds.setLayoutY(100);
        changeToDiamonds.setPrefHeight(40);
        changeToDiamonds.setPrefWidth(40);
        ImageView backDiamonds = new ImageView(getClass().getResource("Resized_signs/diamonds.png").toExternalForm());
        changeToDiamonds.getChildren().add(backDiamonds);
        changeToDiamonds.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            signToChange = "diamonds";
            isTurn = true;
            try {
                sendMessage(new Order("all", 0, 0, new Card(downCard.getNumber(), signToChange, downCard.getUrl()), packOfCards));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            changeToSpades.setVisible(false);
            changeToDiamonds.setVisible(false);
            changeToClubs.setVisible(false);
            changeToHears.setVisible(false);
        });
        changeToDiamonds.setVisible(false);


        mPane.getChildren().add(changeToHears);
        changeToHears.setLayoutY(191);
        changeToHears.setLayoutX(131);
        changeToHears.setPrefWidth(40);
        changeToHears.setPrefHeight(40);
        ImageView backHearts = new ImageView(getClass().getResource("Resized_signs/hearts.png").toExternalForm());
        changeToHears.getChildren().add(backHearts);
        changeToHears.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            signToChange = "hearts";
            isTurn = true;
            try {
                sendMessage(new Order("all", 0, 0, new Card(downCard.getNumber(), signToChange, downCard.getUrl()), packOfCards));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //CELE 4 BUTOANE DISPAR
            changeToSpades.setVisible(false);
            changeToDiamonds.setVisible(false);
            changeToClubs.setVisible(false);
            changeToHears.setVisible(false);
        });
        changeToHears.setVisible(false);


        mPane.getChildren().add(changeToSpades);
        changeToSpades.setLayoutY(191);
        changeToSpades.setLayoutX(35);
        changeToSpades.setPrefSize(40,40);
        ImageView backSpades = new ImageView(getClass().getResource("Resized_signs/spades.png").toExternalForm());
        changeToSpades.getChildren().add(backSpades);
        changeToSpades.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            signToChange = "spades";
            isTurn = true;
            try {
                sendMessage(new Order("all", 0, 0, new Card(downCard.getNumber(), signToChange, downCard.getUrl()), packOfCards));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //CELE 4 BUTOANE DISPAR
            changeToSpades.setVisible(false);
            changeToDiamonds.setVisible(false);
            changeToClubs.setVisible(false);
            changeToHears.setVisible(false);
        });
        changeToSpades.setVisible(false);





        nume = JOptionPane.showInputDialog("Enter your name. Leave empty or close for a random name");
        if(nume == null || nume.equals("") || nume.trim().isEmpty())
            nume = "user" + String.valueOf(new Random().nextInt(999)+1);
        else
            nume = nume + String.valueOf(new Random().nextInt(999)+1);



        new Thread(new startClient(3000)).start();
    }

    private void endTurn(){
        if(hasTurnsToStay){
            turnsToStay = turnsToPass - 1;
            turnsToPass = 0;
            hasTurnsToStay = false;
        }

        if(handOfCards.size() == 1 && isMacaua == false){
            addCard(5);
        }

        finishTurnButton.setDisable(true);
        getCardsPane.setDisable(true);
        isTurn = false;


        Platform.runLater(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Order newOrder;
                if (playersState.getReadyPlayer().indexOf(nume) == playersState.getReadyPlayer().size() - 1) {
                    newOrder = new Order(playersState.getReadyPlayer().get(0), cardsToPass, turnsToPass, null, packOfCards);
                    try {
                        sendMessage(newOrder);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    newOrder = new Order(playersState.getReadyPlayer().get(playersState.getReadyPlayer().indexOf(nume) + 1), cardsToPass, turnsToPass, null, packOfCards);
                    try {
                        sendMessage(newOrder);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(handOfCards.size() == 0){
                    playersState.getReadyPlayer().remove(nume);
                    playersState.getLobbyPlayers().add(nume);
                try {
                    sendMessage(playersState);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void addCard(int index){
        Platform.runLater(()-> {
            for(int i = 0; i< index; i++){
                Pane pane = new Pane();
                mPane.getChildren().add(pane);
                labelsOfCards.add(pane);
                Card card = packOfCards.getACard(0);
                handOfCards.add(card);
                ImageView bk = new ImageView(getClass().getResource(card.getUrl()).toExternalForm());
                pane.getChildren().add(bk);
                setCardsPane(pane);
            }
            resetPositionOfCards(labelsOfCards.size());
                });

    }

    private void addStartCards(int index){
        Platform.runLater(() -> {
            for (int i = 0; i < 6; i++) {
                Pane pane = new Pane();
                mPane.getChildren().add(pane);
                labelsOfCards.add(pane);
                Card card = packOfCards.getACard(index * 6);
                handOfCards.add(card);
                ImageView bk = new ImageView(getClass().getResource(card.getUrl()).toExternalForm());
                pane.getChildren().add(bk);
                setCardsPane(pane);
            }
            resetPositionOfCards(labelsOfCards.size());
        });
    }

    private void resetPositionOfCards(int index){
        if(index>6)
            for(Pane pane: labelsOfCards){
                pane.setLayoutX((450/(index-1))*(labelsOfCards.indexOf(pane))+5);
            }
        else
            for(Pane pane: labelsOfCards){
                pane.setLayoutX(90*labelsOfCards.indexOf(pane)+5);
            }

    }

    private void deleteCards() {
        Platform.runLater(() -> {
            List<Pane> list = labelsOfCards;

            handOfCards.clear();

            for(Pane pane: list){
                labelsOfCards.get(list.indexOf(pane)).getChildren().remove(0);
                mPane.getChildren().remove(labelsOfCards.get(list.indexOf(pane)));
            }
            labelsOfCards.clear();
            resetPositionOfCards(labelsOfCards.size());


        });

        handOfCards.clear();

        System.out.println(labelsOfCards.size());
        resetPositionOfCards(labelsOfCards.size());
    }

    public void setCardsPane(Pane pane){
        pane.setLayoutY(375);
        pane.setPrefHeight(190);
        pane.setPrefWidth(90);
        pane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> pane.setLayoutY(pane.getLayoutY() - 40));
        pane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> pane.setLayoutY(pane.getLayoutY() + 40));
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(isTurn) {

                     if ((!hasTurnsToStay && !hasCardsToDraw && firstcard && (downCard.getNumber().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber()) || downCard.getSign().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getSign())))
                        ||(!hasTurnsToStay && !hasCardsToDraw && !firstcard && (downCard.getNumber().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber())))
                        ||(!hasTurnsToStay && !hasCardsToDraw && firstcard && ((handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("ace")) || handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("joker")))
                        ||(!hasTurnsToStay && !hasCardsToDraw && firstcard && ((downCard.getSign().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getSign())) && downCard.getNumber().equals("ace")))
                        ||(!hasTurnsToStay && !hasCardsToDraw && firstcard && downCard.getNumber().equals("joker"))
                        ||(!hasTurnsToStay && hasCardsToDraw && (downCard.getNumber().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber()) || (downCard.getSign().equals(handOfCards.get(labelsOfCards.indexOf(pane)).getSign()) && (handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("2") || handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("3"))) || handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("joker") || downCard.getNumber().equals("joker")))
                        ||(hasTurnsToStay && handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("4"))
                        ){

                    finishTurnButton.setDisable(false);
                    getCardsPane.setDisable(true);
                    firstcard = false;
                    hasCardsToDraw = false;
                    hasTurnsToStay = false;

                    if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("2")){
                        cardsToPass += 2;
                    }else if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("3")){
                        cardsToPass += 3;
                    }else if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("joker") && handOfCards.get(labelsOfCards.indexOf(pane)).getSign().equals("black")){
                        cardsToPass += 5;
                    }else if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("joker") && handOfCards.get(labelsOfCards.indexOf(pane)).getSign().equals("red")){
                        cardsToPass += 10;
                    }else if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("4")){
                        turnsToPass += 1;
                    }

                    downCard = handOfCards.get(labelsOfCards.indexOf(pane));
                    ImageView back = new ImageView(getClass().getResource(downCard.getUrl()).toExternalForm());
                    downCardPane.getChildren().remove(0);
                    downCardPane.getChildren().add(back);

                    packOfCards.getListOfAvaibleCards().add(downCard);


                    Order someOrder;

                    if(handOfCards.get(labelsOfCards.indexOf(pane)).getNumber().equals("ace")){
                        //CELE 4 BUTOANE APAR
                        changeToSpades.setVisible(true);
                        changeToDiamonds.setVisible(true);
                        changeToClubs.setVisible(true);
                        changeToHears.setVisible(true);

                        isTurn = false;
                    }
                    else
                        try {
                            sendMessage(new Order("all", 0, 0, downCard, packOfCards));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    handOfCards.remove(labelsOfCards.indexOf(pane));
                    pane.getChildren().remove(0);
                    labelsOfCards.remove(labelsOfCards.indexOf(pane));
                    mPane.getChildren().remove(pane);
                    resetPositionOfCards(labelsOfCards.size());
                }
            }
        });
    }

    private void startGame() {
        if(playersState.getReadyPlayer().contains(nume)) {
            int index = playersState.getReadyPlayer().indexOf(nume);
            Platform.runLater(() -> {
                readyButton.setDisable(true);
                spectateButton.setDisable(true);
            });
            addStartCards(index);
        }
    }


    private void showMessage(String s) {
        mTextArea.appendText(s);
    }

    public void sendMessage(String message) throws IOException {
        output.reset();
        output.writeObject(message);
        output.flush();
    }

    public void sendMessage(PlayersState playersState) throws IOException {
        output.reset();
        output.writeObject(playersState);
        output.flush();
    }

    public void sendMessage(Order order) throws IOException {
        output.reset();
        output.writeObject(order);
        output.flush();
    }



    public void resetLists(List<String> lobbyList, List<String> readyList){
        Platform.runLater(() -> {
            spectateLabel.setText("Players in Lobby: ("+String.valueOf(playersState.getLobbyPlayers().size())+"/100)");
            readyLabel.setText(("Ready Players: ("+String.valueOf(playersState.getReadyPlayer().size())+"/8)"));
            if(playersState.getReadyPlayer().size() < 8 && playersState.getLobbyPlayers().contains(nume)){
                readyButton.setDisable(false);
            }else if(playersState.getReadyPlayer().size() == 8 || playersState.getReadyPlayer().contains(nume)){
                readyButton.setDisable(true);
            }

        });
        mListLobby.clear();
        mListViewReady.clear();

        for(String person: lobbyList){
            mListLobby.appendText(person + "\n");
        }

        for(String person: readyList){
            mListViewReady.appendText(person + "\n");
        }
    }

    public class startClient implements Runnable{


        private int mPortNumber;

        public startClient(int portNumber){
            mPortNumber = portNumber;
        }

        @Override
        public void run() {
            try {
                connectToServer();
                setupStreams();
                whileChatting();
            } catch (EOFException var6) {
                showMessage("\n Client terminated the connection");
            } catch (IOException var7) {

            } finally {
                closeConnection();
            }
        }

        private void connectToServer() throws IOException {
            showMessage("Attempting connection... \n");
            connection = new Socket(InetAddress.getByName(serverIP), 3000);
            showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());

        }

        private void setupStreams() throws IOException {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            showMessage("\n The streams are now set up! \n");
        }

        private void whileChatting() throws IOException {
            List<String> readyList = new ArrayList<>();
            readyList.add("ceva");

            sendMessage("&nume"+nume);
            do {
                try {
                    Object object = input.readObject();

                    if(object instanceof String) {
                        if(((String) object).equals("&$start")){
                            handOfCards.clear();
                            labelsOfCards.clear();
                            startGame();
                        }else if(((String)object).equals("/end")){
                            deleteCards();
                            if(playersState.getReadyPlayer().contains(nume)) {
                                isTurn = false;
                                playersState.getReadyPlayer().remove(nume);
                                playersState.getLobbyPlayers().add(nume);
                                sendMessage(playersState);
                            }
                        }

                        else
                            showMessage("\n" + (String) object);
                    }

                    else if(object instanceof PlayersState){
                        playersState = (PlayersState) object;
                        resetLists(playersState.getLobbyPlayers(),playersState.getReadyPlayer());
                    }
                    else if(object instanceof Pack){
                        packOfCards = (Pack) object;
                    }

                    else if(object instanceof Order){
                        if(((Order) object).getName().equals("all")){
                            Platform.runLater(() -> {
                                Order newWorldOrder = (Order) object;
                                downCard = newWorldOrder.getDownCard();
                                downCardPane.getChildren().remove(0);
                                ImageView back = new ImageView(getClass().getResource(newWorldOrder.getDownCard().getUrl()).toExternalForm());
                                downCardPane.getChildren().add(back);
                                packOfCards = newWorldOrder.getPackOfCards();
                            });
                        }
                        else if(((Order) object).getName().equals(nume)){
                            Platform.runLater(() -> {

                                if(turnsToStay > 0){
                                    turnsToStay -= 1;
                                    packOfCards = ((Order) object).getPackOfCards();
                                    turnsToPass = ((Order) object).getTurnsToStay();
                                    cardsToPass = ((Order) object).getCardsToDraw();
                                    endTurn();
                                }else {

                                    getCardsPane.setDisable(false);
                                    hasCardsToDraw = false;
                                    hasTurnsToStay = false;
                                    isTurn = true;
                                    firstcard = true;
                                    isMacaua = false;
                                    cardsToPass = 0;
                                    turnsToPass = 0;

                                    if (((Order) object).getCardsToDraw() != 0) {
                                        cardsToPass = ((Order) object).getCardsToDraw();
                                        hasCardsToDraw = true;
                                    }
                                    if (((Order) object).getTurnsToStay() != 0) {
                                        turnsToPass = ((Order) object).getTurnsToStay();
                                        hasTurnsToStay = true;
                                        getCardsPane.setDisable(true);
                                        finishTurnButton.setDisable(false);
                                    }

                                    packOfCards = ((Order) object).getPackOfCards();
                                }
                            });
                        }
                    }

                } catch (ClassNotFoundException var2) {
                    showMessage("Unknown data received!");
                }
            } while(!message.equals("SERVER - END"));
        }


        private void closeConnection() {
            showMessage("\n Closing the connection!");

            try {
                output.close();
                input.close();
                connection.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }


    }


}
