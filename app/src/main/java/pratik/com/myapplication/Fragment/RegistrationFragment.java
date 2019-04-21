package pratik.com.myapplication.Fragment;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pratik.com.myapplication.AsyncTasks.RegisterTask;
import pratik.com.myapplication.Interfaces.OnTaskCompleted;
import pratik.com.myapplication.Model.Profile;
import pratik.com.myapplication.Model.Registration;
import pratik.com.myapplication.R;
import pratik.com.myapplication.Interfaces.OnFragmentTouched;
import pratik.com.myapplication.Utils.Utils;

public class RegistrationFragment extends Fragment implements OnTaskCompleted {

    private String TAG = "RegistrationFragment";
    private View rootView;
    private ConstraintLayout clMain;
    private OnFragmentTouched listener;
    private TextInputLayout tlUsername, tlEmail, tlPhone, tlPass;
    private TextView txtRegister;
    private String strUsername = "", strEmail = "", strPhone = "", strPass = "";
    private ImageView imgClose;

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance(int centerX, int centerY, int color) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_regitration, container, false);
        initViews(rootView);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateViews()) {
                    registerUser();
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onFragmentTouched(RegistrationFragment.this, view.getY(), view.getX());
            }
        });
/*        imgClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (listener != null)
                    listener.onFragmentTouched(RegistrationFragment.this, event.getX(), event.getY());
                return true;
            }
        });*/

        return rootView;
    }

    private void initViews(View rootView) {

        clMain = rootView.findViewById(R.id.clMain);
        tlUsername = rootView.findViewById(R.id.tlUsername);
        tlEmail = rootView.findViewById(R.id.tlEmail);
        tlPhone = rootView.findViewById(R.id.tlPhone);
        tlPass = rootView.findViewById(R.id.tlPass);
        txtRegister = rootView.findViewById(R.id.txtRegister);
        imgClose = rootView.findViewById(R.id.imgClose);

        setAnimation();
    }

    private void setAnimation() {
        rootView.setBackgroundColor(getArguments().getInt("color"));

        // To run the animation as soon as the view is layout in the view hierarchy we add this listener and remove it
        // as soon as it runs to prevent multiple animations if the view changes bounds
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt("cx");
                int cy = getArguments().getInt("cy");

                //get the hypothenuse so the radius is from one corner to the other
                //int radius = (int) Math.hypot(right, bottom);
                int radius = (int) Math.hypot(right, bottom);

                Animator reveal = ViewAnimationUtils.createCircularReveal(v, right, cx, 0, radius);
                reveal.setInterpolator(new DecelerateInterpolator(2f));
                reveal.setDuration(1000);
                reveal.start();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentTouched)
            listener = (OnFragmentTouched) activity;
    }

    /**
     * Get the animator to unreveal the circle
     *
     * @param cx center x of the circle (or where the view was touched)
     * @param cy center y of the circle (or where the view was touched)
     * @return
     */
    public Animator prepareUnrevealAnimator(float cx, float cy) {

        int radius = getEnclosingCircleRadius(getView(), (int) cx, (int) cy);
        //Animator anim = ViewAnimationUtils.createCircularReveal(getView(), (int) cy, (int) cx, radius, 0);
        Animator anim = ViewAnimationUtils.createCircularReveal(getView(), (int) cy, (int) cx, radius, (int) cy);
        anim.setInterpolator(new AccelerateInterpolator(2f));
        anim.setDuration(1000);
        return anim;
    }

    /**
     * To be really accurate we have to start the circle on the furthest corner of the view
     *
     * @param v  the view to unreveal
     * @param cx center x of the circle
     * @param cy center y of the circle
     * @return the maximum radius
     */
    private int getEnclosingCircleRadius(View v, int cx, int cy) {
        int realCenterX = cx + v.getLeft();
        int realCenterY = cy + v.getTop();
        int distanceTopLeft = (int) Math.hypot(realCenterX - v.getLeft(), realCenterY - v.getTop());
        int distanceTopRight = (int) Math.hypot(v.getRight() - realCenterX, realCenterY - v.getTop());
        int distanceBottomLeft = (int) Math.hypot(realCenterX - v.getLeft(), v.getBottom() - realCenterY);
        int distanceBotomRight = (int) Math.hypot(v.getRight() - realCenterX, v.getBottom() - realCenterY);

        return Math.max(Math.max(Math.max(distanceTopLeft, distanceTopRight), distanceBottomLeft), distanceBotomRight);
    }

    private boolean validateViews() {
        strUsername = tlUsername.getEditText().getText().toString().trim();
        strEmail = tlEmail.getEditText().getText().toString().trim();
        strPhone = tlPhone.getEditText().getText().toString().trim();
        strPass = tlPass.getEditText().getText().toString().trim();

        if (strUsername.length() == 0) {
            tlUsername.setError("Username is required!");
            tlUsername.requestFocus();
            return false;
        } else if (strEmail.length() == 0) {
            tlUsername.setError(null);
            tlEmail.setError("Email is required!");
            tlEmail.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(strEmail)) {
            tlUsername.setError(null);
            tlEmail.setError("Email is invalid!");
            tlEmail.requestFocus();
            return false;
        } else if (strPhone.length() == 0) {
            tlUsername.setError(null);
            tlEmail.setError(null);
            tlPhone.setError("Phone Number is required!");
            tlPhone.requestFocus();
            return false;
        } else if (strPhone.length() != 10) {
            tlUsername.setError(null);
            tlEmail.setError(null);
            tlPhone.setError("Invalid Phone Number, Note: Do not use +, +91, 91");
            tlPhone.requestFocus();
            return false;
        } else if (strPass.length() == 0) {
            tlUsername.setError(null);
            tlEmail.setError(null);
            tlPhone.setError(null);
            tlPass.setError("Password is required!");
            tlPass.requestFocus();
            return false;
        } else if (strPass.length() < 6) {
            tlUsername.setError(null);
            tlEmail.setError(null);
            tlPhone.setError(null);
            tlPass.setError("Password must be more then 6 char!");
            tlPass.requestFocus();
            return false;
        } else {
            tlUsername.setError(null);
            tlEmail.setError(null);
            tlPhone.setError(null);
            tlPass.setError(null);
            return true;
        }
    }

    private void registerUser() {
        Registration registration = new Registration();
        registration.setUsername(strUsername);
        registration.setEmail(strEmail);
        registration.setPass(strPass);
        registration.setPhone(strPhone);
        new RegisterTask(getContext(), registration, RegistrationFragment.this).execute();
    }

    @Override
    public void getProfile(Profile profile) {

    }

    @Override
    public void onRegistrationCompleted() {
        Toast.makeText(getContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
        tlUsername.getEditText().setText("");
        tlEmail.getEditText().setText("");
        tlPhone.getEditText().setText("");
        tlPass.getEditText().setText("");
        imgClose.performClick();
    }
}