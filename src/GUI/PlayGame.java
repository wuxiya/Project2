package GUI;

import DouShouQiCode.Animal;
import DouShouQiCode.Move;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by CiCi on 2016/12/27.
 */
public class PlayGame extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set music
        Media media = new Media(new File("C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\audio\\ひかりにわ楼兰.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();


        //set button
        ButtonSet btNewGame = new ButtonSet("New Game");
        ButtonSet btPrevious = new ButtonSet("Previous");
        ButtonSet btStyle = new ButtonSet("Game style");
        ButtonSet btExit = new ButtonSet("Exit");

        btNewGame.setOnAction(event -> {
            mediaPlayer.stop();
            primaryStage.close();
            try {
                new ChessStage().start(new Stage());
                Animal.player = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        btPrevious.setOnAction(event -> {
            mediaPlayer.stop();
            primaryStage.close();
            try {
                new ChessStage().start(new Stage());
                try (
                        ObjectInputStream input = new ObjectInputStream(new FileInputStream("preserve.cc"));

                ) {

                    Move.animalMap = (char[][]) (input.readObject());
                    ChessStage.setPlayer(input.readBoolean());
                    if (Animal.player == true) {
                        ChessStage.label.setText("It turns left");
                    } else {
                        ChessStage.label.setText("It turns right");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btExit.setOnAction(event -> primaryStage.close());
        btStyle.setOnAction(event -> {
        });

        //set background
        Image image = new Image("file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\animals\\Home2.jpg ");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(500);

        //set vBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(btNewGame, btPrevious, btStyle, btExit);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(30, 650, 250, 50));

        Pane pane = new Pane();
        pane.getChildren().addAll(imageView, vBox);


        //set scene
        Scene scene = new Scene(pane, 800 - 10, 500 - 10);
        primaryStage.setResizable(false);
        primaryStage.setTitle("WuXiYa's Animal Checker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class ButtonSet extends Button {
    public ButtonSet() {
        setText("Back");
        setMyFont(50, 200, 100);
    }

    public ButtonSet(String label) {
        setText(label);
        setMyFont(20, 150, 50);
    }

    public void setMyFont(int size, int weith, int height) {
        setFont(Font.font("Times New Roman", FontWeight.LIGHT, FontPosture.ITALIC, size));
        setPrefSize(weith, height);
        setStyle("-fx-border-color: lightblue");
        setStyle("-fx-background-color: ButtonHighlight");
    }
}