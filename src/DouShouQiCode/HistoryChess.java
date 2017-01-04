package DouShouQiCode;

import java.io.FileNotFoundException;

/**
 * 用于保存棋盘，悔棋，撤销悔棋
 * Created by CiCi on 2016/11/26.
 */
public class HistoryChess {

    private static char historyStep[][][];
    private static int currentStep;
    private static int maxStep;
    private static HistoryChess historyChess = null;
    private Move move = Move.getMove();

    HistoryChess() throws FileNotFoundException {
        this.historyStep = new char[1024][7][9];
        this.maxStep = 0;
        for (int i = 0; i < Move.animalMap.length; i++) {
            for (int j = 0; j < Move.animalMap[i].length; j++) {
                historyStep[0][i][j] = Move.animalMap[i][j];
            }
        }
    }

    public void preserveStation() {
        currentStep++;
        for (int i = 0; i < Move.animalMap.length; i++) {
            for (int j = 0; j < Move.animalMap[i].length; j++) {
                historyStep[currentStep][i][j] = Move.animalMap[i][j];
            }
        }
        maxStep = this.currentStep;
    }

    private void giveStation() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                Move.animalMap[i][j] = historyStep[currentStep][i][j];
            }
        }
    }

    public boolean undo() {
        if (this.currentStep <= 0) {
            return false;
        } else {
            this.currentStep--;
            giveStation();
        }
        return true;
    }

    public boolean redo() {
        if (currentStep == maxStep) {
            return false;
        } else {
            currentStep++;
            giveStation();
        }
        return true;
    }

    public void restart() {
        this.currentStep = 0;
        giveStation();
    }

    public static HistoryChess getHistoryChess() throws FileNotFoundException {
        if (historyChess == null) {
            historyChess = new HistoryChess();
        }
        return historyChess;
    }
}
