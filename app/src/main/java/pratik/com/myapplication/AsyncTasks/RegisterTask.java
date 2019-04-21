package pratik.com.myapplication.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import pratik.com.myapplication.Database.DatabaseClient;
import pratik.com.myapplication.Database.RegisterEntity;
import pratik.com.myapplication.Interfaces.OnTaskCompleted;
import pratik.com.myapplication.Model.Registration;

public class RegisterTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private Registration registration;
    private OnTaskCompleted onTaskCompleted;

    public RegisterTask(Context context, Registration registration, OnTaskCompleted onTaskCompleted) {
        this.context = context;
        this.registration = registration;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        //Registration registration = new Registration();
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setUsername(registration.getUsername());
        registerEntity.setEmail(registration.getEmail());
        registerEntity.setPhone(registration.getPhone());
        registerEntity.setPassword(registration.getPass());

        //adding to database
        DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase().databaseDao().insert(registerEntity);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onTaskCompleted.onRegistrationCompleted();
    }
}
