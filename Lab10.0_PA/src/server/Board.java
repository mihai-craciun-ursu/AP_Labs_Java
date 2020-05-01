package server;

public class Board {
    private int[][] matrixGame = new int[16][16];

    public Board(){
        for(int i = 0; i<16; i++){
            for(int j = 0; j<16; j++){
                matrixGame[i][j] = 0;
            }
        }
    }

    public boolean isSpaceFree(int i, int j){
        return (matrixGame[i][j] == 0);
    }

    public void setPiece(int i, int j, int turn){
        matrixGame[i][j] = turn;
    }

    public boolean fiveInARow(int i, int j, int turn) {
        int maxH = 1;
        int maxV = 1;
        int maxD1 = 1;
        int maxD2 = 1;

        int left = j;
        int right = j;

        int top = i;
        int bottom = i;

        int topLeftI = i;
        int topLeftJ = j;
        int bottomRightI = i;
        int bottomRightJ = j;


        int topRightI = i;
        int topRightJ = j;
        int bottomLeftI = i;
        int bottomLeftJ = j;

        //horizontal
        while (right < 15 && matrixGame[i][right] == turn) {
            right++;
        }
        while (left > 0 && matrixGame[i][left] == turn) {
            left--;
        }

        if (right - left > 5) {
            return true;
        }

        //vertical
        while (bottom < 15 && matrixGame[bottom][j] == turn) {
            bottom++;
        }
        while (top > 0 && matrixGame[top][j] == turn) {
            top--;
        }

        if (bottom - top > 5) {
            return true;
        }

        //D1
        while(bottomRightI < 15 && bottomRightJ < 15 && matrixGame[bottomRightI][bottomRightJ] == turn){
            bottomRightI++;
            bottomRightJ++;
        }
        while(topLeftI > 0 && topLeftJ > 0 && matrixGame[topLeftI][topLeftJ] == turn){
            topLeftI--;
            topLeftJ--;
        }

        if(bottomRightI - topLeftI > 5){
            return true;
        }

        //d2
        while(bottomLeftI < 15 && bottomLeftJ > 0 && matrixGame[bottomLeftI][bottomLeftJ] == turn){
            bottomLeftJ--;
            bottomLeftI++;
        }
        while(topRightI < 15 && topRightJ > 0 && matrixGame[topRightI][topRightJ] == turn){
            topRightI++;
            topRightJ--;
        }
        if(topRightI - bottomLeftJ > 5){
            return true;
        }


        return false;
    }
}
