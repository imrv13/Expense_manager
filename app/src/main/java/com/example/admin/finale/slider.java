package com.example.admin.finale;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class slider extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    PersonDatabaseHelper helper2;
    account a;


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        Intent newintent5=getIntent();
        long value5=newintent5.getLongExtra("rowid", 0);
        Bundle bundle5=new Bundle();
        bundle5.putLong("id", value5);
        Log.d(TAG, "clicked on home: " + value5);
        Overview fragment3=new Overview();
        fragment3.setArguments(bundle5);
        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.frame, fragment3);
        fragmentTransaction3.commit();


        helper2=new PersonDatabaseHelper(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent newintent3=getIntent();
        long value3=newintent3.getLongExtra("rowid", 0);
        a=new account(value3);

        Cursor res = helper2.getincome(value3);
        if (res.moveToFirst()) {
            do {
                String name = res.getString(res.getColumnIndex(res.getColumnName(1)));
                // do what ever you want here
                a.setname(name);
            } while (res.moveToNext());
        }
        res.close();
        getSupportActionBar().setTitle(a.getname(a));




        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                navigationView.getMenu().getItem(1).setChecked(true);
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.Home:
                        Toast.makeText(getApplicationContext(),"Home Selected",Toast.LENGTH_SHORT).show();
                        Intent newintent5=getIntent();
                        long value5=newintent5.getLongExtra("rowid", 0);
                        Bundle bundle5=new Bundle();
                        bundle5.putLong("id", value5);
                        Log.d(TAG, "clicked on home: " + value5);
                        Overview fragment3=new Overview();
                        fragment3.setArguments(bundle5);
                        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.frame,fragment3);
                        fragmentTransaction3.commit();
                        return true;


                    // For rest of the options we just show a toast on click

                    case R.id.Expense:
                        Intent newintent=getIntent();
                        long value=newintent.getLongExtra("rowid", 0);
                        float percent=getper(value);
                        Log.d(TAG, "getpercent: " + percent);
                        if(percent>=0.8){
                            showNotification();
                        }

                        Bundle bundle=new Bundle();
                        bundle.putLong("id", value);
                        Log.d(TAG, "clicked on home: " +value);
                        ContentFragment fragment = new ContentFragment();
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.Calender:
                        Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        Intent newintent1=getIntent();
                        long value1=newintent1.getLongExtra("rowid", 0);
                        Bundle bundle11=new Bundle();
                        bundle11.putLong("id", value1);
                        mycal fragment2 = new mycal();
                        fragment2.setArguments(bundle11);
                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame,fragment2);
                        fragmentTransaction2.commit();
                        return true;
                    case R.id.pie:
                        Intent newi=getIntent();
                        long va=newi.getLongExtra("rowid", 0);
                        Bundle bundle1=new Bundle();
                        bundle1.putLong("id", va);
                        piefragment fragment1 = new piefragment();
                        fragment1.setArguments(bundle1);
                        android.support.v4.app.FragmentTransaction fragmentTransaction1= getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.frame,fragment1);
                        fragmentTransaction1.commit();

                        Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();








    }

    public float getper(long id){



        int total_expense=helper2.getexpense(id);
        Log.d(TAG, "getexpense: " + total_expense);
        Cursor rincome=helper2.getincome(id);
        if (rincome.moveToFirst()) {
            do {
                int income = rincome.getInt(rincome.getColumnIndex(rincome.getColumnName(2)));
                a.setincome(income);
            } while (rincome.moveToNext());
        }
        rincome.close();

        int remain_income=a.getincome();
        Log.d(TAG, "getincome: " +remain_income);
        int total=remain_income+total_expense;
        float per=((float)total_expense/total);
        Log.d(TAG, "getpercentage: " +per);
        return per;
    }
    public void showNotification(){
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this,reciever.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification mNotification = new Notification.Builder(this).setContentTitle("your saving").setContentText("you have reached 80% limit").setSound(soundUri).setContentIntent(pIntent).build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

