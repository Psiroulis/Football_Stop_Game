package gr.redpepper.footballrunningtime;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Entity
public class TeamsEntity {

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


    public TeamsEntity(String name, int overall, int locked, int cup, int worldcup) {
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

    public static TeamsEntity[] populateData() {
        return new TeamsEntity[]{

                new TeamsEntity("Spain", 95, 0, 1,1),
                new TeamsEntity("France", 93, 0,1,1),
                new TeamsEntity("England", 85, 0,1,1),
                new TeamsEntity("Greece", 73, 0,1,1),
                new TeamsEntity("Italy", 90, 1,1,0),
                new TeamsEntity("Netherlands", 88, 1,1,0),
                new TeamsEntity("Germany", 98, 1,1,0),
                new TeamsEntity("Portugal", 85, 1,1,0),
                new TeamsEntity("Belgium", 85, 1,1,0),
                new TeamsEntity("Turkey", 73, 1,1,0),
                new TeamsEntity("Sweden", 80, 1,1,0),
                new TeamsEntity("Denmark", 75, 1,1,0),
                new TeamsEntity("Croatia", 81, 1,1,0),
                new TeamsEntity("Switzerland", 75, 1,1,0),
                new TeamsEntity("Iceland", 68, 1,1,0),
                new TeamsEntity("Albania", 65, 1,1,0),
                //Copa America Teams
                new TeamsEntity("Brazil", 95, 0,2,1),
                new TeamsEntity("Argentina", 98, 0,2,1),
                new TeamsEntity("Chile", 80, 0,2,1),
                new TeamsEntity("Uruguay", 85, 0,2,1),
                new TeamsEntity("Mexico", 80, 1,2,0),
                new TeamsEntity("Colombia", 75, 1,2,0),
                new TeamsEntity("USA", 75, 1,2,0),
                new TeamsEntity("Paraguay", 73, 1,2,0),
                new TeamsEntity("Peru", 68, 1,2,0),
                new TeamsEntity("Ecuador", 68, 1,2,0),
                new TeamsEntity("Venezuela", 63, 1,2,0),
                new TeamsEntity("Bolivia", 68, 1,2,0),
                new TeamsEntity("Panamas", 53, 1,2,0),
                new TeamsEntity("Honduras", 55, 1,2,0),
                new TeamsEntity("Costa Rica", 70, 1,2,0),
                new TeamsEntity("Puerto Rico", 55, 1,2,0),
                //Copa Africa TEams
                new TeamsEntity("Nigeria",80 ,0 ,3,1),
                new TeamsEntity("Egypt",85 , 0,3,1),
                new TeamsEntity("Ivory Coast",88 ,0 ,3,1),
                new TeamsEntity("Cameroon",88 ,0 ,3,1),
                new TeamsEntity("Morocco", 80, 1,3,0),
                new TeamsEntity("Tunisia",70 ,1 ,3,0),
                new TeamsEntity("Zimbabwe",73 ,1 ,3,0),
                new TeamsEntity("Ghana",90 ,1 ,3,0),
                new TeamsEntity("Angola",73 ,1 ,3,0),
                new TeamsEntity("Mali",68 ,1 ,3,0),
                new TeamsEntity("Togo",68 ,1 ,3,0),
                new TeamsEntity("Mozambique",63 ,1 ,3,0),
                new TeamsEntity("Burkina Faso",55 ,1 ,3,0),
                new TeamsEntity("Congo",50 ,1 ,3,0),
                new TeamsEntity("Kenya",55 ,1 ,3,0),
                new TeamsEntity("Ethiopia",50 ,1 ,3,0),



                //World Cup Locked Teams
                new TeamsEntity("Australia",70 ,1 ,0,1),
                new TeamsEntity("China",60 ,1 ,0,1),
                new TeamsEntity("Korea",65 ,1 ,0,1),
                new TeamsEntity("Canada",50 ,1 ,0,1),

        };
    }

}