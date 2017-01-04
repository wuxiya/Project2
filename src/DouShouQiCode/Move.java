package DouShouQiCode;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static GUI.ChessStage.label;

/**
 * 移动和判断
 * Created by CiCi on 2016/11/26.
 */
public class Move {

    private static Move move = null;

    private static boolean player = Animal.player;

    public static char animalMap[][] = new char[7][9];

    public static char tileMap[][] = new char[7][9];

    public String[] animalName;

    private static int x;
    private static int y;
    private static int i;
    private static int j;
    private String aniS1;
    private String aniS2;


    private Move() throws FileNotFoundException {
        //读取地形地图，并且赋值，方便后续动物处在不同的地方

        Scanner s1 = new Scanner(new File("tile.txt"));
        String line1 = s1.nextLine();
        line1 += s1.nextLine();
        line1 += s1.nextLine();
        line1 += s1.nextLine();
        line1 += s1.nextLine();
        line1 += s1.nextLine();
        line1 += s1.nextLine();
        //读取动物地图并赋值
        Scanner s2 = new Scanner(new File("animal.txt"));
        String line = s2.nextLine();
        line += s2.nextLine();
        line += s2.nextLine();
        line += s2.nextLine();
        line += s2.nextLine();
        line += s2.nextLine();
        line += s2.nextLine();
        //读取地形地图，并且赋值，方便后续动物处在不同的地方
        int i;
        int j;

        for (i = 0; i < 7; i++) {
            for (j = 0; j < 9; j++) {
                char theChar1 = line1.charAt(i * 9 + j);
                tileMap[i][j] = theChar1;
                char theChar = line.charAt(i * 9 + j);
                animalMap[i][j] = theChar;
            }
        }
        animalName = new String[]{"老鼠", "小猫", "老狼", "小狗", "豹子", "老虎", "狮子", "大象"};

    }

    public int afterHaveChess() {

        /*reMis是move后所返回的值，代表各种输赢、继续走、犯规的情况。
         1、当reMis==05或者50时，代表有赢家出现，将reMis传入jundgeWinner()，判断胜负方，并结束游戏
         2、当reMis==0代表了下一步棋可以走，并且没有判断出输赢
         3、其余的返回值传到printMistake中，不同的数字代表着不同的违规点，打印违规点，输入无效并重新输入
         */
        int reMis = move();
        if (reMis == 05 || reMis == 50) {
            judgeWinner(reMis);
            return 11;//退出
        } else if (reMis == 0) {
            // historyChess.preserveStation(animalMap);


            //每次成功下完一步后，都利用judgeWinner()判断对方动物是否全部死亡。若没有则继续输入。
            if (judgeEatOut() != 0) {
                judgeWinner(judgeEatOut());
                return 11;//退出
            } else {
                if (Move.player == true) {
                    Move.player = false;
                } else {
                    Move.player = true;
                }
                return 00;//成功下了一步
            }
        } else {
            return 22;//违规
        }
    }

    /**
     * findMicePlace() 找到对方的老鼠此时的位置，给狮虎跳河找到先决条件。
     * moveShiHu()根据findMicePlace()返回值的不同，移动棋子
     * 通过不同的return 值来判别老鼠是否在河中，在哪条河。
     *
     * @return int
     */


