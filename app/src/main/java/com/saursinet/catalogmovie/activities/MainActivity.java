package com.saursinet.catalogmovie.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.fragments.CarousselFragment_;
import com.saursinet.catalogmovie.fragments.RecyclerViewFragment;
import com.saursinet.catalogmovie.fragments.RecyclerViewFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.fragmentContainer)
    FrameLayout frameLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void initDrawer() {
        new DrawerBuilder().withActivity(this).build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Top Rated").withIcon(R.drawable.ic_movie);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Carroussel").withIcon(R.drawable.ic_caroussel);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Fragment fragment = null;
                        Class fragmentClass;

                        switch (position) {
                            case 0:
                                fragmentClass = RecyclerViewFragment_.class;
                                break;
                            case 1:
                                fragmentClass = CarousselFragment_.class;
                                break;
                                default:
                                    fragmentClass = RecyclerViewFragment_.class;
                        }
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

                        return false;
                    }
                })
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerViewFragment fragment = RecyclerViewFragment_.builder().build();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}
