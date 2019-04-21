package pratik.com.myapplication.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import pratik.com.myapplication.Database.DatabaseClient;
import pratik.com.myapplication.Database.RegisterEntity;
import pratik.com.myapplication.Interfaces.OnTaskCompleted;
import pratik.com.myapplication.Model.Profile;
import pratik.com.myapplication.Model.Registration;

public class LoginTask extends AsyncTask<Void, Void, Profile> {

    private Context context;
    private String strUsername = "";
    private OnTaskCompleted onTaskCompleted;

    public LoginTask(Context context, String strUsername, OnTaskCompleted onTaskCompleted) {
        this.context = context;
        this.strUsername = strUsername;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected Profile doInBackground(Void... voids) {

        //adding to database
        return DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase().databaseDao().getUserFromUsername(strUsername);
    }

    @Override
    protected void onPostExecute(Profile profile) {
        super.onPostExecute(profile);
        onTaskCompleted.getProfile(profile);
    }
}