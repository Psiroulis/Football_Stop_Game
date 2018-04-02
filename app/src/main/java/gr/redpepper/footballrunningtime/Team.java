package gr.redpepper.footballrunningtime;

public class Team {

    private int id;

    private String name;

    private int locked;

    private int cup;

    public Team(int id, String name, int locked, int cup) {
        this.id = id;
        this.name = name;
        this.locked = locked;
        this.cup = cup;
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

}
