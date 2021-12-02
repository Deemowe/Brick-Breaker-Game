package BrickBreaker;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*; //For timer
import javax.swing.Timer;
import java.awt.*; //For graphics
import java.awt.event.*;//For listeners
import java.io.*;
import java.util.*;

/* "extends" and "implements" which are used in Java for inheritance
 __________________________________________________________________________
|            extends                 |            implements               |
|____________________________________|_____________________________________|
|extends keyword is used to indicate |*implements keyword is used to       |
|that the class which is being       |implement an interface.              |
|defined is derived from the base    |*An interface is an abstract "class" |
|class using inheritance.            |that is used to group related methods|
|                                    |with "empty" bodies                  |
|__________________________________________________________________________|
*/
public class panel extends JPanel implements KeyListener, ActionListener {
    private boolean play=false; //Because when the game is start shouldn't play by itself!
    private int score=0; //The scores in beginning is zero 'not started yet'
    private int totalBricks = 21; // 3 maps
    private Timer timer; //Timer of ball how fast it should move
    private int speed = 8 ; //The speed of timer
    int bestScore = readBestScorefromTheFile(); //read the best score from .txt file


    private MyTimer time;//running time!

    Random random = new Random();//make random position to ball abd paddle

    //customizes colors
    public static final Color Very_light_blue = new Color(228,255,255);
    public static final Color blue= new Color(19,75,131);

    //Java’s coordinate system is a scheme for identifying points on the screen.
    //Set the property of x-axis and y-axis of the paddle and ball
    private int playerX = 310;//the starting position in paddle
    private int ballPositionX= random.nextInt(22); //Ball positions for X-axis
    private int ballPositionY=22+random.nextInt(17);//Ball positions for Y-axis
    private int  ballXDirection=-1;//Set the direction of ball in X-axis
    private int  ballYDirection=-2;//Set the direction of ball in Y-axis

    private mapGenerator map; //object for mapGenerator class

    public panel(){ //constructor
        map = new mapGenerator(3,7); // object foe mapGenerator class
        addKeyListener(this);
        setFocusable(true); //default true in JPanel but not in class Panel!,This method make that the component is displayable
        setFocusTraversalKeysEnabled(false);//Decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.)
        timer = new Timer(speed,this); //object for Time
        timer.start();

        //object for the time
        time=new MyTimer();


    }



    public void paint(Graphics obj){ //Receive Graphic object

        //background
        obj.setColor(Color.white); //background color
        obj.fillRect(0,0,910,592);//rectangular for background

        //for the squares
        obj.setColor(Color.DARK_GRAY);
        for(int i=6; i<=700; i+=16)
            for(int j=6; j<=533; j+=17)
                obj.fillRect(i, j, 13, 13);

        //COLORED background again
        obj.setColor(Very_light_blue);
        obj.fillRect(0, 5, 695, 550);


        //drawing map
        map.draw((Graphics2D)obj);



        //paddle
        obj.setColor(blue);
        obj.fillRect(playerX,540,100,8);

        //ball
        obj.setColor(Color.GRAY);
        obj.fillOval(ballPositionX,ballPositionY,20,20);


        //scores
        obj.setColor(Color.BLACK);
        obj.setFont(new Font("Arial",Font.BOLD, 20));
        obj.drawString("Developed By", 750, 60);
        obj.drawString("Deem Alowairdhi", 730, 90);

        obj.setColor(Color.darkGray);

        //time
        obj.drawString("Time", 750, 130);
        obj.drawString(time.min+":", 803, 130);
        obj.drawString(time.getTime()+"", 821, 130);

        // لقياس حجم الأرفام الظاهرة في المربعات الثلاث بالبيكسل حتى نستطيع عرضهم في الوسط FontMetrics هنا قمنا بإنشاء الكائن
        FontMetrics fm = obj.getFontMetrics();


        // customized font family and width
        obj.setFont(new Font("Arial", Font.PLAIN, 18));

        //Best Score
        obj.drawString("Best Score", 750, 180);
        obj.drawRect(730, 190, 140, 30);
        obj.drawString(bestScore+"", 730+(142-fm.stringWidth(bestScore+""))/2, 212);

        //Total Score
        obj.drawString("Total Score", 750, 260);
        obj.drawRect(730, 270, 140, 30);
        obj.drawString(score+"", 730+(142-fm.stringWidth(score+""))/2, 292);


        //Controls
        obj.setFont(new Font("Arial", Font.BOLD, 16));
        obj.drawString("Controls", 720, 380);

        obj.setFont(new Font("Arial", Font.PLAIN, 14));
        obj.drawString("moveTo Left : Arrow Left", 720, 410);
        obj.drawString("moveTo Right : Arrow Right", 720, 435);
        obj.drawString("Exit : End(F10)", 720, 458);

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("QU_logo.png");
        obj.drawImage(i, 175,70,this);


        //for wining
        if(totalBricks <=0){
            play=false;
            ballXDirection = 0;
            ballYDirection = 0;

            obj.setColor(Color.DARK_GRAY);
            obj.setFont(new Font("serif",Font.BOLD,45));
            obj.drawString("Winner Winner Chicken Dinner!",63,250);
            obj.setFont(new Font("serif",Font.BOLD,40));
            obj.drawString("Press Enter To Restart!",155,350);

            //reset the time
            time.reset();
            time.min=0;
        }


        //for ending the game (gameOver)
        if(ballPositionY > 570){
            play=false;
            ballXDirection = 0;
            ballYDirection = 0;

            obj.setColor(Color.DARK_GRAY);
            obj.fillRect(5, 5, 687, 550);

            Image a=t.getImage("gameOver.png");
            obj.drawImage(a, 175,70,this);


            obj.setColor(Color.WHITE);
            obj.setFont(new Font("serif",Font.BOLD,40));
            obj.drawString("Press Enter To Restart!",155,450);
            writeBestScoreInTheFile();




        }

        obj.dispose();

        //for best score
        if(score > bestScore )
            bestScore = score;


    }



