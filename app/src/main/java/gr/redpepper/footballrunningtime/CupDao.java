package gr.redpepper.footballrunningtime;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */
@Dao
public interface CupDao {

    @Query("SELECT * FROM CupEntity")
    List<CupEntity> getAll();

    @Query("SELECT * FROM CupEntity WHERE uid = :Uid")
    CupEntity findById(int Uid);

    @Insert
    void insertAll(CupEntity... cups);

    @Query("DELETE FROM CupEntity")
    void emptyTable();

}
