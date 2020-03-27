package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private final Button saveButton = new Button ("Save");
    private final Button loadButton = new Button ("Load");
    private final Button resetButton = new Button ("Reset");
    private final Button exitButton = new Button ("Exit");

    private GridPane canvasHolder = new GridPane();
    private GridPane controlPanel = new GridPane();

    private final Label notification = new Label ();

    private final Canvas canvas = new Canvas(797, 400);

    private final ComboBox<String> shapes = new ComboBox<>();
    private final ComboBox<String> shapeSize = new ComboBox<>();
    private final ComboBox<String> drawingMode = new ComboBox<>();
    private final ComboBox<String> color = new ComboBox<>();


    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 800, 600);


        shapes.getItems().addAll(
                "Circle",
                "Square",
                "Round Rectangle"
        );

        shapeSize.getItems().addAll(
                "Big",
                "Medium",
                "Little"
        );

        drawingMode.getItems().addAll(
                "Draw on Click",
                "Draw on Drag"
        );

        color.getItems().addAll(
                "Black",
                "Blue",
                "Yellow",
                "Red",
                "Purple",
                "Brown",
                "Green"
        );

        shapes.setValue("Circle");
        shapeSize.setValue("Medium");
        drawingMode.setValue("Draw on Click");
        color.setValue("Red");



        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(11);


        grid.setPadding(new Insets(5, 5, 0, 0));


        grid.add(new Label("Shape: "), 0, 0);
        grid.add(shapes, 1, 0);

        grid.add(new Label("Size: "), 2, 0);
        grid.add(shapeSize, 3, 0);

        grid.add(new Label("Draw Mode: "), 4, 0);
        grid.add(drawingMode, 5, 0);

        grid.add(new Label("Color: "), 6, 0);
        grid.add(color, 7, 0);


        canvas.setOnMouseClicked(event -> {
            if(drawingMode.getValue().equals("Draw on Click")) {
                drawShape(canvas.getGraphicsContext2D(), event);
            }
        });

        canvas.setOnMouseDragged(event -> {
            canvas.getGraphicsContext2D().setFill(getColor());
            if(drawingMode.getValue().equals("Draw on Drag")) {
                drawShape(canvas.getGraphicsContext2D(), event);
            }
        });

        canvasHolder.getChildren().add(canvas);

        canvasHolder.setStyle("-fx-border-color: red");
        grid.add(canvasHolder, 0, 2, 4, 1);




        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);

                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage(800, 400);
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        System.out.println(ex.getStackTrace());
                    }
                }
            }

        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent t) {
               System.exit(0);
           }
         });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            }
        });

        controlPanel.setHgap(10);
        controlPanel.setVgap(10);
        controlPanel.add(saveButton, 2, 6);
        controlPanel.add(loadButton, 3, 6);
        controlPanel.add(resetButton, 4, 6);
        controlPanel.add(exitButton, 60, 6);
        controlPanel.setStyle("-fx-background-color: grey");


        grid.add(controlPanel, 0, 3, 50, 30);

        grid.add (notification, 1, 3, 3, 1);


        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void drawShape(GraphicsContext graphicsContext, MouseEvent event){
        graphicsContext.setFill(getColor());
        int shapeSize = getSize();

        switch (shapes.getValue()){
            case "Circle":{
                graphicsContext.fillOval(event.getX() - shapeSize/2, event.getY() - shapeSize/2, shapeSize, shapeSize);
                break;
            }case "Square":{
                graphicsContext.fillRect(event.getX() - shapeSize/2, event.getY() - shapeSize/2, shapeSize, shapeSize);
                break;
            }case "Round Rectangle":{
                graphicsContext.fillRoundRect(event.getX() - shapeSize/2, event.getY() - shapeSize/2, shapeSize, shapeSize, 10, 10);
            }
        }
    }

    private Color getColor(){
        switch (color.getValue()){
            case "Blue":{
                return Color.BLUE;
            }
            case "Yellow":{
                return Color.YELLOW;
            }
            case "Red":{
                return Color.RED;
            }
            case "Purple":{
                return Color.PURPLE;
            }
            case "Green":{
                return Color.GREEN;
            }
            case "Brown":{
                return Color.BROWN;
            }
            default:{
                return Color.BLACK;
            }
        }
    }

    private int getSize(){
        switch (shapeSize.getValue()){
            case "Big":{
                return 50;
            }
            case "Little":{
                return 16;
            }
            default:{
                return 30;
            }
        }
    }

}