    //ActionListener
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();



        //create rectangle for the ball to detecting the intersection with the paddle
        if( new Rectangle(ballPositionX,ballPositionY,20,20).intersects(new Rectangle(playerX,540,100,8) )){
            ballYDirection = -ballYDirection;
        }

        A:for ( int i=0 ; i< map.map.length ; i++){ //the first map is object for mapGenerator class declared in panel class and the second is array in mapGenerator class
            for( int j=0 ; j<map.map[0].length ; j++){
                if(map.map[i][j]>0){ //detect the intersection
                    //first we need to detect the position of ball and bricks with respect to the width and height for bricks
                    int brickX = j* map.brickWidth +80 ;
                    int brickY = i* map.brickHeight +50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;

                    //create rectangle around brick for detect the intersection
                    Rectangle rect = new Rectangle(brickX , brickY , brickWidth , brickHeight);

                    //create rectangle around ball for detect the intersection
                    Rectangle ballRec = new Rectangle(ballPositionX , ballPositionY , 20 ,20);

                    Rectangle brickRec= rect; //pass the reference for particular rectangle

                    //determines the intersection
                    if(ballRec.intersects(brickRec)){
                        map.setBrickValue(0,i,j); //change the value to zero if intersect the ball with brick
                        totalBricks--; //change the value of Bricks (21)
                        playSound("brick.wav");
                        score+=5; //add point to score

                        if(ballPositionX + 19 <= brickRec.x || ballPositionX + 1 >= brickRec.x + brickRec.width){
                            //move the ball to opposite direction
                            ballXDirection = -ballXDirection;

                        }
                        else{
                            //move the ball towards the right, top or bottom direction
                            ballYDirection = -ballYDirection;
                        }
                        break A; //break from outer loop (should add label)
                    }




                }
            }
        }



        if(play){  //if we start play (we press the lift or right arrow key)

            ballPositionX+= ballXDirection;
            ballPositionY+= ballYDirection;

            if(ballPositionX < 0 ){  //lift border
                ballXDirection = -ballXDirection;
            }

            if(ballPositionY < 0 ){ //top
                ballYDirection = -ballYDirection;
            }

            if(ballPositionX > 670 ){ //right border
                ballXDirection = -ballXDirection;
            }


        }

        //if not play (not starting yet) time=0
        if(!play){
            time.reset();
        }

        repaint(); //recall paint method

    }




    //KeyListener
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}



    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();

            }
        }


        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();

            }
        }

        //Restart the game
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXDirection = -1;
                ballYDirection = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new mapGenerator(3, 7);

                repaint();
                playSound("replay.wav");


            }
        }


        if(keyEvent.getKeyCode() == KeyEvent.VK_END){
            System.exit(0); //exit from tha game

        }
    }


    public void moveRight(){
        play=true; //change the value
        playerX+=20;
    }

    public void moveLeft(){
        play=true; //change the value
        playerX-=20;
    }




    //best score
    private void writeBestScoreInTheFile()
    {
        if(score >= bestScore)
        {
            try {
                FileOutputStream fos = new FileOutputStream("./Brick-Breaker-game-best-score.txt");
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(bestScore+"");
                osw.flush();
                osw.close();
            }
            catch(IOException e) {
            }
        }
    }


    private int readBestScorefromTheFile()
    {
        try {
            InputStreamReader isr = new InputStreamReader( new FileInputStream("./Brick-Breaker-game-best-score.txt"), "UTF-8" );
            BufferedReader br = new BufferedReader(isr);

            String str = "";
            int c;
            while( (c = br.read()) != -1){
                if(Character.isDigit(c))
                    str += (char)c;
            }
            if(str.equals(""))
                str = "0";

            br.close();
            return Integer.parseInt(str);
        }
        catch(IOException e) {
        }
        return 0;
    }







    public static synchronized void playSound( String url) {

        InputStream music;
        try{

            music = new FileInputStream(new File(url));
            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);



        }
        catch(Exception e){

        }
    }







/*

 if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {

            // سيتم إيقاف اللعبة بشكل مؤقت إذا كانت اللعبة شغالة
            if (timer.isRunning() && play) {
                timer.stop();

            }

                // سيتم إعادة اللعبة للعمل إذا كان قد تم إيقافها سابقاً
            else if (!timer.isRunning() && play) {
                    timer.start();

                }

        }
*/


}








