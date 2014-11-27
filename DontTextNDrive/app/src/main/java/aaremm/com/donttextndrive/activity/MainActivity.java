package aaremm.com.donttextndrive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.gc.materialdesign.views.ButtonFloat;

import aaremm.com.donttextndrive.R;
import aaremm.com.donttextndrive.adapter.TabsPagerAdapter;
import aaremm.com.donttextndrive.config.BApp;
import aaremm.com.donttextndrive.service.UserActivityService;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends FragmentActivity {
    @InjectView(R.id.buttonFloat_switch)ButtonFloat bf_switch;
    @InjectView(R.id.ll_main_mode)LinearLayout ll_passMode;
    @InjectView(R.id.buttonFloat_mode)ButtonFloat bf_mode;
    private TabsPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        tabs.setIndicatorColor(getResources().getColor(R.color.peter));
        startService(new Intent(this, UserActivityService.class));

        bf_switch.setBackgroundColor(getResources().getColor(R.color.red));
        if(BApp.getInstance().getSPBoolean("switch")){
            bf_switch.setDrawableIcon(getResources().getDrawable(R.drawable.on));
        }else{
            bf_switch.setDrawableIcon(getResources().getDrawable(R.drawable.off));
        }
        if(BApp.getInstance().getSPBoolean("mode")){
            ll_passMode.setVisibility(View.VISIBLE);
        }else{
            ll_passMode.setVisibility(View.GONE);
        }
        bf_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BApp.getInstance().getSPBoolean("mode")){
                    ll_passMode.setVisibility(View.GONE);
                    BApp.getInstance().setSPBoolean("mode",false);
                }else {
                    ll_passMode.setVisibility(View.VISIBLE);
                    BApp.getInstance().setSPBoolean("mode",true);
                }
            }
        });


        bf_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BApp.getInstance().getSPBoolean("switch")){
                    bf_switch.setDrawableIcon(getResources().getDrawable(R.drawable.off));
                    BApp.getInstance().setSPBoolean("switch",false);
                }else {
                    bf_switch.setDrawableIcon(getResources().getDrawable(R.drawable.on));
                    BApp.getInstance().setSPBoolean("switch",true);
                }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
