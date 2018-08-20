package gr.redpepper.footballrunningtime.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Dao
public interface DatabaseDao {

    @Query("SELECT * FROM TeamEntity")
    List<TeamEntity> getAll();

    @Query("SELECT * FROM TeamEntity WHERE uid = :Uid")
    TeamEntity findById(int Uid);

    @Query("SELECT * FROM TeamEntity where cup = :cup")
    List<TeamEntity> getCupsTeams(int cup);

    @Insert
    void insertAll(TeamEntity... euroteams);

    @Query("DELETE FROM TeamEntity")
    void emptyTable();

    @Query("SELECT * FROM CupEntity")
    List<CupEntity> getAllCups();

    @Query("SELECT * FROM CupEntity WHERE uid = :Uid")
    CupEntity findByIdCup(int Uid);

    @Insert
    void insertAll(CupEntity... cups);

    @Query("DELETE FROM CupEntity")
    void emptyTableCup();

    @Query("SELECT * FROM TeamEntity WHERE uid != :Uid AND cup = :cup")
    List<TeamEntity> getAllExceptSelected(int Uid, int cup);

    @Query("SELECT * FROM TeamEntity WHERE worldcup = 1")
    List<TeamEntity> getAllWorldCupTeams();
}
