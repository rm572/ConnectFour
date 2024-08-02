// package ConnectFour;

import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;

public class GameGraphics {
	public static String[][] BOARD = {{" ", " ", " ", " ", " ", " ", " "}, 
									  {" ", " ", " ", " ", " ", " ", " "}, 
									  {" ", " ", " ", " ", " ", " ", " "}, 
									  {" ", " ", " ", " ", " ", " ", " "}, 
									  {" ", " ", " ", " ", " ", " ", " "}, 
									  {" ", " ", " ", " ", " ", " ", " "}};
	public static int[] COLS = {0, 0, 0, 0, 0, 0, 0};
	public static DrawingPanel PANEL = new DrawingPanel(715, 700);
	public static Graphics G = PANEL.getGraphics();
	public static int LENGTH = PANEL.getWidth();
	public static int PLAYER = 0;
	public static int DIFF = PANEL.getHeight() - LENGTH;
	public static int HEIGHT = PANEL.getHeight();
	public static int POS = (LENGTH)/7;
	public static int LW = 15;
	public static int TOTAL_MOVES = 0;
	public static int[] SCOREBOARD = {0, 0};
	public static boolean noBest = false;

public static void main(String[] args) {
	Scanner console = new Scanner(System.in);
	while(true) {
		boolean gameOn = true;
		TOTAL_MOVES = 0;
		resetBoard();
		printBoard();
		String player = "Player";
		PLAYER = 1;
		while(gameOn) {
			System.out.println("Total Moves: " + TOTAL_MOVES);
			PLAYER = (PLAYER + 1) % 2;
			int[] moves;
			// updateScore();
			if (PLAYER == 0) {
				player = "Player";
				moves = playerMove(console);
				printBoard();
			}
			else {
				player = "Computer";
				System.out.println(player + " move");
				moves = compMove();
				printBoard();
			}
			printCOLS();
			
			TOTAL_MOVES++;		
			// gameOn = contGame();
			gameOn = contGame2(moves[0], moves[1]);
			if(TOTAL_MOVES == 42) {
				break;
			}
		}
		
		
		
		if (!gameOn) {
			System.out.println(player + " wins!");
//				PLAYER = (PLAYER + 1) % 2;
			SCOREBOARD[PLAYER]++;
		}
		else if (TOTAL_MOVES == 42) {
			System.out.println("It's a draw!");
		}
		
		System.out.println();
		System.out.println("*****SCORE*****");
		System.out.println(SCOREBOARD[0] + " : " + SCOREBOARD[1]);
		System.out.println("***************");
		System.out.println("Do you want to play again? ");
		System.out.print("Press q to quit and any other key to continue: ");
		String s = console.next();
		System.out.println();
		if (s.toUpperCase().equals("Q")) {
			break;
		}
		
	}
	
	console.close();
}

	public static void printCOLS() {
		System.out.println(Arrays.toString(COLS));
	}
	
	// public static String[][] playerMove(int col, int row, int player) {
	// 	String p;
	// 	if (player == 0) {
	// 		p = "O";
	// 		G.setColor(Color.GREEN);
	// 		G.fillOval(col * LW + col * POS, 100 + row * LW + row * POS, POS, POS);
			
	// 	}
	// 	else {
	// 		p = "X";
	// 		G.setColor(Color.RED);
	// 		G.fillOval(col * LW + col * POS, 100 + row * LW + row * POS, POS, POS);
	// 	}
	// 	BOARD[row][col] = p;
		
	// 	return BOARD;
	// }

	public static int[] playerMove(Scanner console) {
		int col;
		while(true) {
			System.out.println("Player move");
			System.out.print("Enter col: ");
			col = console.nextInt();
			if (isMoveLegal(col)) {
				break;
			}
			

		}
        int row = COLS[col];
        COLS[col] = row + 1;
		BOARD[row][col] = "O";
		G.setColor(Color.GREEN);
		// G.fillOval(col * LW + col * POS, 100 + row * LW + row * POS, POS, POS);
		G.fillOval(LW + POS * col, 540-(LW + row * POS), POS-LW, POS-LW);
		return new int[]{row, col};
		// return BOARD;
	}
	public static boolean isMoveLegal(int c) {
		try { 
            int r = COLS[c];
            // System.out.println("r is: " + r);
			if ((0 <= c && c <= 6) && (0 <= r && r <= 5)) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}

	}
	
