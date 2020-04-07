package com.appdeveloper.prince.princenitjsr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView mNavigationView;
    View mHeaderView;
    private FirebaseAuth mAuth;
    TextView mUserName,mUserEmail;
    ImageView mUserImage;
    public String userEmail;
    String linkedin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(this);
        if(sharedPrefManager.isLoggedIn()){
            updateSideNavHeader();
        }
        else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 linkedin="https://www.linkedin.com/in/princenitjsr/";
                Snackbar.make(view, "Hello! "+"Click here to visit my LinkedIn Profile!", Snackbar.LENGTH_LONG)
                        .setAction("Visit", new MyUndoListener()).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void updateSideNavHeader(){

        mAuth = FirebaseAuth.getInstance();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // NavigationView Header
        mHeaderView =  mNavigationView.getHeaderView(0);

        // View
        mUserName = (TextView) mHeaderView.findViewById(R.id.name);
        mUserEmail = (TextView) mHeaderView.findViewById(R.id.email);
        mUserImage = (ImageView) mHeaderView.findViewById(R.id.imageView);

        userEmail=mAuth.getCurrentUser().getEmail();

        // Set username & email
        mUserName.setText(mAuth.getCurrentUser().getDisplayName());
        mUserEmail.setText(mAuth.getCurrentUser().getEmail());
        Glide.with(getApplicationContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(mUserImage);

        mNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    public class MyUndoListener implements  View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedin));
            startActivity(browserIntent);
        }
    }
}
