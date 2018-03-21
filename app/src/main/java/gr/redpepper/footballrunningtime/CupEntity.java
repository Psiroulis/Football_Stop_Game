package gr.redpepper.footballrunningtime;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */
@Entity
public class CupEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "locked")
    private int locked;


    public CupEntity(String name, int locked) {
        this.name = name;
        this.locked = locked;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public static CupEntity[] populateData(){

        return new CupEntity[]{

                new CupEntity("Euro", 0),
                new CupEntity("Copa America", 1),
                new CupEntity("Copa Africa", 1),
                new CupEntity("World Cup", 1)

        };

    }
}
