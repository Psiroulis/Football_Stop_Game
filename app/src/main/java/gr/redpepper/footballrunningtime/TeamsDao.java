package gr.redpepper.footballrunningtime;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Dao
public interface TeamsDao {

    @Query("SELECT * FROM TeamsEntity")
    List<TeamsEntity> getAll();

    @Query("SELECT * FROM TeamsEntity WHERE uid = :Uid")
    TeamsEntity findById(int Uid);

    @Query("SELECT * FROM TeamsEntity where cup = :cup")
    List<TeamsEntity> getCupsTeams(int cup);

    @Insert
    void insertAll(TeamsEntity... euroteams);

    @Query("DELETE FROM TeamsEntity")
    void emptyTable();
}
