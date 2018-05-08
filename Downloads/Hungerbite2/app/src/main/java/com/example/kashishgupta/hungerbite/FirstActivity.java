package com.example.kashishgupta.hungerbite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;




public class FirstActivity extends AppCompatActivity implements hellofragment.OnFragmentInteractionListener{
    private static final long RIPPLE_DURATION = 250;


    @BindView(R.id.r1)
    FrameLayout r1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;

    Fragment fragment= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        Intent rcv=getIntent();
        String Locna=rcv.getStringExtra("Lpk");
        ArrayList<String> hl = rcv.getStringArrayListExtra("name");
        ButterKnife.bind(this);
        Bundle bundle=new Bundle();
        bundle.putString("Lpk",Locna);
        bundle.putStringArrayList("nm",hl);
        final FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragment= new RestaurantFragment();
        fragment.setArguments(bundle);



        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        final View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);

        root.addView(guillotineMenu);
        LinearLayout lv1=(LinearLayout) findViewById(R.id.profile_group);
        lv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment= new hellofragment();
            }
        });


        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        fragmentTransaction.add(R.id.root,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}