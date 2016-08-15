package demodata.sd.com.sddemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import demodata.sd.com.sddemo.Adapter.ItemviewAdapter;
import demodata.sd.com.sddemo.Adapter.SalesviewAdapter;
import demodata.sd.com.sddemo.Model.MyComparator;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.Model.StcOutModel;
import demodata.sd.com.sddemo.Model.Stockin;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.RecyclerItemClickListener;
import demodata.sd.com.sddemo.Utils.Utilse;

public class LaunchActivity extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener {
    private RecyclerView _mMasterRecy, _mSalesOrder;
    public RecyclerView.LayoutManager _masrterlat;
    public RecyclerView.LayoutManager _mSallayi;
    public ItemviewAdapter _mOrdAdapter;
    private SalesviewAdapter _slesOrder;
    public static ArrayList<Stockin> tsArray = new ArrayList<Stockin>();
    public static HashMap<String,StcOutModel> stcOutArr = new HashMap<String,StcOutModel>();
    public static ArrayList<StcOutModel> ascenDOutArr = new ArrayList<StcOutModel>();
    private ArrayList<Orderin> ordrArray = new ArrayList<Orderin>();
    private String xIntrg = "";
    private int poTTs = 0;
    private JSONArray jsdxx = new JSONArray();
    private JSONArray xtems = new JSONArray();
    private ProgressDialog pdDilog;
    private Button logout, neworder, erpSubmit;
    private RecyclerView.Adapter detaiAdapter;
    private String hrdUrl = "http://192.168.0.21:8084/kaiserpad/web_getSOHeader?compcode=01&docdate=2016/04/01&loginuserid=KP&custno=%25";
    private SearchView srView;
    private ArrayList<Integer> tsIntr = new ArrayList<>();
    private String[] servrConfig = {};
    private Button _salesOrder;
    private int finlCunt = -1;

    private String[] xcRt = {"01", "", "", "", "201403", "-", "", "", "2",Utilse.SalesMan, "SGD", "1.00", "0", "7", "0", "100", "o", ""};
    //private String[] dtlsRt = {"01", "", "", "", "", "", "", "", "", "", "", "", "N", "0", "0", "100", "GST&", "7","7","",""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_main);
        _mSalesOrder = (RecyclerView) findViewById(R.id._details);
        _mMasterRecy = (RecyclerView) findViewById(R.id._master);
        _masrterlat = new GridLayoutManager(this, 1);
        _mMasterRecy.setLayoutManager(_masrterlat);
        _mMasterRecy.setHasFixedSize(true);
        _mSallayi = new GridLayoutManager(this, 1);
        _mSalesOrder.setLayoutManager(_mSallayi);
        _mSalesOrder.setHasFixedSize(true);
        pdDilog = new ProgressDialog(this);
        pdDilog.setMessage("Loading Data.....");
        pdDilog.setCancelable(false);
        pdDilog.setIndeterminate(true);
        srView = (SearchView) findViewById(R.id.scre);
        _salesOrder = (Button) findViewById(R.id.erps);
        if (getIntent().getExtras().getString("thisMan").equalsIgnoreCase("1")) {
            try {
                jsdxx = new JSONArray();
                jsdxx = Utilse.loadJSONArray(LaunchActivity.this, "MainJsonArrya", "js");
                mthd(jsdxx);
            } catch (Exception e) {

            }

        }
        else{
            chngSalesOrder();
        }


        servrConfig = Utilse.loadArray("serverName", LaunchActivity.this);
        Utilse.MAINTAG = servrConfig[0] + ":" + servrConfig[1];
        Utilse.newItemOrder = new LinkedHashMap<String, String[]>();
        Utilse.xsCustno = "";

