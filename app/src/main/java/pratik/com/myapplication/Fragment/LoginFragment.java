package pratik.com.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pratik.com.myapplication.AsyncTasks.LoginTask;
import pratik.com.myapplication.Interfaces.OnTaskCompleted;
import pratik.com.myapplication.Model.Profile;
import pratik.com.myapplication.R;

public class LoginFragment extends Fragment implements OnTaskCompleted {

    private String TAG = "LoginFragment";
    private View view;
    private TextInputLayout tlUsername, tlPass;
    private TextView txtLogin;
    private String strUsername = "", strPass = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateViews()) {
                    new LoginTask(getContext(), strUsername, LoginFragment.this).execute();
                }
            }
        });

        return view;
    }

    private void initViews() {
        tlUsername = view.findViewById(R.id.tlUsername);
        tlPass = view.findViewById(R.id.tlPass);
        txtLogin = view.findViewById(R.id.txtLogin);
    }

    private boolean validateViews() {
        strUsername = tlUsername.getEditText().getText().toString().trim();
        strPass = tlPass.getEditText().getText().toString().trim();

        if (strUsername.length() == 0) {
            tlUsername.setError("Username is required!");
            tlUsername.requestFocus();
            return false;
        } else if (strPass.length() == 0) {
            tlUsername.setError(null);
            tlPass.setError("Password is required!");
            tlPass.requestFocus();
            return false;
        } else if (strPass.length() < 6) {
            tlUsername.setError(null);
            tlPass.setError("Password must be more then 6 char!");
            tlPass.requestFocus();
            return false;
        } else {
            tlUsername.setError(null);
            tlPass.setError(null);
            return true;
        }
    }

    @Override
    public void getProfile(Profile profile) {
        if (profile != null) {
            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
            tlUsername.getEditText().setText("");
            tlPass.getEditText().setText("");
        }
    }

    @Override
    public void onRegistrationCompleted() {

    }
}
