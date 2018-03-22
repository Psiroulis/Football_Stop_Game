package gr.redpepper.footballrunningtime;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Database(entities = {TeamsEntity.class,CupEntity.class} , version = 1)
public abstract class TeamsDatabase extends RoomDatabase {

    public abstract TeamsDao teamsDao();

    private static TeamsDatabase INSTANCE;

    public synchronized static TeamsDatabase getInstance(Context context){

        if(INSTANCE == null){

            INSTANCE = buildDatabase(context);

        }

        return INSTANCE;
    }

    private static TeamsDatabase buildDatabase(final Context context){

        return Room.databaseBuilder(context,TeamsDatabase.class, "teamsDatabase")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                                getInstance(context).teamsDao().insertAll(TeamsEntity.populateData());

                                getInstance(context).teamsDao().insertAll(CupEntity.populateData());

                            }
                        });
                    }
                }).build();

    }
}
