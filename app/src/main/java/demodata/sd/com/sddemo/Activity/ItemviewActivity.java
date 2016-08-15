package demodata.sd.com.sddemo.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import demodata.sd.com.sddemo.Adapter.ItemviewAdapter;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.Adapter.OrderviewAdapter;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.RecyclerItemClickListener;
import demodata.sd.com.sddemo.Model.Stockin;

public class ItemviewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public RecyclerView ordRecycle;
    public RecyclerView detailRc;
    private RelativeLayout scndfrm;
    public RecyclerView.LayoutManager ordLayoutManager;
    private RecyclerView.LayoutManager detailManager;
    public RecyclerView.Adapter mOrdAdapter;
    public OrderviewAdapter detaiAdapter;
    public static int xyzInt = 0;
    private Animation slide_down, slide_up;
    public static JSONArray txArray = new JSONArray();
    private int dcno = 101221;
    private RelativeLayout myView, relVt;
    private SearchView mSearchView;
    private String[] detM = {"12789564", "30 days", "Alvin", "3E GROUP PTE LTD", "16 Raffles Quay, Singapore 048581", "7864321A21"};
    public static ArrayList<Orderin> tstOrder = new ArrayList<Orderin>();
    private ArrayList<Stockin> tsArray = new ArrayList<>();
    private ArrayList<Orderin> ordrArray = new ArrayList<Orderin>();
    private int[] txtIn = {R.id.srchbtn, R.id.save, R.id.cls};
    private RelativeLayout _rdLayout;
    public static int frstTIm=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = ((RelativeLayout) findViewById(R.id.rc1));
        relVt = ((RelativeLayout) findViewById(R.id.rel));
        ordRecycle = (RecyclerView) findViewById(R.id.orre);
        _rdLayout = ((RelativeLayout) findViewById(R.id.secndr));
        scndfrm = ((RelativeLayout) findViewById(R.id.scndfrm));
        ordLayoutManager = new GridLayoutManager(this, 1);
        ordRecycle.setLayoutManager(ordLayoutManager);
        ordRecycle.setHasFixedSize(true);
        ordRecycle.setNestedScrollingEnabled(true);
        mSearchView = (SearchView) findViewById(R.id.srcdddd);
        detailRc = (RecyclerView) findViewById(R.id.tdt);
        detailManager = new GridLayoutManager(getApplicationContext(), 1);
        detailRc.setLayoutManager(detailManager);
        detailRc.setHasFixedSize(true);
//Load animation
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(_rdLayout.getVisibility()==View.VISIBLE && scndfrm.getVisibility()!=View.VISIBLE){
                            scndfrm.setAnimation(inFromLeftAnimation());
                            scndfrm.setVisibility(View.VISIBLE);

                            RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(460,
                                    RelativeLayout.LayoutParams.FILL_PARENT );

                            scndfrm.setLayoutParams(layout_description);


                        }
                        else{
                            scndfrm.setAnimation(outFromLeftAnimation());
                            RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(10,
                                    RelativeLayout.LayoutParams.FILL_PARENT );

                            scndfrm.setLayoutParams(layout_description);
                        }

                    }
                }, 200);
            }
        });
