package demodata.sd.com.sddemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import demodata.sd.com.sddemo.Adapter.CustomPagerAdapter;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    int page = 1;
    private Animation anim, animw;
    private CustomPagerAdapter mCustomPagerAdapter;
    private int[] selectedItem;
    public Timer timer;
    private int dotsCount;
    private TextView seting;
    private ImageView[] dots;
    private String[] SalesMan;
    private Map<String, String> txMains = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        pageSwitcher(2);


        seting = (TextView) findViewById(R.id.seting);

        mtActivity();

        seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intX = new Intent(WelcomeActivity.this, MainActivity.class);
                intX.putExtra("checkfrom","0");
                startActivity(intX);
            }
        });

        final EditText user = (EditText) findViewById(R.id.us1);
        final EditText pass = (EditText) findViewById(R.id.pass2);

        ((TextView) findViewById(R.id.vrsion)).setText("V: "+getString(R.string.version));
        ((TextView) findViewById(R.id.exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        try {
            //txMains=new HashMap<>();
            txMains = Utilse.loadMap(WelcomeActivity.this, "salesman");
            Log.d("mapsssss",""+txMains.keySet());
//            SalesMan = Utilse.loadArray("salesman", WelcomeActivity.this);
//            Log.d("enttrrr",""+SalesMan+"length"+SalesMan[0]);
        } catch (Exception e) {

        }


if(!PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("usernm","defaultStringIfNothingFound").equalsIgnoreCase("defaultStringIfNothingFound"))
    user.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("usernm","defaultStringIfNothingFound"));
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    fdPos(user.getText().toString(),pass.getText().toString());
                  /* Write your logic here that will be executed when user taps next button */


                    handled = true;
                }
                return handled;
            }
        });

        Button dialogButton1 = (Button) findViewById(R.id.sdilog);
        // if button is clicked, close the custom dialog
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdPos(user.getText().toString(),pass.getText().toString());

            }
        });


    }


    private void mtActivity() {
        if (Utilse.loadArray("serverName", WelcomeActivity.this).length != 5) {
            Utilse.userAuth = null;
            Utilse.passAuth = null;
            Intent intX = new Intent(WelcomeActivity.this, MainActivity.class);
            intX.putExtra("checkfrom","0");
            startActivity(intX);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
    }

    public void pageSwitcher(int seconds) {
        Timer timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 4) {
                        page = 0;
                        mViewPager.setCurrentItem(page);

                        // In my case the number of pages are 5
//                        timer.cancel();
//                        // Showing a toast for just testing purpose
//                        Toast.makeText(getApplicationContext(), "Timer stoped",
//                                Toast.LENGTH_LONG).show();
                    } else {
                        mViewPager.setCurrentItem(page++);
                        setUiPageViewController();
                    }

                }
            });

        }
    }


    private void fdPos(String userrd,String passwrd){
        if (txMains.containsKey(userrd)||txMains.containsKey(userrd.toUpperCase())) {
            if (txMains.get(userrd.toUpperCase()).equals(passwrd)) {
                Utilse.SalesMan = userrd;
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("usernm", userrd).commit();

                Intent inti = new Intent(WelcomeActivity.this, LaunchActivity.class);
                inti.putExtra("thisMan", "1");
                startActivity(inti);
            }
            else {

                Toast toast = Toast.makeText(WelcomeActivity.this, "Entered User Id doesn't exist. Please enter valid userid.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                // return;
            }
        } else {

            Toast toast = Toast.makeText(WelcomeActivity.this, "Entered User Id doesn't exist. Please enter valid userid.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            // return;
        }
    }

    public void FullScreencall(Context xocn) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = ((Activity) xocn).getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ((Activity) xocn).getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void setUiPageViewController() {

        dotsCount = mCustomPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT
            );

            params.setMargins(4, 0, 4, 0);
            mViewPager.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}




