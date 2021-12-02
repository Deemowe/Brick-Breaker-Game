package BrickBreaker;

import java.util.Date;
public class MyTimer {
    Date start;
    long mSecond;
    long second;
    int min;


    public MyTimer(){
        start = new Date();

    }


    public long getTime() {

        Date now = new Date();
        mSecond = now.getTime() - start.getTime();

        second = mSecond / 1000;

        if (second >= 60) {
            reset();
            min++;

        }


        return second;
    }




    public void reset(){

        start = new Date();
    }


}


      /* public void stopTime(){
        if (run){
            this.run=false;

        }
}




    public long getTime2() {

        second = mSecond / 1000;

        if (second == 60) {
            reset();

            min++;

        }
        return second;
    }

*/

