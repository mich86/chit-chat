package android.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.example.chitchat.dummy.DummyContent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity implements ChatMessageFragment.OnFragmentInteractionListener, HistoryFragment.OnListFragmentInteractionListener, MembersFragment.OnListFragmentInteractionListener {

    //use to write to the log
    String TAG = "FirebaseTest";

    //global variables
    FirebaseApp mApp;
    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener mAuthListener;
    String mDisplayName;

    ViewPager mViewPager;
    FragmentAdapter mFragmentAdapter;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();
        initViewPager();
    }


//    public void setDisplayName(String displayName) {
//
//        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(displayName).build();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        currentUser.updateProfile(changeRequest);
//
//        Log.d(TAG, "Display name set [" + displayName + "]");
//    }

    private void initFirebase() {

        //set the global variables as locals and assign to Firebase instances
        mApp = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(mApp);

        //create listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //is a user already logged in
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    //use log to check status
                    Log.e(TAG, "Status Update : Valid user logged on : email [" + user.getEmail() + "] display name [" + user.getDisplayName() + "]");

                    //display users name
                    String displayName = user.getDisplayName();

                    if (displayName != null)
                        mDisplayName = displayName;
                    else
                        mDisplayName = "Unknown DisplayName";

                } else {
                    Log.e(TAG, "Status Update : No valid user logged on");
                    //display name as none
                    mDisplayName = "No valid user";

                    mAuth.removeAuthStateListener(mAuthListener);
                    Intent signInIntent = new Intent(getApplicationContext(), SignIn.class);
                    startActivityForResult(signInIntent, 101); //identifier for this result to pass back
                }
            }
        };
        //add listener
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onActivityResult(int RequestCode, int ResultCode, Intent data) {

        if ((RequestCode == 101) && (ResultCode == RESULT_OK)) {

            //get displayName from intent
            mDisplayName = data.getStringExtra("display name");
            Log.e(TAG, "Returned activity display name [" + mDisplayName + "]");
            mAuth.addAuthStateListener(mAuthListener);
        }
        super.onActivityResult(RequestCode, ResultCode, data);
    }

    //menu section
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    //use log out menu item to log out
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.option_logout) {
            Log.e(TAG, "Log Out option selected");
            mAuth.signOut();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        //link view pager and frag adapter
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //implement user interface listener from chatmessagefragment.java
    public void onFragmentInteraction(Uri uri) {

        Log.e(TAG, "Fragment Interaction Listener");
    }

    //implementing the history list fragment for the recycler view
    public void onHistoryListFragmentInteraction(DummyContent.DummyItem item) {

        Log.e(TAG, "History Fragment");
    }

    //implement communications class members list
    public void onMembersListFragmentInteraction(DummyContent.DummyItem item) {

        Log.e(TAG, "Members Fragment");
    }
}
