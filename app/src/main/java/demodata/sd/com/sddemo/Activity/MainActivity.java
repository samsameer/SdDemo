package demodata.sd.com.sddemo.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

public class MainActivity extends Activity {
    private Button connectBtn, cancelBtn;// R.id.edit_5,, R.id.edit_7
    private int[] myArray = {R.id.edit_1, R.id.edit_2, R.id.edit_3, R.id.edit_4, R.id.edit_6};
    private EditText[] miBox = new EditText[myArray.length];
    private String[] serConfig = new String[myArray.length];
    private ArrayList arrayListcat = new ArrayList();
    private HashMap<String, ArrayList<String>> arrayItemid = new HashMap<String, ArrayList<String>>();
    private ArrayList arr = new ArrayList();
    private JSONArray addArray = new JSONArray();
    private JSONArray addObj = new JSONArray();
    private JSONArray totlArray = new JSONArray();
    private int[] catIndx;
    private String groupCode = "";
    private HashMap<String, String> arraemid = new HashMap<String, String>();
    private int xCheck = 0;
    private HashMap<String, String> cxHash;
    private HashMap<Integer, String> mapper1 = new HashMap<Integer, String>();
    private ProgressDialog progressDialog;
    private ArrayList<String> cdI = new ArrayList<String>();
    private ArrayList<ArrayList<String>> gcdI = new ArrayList<ArrayList<String>>();
    private ArrayList<HashMap<Integer, ArrayList<JSONObject>>> response;
    private HashMap<Integer, JSONArray> mapI = new HashMap<Integer, JSONArray>();
    private ArrayList<HashMap<Integer, ArrayList<JSONObject>>> cArryList = new ArrayList<HashMap<Integer, ArrayList<JSONObject>>>();
    private HashMap<String, ArrayList<String>> xczI = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> cx = new ArrayList<String>();
    private StringBuilder sBuild;
    private String xChange="";

    private Map<String, String> xhaNames = new HashMap<String, String>();
    //public JSONParser jsonParser = new JSONParser();
    private JSONArray xyz;
    private JSONArray jdsGR = new JSONArray();
    JSONArray tz = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data.....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        getWindow().setLayout(1300, 1500);
        FullScreencall(this);
        connectBtn = (Button) findViewById(R.id.connect);
        cancelBtn = (Button) findViewById(R.id.cancel);



        for (int i = 0; i < myArray.length; i++) {
            miBox[i] = (EditText) findViewById(myArray[i]);
        }
        if (Utilse.loadArray("serverName", MainActivity.this).length == 5) {
            serConfig = Utilse.loadArray("serverName", MainActivity.this);
            for (int k = 0; k < myArray.length; k++) {
                miBox[k].setText(serConfig[k]);
            }
        } else {
            for (int k = 0; k < myArray.length; k++) {
                miBox[k].setText("");
            }
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.nothing, R.anim.fade_in);
            }
        });
//
        if(getIntent().getStringExtra("checkfrom").equalsIgnoreCase("1")){
            xChange=""+1; mthsFr();
        }
        else
        xChange=""+2;


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  final JSONParser client = new JSONParser(MainActivity.this, l);
                mthsFr();



            }
        });

    }


    //    Thread closeActivity = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(3000);
