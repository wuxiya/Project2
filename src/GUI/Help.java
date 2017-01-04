package GUI;

import DouShouQiCode.Print;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by CiCi on 2016/12/27.
 */
public class Help extends Application {
    Print print = new Print();

    @Override
    public void start(Stage primaryStage) throws Exception {

        //set back button
        ButtonSet btBack = new ButtonSet();
        btBack.setOnAction(event -> {
            primaryStage.close();
        });

        //set help
        LabelSet label1 = new LabelSet(" 1、移动方式");
        LabelSet label2 = new LabelSet("      走入对方的家中为胜利");
        LabelSet label3 = new LabelSet("      强的可以吃弱的，根据战斗力分别对应鼠<猫<猴<狗<豹<虎<狮<象");
        LabelSet label4 = new LabelSet("      老鼠吃大象；其中只有老鼠能下河，狮虎能跳河");
        LabelSet label5 = new LabelSet(" 2、游戏指令——Tab");
        LabelSet label6 = new LabelSet("      点击restart重新开始游戏");
        LabelSet label7 = new LabelSet("      点击help查看帮助");
        LabelSet label8 = new LabelSet("      点击undo悔棋;redo撤销悔棋");
        LabelSet label9 = new LabelSet("      点击preserve保存；previous回到上一棋局");
        LabelSet label10 = new LabelSet("     点击back返回Home;点击exit退出游戏");


        VBox vBox = new VBox(5);

        //set background
        Image image = new Image("file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\animals\\Home2.jpg ");
        ImageView imageView = new ImageView(image);
        imageView.fitHeightProperty().bind(vBox.heightProperty());
        imageView.fitWidthProperty().bind(vBox.widthProperty());

        //set hBox
        vBox.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8, label9, label10);
        vBox.setPadding(new Insets(10, 0, 10, 10));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, vBox, btBack);

        Scene scene = new Scene(stackPane, 800, 400);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CiCi's Animal Checker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class LabelSet extends Label {
    public LabelSet(String label) {
        setText(label);
        setPrefSize(800, 20);
        setFont(Font.font("Times New Roman", FontWeight.LIGHT, FontPosture.ITALIC, 15));
        setStyle("-fx-border-color: lightblue");
        setStyle("-fx-background-color: ButtonHighlight");
    }
}