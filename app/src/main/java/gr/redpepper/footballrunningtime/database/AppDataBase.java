package gr.redpepper.footballrunningtime.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executors;

/**
 * Created by psirogiannisdimitris on 21/03/2018.
 */

@Database(entities = {TeamEntity.class,CupEntity.class} , version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract DatabaseDao dbDao();

    private static AppDataBase INSTANCE;

    public synchronized static AppDataBase getInstance(Context context){

        if(INSTANCE == null)
        {

            INSTANCE = buildDatabase(context);

        }

        return INSTANCE;
    }

    private static AppDataBase buildDatabase(final Context context){

        return Room.databaseBuilder(context,AppDataBase.class,"appDatabase")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                                Log.d("blepo","Appdatabase execute auto feed");

                                getInstance(context).dbDao().insertAll(TeamEntity.populateData());
                                getInstance(context).dbDao().insertAll(CupEntity.populateData());
                            }
                        });
                    }
                }).build();

    }

}


