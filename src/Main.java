import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        BrickBreaker brickBreaker = new BrickBreaker(7,7);
        brickBreaker.placeBrick(2,2,1);
        brickBreaker.placeBrick(2,3,1);
        brickBreaker.placeBrick(2,4,1);
        brickBreaker.placeBrick(3,2,1);
        brickBreaker.placeBrick(3,4,1);
        Scanner scanner = new Scanner(System.in);

        while(true){
            if(BrickBreaker.ballLife == 0){
                System.out.println("Ball life over");
                System.exit(0);
            }
            brickBreaker.printBoard();
            System.out.println("Enter the direction: ");
            String direction = scanner.nextLine();
            switch (direction){
                case "st" -> {
                    int[] ballPos = brickBreaker.getBallPosition();
                    brickBreaker.initializeMoves(ballPos[0], ballPos[1], -1, 0);
                }
                case "lt" -> {
                    int[] ballPos = brickBreaker.getBallPosition();
                    brickBreaker.initializeMoves(ballPos[0], ballPos[1], -1, -1);
                }
                case "rt" -> {
                    int[] ballPos = brickBreaker.getBallPosition();
                    brickBreaker.initializeMoves(ballPos[0], ballPos[1], -1, 1);
                }
                default -> {
                    System.out.println("Enter valid direction");
                }
            }
        }
    }
}