        ((Button) findViewById(R.id.home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertT(1);

            }
        });

                ((ImageView) findViewById(R.id.refrsh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intX = new Intent(LaunchActivity.this, MainActivity.class);
                intX.putExtra("checkfrom","1");
                startActivity(intX);

            }
        });
        setupSearchView();
        _salesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chngSalesOrder();


            }
        });

        ((Button) findViewById(R.id.amnd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // alertT(2);
                try {
                    xtems = Utilse.loadJSONArray(LaunchActivity.this, "listitems", "1");
                    jsdxx = Utilse.loadJSONArray(LaunchActivity.this, "MainJsonArrya", "js");


                } catch (Exception e) {

                }
                Log.d("gethsssss", jsdxx + "");

//
//                        xcRt={"01","","","","201403","-","","","","","SGD","1.00","0","7","0","100","o",""};
                tsIntr = new ArrayList<>();
                int timesssr = 1500;
                ;

                Log.i("final count ", "" + finlCunt);

                for (int i = 0; i < jsdxx.length(); i++) {
                    poTTs = i;
                    if (i == (jsdxx.length() - 1))
                        finlCunt = jsdxx.length();


                    try {
                        xcRt[1] = jsdxx.getJSONObject(poTTs).getString("sono");
                        xcRt[2] = jsdxx.getJSONObject(poTTs).getString("docdate");
                        xcRt[3] = jsdxx.getJSONObject(poTTs).getString("custno");
                        xcRt[6] = jsdxx.getJSONObject(poTTs).getString("docdate");
                        xcRt[7] = "";
                        xcRt[5] = jsdxx.getJSONObject(poTTs).getString("custpo");
                        xcRt[xcRt.length-1] = jsdxx.getJSONObject(poTTs).getString("remarks");

                        JSONObject _masterObj = new JSONObject();
                        String mstrName = "";
                        for (int l = 0; l < xcRt.length; l++) {
                            _masterObj.put(Utilse.mstrArray[l], xcRt[l]);
                        }

                        Iterator<String> _iter = _masterObj.keys();
                        while (_iter.hasNext()) {
                            String key = _iter.next();
                            try {
                                Object value = _masterObj.get(key);
                                mstrName += "<" + key + ">" + value.toString() +
                                        "</" + key + ">";

                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        // Log.d("**vaeeee", "*************id******" + mstrName);

                        String xmtrMai = "<" + "master" + ">" + mstrName +
                                "</" + "master" + ">";

                        JSONArray jsDR = new JSONArray();
                        jsDR = jsdxx.getJSONObject(poTTs).getJSONArray("details");

                        Log.d("**vaeeeejsDR", xtems.length() + "*************id******" + jsDR.length());
                        String Details = "";
                        for (int k = 0; k < jsDR.length(); k++) {

                            for (int w = 0; w < xtems.length(); w++) {
                                if (jsDR.getJSONObject(k).getString("itemno").equalsIgnoreCase(xtems.getJSONObject(w).getString("item_no"))) {
                                    JSONObject xdc = xtems.getJSONObject(w);
                                    String dix=jsDR.getJSONObject(k).getString("precn");
                                    if(dix.matches(""))
                                        dix="0";


                                    String[] dtlsRt = {xdc.getString("comp_code"), jsdxx.getJSONObject(poTTs).getString("sono"), "" + w, xdc.getString("item_id"),
                                            jsDR.getJSONObject(k).getString("itemno"), xdc.getString("item_descr"), "-", xdc.getString("uom"), "1", jsDR.getJSONObject(k).getString("qty"), xdc.getString("price"), "" + (Double.parseDouble(jsDR.getJSONObject(k).getString("qty")) * Double.parseDouble(xdc.getString("price"))), "%",dix, "0", "0", "GST7", "7", "7", jsdxx.getJSONObject(poTTs).getString("docdate"), jsdxx.getJSONObject(poTTs).getString("docdate"),jsDR.getJSONObject(k).getString("remark")};


                                    JSONObject _dtllistObj = new JSONObject();
                                    String dtllist = "";
                                    for (int l = 0; l < Utilse.dtlsArray.length; l++) {
                                        _dtllistObj.put(Utilse.dtlsArray[l], dtlsRt[l]);
                                    }
                                    Log.d("dtlsRt", "" + _dtllistObj);
                                    Iterator<String> _iterDtr = _dtllistObj.keys();
                                    while (_iterDtr.hasNext()) {
                                        String key = _iterDtr.next();
                                        try {
                                            Object value = _dtllistObj.get(key);
                                            dtllist += "<" + key + ">" + value.toString() +
                                                    "</" + key + ">";
                                            // Log.d("**vaeeee", "*************id******" + dtllist);
                                        } catch (JSONException e) {
                                            // Something went wrong!
                                        }
                                    }

                                    Details += "<" + "details" + ">" + dtllist +
                                            "</" + "details" + ">";

                                    break;
                                }

                            }


                        }
                        String xcl = "";
                        xcl = xmtrMai += Details;
                        Log.d("logger", "" + poTTs % 2);
                        if (poTTs % 2 == 0) {
                            httpCall(xcl, poTTs, 1);

                        } else
                            httpOddCall(xcl, poTTs, 1);


                    } catch (Exception e) {

                    }


                    Log.d("make impr", "" + i + "total length" + jsdxx.length());


                }


            }
        });


        ((Button) findViewById(R.id._newitm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent xyz = new Intent(LaunchActivity.this, EditActivity.class);
                xyz.putExtra("intvalue", "" + -2);
                xyz.putExtra("chr", "new");
                startActivity(xyz);


            }
        });


        _mMasterRecy.setItemAnimator(new DefaultItemAnimator());
        _mMasterRecy.addOnItemTouchListener(
                new RecyclerItemClickListener(LaunchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        ((ImageView) view.findViewById(R.id.delItm)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                AlertDialog.Builder alertDel = new AlertDialog.Builder(LaunchActivity.this);
                                alertDel.setMessage("Are you sure you want to delete?");
                                alertDel.setTitle("Delete ");
                                alertDel.setPositiveButton("         Yes              ", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        jsdxx.remove(position);
                                        Utilse.saveJSONArray(LaunchActivity.this, "MainJsonArrya", "js", jsdxx);
                                        tsArray.remove(position);
                                        _mOrdAdapter = new ItemviewAdapter(LaunchActivity.this, tsArray);
                                        _mMasterRecy.setAdapter(_mOrdAdapter);

                                    }
                                });

                                alertDel.setNegativeButton("NO                          ", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // what ever you want to do with No option.
                                    }
                                });

                                alertDel.show();


                            }
                        });

                    }
                }
                )
        );


    }

    private void setupSearchView() {
        srView.setIconifiedByDefault(false);
        srView.setOnQueryTextListener(this);
        srView.setSubmitButtonEnabled(true);
        srView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {

        if(_mSalesOrder.getVisibility()!=View.VISIBLE)
        _mOrdAdapter.getFilter().filter(newText);
        else
            _slesOrder.getFilter().filter(newText);

        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void mth(int xyzInt) {

        try {
            String json = "";
            try {
                Log.e("myAppssssssxyzInt", xyzInt + "cccccccccccc");

                JSONArray jsonDAr = new JSONArray(json);
                NewOrderActivity.jdsMainarray = new JSONArray(json);
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
                    vIS.setprice("" + (10 + x));
                    vIS.sets_no("" + (x + 1));
                    vIS.setcustNo(eO.getString("cust_no"));
                    vIS.settxtis(ntOrderList(x));
                    tsArray.add(vIS);
                }

                _mOrdAdapter = new ItemviewAdapter(LaunchActivity.this, tsArray);
                _mMasterRecy.setAdapter(_mOrdAdapter);


            }
        } catch (Exception e) {

        }
    }


    private ArrayList<Orderin> ntOrderList(int xp) {
        try {

            JSONArray jds = new JSONArray();
            jds = NewOrderActivity.jdsMainarray.getJSONObject(xp).getJSONArray("Details");
            Log.d("test", "" + jds.length());
//        mrt(jsonDAr, xyzInt);

            ordrArray = new ArrayList<Orderin>();
            Orderin vS;
            for (int x = 0; x < jds.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = jds.getJSONObject(x);
                vS = new Orderin();
                vS.sets_no(eO.getString("s_no"));
                vS.setqty_in("1.00");
                vS.setprice(eO.getString("prod_date"));
                vS.setitem_no(eO.getString("item_no"));
                vS.setitem_desc(eO.getString("item_desc"));
                vS.setuom("%" + (2 + x));
                ordrArray.add(vS);
            }
        } catch (Exception e) {

        }

        return ordrArray;
    }


    private void httpCall(String xuyz, final int POS, final int finalt) {
        try {
            String urlT = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + xuyz + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
            hrdUrl = Utilse.MAINTAG + Utilse.PUTCLIENT + urlT + "&userid=" + "DBA";

            Log.d("loggehrdUrlr", hrdUrl + "");

        } catch (Exception e) {

        }


        Utilse.get(hrdUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                pdDilog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                try {
                    JSONArray jsdss = new JSONArray();
                    jsdss = timeline;
                    if (jsdss.getJSONObject(0).getString("ReturnValue").equalsIgnoreCase("0")) {

                        Log.d("came here ", "" + POS + "__ array" + tsIntr.size());
                        tsIntr.add(POS);
                        Log.d("came here ", "" + POS + "__ array" + tsIntr.size());

                    }

                } catch (Exception e) {

                }
                Log.d("posttt", "" + (jsdxx.length() - 1) + "postion " + POS);

                Log.d("postioJSONn", "" + timeline);


                if (jsdxx.length() == finlCunt) {
                    try {
                        for (int h = 0; h < tsIntr.size(); h++) {
                            JSONObject js = new JSONObject();
                            jsdxx.put(tsIntr.get(h), js);
                        }
                        JSONArray jdx = new JSONArray();
                        for (int k = 0; k < jsdxx.length(); k++) {
                            if (jsdxx.getJSONObject(k).length() != 0) {
                                jdx.put(jsdxx.getJSONObject(k));
                            }
                        }


                        Utilse.saveJSONArray(LaunchActivity.this, "MainJsonArrya", "js", jdx);
                        mthd(jdx);


                    } catch (Exception e) {

                    }
                    Toast.makeText(LaunchActivity.this, "successfully Uploaded", Toast.LENGTH_SHORT).show();
                }


                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("errror", "" + errorResponse);
//                Utils.alertDialogShow(PaymentActivity.this, getString(R.string.failOrder)+"1", " Fail Alert", 1);
            }

            @Override
            public void onFinish() {
                Log.d("postion", "" + POS);
                pdDilog.dismiss();

            }
        });

    }


    private void httpOddCall(String xuyz, final int POS, final int finalt) {
        try {
            String urlT = URLEncoder.encode((getResources().getString(R.string.encdq)).toString() + xuyz + (getResources().getString(R.string.encdtaiq)).toString(), "UTF-8");
            hrdUrl = Utilse.MAINTAG + Utilse.PUTCLIENTSO2 + urlT + "&userid=" + "DBA";
            Log.d("loggehrdUrlrssssss", hrdUrl + "");

        } catch (Exception e) {

        }


        Utilse.gettxt(hrdUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                pdDilog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                try {
                    JSONArray jsdss = new JSONArray();
                    jsdss = timeline;
                    if (jsdss.getJSONObject(0).getString("ReturnValue").equalsIgnoreCase("0")) {

                        Log.d("came here ", "" + POS + "__ array" + tsIntr.size());
                        tsIntr.add(POS);
                        Log.d("came here ", "" + POS + "__ array" + tsIntr.size());

                    }

                } catch (Exception e) {

                }
                Log.d("posttt", "" + (jsdxx.length() - 1) + "postion " + POS);

                Log.d("postioJSONn", "" + timeline);
                if (jsdxx.length() == finlCunt) {
                    try {
                        for (int h = 0; h < tsIntr.size(); h++) {
                            JSONObject js = new JSONObject();
                            jsdxx.put(tsIntr.get(h), js);
                        }
                        JSONArray jdx = new JSONArray();
                        for (int k = 0; k < jsdxx.length(); k++) {
                            if (jsdxx.getJSONObject(k).length() != 0) {
                                jdx.put(jsdxx.getJSONObject(k));
                            }
                        }


                        Utilse.saveJSONArray(LaunchActivity.this, "MainJsonArrya", "js", jdx);
                        mthd(jdx);

                    } catch (Exception e) {

                    }
                    Toast.makeText(LaunchActivity.this, "successfully Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        for (int h = 0; h < tsIntr.size(); h++) {
                            jsdxx.remove(tsIntr.get(h));
                        }
                        Utilse.saveJSONArray(LaunchActivity.this, "MainJsonArrya", "js", jsdxx);
                        mthd(jsdxx);

                    } catch (Exception e) {

                    }
                    Toast.makeText(LaunchActivity.this, "successfully Uploaded", Toast.LENGTH_SHORT).show();
                }


                System.out.println(timeline);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("errror", "" + errorResponse);
//                Utils.alertDialogShow(PaymentActivity.this, getString(R.string.failOrder)+"1", " Fail Alert", 1);
            }

            @Override
            public void onFinish() {
                Log.d("postion", "" + POS);

                pdDilog.dismiss();

            }
        });

    }


    private void mthd(JSONArray timeline) {
        try {
            JSONArray jsAr = timeline;
            Log.d("**@jsArryfor", "" + jsAr);

            tsArray = new ArrayList<Stockin>();
            Stockin vIS;


            for (int x = 0; x < timeline.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = timeline.getJSONObject(x);
                if(eO.length()!=0){
                    vIS = new Stockin();
                    vIS.setitem_desc(eO.getString("custName"));
                    vIS.setitem_no(eO.getString("sono"));
                    vIS.setuom(eO.getString("docdate"));
                    vIS.setprod_date("");
                    vIS.setprice("" + (10 + x));
                    vIS.setcustNo(eO.getString("custno"));
                    vIS.sets_no("" + (x + 1));
                    vIS.settxtis(ntOrderList(x));
                    tsArray.add(vIS);
                }



            }
            _mOrdAdapter = new ItemviewAdapter(LaunchActivity.this, tsArray);
            _mMasterRecy.setAdapter(_mOrdAdapter);

//

        } catch (Exception exception) {
            Log.e("myappname", "exception", exception);
            // handleError(exception);
        }

    }

    private void alertT(int enbl) {
        String xyEnab, log;

        xyEnab = "                    Are you sure you want to Logout?                                               ";
        log = "LOGOUT ";


        AlertDialog.Builder alertDel = new AlertDialog.Builder(LaunchActivity.this);
        alertDel.setMessage(xyEnab);
        alertDel.setTitle(log);
        alertDel.setPositiveButton("         Yes              ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent xyz = new Intent(LaunchActivity.this, WelcomeActivity.class);
                startActivity(xyz);
            }
        });

        alertDel.setNegativeButton("NO                          ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }

    @Override
    public void onBackPressed() {

    }

    private void adminLogin() {
        ItemviewActivity.frstTIm = 2;
        final android.app.Dialog alertDialog = new android.app.Dialog(LaunchActivity.this);
        alertDialog.setContentView(R.layout.admin);
        alertDialog.setTitle("");
        alertDialog.setCanceledOnTouchOutside(false);

        final EditText user = (EditText) alertDialog.findViewById(R.id.us1);
        final EditText pass = (EditText) alertDialog.findViewById(R.id.pass2);
        mth(1);


        Button dialogButton1 = (Button) alertDialog.findViewById(R.id.sdilog);
        // if button is clicked, close the custom dialog
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LaunchActivity.this, user.getText() + "has logged in.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void chngSalesOrder() {

        if (_salesOrder.getText().toString().equalsIgnoreCase("SALES ORDERS")) {




            srView.setVisibility(View.VISIBLE);



            _salesOrder.setText("BACK");
            _mSalesOrder.setVisibility(View.VISIBLE);
            _mMasterRecy.setVisibility(View.GONE);
            utilsMthd();
//            _slesOrder = new SalesviewAdapter(LaunchActivity.this, tsArray);
//            _mSalesOrder.setAdapter(_slesOrder);
            ((Button) findViewById(R.id.amnd)).setVisibility(View.GONE);
            _salesOrder.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            _salesOrder.setTextColor(getResources().getColor(android.R.color.white));


        } else {

                try {
                    jsdxx = new JSONArray();
                    jsdxx = Utilse.loadJSONArray(LaunchActivity.this, "MainJsonArrya", "js");
                    mthd(jsdxx);
                } catch (Exception e) {

                }

            srView.clearFocus();
            srView.setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.amnd)).setVisibility(View.VISIBLE);
            _mSalesOrder.setVisibility(View.GONE);
            _mMasterRecy.setVisibility(View.VISIBLE);
            _salesOrder.setText("SALES ORDERS");
            _salesOrder.setBackgroundColor(getResources().getColor(android.R.color.white));
            _salesOrder.setTextColor(getResources().getColor(android.R.color.black));
        }

    }

    private void utilsMthd() {


        String posUrl = "";


        Utilse.get(Utilse.MAINTAG + Utilse.SALESORDER+"&loginuserid="+Utilse.SalesMan+"&custno=%25", null, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                pdDilog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonFromNet) {
                // Pull out the first event on the public timeline
                try {

                  stcOutArr = new HashMap<String,StcOutModel>();;
                    List<String> stringList = new ArrayList<>();
                    StcOutModel vIS;
                    for (int x = 0; x < jsonFromNet.length(); x++) {

                        JSONObject eO = new JSONObject();
                        eO = jsonFromNet.getJSONObject(x);
                        vIS = new StcOutModel();

                        String bse4="";
                        try{
                            String ps2 = eO.getString("cust_name");
                            byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                            bse4 = new String(tmp2, "UTF-8");
                        }catch (Exception e){}

                        String [] txsd={eO.getString("cust_po_no"),eO.getString("remarks")};
                        vIS.settxar(txsd);
                        vIS.setitem_desc(bse4);
                        stringList.add(eO.getString("so_no"));
                        vIS.setitem_no(eO.getString("so_no"));
                        vIS.setuom(eO.getString("doc_date"));
                        vIS.setprod_date("OutStanding");
                        vIS.setprice("" + eO.getString("total"));
                        vIS.sets_no("" + (x + 1));
                        vIS.setcustNo(eO.getString("cust_no"));

                        stcOutArr.put(eO.getString("so_no"),vIS);
                    }

                    MyComparator myComparator = new MyComparator();
                    Collections.sort(stringList, myComparator);
                    //Collections.sort(stringList);
                    ascenDOutArr=new ArrayList<StcOutModel>();
                    for (int k = 0; k <stringList.size() ; k++) {
                        ascenDOutArr.add(stcOutArr.get(stringList.get(k)));
                    }
                    _slesOrder = new SalesviewAdapter(LaunchActivity.this, ascenDOutArr);
                    _mSalesOrder.setAdapter(_slesOrder);



                } catch (Exception e) {


                    return;
                }


                // Do something with the response
                System.out.println("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // xCheck = 1;
                throwable.printStackTrace(System.out);
                Utilse.alertDialogShow(LaunchActivity.this, "Eroor", " Connection  refused", 3);
            }


            @Override
            public void onFinish() {
                pdDilog.dismiss();
                srView.clearFocus();


            }


        });


    }

    private void mtr(int xpr) {


    }


}