//        LayoutTransition l = new LayoutTransition();
//        l.enableTransitionType(LayoutTransition.CHANGING);
//        ((RelativeLayout)findViewById(R.id.rel)).setLayoutTransition(l);
//        ObjectAnimator animation1 = ObjectAnimator.ofFloat( ((Button)findViewById(R.id.srchbtn)),
//                "rotation", 360);
//        animation1.setDuration(2000);
//        animation1.start();

        ((Button) findViewById(txtIn[0])).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vMethod(0);
            }
        });
        ((Button) findViewById(txtIn[2])).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vMethod(1);
            }
        });
        ((Button) findViewById(R.id.newit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent xyz = new Intent(ItemviewActivity.this, NewOrderActivity.class);
                xyz.putExtra("inxr", "abc");
                startActivity(xyz);


            }
        });
        ((Button) findViewById(R.id.amnd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        ((Button) findViewById(txtIn[1])).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("getAdapter", "" + ItemviewActivity.xyzInt);
                //if(ItemviewActivity.xyzInt==-5)
                Toast.makeText(ItemviewActivity.this, "Successfully updated.", Toast.LENGTH_SHORT).show();


            }
        });
        ordRecycle.setItemAnimator(new DefaultItemAnimator());
        ordRecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(ItemviewActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("getAdapter", "" + ItemviewActivity.xyzInt);
                        for (int i = 0; i < ordRecycle.getChildCount(); i++) {
                            if (i == position) {
                                View xyz = (ordRecycle.getChildAt(position));
                                xyz.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            } else {
                                View xyz = (ordRecycle.getChildAt(i));
                                xyz.setBackgroundColor(getResources().getColor(android.R.color.white));
                            }

                        }
                        _rdLayout.setVisibility(View.GONE);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                _rdLayout.setVisibility(View.VISIBLE);
                                _rdLayout.setAnimation(inFromLeftAnimation());
                            }
                        }, 200);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                if (ItemviewActivity.xyzInt == -5)
                                    Toast.makeText(ItemviewActivity.this, "Please Choose any order.", Toast.LENGTH_SHORT).show();
                                else {
//                    mth(2);
//                    tsArray
                                    mrt(null, 2);
                                    vMethod(1);
                                }
                            }
                        }, 1000);


                    }


                }

                )


        );
        mth(1);

    }


    private void mth(int xyzInt) {

        try {
            String json = "";
            try {
                Log.e("myAppssssssxyzInt", xyzInt + "cccccccccccc");

                if (xyzInt == 1) {
                    BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.fg));

                    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        json += line;
                    }
                    reader.close();
                    resourceStream.close();

                } else {
                    BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.dtls));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json += line;
                    }
                    reader.close();
                    resourceStream.close();
                }
                JSONArray jsonDAr = new JSONArray(json);
                mrt(jsonDAr, xyzInt);

            } catch (Exception ex) {
                Log.e("myApp", ex.getMessage());
            }

            JSONArray jsonDAr = new JSONArray(json);
            //metdo(jsonDAr, xyzInt);
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mrt(JSONArray jds, int xcf) {
        try {
            if (xcf == 1) {
                tsArray = new ArrayList<Stockin>();
                Stockin vIS;
                for (int x = 0; x < jds.length(); x++) {
                    JSONObject eO = new JSONObject();
                    eO = jds.getJSONObject(x);
                    vIS = new Stockin();
                    vIS.setitem_desc(eO.getString("vend_name"));
                    vIS.setitem_no(eO.getString("doc_no"));
                    vIS.setuom(eO.getString("doc_date"));
                    vIS.setprod_date("NEW");
                    vIS.sets_no("" + (x + 1));
                    vIS.settxtis(ntOrderList());
                    tsArray.add(vIS);
                }

                mOrdAdapter = new ItemviewAdapter(ItemviewActivity.this, tsArray);
                ordRecycle.setAdapter(mOrdAdapter);


            } else {

                detaiAdapter = new OrderviewAdapter(ItemviewActivity.this, tsArray.get(ItemviewActivity.xyzInt).gettxtis(),1);
                detailRc.setAdapter(detaiAdapter);
            }

        } catch (Exception e) {

        }
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        detaiAdapter.getFilter().filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onBackPressed() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public class PowerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_POWER_CONNECTED) {
                //Handle power connected
            } else if (intent.getAction() == Intent.ACTION_POWER_DISCONNECTED) {
                //Handle power disconnected
            }
        }
    }

    private void txMai() {


    }

    private void vMethod(int xy) {
        if (xy == 0) {
            //mSearchView.setVisibility(View.VISIBLE);

            //.startAnimation(slide_up);
            txtAnmination(relVt, 1);
            //((RelativeLayout) findViewById(R.id.rc1)).setVisibility(View.GONE);
            exitReveal();

            for (int i = 0; i < txtIn.length; i++)
                ((Button) findViewById(txtIn[i])).setVisibility(View.GONE);

            ((Button) findViewById(txtIn[2])).setVisibility(View.VISIBLE);

        } else {
            backAnimation(relVt);
            for (int i = 0; i < txtIn.length; i++)
                ((Button) findViewById(txtIn[i])).setVisibility(View.VISIBLE);

            ((Button) findViewById(txtIn[2])).setVisibility(View.GONE);
            mSearchView.setQuery("", false);
            mSearchView.clearFocus();
            mSearchView.setVisibility(View.INVISIBLE);

        }
    }


    void enterReveal() {
        // previously invisible view


        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    void exitReveal() {
        // previously visible view


        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.GONE);
            }
        });

        // start the animation
        anim.start();
    }

    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(600);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void txtAnmination(View viewRoot, final int xc) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        //viewRoot.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (xc == 1)
                    animateRevealShow(mSearchView);
                //   animateButtonsIn();
            }
        });
        anim.start();
    }

    private void backAnimation(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        // viewRoot.setBackgroundColor(color);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animateRevealShow(myView);
            }
        });
        anim.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ArrayList<Orderin> ntOrderList() {
        try {
            String json = "";
            BufferedInputStream resourceStream = new BufferedInputStream(getResources().openRawResource(R.raw.dtls));
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            reader.close();
            resourceStream.close();

            JSONArray jds = new JSONArray(json);
//        mrt(jsonDAr, xyzInt);

            ordrArray = new ArrayList<Orderin>();
            tstOrder = new ArrayList<Orderin>();
            Orderin vS;
            for (int x = 0; x < jds.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = jds.getJSONObject(x);
                vS = new Orderin();
                vS.sets_no(eO.getString("s_no"));
                vS.setqty_in("1.00");
                vS.setitem_no(eO.getString("item_no"));
                vS.setitem_desc(eO.getString("item_desc"));
                vS.setuom("% 0");
                tstOrder.add(vS);
                ordrArray.add(vS);
            }
        } catch (Exception e) {

        }

        return ordrArray;
    }

    private Animation inFromLeftAnimation() {

        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outFromLeftAnimation() {

        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }
}
