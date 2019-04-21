package pratik.com.myapplication;

import android.animation.Animator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import pratik.com.myapplication.Fragment.LoginFragment;
import pratik.com.myapplication.Fragment.RegistrationFragment;
import pratik.com.myapplication.Interfaces.OnFragmentTouched;

public class MainActivity extends AppCompatActivity implements OnFragmentTouched {

    private String TAG = "MainActivity";
    private android.support.design.widget.FloatingActionButton fabRegister;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews(savedInstanceState);
        /*btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateViews()) {
                    registerUser();
                }
            }
        });*/
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomColor = getResources().getColor(R.color.light_pink_plus);
                Fragment fragment = RegistrationFragment.newInstance(20, 20, randomColor);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout, fragment)
                        .commit();
                fabRegister.setVisibility(View.GONE);
            }
        });
    }

    private void initViews(Bundle savedInstanceState) {
        fabRegister = findViewById(R.id.fabRegister);
        frameLayout = findViewById(R.id.frameLayout);
        Fragment fragment = new LoginFragment();
        if (savedInstanceState == null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onFragmentTouched(Fragment fragment, float x, float y) {
        if (fragment instanceof RegistrationFragment) {
            final RegistrationFragment registrationFragment = (RegistrationFragment) fragment;

            Animator unreveal = registrationFragment.prepareUnrevealAnimator(x, y);

            unreveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //remove the fragment only when the animation finishes
                    getSupportFragmentManager().beginTransaction().remove(registrationFragment).commit();
                    fabRegister.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            unreveal.start();
        }
    }
}