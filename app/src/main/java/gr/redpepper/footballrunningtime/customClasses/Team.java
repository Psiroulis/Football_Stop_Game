package gr.redpepper.footballrunningtime.customClasses;

import java.io.Serializable;

public class Team implements Serializable{

    private int id;

    private String name;

    private int locked;

    private int cup;

    private int overall;

    public Team(int id, String name, int locked, int cup, int overall) {
        this.id = id;
        this.name = name;
        this.locked = locked;
        this.cup = cup;
        this.overall = overall;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLocked() {
        return locked;
    }

    public int getCup() {
        return cup;
    }

    public int getOveral(){return overall;}
}
