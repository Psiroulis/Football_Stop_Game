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


    public TeamsEntity(String name, int overall, int locked, int cup) {
        this.name = name;
        this.overall = overall;
        this.locked = locked;
        this.cup = cup;
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

                new TeamsEntity("Spain", 95, 0, 1),
                new TeamsEntity("France", 93, 0,1),
                new TeamsEntity("England", 85, 0,1),
                new TeamsEntity("Greece", 73, 0,1),
                new TeamsEntity("Italy", 90, 1,1),
                new TeamsEntity("Netherlands", 88, 1,1),
                new TeamsEntity("Germany", 98, 1,1),
                new TeamsEntity("Portugal", 85, 1,1),
                new TeamsEntity("belgium", 85, 1,1),
                new TeamsEntity("Turkey", 73, 1,1),
                new TeamsEntity("Sweden", 80, 1,1),
                new TeamsEntity("Denmark", 75, 1,1),
                new TeamsEntity("Croatia", 81, 1,1),
                new TeamsEntity("Switzerland", 75, 1,1),
                new TeamsEntity("Iceland", 68, 1,1),
                new TeamsEntity("albania", 65, 1,1),
                //Copa America Teams
                new TeamsEntity("Brazil", 95, 0,2),
                new TeamsEntity("Argentina", 98, 0,2),
                new TeamsEntity("Chile", 80, 0,2),
                new TeamsEntity("Uruguay", 85, 0,2),
                new TeamsEntity("Mexico", 80, 1,2),
                new TeamsEntity("Colombia", 75, 1,2),
                new TeamsEntity("USA", 75, 1,2),
                new TeamsEntity("Paraguay", 73, 1,2),
                //Copa Africa TEams
                new TeamsEntity("Nigeria",80 ,0 ,3),
                new TeamsEntity("Egypt",85 , 0,3),
                new TeamsEntity("Coite de Voire",88 ,0 ,3),
                new TeamsEntity("Cameroon",88 ,0 ,3),
                new TeamsEntity("Marocco", 80, 1,3),
                new TeamsEntity("Tunisia",70 ,1 ,3),
                new TeamsEntity("Zambia",73 ,1 ,3),
                new TeamsEntity("Ghana",90 ,1 ,3),
                //World Cup Locked Teams
                new TeamsEntity("Australia",70 ,1 ,0),
                new TeamsEntity("China",60 ,1 ,0),
                new TeamsEntity("Korea",65 ,1 ,0),
                new TeamsEntity("Canada",50 ,1 ,0),
                new TeamsEntity("Japan",73 ,1 ,0),

        };
    }

}