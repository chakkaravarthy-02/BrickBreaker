import java.util.HashMap;
import java.util.Map;

public class BrickBreaker {
    private static final String wall = "w ";
    private static final String ball = "o ";
    private static final String ground = "g ";
    private static final String brick = "1 ";

    public static int ballLife = 5;
    private static int[] ballPosition = null;
    private static String[][] gameBoard = null;
    private static final Map<Integer, Integer> brickLifeMap = new HashMap<>();

    private static int length;

    BrickBreaker(int row, int col) {
        gameBoard = new String[row][col];
        length = gameBoard.length;
        prepareBoard(row, col);
        gameBoard[row - 1][col / 2] = ball;
        ballPosition = new int[]{row - 1, col / 2};
    }

    private void prepareBoard(int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || j == 0 || j == col - 1) {
                    gameBoard[i][j] = wall;
                } else if (i == row - 1) {
                    gameBoard[i][j] = ground;
                } else {
                    gameBoard[i][j] = "  ";
                }
            }
        }
    }

    public void placeBrick(int row, int col, int brickLife) {
        gameBoard[row][col] = brick;
        int exactPosition = getExactPosition(row, col);
        brickLifeMap.put(exactPosition, brickLife);
    }

    private int getExactPosition(int row, int col) {
        return (row * length) + col + 1;
    }

    public void printBoard() {
        for (String[] games : gameBoard) {
            for (String game : games) {
                System.out.print(game);
            }
            System.out.println();
        }
    }

    public void initializeMoves(int ballRow, int ballCol, int rowDirection, int colDirection) {
        moveTheBall(ballRow, ballCol, rowDirection, colDirection);
        if (!gameBoard[ballRow][ballCol].equals(ball)) {
            gameBoard[ballRow][ballCol] = ground;
        }
    }

    private void moveTheBall(int ballRow, int ballCol, int rowDirection, int colDirection) {
        while (gameBoard[ballRow][ballCol].equals(wall)) {
            if (gameBoard[ballRow][ballCol].equals(brick)) {
                ballGoesDown(ballRow, ballCol);
                return;
            }
            movingIllusion(ballRow, ballCol);
            ballRow += rowDirection;
            ballCol += colDirection;
        }

        wallHit(ballRow, ballCol);

        rowDirection = 0;
        colDirection *= -1;

        if (colDirection == 0) {
            ballGoesDown(ballRow + 1, ballCol);
        } else {
            moveTheBall(ballRow, ballCol + colDirection, rowDirection, colDirection);
        }
    }

    private void wallHit(int ballRow, int ballCol) {
        gameBoard[ballRow][ballCol] = ball;
        printBoard();
        sleepForOneSec();
        gameBoard[ballRow][ballCol] = wall;
    }

    private void ballGoesDown(int ballRow, int ballCol) {
        while (ballRow != length) {
            movingIllusion(ballRow, ballCol);
            ballRow++;
        }
        ballPosition = new int[]{ballRow - 1, ballCol};
        gameBoard[ballPosition[0]][ballPosition[1]] = ball;
    }

    private void movingIllusion(int ballRow, int ballCol) {
        if (gameBoard[ballRow][ballCol].equals(brick)) {
            reduceBrickAndBallLife(ballRow, ballCol);
            if (brickLifeMap.get(getExactPosition(ballRow, ballCol)) == 0) {
                gameBoard[ballRow][ballCol] = "  ";
            }
        } else {
            gameBoard[ballRow][ballCol] = ball;
            printBoard();
            gameBoard[ballRow][ballCol] = "  ";
            sleepForOneSec();
        }
    }

    private void sleepForOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.getCause();
        }
    }

    private void reduceBrickAndBallLife(int ballRow, int ballCol) {
        int exactPosition = getExactPosition(ballRow, ballCol);
        ballLife--;
        if (ballLife > 0)
            brickLifeMap.put(exactPosition, brickLifeMap.get(exactPosition) - 1);
    }

    public int[] getBallPosition() {
        return ballPosition;
    }
}
