package DouShouQiCode;

import GUI.ChessStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;

import static GUI.ChessStage.*;


/**
 * Created by CiCi on 2016/12/25.
 */
public class Animal extends ImageView {

    public static Boolean player = ChessStage.player;
    public static HistoryChess historyChess;
    public static int clickX1;
    public static int clickY1;
    public static int clickX2;
    public static int clickY2;
    public static char ani1;
    public static char ani2;
    public static String aniS1;
    public static String aniS2;
    public int x;
    public int y;
    public static int clickCount = 0;
    public String path;

    public static ChessStage chessStage;

    static {
        try {
            chessStage = new ChessStage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            historyChess = HistoryChess.getHistoryChess();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String index;

    private static MediaPlayer mediaPlayer;

    private Media media;

    Move move = Move.getMove();


    public Animal(int i, int j) throws FileNotFoundException {
        x = i;
        y = j;


        //set animal image
        setFitHeight(86);
        setFitWidth(90);
        setImage(getAnimalImage(i, j));


        //to deal with the event
        gridPane.setOnMouseClicked(event -> {

            try {
                handMouseClick(event);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void handMouseClick(MouseEvent event) throws FileNotFoundException {

        //to get the click animal's location
        if (clickCount == 0) {

            //to get the click message
            clickX1 = (int) ((event.getSceneY() - 80) / 90);
            clickY1 = (int) ((event.getSceneX() + 10) / 90);
            ani1 = move.animalMap[clickX1][clickY1];
            aniS1 = new String(String.valueOf(ani1));

            if (player == true) {
                if (aniS1.matches("[1-8]")) {
                    //TODO:加上图片的闪烁效果

                    getMediaPlayer().play();

                    clickCount = 1;

                    chessStage.label.setText("It turns left");
                }
            } else {
                if (aniS1.matches("[a-h]")) {

                    getMediaPlayer().play();

                    clickCount = 1;

                    chessStage.label.setText("It turns right");
                }
            }

            //to move and judge
        } else if (clickCount == 1) {

            //to get the click2 message
            clickX2 = (int) ((event.getSceneY() - 80) / 90);
            clickY2 = (int) ((event.getSceneX() + 10) / 90);
            ani2 = move.animalMap[clickX2][clickY2];
            aniS2 = new String(String.valueOf(ani2));


            //judgeLegal 判断点击的棋子不是同一个，并且在同一直线，除了老虎狮子以外不蹦着走
            if (judgeLegal() == 1) {


                if (aniS2.matches((player) ? "[1-8]" : "[a-h]")) {

                    chessStage.label.setText("不能自相残杀");
                    clickCount = 0;

                } else {
                    int deal = dealContact();
                    if (player == true) {

                        if (deal == 0) {

                            player = !player;

                            chessStage.label.setText("It turns right");
                        }
                    } else {

                        if (deal == 0) {
                            player = !player;
                            chessStage.label.setText("It turns left");
                        }
                    }
                }
                if (clickCount == 2) {
                    historyChess.preserveStation();
                    clickCount = 0;

                }
            } else {

            }
        }
    }

    //contact和move中的方法进行联系，改变
    private int dealContact() throws FileNotFoundException {
        int step = move.afterHaveChess();
        if (step == 11) {
            //ft.stop();
            return 11;//胜负已判，直接退出
        } else if (step == 22) {
            //ft.stop();
            clickCount = 0;
            return 22;//违规
        } else {
            clickCount = 2;

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    gridPane.getChildren().remove(cell[i][j]);
                    cell[i][j] = new Animal(i, j);
                    gridPane.add(cell[i][j], j, i);
                }
            }
            // ft.stop();
            return 0;//继续下棋
        }
    }

    //判断第二次点击的是否合理，同一点点两次的话取消闪烁，不能交叉跳
    private int judgeLegal() {

        if (clickX1 == clickX2 && clickY2 == clickY1) {

            clickCount = 0;

            chessStage.label.setText("同一个不能点两次");

            return 0;//同一个动物点两次
        } else if (clickX1 != clickX2 && clickY2 != clickY1) {
            chessStage.label.setText("请按照游戏规则进行");

            clickCount = 0;

            return 0;//交叉走

        } else {

            if ((aniS1.equals((player) ? "7" : "g") || (aniS1.equals((player) ? "6" : "f")))) {

                return 1;
            } else {

                int sum = clickX1 - clickX2 + clickY1 - clickY2;

                if (sum != 1 && sum != (-1)) {

                    chessStage.label.setText("不能蹦着走");
                    clickCount = 0;


                    return 0;//除了狮子老虎以外蹦着走
                }
            }
        }
        return 1;
    }

    //处理当玩家点击按钮时的信息，悔棋，帮助等
    public static void play(String input) throws FileNotFoundException {

        if (input.equals("restart")) {

            historyChess.restart();

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    gridPane.getChildren().remove(cell[i][j]);
                    cell[i][j] = new Animal(i, j);
                    gridPane.add(cell[i][j], j, i);
                }
            }
            player = true;
            clickCount = 0;
            if (player == true) {
                label.setText("It turns left");
            } else {
                label.setText("It turns right");
            }

        } else if (input.equals("undo")) {
            boolean success = historyChess.undo();
            if (!success) {
                label.setText("已经到开局了，不能再悔棋了");
            } else {

                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 9; j++) {
                        gridPane.getChildren().remove(cell[i][j]);
                        cell[i][j] = new Animal(i, j);
                        gridPane.add(cell[i][j], j, i);
                    }
                }
                player = !player;
                clickCount = 0;
                if (player == true) {
                    label.setText("It turns left");
                } else {
                    label.setText("It turns right");
                }
            }
        } else if (input.equals("preserve")) {
            try (
                    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("preserve.cc"));

            ) {

                output.writeObject(Move.animalMap);
                output.writeBoolean(player);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (input.equals("redo")) {
            boolean success = historyChess.redo();
            if (!success) {
                label.setText("已经到当前局面，不可再撤销悔棋");
            } else {
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 9; j++) {
                        gridPane.getChildren().remove(cell[i][j]);
                        cell[i][j] = new Animal(i, j);
                        gridPane.add(cell[i][j], j, i);
                    }
                }
                player = !player;
                clickCount = 0;
                if (player == true) {
                    label.setText("It turns left");
                } else {
                    label.setText("It turns right");
                }
            }
        }
    }

    //处理音乐
    private MediaPlayer getMediaPlayer() {
        String musicName = new String(" ");
        if (cell[clickX1][clickY1].getAniS1().matches("[1-8]")) {
            musicName = new String(cell[clickX1][clickY1].getAniS1());
        } else if (cell[clickX1][clickY1].getAniS1().matches("[a-g]")) {
            musicName = new String(String.valueOf((char) (cell[clickX1][clickY1].getAni1() - 'a' + '1')));
        }
        String path = "C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\audio\\";
        String s1 = new File(path + musicName + ".mp3").toURI().toString();

        media = new Media(s1);
        mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }

    //处理图形
    private Image getAnimalImage(int i, int j) {
        path = "file:C:\\Users\\CiCi\\Desktop\\JAVA学习\\PJ2DouShouQi\\image\\animals\\";
        index = "";
        Image image;
        if (move.animalMap[i][j] != '0') {

            switch (move.animalMap[i][j]) {
                case '1':
                    index = "1";
                    break;
                case '2':
                    index = "2";
                    break;
                case '3':
                    index = "3";
                    break;
                case '4':
                    index = "4";
                    break;
                case '5':
                    index = "5";
                    break;
                case '6':
                    index = "6";
                    break;
                case '7':
                    index = "7";
                    break;
                case '8':
                    index = "8";
                    break;
                case 'a':
                    index = "a";
                    break;
                case 'b':
                    index = "b";
                    break;
                case 'c':
                    index = "c";
                    break;
                case 'd':
                    index = "d";
                    break;
                case 'e':
                    index = "e";
                    break;
                case 'f':
                    index = "f";
                    break;
                case 'g':
                    index = "g";
                    break;
                case 'h':
                    index = "h";
                    break;
                default:
                    break;
            }
            image = new Image(path + index + ".png");
        } else {
            image = new Image(path + 0 + ".png");
        }
        return image;
    }

    //aniS1表示了第一个动物，用于图形和音乐的路径
    private String getAniS1() {
        return aniS1;
    }

    //用于处理路径
    private char getAni1() {
        return ani1;
    }

}


