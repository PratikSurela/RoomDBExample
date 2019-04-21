package pratik.com.myapplication.Database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;
    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;
        //creating the app database with Room database builder
        //Register is the name of the database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "User").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
