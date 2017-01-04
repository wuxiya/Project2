package GUI;

import DouShouQiCode.Animal;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by CiCi on 2016/12/13.
 */
public class ChessStage extends Application {
    public static Animal[][] cell = new Animal[7][9];

    //private  Map map = new Map();
    private static MediaPlayer mediaPlayer;
    private Media media;
    public static Label label;
    public static GridPane gridPane = new GridPane();
    public static Boolean player = true;

    public ChessStage() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("WuXiYa's Animal Checker");
        String path = "file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\animals\\";


        Menu menuTab = new Menu("Tab");
        MenuItem itRestart = new MenuItem("Restart");
        MenuItem itUndo = new MenuItem("Undo");
        MenuItem itRedo = new MenuItem("Redo");
        MenuItem itPreserve = new MenuItem("Preserve");
        MenuItem itGOBack = new MenuItem("Back");
        MenuItem itExit = new MenuItem("Exit");
        menuTab.getItems().addAll(itRestart, itUndo, itRedo, itPreserve, itGOBack, itExit);

        itRestart.setOnAction(event -> {

            try {
                Animal.play("restart");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        itGOBack.setOnAction(event -> {
            mediaPlayer.stop();
            primaryStage.close();
            try {
                new PlayGame().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        itExit.setOnAction(event -> {
            primaryStage.close();
        });


        itPreserve.setOnAction(event -> {
            try {
                Animal.play("preserve");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        itRedo.setOnAction(event -> {
            try {
                Animal.play("redo");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        itUndo.setOnAction(event -> {
            try {
                Animal.play("undo");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });


        Menu menuHelp = new Menu("Help");
        MenuItem itHelp = new MenuItem("Rules");
        menuHelp.getItems().addAll(itHelp);
        itHelp.setOnAction(event -> {
            try {
                new Help().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //set music menu
        Menu menuMusic = new Menu("Music");
        MenuItem itBgm = new MenuItem("bgm");
        MenuItem itLouLan = new MenuItem("ひかりにわ楼兰");
        MenuItem itSky = new MenuItem("天空之城");
        MenuItem itQingHuaCi = new MenuItem("QingHuaCi");
        MenuItem itWeWill = new MenuItem("We Will Rock You");
        MenuItem itClose = new MenuItem("Close");

        menuMusic.getItems().addAll(itBgm, itSky, itLouLan, itWeWill, itQingHuaCi, itClose);


        //set background music
        getMediaPlayer("ひかりにわ楼兰").play();
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);

        itBgm.setOnAction(event -> {
            mediaPlayer.stop();
            getMediaPlayer("Bgm").play();
        });
        itSky.setOnAction(event -> {
            mediaPlayer.stop();
            getMediaPlayer("天空之城").play();
        });
        itLouLan.setOnAction(event -> {
            mediaPlayer.stop();
            getMediaPlayer("ひかりにわ楼兰").play();
        });
        itWeWill.setOnAction(event -> {
            mediaPlayer.stop();
            getMediaPlayer("We Will Rock You").play();
        });
        itQingHuaCi.setOnAction(event -> {
            mediaPlayer.stop();
            getMediaPlayer("QingHuaCi").play();
        });
        itClose.setOnAction(event -> {
            mediaPlayer.stop();
        });


        //set menuBar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuTab, menuHelp, menuMusic);


        //set vBox
        GridPane gridPane = new GridPane();

        gridPane.setPrefSize(9 * 90, 7 * 90);
        gridPane.getChildren().addAll(getBackImageView(), getGridPane());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(getStackPane(), gridPane);


        //set borderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vBox);


        //set scene
        Scene scene = new Scene(borderPane, 9 * 90 - 10, 7 * 90 + 80);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //set background

    private ImageView getBackImageView() {
        Image image = new Image("file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\Map1.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(7 * 90);
        imageView.setFitWidth(9 * 90);

        return imageView;
    }

    public MediaPlayer getMediaPlayer(String s) {
        String path = "C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\audio\\";
        String s1 = new File(path + s + ".mp3").toURI().toString();
        String name = s1;
        media = new Media(name);
        mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    //set hBox
    private StackPane getStackPane() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        label = new Label("Game start");
        label.setFont(Font.font("Times New Roman", FontWeight.LIGHT, FontPosture.ITALIC, 35));
        hBox.setPrefSize(9 * 90 - 10, 80);

        Image image = new Image("file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\animals\\Home6.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(9 * 90 - 10);
        hBox.getChildren().addAll(label);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, hBox);
        return stackPane;
    }

    //set gridPane
    public GridPane getGridPane() throws FileNotFoundException {

        gridPane = new GridPane();


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {


                cell[i][j] = new Animal(i, j);
                gridPane.add(cell[i][j], j, i);

            }
        }
        return gridPane;
    }


    public Animal[][] getCell() {
        return cell;
    }

    public static void setPlayer(Boolean player) {

    }
}
