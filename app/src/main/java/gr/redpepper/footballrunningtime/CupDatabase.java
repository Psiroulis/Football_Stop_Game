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

@Database(entities = {CupEntity.class} , version = 1)
public abstract class CupDatabase extends RoomDatabase {

    public abstract CupDao cupDao();

    private static CupDatabase INSTANCE;

    public synchronized static CupDatabase getInstance(Context context){

        if(INSTANCE == null){

            INSTANCE = buildDatabase(context);

        }

        return INSTANCE;
    }

    private static CupDatabase buildDatabase(final Context context){
        return Room.databaseBuilder(context,CupDatabase.class, "teamsDatabase")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                                getInstance(context).cupDao().insertAll(CupEntity.populateData());

                            }
                        });
                    }
                }).build();
    }
}