	public static boolean contGame() {
		//Vertical
		if (!(BOARD[0][0].equals(" ")) && ((BOARD[0][0] == BOARD[1][0]) && (BOARD[0][0] == BOARD[2][0]))) {
			return false; 
		}
		else if (!(BOARD[1][0].equals(" ")) && ((BOARD[1][0] == BOARD[1][1]) && (BOARD[1][0] == BOARD[1][2]))) {
			return false;
		}
		else if (!(BOARD[2][0].equals(" ")) && ((BOARD[2][0] == BOARD[2][1]) && (BOARD[2][0] == BOARD[2][2]))) {
			return false;
		}
		//Horizontal
		else if (!(BOARD[0][0].equals(" ")) && ((BOARD[0][0] == BOARD[0][1]) && (BOARD[0][0] == BOARD[0][2]))) {
			return false;
		}
		else if (!(BOARD[0][1].equals(" ")) && ((BOARD[0][1] == BOARD[1][1]) && (BOARD[0][1] == BOARD[2][1]))) {
			return false;
		}	
		else if (!(BOARD[0][2].equals(" ")) && ((BOARD[0][2] == BOARD[1][2]) && (BOARD[0][2] == BOARD[2][2]))) {
			return false;
		}
		//diagonal
		else if (!(BOARD[0][0].equals(" ")) && ((BOARD[0][0] == BOARD[1][1]) && (BOARD[0][0] == BOARD[2][2]))) {
			return false;
		}
		else if (!(BOARD[2][0].equals(" ")) && ((BOARD[2][0] == BOARD[1][1]) && (BOARD[2][0] == BOARD[0][2]))) {
			return false;
		}
		return true;
	}

