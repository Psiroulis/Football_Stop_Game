package gr.redpepper.footballrunningtime.customClasses;

public class TheMatch {

    private int playerGoals;

    private int opponentGoals;

    private int posts;

    private int ownGoals;

    private MyChronometer chronometer;


    public TheMatch(MyChronometer chronometer, int opponentGoals) {

        this.chronometer = chronometer;

        this.opponentGoals = opponentGoals;

        this.playerGoals = 0;

        this.posts = 0;

    }

    public void RunTheTimer(){

        if(!chronometer.ChangeHalf()){

            chronometer.StartFirstHalf();

        }else{

            chronometer.StartSecondHalf();

        }

    }

    public int Shoot(){   //0->post, 1->goal, 2->missed, 3->own goal

        chronometer.Pause();

        if (chronometer.getMillis() >= 0 && chronometer.getMillis() <= 9) {

            playerGoals ++;

            return 1;

        }else if ((chronometer.getMillis() >= 990 && chronometer.getMillis() <= 999) || (chronometer.getMillis() > 9 && chronometer.getMillis() <= 18)) {

            posts++;

            return 0;

        }else if (chronometer.getMillis() >= 480 && chronometer.getMillis() <= 520) {

            opponentGoals++;

            return 3;

        }else if (chronometer.getMillis() == 1000) {

            playerGoals ++;

            return 1;

        }

        return 2;

    }

    public int getPlayerGoals() {
        return playerGoals;
    }

    public int getOpponentGoals() {
        return opponentGoals;
    }

    public int getPosts() {
        return posts;
    }

    public int getOwnGoals() {
        return ownGoals;
    }

    public void setPlayerGoals(int playerGoals) {
        this.playerGoals = playerGoals;
    }

    public void setOpponentGoals(int opponentGoals) {
        this.opponentGoals = opponentGoals;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public void setOwnGoals(int ownGoals) {
        this.ownGoals = ownGoals;
    }
}

