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

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

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
                new TeamsEntity("Belgium", 85, 1,1),
                new TeamsEntity("Turkey", 73, 1,1),
                new TeamsEntity("Sweden", 80, 1,1),
                new TeamsEntity("Denmark", 75, 1,1),
                new TeamsEntity("Croatia", 81, 1,1),
                new TeamsEntity("Switzerland", 75, 1,1),
                new TeamsEntity("Iceland", 68, 1,1),
                new TeamsEntity("Albania", 65, 1,1),


        };
    }

}