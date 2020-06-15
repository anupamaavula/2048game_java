package com.codegym.games.game2048;
import com.codegym.engine.cell.*;
import com.codegym.engine.cell.Game;
import java.util.Arrays;

public class Game2048 extends Game{
    
    private static final int SIDE=4;
    
    private int[][] gameField = new int [SIDE][SIDE];
    
    private boolean isGameStopped = false;
    
    private int score=0;
    
    @Override
    public void initialize() {
        
        setScreenSize(SIDE,SIDE);
        
        createGame();
        
        drawScene();
        
    }
    public void onKeyPress(Key key){
        if ( isGameStopped) {               // Checks if the game is stopped before the game over conditions
            if (key == Key.SPACE)
                restart();
                return;
        }
        
       if( key == Key.LEFT) {
           moveLeft();
           drawScene();
       }
       else if(key == Key.RIGHT) {
           moveRight();
           drawScene();
       }
       else if(key == Key.UP) {
           moveUp();
           drawScene();
       }
       else if(key == Key.DOWN){
                moveDown();
                drawScene();
        }
        
        if (!canUserMove()) {
            gameOver();
            if (key == Key.SPACE){
                restart();
            }
            return;
        }
    }
    private void restart () {
        score=0;
        isGameStopped = false;
        createGame();
        drawScene();
        setScore(score);
    }

    private void moveLeft(){
        
        boolean compress;    
        boolean merge;         
        boolean compresss;    
        int move=0;                  
       for (int i = 0; i < SIDE; i++){
            compress = compressRow(gameField[i]);
            merge = mergeRow(gameField[i]);
            compresss=compressRow(gameField[i]);
            if(compress || merge || compresss)
                  move++;
           }
       if( move!= 0 ){
           createNewNumber();

} 
    }
    private void moveRight(){
        boolean compressed = false;
        boolean merged = false;
        boolean compressed2 = false;
        int move = 0; 
        rotateClockwise(); 
        rotateClockwise(); 

        for (int i = 0; i < SIDE; i++)
        {

            compressed = compressRow(gameField[i]); 
            merged = mergeRow(gameField[i]);
            compressed2 = compressRow(gameField[i]);

            if (compressed || merged || compressed2)
                move ++;
        }

        if (move != 0)
            createNewNumber();

        rotateClockwise();
        rotateClockwise();
        
    }
    private void moveUp(){
        boolean compressed = false;
        boolean merged = false;
        boolean compressed2 = false;
        int move = 0; 

        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        for (int i = 0; i < SIDE; i++)
        {

            compressed = compressRow(gameField[i]); 
            merged = mergeRow(gameField[i]);
            compressed2 = compressRow(gameField[i]);

            if (compressed || merged || compressed2)
                move ++;
        }

        if (move != 0)
            createNewNumber();

        rotateClockwise();
        
    }
    private void moveDown(){
        boolean compressed = false;
        boolean merged = false;
        boolean compressed2 = false;
        int move = 0; 
        rotateClockwise();
        for (int i = 0; i < SIDE; i++)
        {

        compressed = compressRow(gameField[i]); 
        merged = mergeRow(gameField[i]);
        compressed2 = compressRow(gameField[i]);

        if (compressed || merged || compressed2)
        move ++;
        }

        if (move != 0)
        createNewNumber();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        
    }
    
    
    private void rotateClockwise() {
        
        for (int i = 0; i < SIDE / 2; i++)
        {
            for (int j = i; j < SIDE - i - 1; j++)
            {
                // Swap elements of each cycle in clockwise direction
                int temp = gameField[i][j];
                gameField[i][j] = gameField[SIDE - 1 - j][i];
                gameField[SIDE - 1 - j][i] = gameField[SIDE - 1 - i][SIDE - 1 - j];
                gameField[SIDE - 1 - i][SIDE - 1 - j] = gameField[j][SIDE - 1 - i];
                gameField[j][SIDE - 1 - i] = temp;
            }
        }
    }
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
        
    }
    
    private void drawScene(){
        for(int i= 0; i<4; i++){
            for(int j= 0; j<4; j++){
            //  setCellColor(i, j, Color.RED); 
            setCellColoredNumber(i,j, gameField[j][i]);
            }
        }
        
          
    }
    private void createNewNumber(){
        
        int x;
        int y;
        do{
            x = getRandomNumber(SIDE);
            y = getRandomNumber(SIDE); 
        }while(gameField[x][y] != 0);
        
        int chance = getRandomNumber(10);
        
        if (chance == 9) gameField[x][y] = 4;
        else gameField[x][y] = 2;  
        
        if (getMaxTileValue() == 2048)
            win();
    }
    private Color getColorByValue(int value) {
        Color color = null;
        switch (value) {
            case 0:
                color = Color.WHITE;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 4:
                color = Color.RED;
                break;
            case 8:
                color = Color.CYAN;
                break;
            case 16:
                color = Color.GREEN;
                break;
            case 32:
                color = Color.YELLOW;
                break;
            case 64:
                color = Color.ORANGE;
                break;
            case 128:
                color = Color.PINK;
                break;
            case 256:
                color = Color.MAGENTA;
                break;
            case 512:
                color = Color.BLACK;
                break;
            case 1024:
                color = Color.PURPLE;
                break;
            case 2048:
                color = Color.GRAY;
                break;
        }    
            return color;    
    }
    private void setCellColoredNumber(int x, int y, int value) {
        Color color = getColorByValue(value);
        if (value == 0) {
            setCellValueEx(x,y, color, "");
            
        } else {
            setCellValueEx(x, y, color, Integer.toString(value));
            
        }
    }
    private boolean compressRow(int[] row) {
        int temp = 0;
        int[] rowtemp = row.clone();
        boolean isChanged = false;
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row.length-i-1; j++){
                if(row[j] == 0) {
                    temp = row[j];
                    row[j] = row[j+1];
                    row[j+1] = temp;
                }
            }
        }
        if(!Arrays.equals(row,rowtemp))
            isChanged = true;
       
        return isChanged;
    }
 private boolean mergeRow(int[] row){
   boolean moved = false;
     for (int i=0; i< row.length-1;i++)
        if ((row[i] == row[i+1])&&(row[i]!=0)){
                
                row[i] = 2*row[i];
                 row[i+1] = 0;
                 moved = true;
                 score +=row[i];
                setScore(score);


        }

    return moved;
 }

private int getMaxTileValue()
    {
        int max = 0;
        for (int i = 0 ; i < SIDE; i++)
            for (int j = 0 ; j<SIDE; j++)
                if (gameField[i][j] > max)
                max = gameField[i][j];


         return max;
    }


private void win()
{
     isGameStopped = true;
     showMessageDialog(Color.GOLD, "You are a Winner!", Color.BLACK, 45);
     
}
private boolean canUserMove () {
        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE; c++)
                if (gameField[r][c] == 0)
                    return true;

        for (int r = 0; r < SIDE-1; r++)
            for (int c = 0; c < SIDE; c++)
                if (gameField[r][c] == gameField[r+1][c])
                        return true;

        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE - 1; c++)
                if (gameField[r][c] == gameField[r][c + 1])
                    return true;

        return false;
    }
    private void gameOver () {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Game Over", Color.RED, 75);
    }
    
    
}