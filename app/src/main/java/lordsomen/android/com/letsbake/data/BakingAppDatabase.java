package lordsomen.android.com.letsbake.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {BakingAppData.class} , version = 1 , exportSchema = false)
public abstract class BakingAppDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "Baking_Database";
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = BakingAppDatabase.class.getSimpleName();
    private static BakingAppDatabase dataInstance;

    public static BakingAppDatabase getDataInstance(Context context){
        if(null == dataInstance){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                dataInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BakingAppDatabase.class, BakingAppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");

        return dataInstance;
    }

    public abstract BakingAppDao BakingAppDao() ;

}
