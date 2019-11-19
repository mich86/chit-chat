package android.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    String TAG = "FirebaseSignIn";

    EditText mUserEmail;
    EditText mUserPassword;
    EditText mUserConfirmPassword;
    EditText mUserDisplayName;

    Button mLogonButton;
    TextView mUserRegister;

    //for visibility
    boolean mLogonInProgress = false;
    boolean mRegisterInProgress = false;

    String mDisplayName = "Unknown";

    FirebaseApp mApp;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initDisplayControls();
        initListeners();
        initFirebase();
    }

    private void initDisplayControls() {

        mLogonInProgress = false;
        mRegisterInProgress = false;
        mDisplayName = "Unknown";


        mUserEmail = (EditText) findViewById(R.id.emailEdit);
        mUserPassword = (EditText) findViewById(R.id.passwordEdit);
        mUserConfirmPassword = (EditText) findViewById(R.id.confirmPasswordEdit);
        mUserDisplayName = (EditText) findViewById(R.id.displayNameEdit);

        mLogonButton = findViewById(R.id.logonButton);
        mUserRegister = findViewById(R.id.registerText);

        //make controls invincible
        mUserEmail.setVisibility(View.GONE);
        mUserPassword.setVisibility(View.GONE);
        mUserConfirmPassword.setVisibility(View.GONE);
        mUserDisplayName.setVisibility(View.GONE);
    }

    //listener for logon button
    private void initListeners() {

        mLogonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterInProgress = false;

                if (!mLogonInProgress) {

                    //first time login button clicked to logon
                    mUserEmail.setVisibility(View.VISIBLE);
                    mUserPassword.setVisibility(View.VISIBLE);
                    mUserConfirmPassword.setVisibility(View.GONE);
                    mUserDisplayName.setVisibility(View.GONE);

                    mLogonInProgress = true;

                } else {

                    //logon button clicked again
                    String email = mUserEmail.getText().toString();
                    String password = mUserPassword.getText().toString();

                    loginUser(email, password);

                }

            }
        });

        //listener for register text field
        mUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLogonInProgress = false;


                if (!mRegisterInProgress) {

                    //first time registration text clicked
                    mUserEmail.setVisibility(View.VISIBLE);
                    mUserPassword.setVisibility(View.VISIBLE);
                    mUserConfirmPassword.setVisibility(View.VISIBLE);
                    mUserDisplayName.setVisibility(View.VISIBLE);

                    mRegisterInProgress = true;

                } else {

                    //registration text clicked again
                    String email = mUserEmail.getText().toString();
                    String password = mUserPassword.getText().toString();
                    String confirmPassword = mUserConfirmPassword.getText().toString();
                    mDisplayName = mUserDisplayName.getText().toString();

                    registerUser(email, password, mDisplayName);
                }
            }
        });
    }

    //initialise Firebase
    private void initFirebase() {

        mApp = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(mApp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    mDisplayName = user.getDisplayName().toString();
                    Log.e(TAG, "SignIn : Valid current user : email [" + user.getEmail() + "] display name [" + mDisplayName + "]");

                    mLogonInProgress = false;
                    mRegisterInProgress = false;

                    finishActivity();
                } else {
                    Log.e(TAG, "SignIn : No current user");
                }

            }
        };
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void registerUser(String email, String password, String displayName) {

        //successful listener
        OnCompleteListener<AuthResult> complete = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //response to call or registration
                if (task.isSuccessful())
                    Log.e(TAG, "SignIn : User Registered ");
                else Log.e(TAG, "SignIn : User Reg failed ");
            }
        };

        //failed listener
        OnFailureListener failure = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e(TAG, "SignIn : Reg failed");
            }
        };

        Log.e(TAG, "SignIn : email [" + email + "] password [" + password + "]");
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(complete).addOnFailureListener(failure);
    }


    private void loginUser(String email, String password) {

        OnCompleteListener<AuthResult> complete = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //response to call or registration
                if (task.isSuccessful())
                    Log.e(TAG, "SignIn : User logged on");
                else Log.e(TAG, "SignIn : User log on response but failed");
            }
        };

        OnFailureListener failure = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "SignIn : Log on failed");
            }
        };

        Log.e(TAG, "SignIn : Logging in : eMail [" + email + "] password [" + password + "]");
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(complete).addOnFailureListener(failure);
    }


    private void finishActivity() {

        Log.e(TAG, "Finishing Sign In Activity");
        mAuth.removeAuthStateListener(mAuthStateListener);

        Intent returningIntent = new Intent();
        //pass data that is to be sent back to activity
        returningIntent.putExtra("display name", mDisplayName);
        setResult(RESULT_OK, returningIntent);

        finish();
    }
}