    public int move() {


        //动物1的信息
        char anil = Animal.ani1;
        x = Animal.clickX1;
        y = Animal.clickY1;

        //第二次点击的信息
        char ani2 = Animal.ani2;
        i = Animal.clickX2;
        j = Animal.clickY2;

        char theChar1;

        //去处理动物的名字的问题
        int a = 0;
        int b = 0;
        if (player == true) {

            theChar1 = anil;

            a = animalMap[x][y] - '1';
            if (animalMap[i][j] != '0') {
                b = animalMap[i][j] - 'a';
                aniS2 = animalName[b];
            } else {
                aniS2 = " ";
            }

        } else {
            theChar1 = (char) (anil - 'a' + '1');

            a = animalMap[x][y] - 'a';
            if (animalMap[i][j] != '0') {
                b = animalMap[i][j] - '1';
                aniS2 = animalName[b];
            } else {
                aniS2 = " ";
            }
        }
        aniS1 = animalName[a];


        char s7 = (player) ? 'g' : '7';
        char s8 = (player) ? 'h' : '8';


        //由地形的判断
        int sum = x - i + y - j;

        if (sum == 1 || sum == (-1)) {
            if (tileMap[i][j] == '1') {//判断老鼠可以下河，狮虎在河中有对方老鼠时不可跳河
                switch (theChar1) {
                    case '1':
                        animalMap[i][j] = anil;
                        animalMap[x][y] = '0';
                        return 0;//老鼠可以随便下河
                    default:
                        label.setText(aniS1 + "不能下河，只有老鼠能下");
                        return 06;
                }
            } else if ((tileMap[i][j] == ((player) ? '3' : '5'))) {//不可进入自己的家中
                label.setText(aniS1 + "不能走进自己的家里");
                return 04;
            } else if (tileMap[i][j] == ((player) ? '5' : '3')) {//走入对方的家里，胜利
                return ((player) ? 05 : 50);//赢了
            } else if (animalMap[i][j] == '0' && tileMap[i][j] == '0') {//空地随便走
                animalMap[i][j] = anil;
                animalMap[x][y] = '0';
                return 0;
            } else if ((tileMap[i][j] == ((player) ? '2' : '4'))) {//自己家的陷阱随便走随便吃
                animalMap[i][j] = anil;
                animalMap[x][y] = '0';
                return 0;
            } else {

            }
        } else {
            if (judgeLegleMoveShiHu() == true) {
                switch (theChar1) {
                    case '6':
                   /*
                   利用moveShihu（）的返回值判断河中是否有敌方的老鼠
                   若有 return 16；若无，则处理下一步 i j，再判断河对面是否有更高阶的兽
                    */
                        if (moveShiHu(i) == 17) {
                            if (animalMap[i][j] == s7 || animalMap[i][j] == s8) {//对面有大象或狮子
                                label.setText(aniS1 + "不能吃" + aniS2);
                                return 07;
                            } else {
                                animalMap[i][j] = anil;
                                animalMap[x][y] = '0';
                                return 0;
                            }
                        } else {//河中有对方老鼠，狮虎不能跳河
                            return 16;
                        }
                    case '7':
                        if (moveShiHu(i) == 17) {
                            if (animalMap[i][j] == s8) {//对面有大象
                                label.setText(aniS1 + "不能吃" + aniS2);
                                return 07;
                            } else {
                                animalMap[i][j] = anil;
                                animalMap[x][y] = '0';
                                return 0;
                            }
                        } else {
                            return 16;
                        }
                }
            } else {
                label.setText(aniS1 + "除了河，其余不能蹦跶");
                return 18;//跳的并不是河
            }
        }
        //由动物判断

        //将食兽变为数字之差
        int daShou;
        if (Animal.aniS2.matches("[a-hA-H]")) {
            daShou = animalMap[i][j] - 'a' + '1' - animalMap[x][y];
        } else {
            daShou = animalMap[i][j] - (animalMap[x][y] - 'a' + '1');
        }

        if (daShou > 0 && daShou < 7) {//不能吃比自己强大的兽
            label.setText(aniS1 + "不能吃" + aniS2);
            return 07;
        } else if (daShou == (-7) && animalMap[x][y] == ((player) ? '8' : 'h')) {//大象在陆地不能吃老鼠
            label.setText(aniS1 + "在陆地，不能吃" + aniS2);
            return 18;
        } else if (daShou == (7) && tileMap[x][y] != '1') {//老鼠可以吃大象，并且不在水里
            animalMap[i][j] = anil;
            animalMap[x][y] = '0';
            return 0;
        } else if (daShou == (7) && tileMap[x][y] == '1') {//老鼠在水里不能吃大象
            label.setText(aniS1 + "在水里，不能吃" + aniS2);
            return 12;
        } else if (daShou == 0) {
            animalMap[i][j] = anil;
            animalMap[x][y] = '0';
            return 0;
        } else {
            animalMap[i][j] = anil;
            animalMap[x][y] = '0';
            return 0;
        }
    }

    //判断狮虎的跳是否在河中
    private static Boolean judgeLegleMoveShiHu() {
        if (i - x == 3) {
            if (tileMap[x + 1][y] == '1') {
                return true;
            }
        } else if (i - x == -3) {
            if (tileMap[x - 1][y] == '1') {
                return true;
            }
        } else if (j - y == 4) {
            if (tileMap[x][y + 1] == '1') {
                return true;
            }
        } else if (j - y == -4) {
            if (tileMap[x][y - 1] == '1') {
                return true;
            }

        } else {
            return false;
        }
        return false;
    }

    //判断河中是否有敌方的老鼠
    private int moveShiHu(int i) {

        int fmp = findMicePlace();
        if (i < 3) {
            if (fmp == 13) {
                label.setText(aniS1 + "不能跳河，河中有敌方老鼠");
                return 16;//有对方老鼠，狮虎不能跳河
            } else {
                return 17;
            }
        } else {
            if (fmp == 14) {
                label.setText(aniS1 + "不能跳河，河中有敌方老鼠");
                return 16;
            } else {
                return 17;//河中无对方老鼠，狮虎可以跳河
            }
        }
    }

    //找到敌方的老鼠
    private int findMicePlace() {
        int i;
        int j = 0;
        r:
        {
            for (i = 0; i < 7; i++) {
                for (j = 0; j < 9; j++) {
                    if (animalMap[i][j] == ((player) ? 'a' : '1')) {
                        break r;
                    }
                }
            }
        }
        if (i > 0 && i < 3 && j > 2 && j < 6) {//上方有敌方老鼠
            return 13;
        } else if (i > 3 && i < 6 && j > 2 && j < 6) {//下方有敌方老鼠
            return 14;
        } else {
            return 15;
        }
    }

    /**
     * judgeWinner() 用于打印胜负方
     * 通过move()的return值来判别胜负方
     */

    private static void judgeWinner(int winner) {
        if (winner == 05) {
            label.setText("恭喜左方玩家，您是世界第一帅");
        } else if (winner == 50) {
            label.setText("恭喜右方玩家，胜利属于-玉树临风，气度不凡，美貌动人的您");
        }
    }

    /**
     * judgeEatOut（）在每成功下好一步棋后，判断对方的棋子是否被吃完
     *
     * @return
     */
    private int judgeEatOut() {
        int sumLeft = -1;
        int sumRight = -1;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (player == true) {
                    if (animalMap[i][j] <= 'h' && animalMap[i][j] >= 'a') {
                        sumRight += sumRight + animalMap[i][j];
                    }
                } else {
                    if (animalMap[i][j] <= '8' && animalMap[i][j] >= '1') {
                        sumLeft += sumLeft + animalMap[i][j];
                    }
                }
            }
        }
        if (player == true) {
            if (sumRight < 0) {
                return 05;
            }
        } else {
            if (sumLeft < 0) {
                return 50;
            }
        }
        return 0;
    }

    public static Move getMove() throws FileNotFoundException {
        if (move == null) {
            move = new Move();
        }
        return move;
    }

}
