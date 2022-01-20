package gr.redpepper.footballrunningtime.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Entity
public class TeamEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "overall")
    private int overall;

    @ColumnInfo(name = "locked")
    private int locked;

    @ColumnInfo(name = "cup")
    private int cup;

    @ColumnInfo(name = "worldcup")
    private int worldcup;


    public TeamEntity(String name, int overall, int locked, int cup, int worldcup) {
        this.name = name;
        this.overall = overall;
        this.locked = locked;
        this.cup = cup;
        this.worldcup = worldcup;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getOverall() {
        return overall;
    }

    public int getLocked() {
        return locked;
    }

    public int getCup() {
        return cup;
    }

    public int getWorldcup(){return worldcup;}

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public void setOverall(int overall) {
        this.overall = overall;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }*/

    public static TeamEntity[] populateData() {
        return new TeamEntity[]{

                new TeamEntity("Spain", 95, 0, 1,1),
                new TeamEntity("France", 93, 0,1,1),
                new TeamEntity("England", 85, 0,1,1),
                new TeamEntity("Greece", 73, 0,1,1),
                new TeamEntity("Italy", 90, 1,1,0),
                new TeamEntity("Netherlands", 88, 1,1,0),
                new TeamEntity("Germany", 98, 1,1,0),
                new TeamEntity("Portugal", 85, 1,1,0),
                new TeamEntity("Belgium", 85, 1,1,0),
                new TeamEntity("Turkey", 73, 1,1,0),
                new TeamEntity("Sweden", 80, 1,1,0),
                new TeamEntity("Denmark", 75, 1,1,0),
                new TeamEntity("Croatia", 81, 1,1,0),
                new TeamEntity("Switzerland", 75, 1,1,0),
                new TeamEntity("Iceland", 68, 1,1,0),
                new TeamEntity("Albania", 65, 1,1,0),
                //Copa America Teams
                new TeamEntity("Brazil", 95, 0,2,1),
                new TeamEntity("Argentina", 98, 0,2,1),
                new TeamEntity("Chile", 80, 0,2,1),
                new TeamEntity("Uruguay", 85, 0,2,1),
                new TeamEntity("Mexico", 80, 1,2,0),
                new TeamEntity("Colombia", 75, 1,2,0),
                new TeamEntity("USA", 75, 1,2,0),
                new TeamEntity("Paraguay", 73, 1,2,0),
                new TeamEntity("Peru", 68, 1,2,0),
                new TeamEntity("Ecuador", 68, 1,2,0),
                new TeamEntity("Venezuela", 63, 1,2,0),
                new TeamEntity("Bolivia", 68, 1,2,0),
                new TeamEntity("Panamas", 53, 1,2,0),
                new TeamEntity("Honduras", 55, 1,2,0),
                new TeamEntity("Costa Rica", 70, 1,2,0),
                new TeamEntity("Puerto Rico", 55, 1,2,0),
                //Copa Africa TEams
                new TeamEntity("Nigeria",80 ,0 ,3,1),
                new TeamEntity("Egypt",85 , 0,3,1),
                new TeamEntity("Ivory Coast",88 ,0 ,3,1),
                new TeamEntity("Cameroon",88 ,0 ,3,1),
                new TeamEntity("Morocco", 80, 1,3,0),
                new TeamEntity("Tunisia",70 ,1 ,3,0),
                new TeamEntity("Zimbabwe",73 ,1 ,3,0),
                new TeamEntity("Ghana",90 ,1 ,3,0),
                new TeamEntity("Angola",73 ,1 ,3,0),
                new TeamEntity("Mali",68 ,1 ,3,0),
                new TeamEntity("Togo",68 ,1 ,3,0),
                new TeamEntity("Mozambique",63 ,1 ,3,0),
                new TeamEntity("Burkina Faso",55 ,1 ,3,0),
                new TeamEntity("Congo",50 ,1 ,3,0),
                new TeamEntity("Kenya",55 ,1 ,3,0),
                new TeamEntity("Ethiopia",50 ,1 ,3,0),



                //World Cup Locked Teams
                new TeamEntity("Australia",70 ,1 ,0,1),
                new TeamEntity("China",60 ,1 ,0,1),
                new TeamEntity("Korea",65 ,1 ,0,1),
                new TeamEntity("Canada",50 ,1 ,0,1),

        };
    }

}