//                // Do some stuff
//            } catch (Exception e) {
//                e.getLocalizedMessage();
//            }
//        }
//    });
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.nothing, R.anim.fade_in);
    }

    private String sBuildxc(String cd) {

        try {
            Log.i("read al giles", cd);
            StringBuilder everything = new StringBuilder();
            URL url = new URL(cd);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String sResponse;
            sBuild = new StringBuilder();

            while ((sResponse = in.readLine()) != null) {
                sBuild = sBuild.append(sResponse);
            }
            in.close();
            Log.i("sd", sBuild.toString());


        } catch (MalformedURLException e) {
        } catch (Exception e) {
            Log.i("sd444", e.getMessage());


        }
//        Bitmap bs = Utilse.decodeBase64(sBuild.toString());

        return sBuild.toString();
    }
    private void mthsFr(){
        for (int j = 0; j < myArray.length; j++) {
            if (miBox[j].getText().toString().matches("")) {
                //Toast.makeText(MainActivity.this, "Enter the Missing " + miBox[j].getHint().toString(), Toast.LENGTH_SHORT).show();
                Utilse.alertDialogShow(MainActivity.this, getString(R.string.cread), getString(R.string.aut_alert), 0);
                return;


            } else {
                serConfig[j] = (miBox[j].getText().toString());


                if (j == myArray.length - 1) {
                    Utilse.userAuth = serConfig[2];
                    Utilse.passAuth = serConfig[3];
                    Log.i("here we get string", "" + Utilse.userAuth + "d" + Utilse.passAuth);
                    //String url = serConfig[0]+":"+serConfig[1]+Utilse.getitemFulldtils+"01"+serConfig[5]+"&paramgroup_code="+serConfig[5];
                    //:8080/kpos/getPosClientParam?compcode=01&regcode=01&outletcode=01
                    if (!serConfig[0].contains("http://")) {
                        serConfig[0] = "http://" + serConfig[0];
                    }


                    if (!Utilse.isConnected(MainActivity.this)) {
                        Utilse.alertDialogShow(MainActivity.this, getString(R.string.netw), "Netwrok status", 0);
                        //Toast.makeText(MainActivity.this, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                    } else {

                        String posUrl = serConfig[0] + ":" + serConfig[1] + Utilse.GETPOSCLIENT;
                        Utilse.MAINTAG = serConfig[0] + ":" + serConfig[1];
                        Log.d("First URle here ", posUrl);
                        // getPublicTimeline(posUrl);
                        //progressDialog.show();
                        Utilse.get(posUrl, null, new JsonHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                progressDialog.show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonFromNet) {
                                // Pull out the first event on the public timeline
                                try {
                                    Log.d("First URle jsonFromNet ", "" + jsonFromNet);
                                    if (jsonFromNet.toString().equalsIgnoreCase("[]")) {
                                        xCheck = 1;
                                        // Toast.makeText(MainActivity.this, "Please fill all missing fields.", Toast.LENGTH_SHORT).show();
                                        Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try again.", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                        //Utilse.alertDialogShow(getApplicationContext(), "Please fill all missing fields.", "Alert", 0);
                                        for (int k = 0; k < myArray.length; k++) {
                                            miBox[k].setText("");
                                        }
                                        serConfig = new String[myArray.length];
                                        Utilse.removeJsonSharedPreferences(MainActivity.this, "serverName");
                                        Utilse.userAuth = "";
                                        Utilse.passAuth = "";
                                        //progressDialog.dismiss();
                                        return;
                                    } else {
                                        Utilse.compnyCd = serConfig[4];
                                        Utilse.saveArray(serConfig, "serverName", MainActivity.this);

                                        Utilse.saveJSONArray(MainActivity.this, "customerlist", "key", jsonFromNet);
                                        String Urdl = serConfig[0] + ":" + serConfig[1] + Utilse.GETLISTITE;
//
                                        Utilse.get(Urdl, null, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onStart() {
                                                //progressDialog.show();

                                            }

                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                                // Pull out the first event on the public timeline
                                                try {
                                                    if (timeline.length() == 0) {
                                                        Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try' again.", Toast.LENGTH_LONG);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.show();
                                                        //progressDialog.dismiss();
                                                        return;

                                                    } else {
                                                        Utilse.saveJSONArray(MainActivity.this, "listitems", "1", timeline);


                                                        String rrdl = serConfig[0] + ":" + serConfig[1] + Utilse.SALESMAN;
//
                                                        Utilse.get(rrdl, null, new JsonHttpResponseHandler() {
                                                            @Override
                                                            public void onStart() {
                                                                //progressDialog.show();

                                                            }

                                                            @Override
                                                            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                                                                // Pull out the first event on the public timeline
                                                                try {
                                                                    if (timeline.length() == 0) {
                                                                        Toast toast = Toast.makeText(MainActivity.this, "Fail to connect server. Please try' again.", Toast.LENGTH_LONG);
                                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                                        toast.show();
                                                                        //progressDialog.dismiss();
                                                                        return;

                                                                    } else {

                                                                        ArrayList<String> namese = new ArrayList<String>();
                                                                        for (int t = 0; t < timeline.length(); t++) {
                                                                            if (timeline.getJSONObject(t).getString("isactive").equalsIgnoreCase("1"))
                                                                                // namese.add(timeline.getJSONObject(t).getString("userid"));
                                                                                arraemid.put(timeline.getJSONObject(t).getString("userid"),timeline.getJSONObject(t).getString("passwd1"));
                                                                        }

                                                                        String[] stockArr = new String[namese.size()];
                                                                        stockArr = namese.toArray(stockArr);

                                                                        Utilse.saveMap( MainActivity.this,arraemid, "salesman");

                                                                    }


                                                                } catch (Exception exception) {
                                                                    //progressDialog.dismiss();
                                                                    return;

                                                                    // handleError(exception);
                                                                }                   // Do something with the response
                                                                System.out.println(timeline);
                                                            }

                                                        });


                                                        // new DownloadWebPageTask().execute();


                                                    }


                                                } catch (Exception exception) {
                                                    //progressDialog.dismiss();
                                                    return;

                                                    // handleError(exception);
                                                }                   // Do something with the response
                                                System.out.println(timeline);
                                            }

                                        });

                                    }
//                                            }else {
//                                                    Utilse.alertDialogShow(MainActivity.this, "Eroor", " Connection  refused", 3);
//                                                }
//
//
//                                            }

                                } catch (Exception e) {


                                    return;
                                }


                                // Do something with the response
                                System.out.println("");
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                xCheck = 1;
                                throwable.printStackTrace(System.out);
                                Utilse.alertDialogShow(MainActivity.this, "Eroor", " Connection  refused", 3);
                            }


                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();
                                if(xChange.equalsIgnoreCase("2")){
                                    Intent i = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                }
                                else{
                                    Intent inti = new Intent(MainActivity.this, LaunchActivity.class);
                                    inti.putExtra("thisMan", "1");
                                    startActivity(inti);
                                }


                            }


                        });

                    }

                }

            }


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
}

//        LabelView label = new LabelView(this);
//        label.setText("POP");
//        label.setBackgroundColor(0xff03a9f4);
//        label.setTargetView(findViewById(R.id.txt1), 10, LabelView.Gravity.LEFT_TOP);
