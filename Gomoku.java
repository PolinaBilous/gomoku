package FirstGame;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Gomoku {
	public static int SIZE = 15;
	public static int WIN_COUNT = 5;

	public static char NO_WINNER = 0;
	public static char EMPTY = ' ';
	public static char HUMAN = 'X';
	public static char COMPUTER = 'O';

	public static char[][] gameTable = new char[SIZE][SIZE];
	public static JLabel cells[][];

	public static int count = 0;
	public static int[][] winningCoordinates = new int[WIN_COUNT][2];

	public static void init() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				gameTable[i][j] = EMPTY;
			}
		}
	}

	public static void makeTurn(int i, int j, char figure) {
		gameTable[i][j] = figure;
		drawFigure(i, j);
	}

	public static void drawFigure(int i, int j) {
		cells[i][j].setText(String.valueOf(gameTable[i][j]));
	}

	public static boolean isCellFree(int i, int j) {
		return gameTable[i][j] == EMPTY;
	}

	public static void makeHumanTurn(int i, int j) {
		makeTurn(i, j, HUMAN);
	}

	public static boolean hasEmptyCells() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (gameTable[i][j] == EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

	public static void clear() {
		count = 0;
	}

	public static void addWinningCoordinate(int row, int col) {
		winningCoordinates[count][0] = row;
		winningCoordinates[count][1] = col;
		count++;
	}

	public static void markWinningCombinationByRedColor() {
		for (int k = 0; k < WIN_COUNT; k++) {
			int row = winningCoordinates[k][0];
			int col = winningCoordinates[k][1];
			cells[row][col].setForeground(Color.RED);
			cells[row][col].setFont(new Font(Font.SERIF, Font.BOLD, 35));
		}
	}
	
	public static void makeComputerTurn() {
		for (int i = WIN_COUNT - 1; i > 0; i--) {
			for (int j = 0; j < 2; j++) {
				if(j == 0) {
					if(tryMakeTurn(COMPUTER, i) == true) return;
				}
				else {
					if(tryMakeTurn(HUMAN, i) == true) return;
				}
			}
		}
		if(makeRandomTurn()) {
			return;
		}
		}
	public static boolean findWinner(char figure) {
		if (findWinnerByRow(figure)) {
			return true;
		}
		if (findWinnerByCol(figure)) {
			return true;
		}
		if (findWinnerByMainDiagonale(figure)) {
			return true;
		}
		if (findWinnerBySecondDiagonale(figure)) {
			return true;
		}
		return false;
	}


	public static boolean findWinnerByRow(char figure)
	{
		clear();
        int count = 0;
    	for (int j = 0; j < SIZE; j++) {
    		 for (int k = 0; k < SIZE; k++)
	         {
	            if (gameTable[j][k] == figure)
	            {
	                addWinningCoordinate(j, k);
	                count++;
	            }
	            else 
	            { 
	            	count = 0; 
	            	clear();
	            }
	            if (count == WIN_COUNT)
 		        {
 		            markWinningCombinationByRedColor();
 		            return true;
 		        }
	        }
    	clear();
    	count = 0;
	    }
	    return false;
	}
	
	public static boolean findWinnerByCol(char figure)
	{
		clear();
        int count = 0;
    	for (int j = 0; j < SIZE; j++) {
    		 for (int k = 0; k < SIZE; k++)
	         {
	            if (gameTable[k][j] == figure)
	            {
	                addWinningCoordinate(k, j);
	                count++;
	            }
	            else 
	            { 
	            	count = 0; 
	            	clear();
	            }
	            if (count == WIN_COUNT)
 		        {
 		            markWinningCombinationByRedColor();
 		            return true;
 		        }
	        }
    	clear();
    	count = 0;
	    }
	    return false;
	}
	
	public static boolean findWinnerByMainDiagonale(char figure)
	{
		for (int i = 0; i < SIZE - WIN_COUNT - 1 ; i++) {
			for (int j = 0; j < SIZE - WIN_COUNT - 1; j++) {
				clear();
				int count = 0;
				for (int k = 0; k < WIN_COUNT; k++) {
					if (gameTable[i + k][j + k] == figure) {
						addWinningCoordinate(i + k, j + k);
						count++;
						if (count == WIN_COUNT) {
							markWinningCombinationByRedColor();
							return true;
						}
					} else {
						clear();
						count = 0;
					}
				}
			}
		}
		return false;
	}

	public static boolean findWinnerBySecondDiagonale(char figure)
	{
		for (int i = 0; i < SIZE - WIN_COUNT - 1 ; i++) {
			for (int j = WIN_COUNT - 1; j < SIZE; j++) {
				clear();
				int count = 0;
				for (int k = 0; k < WIN_COUNT; k++) {
					if (gameTable[i + k][j - k] == figure) {
						addWinningCoordinate(i + k, j - k);
						count++;
						if (count == WIN_COUNT) {
							markWinningCombinationByRedColor();
							return true;
						}
					} else {
						clear();
						count = 0;
					}
				}
			}
		}
		return false;
	}
	
	
		
	private static boolean tryMakeTurn(char figure, int empty) {
		boolean turn = tryMakeTurnByRows(empty, figure);
		if(turn != false) {
			return turn;
		}
		turn = tryMakeTurnByColumns(empty, figure);
		if(turn != false) {
			return turn;
		}
		turn = tryMakeTurnByByMainDiagonal(empty, figure);
		if(turn != false) {
			return turn;
		}
		turn = tryMakeTurnByNotMainDiagonal(empty, figure);
		if(turn != false) {
			return turn;
		}
		return false;
	}
	
    public static boolean tryMakeTurnByRows(int notEmptyCount, char figure) {
    	for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COUNT - 1; j++) {
				int rows = 0;
				int columns = 0;
				boolean hasEmptyCells = false;
				int count = 0;
				for (int k = 0; k < WIN_COUNT; k++) {	
					if (gameTable[i][j+k] == figure) {
						count++;
					} else if (gameTable[i][j+k] == EMPTY) {
						hasEmptyCells = true;
						rows = i;
						columns = j + k;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notEmptyCount && hasEmptyCells) {
					makeTurn(rows, columns, COMPUTER);
                    return true;
				}
			}
		}
		return false;
    }

    public static boolean tryMakeTurnByColumns(int notEmptyCount, char figure) {
    	for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE - WIN_COUNT - 1; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				int rows = 0;
				int columns = 0;
				for (int k = 0; k < WIN_COUNT; k++) {
					if (gameTable[j + k][i] == figure) {
						count++;
					} else if (gameTable[j + k][i] == EMPTY) {
						hasEmptyCells = true;
						rows = j + k;
						columns = i;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notEmptyCount && hasEmptyCells) {
					makeTurn(rows, columns, COMPUTER);
                    return true;
				}
			}
		}
		return false;
    }
    
    public static boolean tryMakeTurnByByMainDiagonal(int notEmptyCount, char figure) {
    	for (int i = 0; i < SIZE - WIN_COUNT - 1; i++) {
			for (int j = 0; j < SIZE - WIN_COUNT - 1; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				int rows = 0;
				int columns = 0;
				for (int k = 0; k < WIN_COUNT; k++) {
					if (gameTable[i + k][j + k] == figure) {
						count++;
					} else if (gameTable[i + k][j + k] == EMPTY) {
						hasEmptyCells = true;
						rows = i + k;
						columns = j + k;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notEmptyCount && hasEmptyCells) {
					makeTurn(rows, columns, COMPUTER);
                    return true;
				}
			}
		}
		return false;
    }

    public static boolean tryMakeTurnByNotMainDiagonal(int notEmptyCount, char figure) {
    	for (int i = 0; i < SIZE - WIN_COUNT - 1; i++) {
			for (int j = WIN_COUNT - 1; j < SIZE; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				int rows = 0;
				int columns = 0;
				for (int k = 0; k < WIN_COUNT; k++) {
					if (gameTable[i + k][j - k] == figure) {
						count++;
					} else if (gameTable[i + k][j - k] == EMPTY) {
						hasEmptyCells = true;
						rows = i + k;
						columns = j - k;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notEmptyCount && hasEmptyCells) {
					makeTurn(rows, columns, COMPUTER);
                    return true;
				}
			}
		}
		return false;
    }

    private static boolean makeRandomTurn() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (isCellFree(i, j)) {
					makeTurn(i, j, COMPUTER);
					return true;
				}
			}
		}
		return false;
	}
 
	
	public static void main(String[] args) {
		GUIGomoku.main(args);
	}
}