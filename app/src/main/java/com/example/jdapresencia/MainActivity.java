package com.example.jdapresencia;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    boolean isAdmin = false;
    TextView usrName, coName;
    ImageView usrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_home_admin,
                R.id.nav_gestionar_trabajadores, R.id.nav_buscador_trabajadores,
                R.id.nav_mis_registros, R.id.nav_send, R.id.nav_receive, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //Se muestra el navigation diferente segun el tipo de usuario
        String userType = getIntent().getStringExtra("rol_key");

        //Sesión con el ID del usuario que hace login utilizando SharedPreferences
        String idSession = getIntent().getStringExtra("idSession_key");

        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("sessionUserId_key", idSession);
        edt.commit();

        /*******************************************************/
        /*Set start destination desde código java en vez de xml*/
        /*******************************************************/
        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);

        if (userType.equals("admin")){
            isAdmin = true;
            graph.setStartDestination(R.id.nav_home_admin);
        } else {
            graph.setStartDestination(R.id.nav_home);
        }

        navController.setGraph(graph);
        /*******************************************************/

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Menu nav_Menu = navigationView.getMenu();

        //Esconde algunas opciones del menú dependiendo del tipo de usuario
        if (isAdmin){
            nav_Menu.findItem(R.id.nav_home).setVisible(false);
            nav_Menu.findItem(R.id.nav_mis_registros).setVisible(false);
            nav_Menu.findItem(R.id.nav_send).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_home_admin).setVisible(false);
            nav_Menu.findItem(R.id.nav_gestionar_trabajadores).setVisible(false);
            nav_Menu.findItem(R.id.nav_buscador_trabajadores).setVisible(false);
            nav_Menu.findItem(R.id.nav_receive).setVisible(false);
        }

        /**
         * NAVIGATION HEADER DATA
         */
        View header = navigationView.getHeaderView(0);
        coName = (TextView) header.findViewById(R.id.textViewApp);
        usrName = (TextView) header.findViewById(R.id.textView);
        usrImg = (ImageView) header.findViewById(R.id.imageView);

        coName.setText("JdA Presència");
        usrName.setText(MVVMRepository.getUserById(idSession));

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storageRef.child(idSession + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri).into(usrImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("INFO", "No user image");
            }
        });
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
}
