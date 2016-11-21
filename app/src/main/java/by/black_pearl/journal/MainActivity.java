package by.black_pearl.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import by.black_pearl.journal.fragments.CardsFragment;
import by.black_pearl.journal.fragments.RackFragment;
import by.black_pearl.journal.fragments.TestsFragment;
import by.black_pearl.journal.workers.JournalWorker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LAST_FRAGMENT = "lastfragment";

    private int mLastFragment = 0;
    private JournalWorker mJournalWorker;

    public MainActivity() {
        this.mJournalWorker = new JournalWorker(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startActivity(new Intent(this, JournalActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_rack:
                beginFragmentReplace(0);
                break;
            case R.id.nav_tests:
                beginFragmentReplace(1);
                break;
            case R.id.nav_cards:
                beginFragmentReplace(2);
                break;
            default:
                beginFragmentReplace(0);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mLastFragment = savedInstanceState.getInt(LAST_FRAGMENT, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        beginFragmentReplace(mLastFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_FRAGMENT, mLastFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void beginFragmentReplace(int id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case 0:
                fragmentTransaction.replace(R.id.content_main, RackFragment.newInstance());
                mLastFragment = 0;
                break;
            case 1:
                fragmentTransaction.replace(R.id.content_main, TestsFragment.newInstance());
                mLastFragment = 1;
                break;
            case 2:
                fragmentTransaction.replace(R.id.content_main, CardsFragment.newInstance());
                mLastFragment = 2;
                break;
            default:
                fragmentTransaction.replace(R.id.content_main, RackFragment.newInstance());
                mLastFragment = 0;
                break;
        }
        fragmentTransaction.commit();
    }

    public JournalWorker getJournalWorker() {
        return mJournalWorker;
    }

    public interface MainActivityInterface {
        void call();
    }
}
