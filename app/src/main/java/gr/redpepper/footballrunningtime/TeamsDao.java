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

    @Query("SELECT * FROM CupEntity")
    List<CupEntity> getAllCups();

    @Query("SELECT * FROM CupEntity WHERE uid = :Uid")
    CupEntity findByIdCup(int Uid);

    @Insert
    void insertAll(CupEntity... cups);

    @Query("DELETE FROM CupEntity")
    void emptyTableCup();

    @Query("SELECT * FROM TeamsEntity WHERE uid != :Uid AND cup = :cup")
    List<TeamsEntity> getAllExceptSelected(int Uid,int cup);

    @Query("SELECT * FROM TeamsEntity WHERE worldcup = 1")
    List<TeamsEntity> getAllWorldCupTeams();
}