	public static boolean contGame2(int row, int col) {
		//Vertical
		if (row >= 3 && BOARD[row][col].equals(BOARD[row-1][col]) && BOARD[row-1][col].equals(BOARD[row-2][col]) && BOARD[row-2][col].equals(BOARD[row-3][col])) {
			return false;
		}

		//Horizontal
		for (int c = 0; c < 4; c++) {
			if (!BOARD[row][c].equals(" ") && BOARD[row][c].equals(BOARD[row][c+1]) && BOARD[row][c].equals(BOARD[row][c+2]) && BOARD[row][c].equals(BOARD[row][c+3])) {
				return false;
			}
		}

		

		for (int r = 0; r < 6 - 3; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (!BOARD[r][c].equals(" ") && BOARD[r][c].equals(BOARD[r + 1][c + 1]) && BOARD[r + 1][c + 1].equals(BOARD[r + 2][c + 2]) && BOARD[r + 2][c + 2].equals(BOARD[r + 3][c + 3])) {
                    return false;
                }
            }
        }
        for (int r = 3; r < 6; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (!BOARD[r][c].equals(" ") && BOARD[r][c].equals(BOARD[r - 1][c + 1]) && BOARD[r - 1][c + 1].equals(BOARD[r - 2][c + 2]) && BOARD[r - 2][c + 2].equals(BOARD[r - 3][c + 3])) {
                    return false;
                }
            }
        }



		return true;
	}
	
	public static void printBoard() {
		for (int r = 5; r >= 0; r--) {
			for (int c = 0; c < 7; c++) {
				System.out.print("|" + BOARD[r][c]);
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.println(" 0 1 2 3 4 5 6");
	}
	
	public static void resetBoard() {
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				BOARD[r][c] = " ";
                COLS[c] = 0;
			}
		}

		PANEL.clear();
		G.setColor(Color.BLACK);
		G.fillRect(0, DIFF, LW, 630);
		for (int c = 1; c <= 7; c++) {
			// vertical
			G.fillRect(c * POS, DIFF, LW, 630);

			//horizontal
			if (c <= 7) {
				G.fillRect(0, (c-1) * POS, LENGTH, LW);
			}
			

			//vertical lines
			// G.fillRect(POS, DIFF, LW, LENGTH);
			// G.fillRect(2 * POS + LW, DIFF, LW, LENGTH);
			
			// //horizontal lines
			// G.fillRect(0, DIFF + POS, LENGTH, LW);
			// G.fillRect(0, DIFF + 2 * POS + LW, LENGTH, LW);
		}
		write();
	}

	public static void write() {
		G.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 		
		G.setColor(Color.BLUE);
		G.drawString(" 0      1      2      3      4      5      6", 30, 675);

	}
	
	public static void updateScore() {
		G.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		G.setColor(Color.GREEN);
		G.drawString(SCOREBOARD[0] + " ", LENGTH/2 - 50, 50);
		
		G.setColor(Color.BLACK);
		G.drawString(":", LENGTH/2, 50);
		
		G.setColor(Color.RED);
		G.drawString(" " + SCOREBOARD[1], LENGTH/2 + 30, 50);
	}

	public static int[] compMove() {
		int row;
		int col;
		System.out.println("comp");
		
		if (TOTAL_MOVES == 1) {
			if(COLS[2] == 0) {
				row = 0;
				col = 2;
			}
			else {
				row = 0;
				col = 3;
			}
			COLS[col] = 1;
		}
		else {
			int[] temp = findBestMove();
			row = temp[0];
			col = temp[1];
		}
		BOARD[row][col] = "X";
		G.setColor(Color.RED);
		G.fillOval(LW + POS * col, 540-(LW + row * POS), POS-LW, POS-LW);
		return new int[]{row, col};
	}
	

	public static boolean isMoveLegal(int r, int c) {
		try {
			if ((0 <= r && r <= 2) && (0 <= c && c <= 2) && BOARD[r][c].equals(" ")) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}

	}

	public static boolean isMovesLeft() {
	    for (int r = 0; r < 3; r++) {
	        for (int c = 0; c < 3; c++) {
	            if (BOARD[r][c].equals(" ")) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	public static int chooseCOL() {
		if (COLS[2] <= COLS[3] && COLS[2] <= COLS[4] && isMoveLegal(2)) {
			return 2;
		}

		else if (COLS[3] <= COLS[2] && COLS[3] <= COLS[4] && isMoveLegal(3)) {
			return 3;
		}

		else if (COLS[4] <= COLS[2] && COLS[4] <= COLS[3] && isMoveLegal(4)) {
			return 4;
		}
		else {
			if (isMoveLegal(2)) {
				return 2;
			}
			else if (isMoveLegal(4)) {
				return 4;
			}
			else if (isMoveLegal(3)) {
				return 3;
			}
			else if (isMoveLegal(1)) {
				return 1;
			}
			else if (isMoveLegal(5)) {
				return 5;
			}
			else if (isMoveLegal(0)) {
				return 0;
			}
			else {
				return 6;
			}
		}		



	}

	public static int evaluate(boolean g) {
		if (g) {
			return 0;
		}

		else {
			return (PLAYER == 1) ? 20 : -20;
		}
	}

	// public static int minimax(boolean isComp, int depth)	{
	public static int minimax(boolean isComp, int depth, int row, int col) {
		// int score = evaluate(contGame());
		int score = evaluate(contGame2(row, col));

		if (score == 20) {
			return score - depth;
		}
		else if (score == -20) {
			return score + depth;
		}
		if (depth == 6) {
			noBest = true;
			return 0;
		}
		if (isMovesLeft() == false) {
			return 0;
		}
		
		if (isComp) {
			int best = Integer.MIN_VALUE;

            for (int c = 0; c < 7; c++) {
                if (isMoveLegal(c)) {
                    int r = COLS[c];
                    COLS[c] = r + 1;
                    BOARD[r][c] = "X";
                    
                    best = Math.max(best, minimax(false, depth + 1, r, c));
                    BOARD[r][c] = " ";
                    COLS[c] = COLS[c] - 1;
                }
            }
			return best;
		}
		
		else {
			int best = Integer.MAX_VALUE;
			
            for (int c = 0; c < 7; c++) {
                if (isMoveLegal(c)) {
                    int r = COLS[c];
                    COLS[c] = r + 1;
                    BOARD[r][c] = "O";
                    best = Math.min(best, minimax(true, depth + 1, r, c));
                    BOARD[r][c] = " ";
                    COLS[c] = COLS[c]-1;
                }
            }
			return best;
		}
	}
	
	
	public static int[] findBestMove()	{
		int bestVal = -1000;
		int[] bestMove = new int[2];
		int col = -1;
		int row = -1;
		boolean n = false;

        for (int c = 0; c < 7; c++){
            if (isMoveLegal(c)) {
                int r = COLS[c];
                COLS[c] = r + 1;
                BOARD[r][c] = "X";
                if (contGame2(r, c) == false) {
                    return new int[]{r, c};
                }
                BOARD[r][c] = "O";
				// BOARD[r][c] = " ";
                // BOARD[r][c] = "O";
				

                int moveVal = minimax(true, 0, r, c);
        
                BOARD[r][c] = " ";
                COLS[c] = COLS[c] - 1;
        
                if (moveVal > bestVal) {
                    col = c;
                    row = r;
                    bestVal = moveVal;
                    n = noBest;
                }
				noBest = false;
            }
        }
		if (!n) {
			bestMove[0] = row;
			bestMove[1] = col;
			COLS[col] = COLS[col] + 1;
		}
		else {
			int c = chooseCOL();
			int r = COLS[c];
			COLS[c] = r + 1;
			bestMove[0] = r;
			bestMove[1] = c;
		}
		noBest = false;
		return bestMove;
	}
	
}