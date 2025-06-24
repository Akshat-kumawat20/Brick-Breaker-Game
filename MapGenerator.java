import java.awt.Graphics2D;
import java.awt.Color;
public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for(int i = 0; i <map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = 1; // Initialize all bricks to be present
            }
        }
        brickWidth = 540 / col; // Width of each brick
        brickHeight = 150 / row; // Height of each brick 
        
    }
    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) { // If the brick is present
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new java.awt.BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    
                }
            }
        }

    }
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value; // Set the value of the brick at specified row and column
    }
}