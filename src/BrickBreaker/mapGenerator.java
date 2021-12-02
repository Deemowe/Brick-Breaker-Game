package BrickBreaker;
import java.awt.*;

public class mapGenerator {
    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    //customizes colors
    public static final Color Very_light_blue = new Color(255,255,255);
    public static final Color blue = new Color(51,153, 255);



    //constructor for number of rows and columns should be generated for particular number of bricks
    public mapGenerator( int row , int column){
        map = new int [row][column];

        for( int i=0 ; i< map.length ; i++){
            for (int j=0; j<map[0].length ; j++){
                map[i][j]=1; // 1 will detect that this particular brick have not been intersected with tha ball
            }
        }
        brickWidth= 540/column; //the width of bricks
        brickHeight= 150/row;   //the height of bricks

    }
    public void draw(Graphics2D obj) { //function for drawing the bricks
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j]>0){   //crate the bricks
                    obj.setColor(blue);
                    obj.fillRect(j * brickWidth + 80 , i * brickHeight + 50 , brickWidth , brickHeight);

                    obj.setStroke(new BasicStroke(3));
                    obj.setColor((Very_light_blue));// the border for bricks with same color of background!
                    obj.drawRect(j * brickWidth + 80 , i * brickHeight + 50 , brickWidth , brickHeight);
                }

            }
        }
    }

    //function for intersection between the ball and bricks
    public void setBrickValue(int value , int row , int column){
        map[row][column] = value; //zero
    }

}
