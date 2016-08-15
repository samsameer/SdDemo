package demodata.sd.com.sddemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import demodata.sd.com.sddemo.Adapter.OrderviewAdapter;
import demodata.sd.com.sddemo.Adapter.listAllAdapter;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.Model.StcOutModel;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

/**
 * Created by jabbir on 23/3/16.
 */
public class EditActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView _mMasterRecy, searcgView;
    private OrderviewAdapter detaiAdapter;
    private listAllAdapter newOdapter;
    private RecyclerView.LayoutManager _dtrel, netManager;
    int pos = 0;
    private int ttlqtyion = 0;
    private ArrayList<Orderin> ordrArray = new ArrayList<Orderin>();
    //private LinearLayout //visp;
    SearchView eText2, __dtlSearchView;
    private int xdInt = 0;
    private String[] txMain = {};
    private ArrayList<StcOutModel> stcOutArr = new ArrayList<StcOutModel>();
    private ArrayList<HashMap<String, Integer[]>> tsAddrt = new ArrayList<>();
    private LinearLayout btmBar;
    private EditText eText1, eText3;
    private ArrayList<String> custmDetai = new ArrayList<String>();
    private ArrayList<String> xArrylis = new ArrayList<String>();
    private JSONArray xCustList, xListItems;
    private int xCounter = 0;
    public ArrayList<Orderin> tArray = new ArrayList<Orderin>();
    private String xcet;
    private int[] xczEdit = {R.id.ext1, R.id.ext3, R.id.ext4, R.id.ext5, R.id.ext6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsview);
        searcgView = (RecyclerView) findViewById(R.id.ntt);
        netManager = new GridLayoutManager(this, 2);
        searcgView.setLayoutManager(netManager);
        searcgView.setHasFixedSize(true);
        pos = Integer.parseInt(getIntent().getStringExtra("intvalue"));
        __dtlSearchView = (SearchView) findViewById(R.id._itsearch);
        _mMasterRecy = ((RecyclerView) findViewById(R.id.tdtddddd));
        btmBar = ((LinearLayout) findViewById(R.id.btmBar));
        _mMasterRecy.setHasFixedSize(true);

        _dtrel = new GridLayoutManager(EditActivity.this, 1);
        LinearLayoutManager layManager = new LinearLayoutManager(EditActivity.this);
        layManager.setOrientation(LinearLayoutManager.VERTICAL);
        _mMasterRecy.setLayoutManager(_dtrel);

        Bundle b = this.getIntent().getExtras();


        if (b != null) {
            if (b.containsKey("keys")) {

                txMain = b.getStringArray("keys");
                xCounter = 1;
                ((EditText) findViewById(R.id.rmtk)).setText(txMain[3]);
                Log.d("String dddddd", txMain[2]);

                // boolean isNew = b.getBoolean("isNewItem", false);

                // TODO: Do something with the value of isNew.
            }
        }

//


        xcet = getIntent().getStringExtra("chr");
        // eText1 = (EditText) findViewById(R.id._qtr);
        eText2 = (SearchView) findViewById(R.id._qtr1);
        xdInt = 0;
//        eText3 = (EditText) findViewById(R.id._qtr2);
//        //visp = (LinearLayout) findViewById(R.id.//visp);
//        //visp.setVisibility(View.GONE);


        try {
            xCustList = Utilse.loadJSONArray(EditActivity.this, "customerlist", "key");
            xListItems = Utilse.loadJSONArray(EditActivity.this, "listitems", "1");
            ntOrderList(pos);
            // mtJsarry(jsd);

        } catch (Exception exception) {
            Log.e("myappname", "exception", exception);
            // handleError(exception);
        }
     methd();
        ((Button) findViewById(R.id._newsd)).setVisibility(View.VISIBLE);
        if (xcet.equalsIgnoreCase("new") && pos == -2) {

            ((Button) findViewById(R.id._newsd)).setVisibility(View.GONE);

            Intent xyz = new Intent(EditActivity.this, CustomerList.class);
            xyz.putExtra("ixr", "" + pos);
            startActivity(xyz);
        } else
            mthdCustmr();

        spSearchView();

        ((Button) findViewById(R.id._dtlcnl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xyzzz = "";
                if (((Button) findViewById(R.id._dtlcsave)).getVisibility() == View.GONE)
                    xyzzz = "" + 2;
                else
                    xyzzz = "" + 1;

                Intent xyz = new Intent(EditActivity.this, LaunchActivity.class);
                xyz.putExtra("thisMan", xyzzz);
                startActivity(xyz);
            }
        });

        ((Button) findViewById(R.id._dtladd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methd();
            }
        });
        ((Button) findViewById(R.id._newsd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray dmainJls = new JSONArray();
                    dmainJls = Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js");
                    saveMf(dmainJls, "00010" + dmainJls.length(), -1);

                } catch (Exception e) {

                }

            }
        });


        newOdapter = new listAllAdapter(EditActivity.this, ntOrdt(), 1);
        searcgView.setAdapter(newOdapter);

        ((Button) findViewById(R.id._dtlcsave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONArray dmainJls = new JSONArray();

                    Log.d("this ms", "" + (Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js")));

                    if (Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js").length() <= 0) {
                        dmainJls = new JSONArray();
                        saveMf(dmainJls, "00010" + 0, -1);
                    } else {
                        dmainJls = Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js");
                        if (userexists(dmainJls, xcet)) {
                            for (int i = 0; i < dmainJls.length(); i++) {
                                {
                                    if (dmainJls.getJSONObject(i).getString("sono").equalsIgnoreCase(xcet)) {
                                        saveMf(dmainJls, xcet, i);
                                        break;
                                    }
                                }

                            }
                        } else {
                            saveMf(dmainJls, "00010" + dmainJls.length(), -1);
                        }


                    }


                } catch (Exception e) {
                    Intent xyz = new Intent(EditActivity.this, LaunchActivity.class);
                    xyz.putExtra("thisMan", "1");
                    startActivity(xyz);
                }

            }
        });


        setupSearchView();

    }

    private void setupSearchView() {
        __dtlSearchView.setIconifiedByDefault(false);
        __dtlSearchView.setOnQueryTextListener(this);
        __dtlSearchView.setSubmitButtonEnabled(true);
        __dtlSearchView.setQueryHint("Search Here");

    }


    private void spSearchView() {

        eText2.setIconifiedByDefault(false);
        eText2.setOnQueryTextListener(this);
        eText2.setSubmitButtonEnabled(true);
        eText2.setQueryHint("Search Here");
    }


    public boolean onQueryTextChange(String newText) {

        if (searcgView.getVisibility() == View.VISIBLE)
            newOdapter.getFilter().filter(newText);
        else {
            if (detaiAdapter != null)
                detaiAdapter.getFilter().filter(newText);
        }

        return true;
    }

    @Override
    public void onBackPressed() {

    }

    private void saveMf(JSONArray dmainJls, String xcet, int pos) {
        try {
            Log.d("came insidthis ms", "" + (Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js")));
            JSONObject jsd = new JSONObject();
            jsd.put("sono", xcet);
            jsd.put("custno", ((EditText) findViewById(xczEdit[0])).getText());
            jsd.put("custName", ((EditText) findViewById(xczEdit[2])).getText());
            jsd.put("custpo", ((EditText) findViewById(xczEdit[4])).getText());
            jsd.put("docdate", Utilse.currenDate());
            jsd.put("remarks", ((EditText) findViewById(R.id.rmtk)).getText().toString());
            JSONArray dtails = new JSONArray();
            for (int j = 0; j < tArray.size(); j++) {
                JSONObject details = new JSONObject();
                details.put("itemno", tArray.get(j).getitem_no());
                details.put("qty", tArray.get(j).getqty_in());
                details.put("precn", tArray.get(j).getdisCount());
                details.put("remark", tArray.get(j).getreMarkso());
                dtails.put(details);
            }


            jsd.put("details", dtails);
            Log.d("thisssss", "" + jsd);
            if (pos == -1)
                dmainJls.put(jsd);
            else
                dmainJls.put(pos, jsd);
            Utilse.saveJSONArray(EditActivity.this, "MainJsonArrya", "js", dmainJls);
            Log.d("fullde", "" + Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js"));
            Intent xyz = new Intent(EditActivity.this, LaunchActivity.class);
            xyz.putExtra("thisMan", "1");
            startActivity(xyz);
        } catch (Exception e) {

        }

    }

    public ArrayList<Orderin> ntOrderList(int xp) {
        try {


            //xListItems
            tArray = new ArrayList<Orderin>();
            Orderin vS;
            JSONArray dmainJls = new JSONArray();
            dmainJls = (Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js"));
            JSONArray jdfDetails = dmainJls.getJSONObject(xp).getJSONArray("details");
            ((EditText) findViewById(R.id.rmtk)).setText(dmainJls.getJSONObject(xp).getString("remarks"));
            Utilse.newItemOrder = new LinkedHashMap<String, String[]>();
            for (int i = 0; i < jdfDetails.length(); i++) {
                for (int j = 0; j < xListItems.length(); j++) {
                    if (jdfDetails.getJSONObject(i).getString("itemno").equalsIgnoreCase((xListItems.getJSONObject(j).getString("item_no")))) {
                        vS = new Orderin();
                        vS.sets_no("");
                        vS.setqty_in("" + jdfDetails.getJSONObject(i).getString("qty"));
                        ttlqtyion += Integer.parseInt(jdfDetails.getJSONObject(i).getString("qty"));
                        double xctF = Double.parseDouble(xListItems.getJSONObject(j).getString("price"));
                        vS.setprice("$ " + String.format("%.2f", xctF));
                        vS.setitem_no(xListItems.getJSONObject(j).getString("item_no"));
                        vS.setitem_desc(xListItems.getJSONObject(j).getString("item_descr"));

                        int prcn = 0;
                        if (jdfDetails.getJSONObject(i).getString("precn").matches("")) {
                            prcn = 0;
                        } else {
                            prcn = Integer.parseInt(jdfDetails.getJSONObject(i).getString("precn"));
                        }


                        String bse4 = "";
                        try {
                            String ps2 = xListItems.getJSONObject(j).getString("flang_descr");
                            byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                            bse4 = new String(tmp2, "UTF-8");
                        } catch (Exception e) {
                        }


                        vS.setprod_date(bse4);
                        vS.setreMarks(jdfDetails.getJSONObject(i).getString("remark"));
                        String[] tsd = {(jdfDetails.getJSONObject(i).getString("qty")), "" + prcn, jdfDetails.getJSONObject(i).getString("remark")};

                        Log.d("came RRRRRRhere", "" + tsd[1]);

                        if (tsd[1].matches(""))
                            vS.setdisCount("0");
                        else
                            vS.setdisCount("" + tsd[1]);

                        Utilse.newItemOrder.put(xListItems.getJSONObject(j).getString("item_no"), tsd);
                        tArray.add(vS);

                        break;
                    }
                }
            }


            // }
            Log.d("testtArdddddddray", "" + tArray);

            detaiAdapter = new OrderviewAdapter(EditActivity.this, tArray, xCounter);
            _mMasterRecy.setAdapter(detaiAdapter);
            ((TextView) findViewById(R.id._ttl)).setText("   Total Qty:       " + ttlqtyion);

        } catch (Exception e) {
            Log.d("testtArdddddddray", "" + e.getMessage());
        }

        return tArray;
    }

    private void mthdCustmr() {
        try {
            String xCustno = "";
            String xCustpo = "";
            if (pos != -2) {
                if (txMain.length != 0) {
                    xCounter = 1;
                    xCustno = txMain[1];
                    dtlsDetails();
                    mkeHides();
                } else {
                    xCounter = 0;
                    JSONArray jsi = new JSONArray();
                    jsi = (Utilse.loadJSONArray(EditActivity.this, "MainJsonArrya", "js"));
                    xCustno = jsi.getJSONObject(pos).getString("custno");
                    xCustpo = jsi.getJSONObject(pos).getString("custpo");
                }

            } else {
                xCustno = Utilse.xsCustno;
                xCustpo = "";
            }

            Log.d("custmo", xCustno);


            for (int i = 0; i < xCustList.length(); i++) {
                if (xCustno.equalsIgnoreCase(xCustList.getJSONObject(i).getString("cust_no"))) {
                    //  salesman_name,addr,cust_name,cust_no,cont_off_tel
                    custmDetai = new ArrayList<>();
                    custmDetai.add(xCustno);
                    custmDetai.add(xCustList.getJSONObject(i).getString("salesman_name"));

                    String bse4 = "";
                    try {
                        String ps2 = xCustList.getJSONObject(i).getString("cust_name");
                        byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                        bse4 = new String(tmp2, "UTF-8");
                    } catch (Exception e) {
                    }


                    custmDetai.add(bse4);
                    custmDetai.add(xCustList.getJSONObject(i).getString("addr"));
                    for (int it = 0; it < xczEdit.length; it++) {


                        if (it == xczEdit.length - 1)
                            if (txMain.length != 0)
                                ((EditText) findViewById(xczEdit[it])).setText(txMain[2]);
                            else
                                ((EditText) findViewById(xczEdit[it])).setText(xCustpo);
                        else
                            ((EditText) findViewById(xczEdit[it])).setText(custmDetai.get(it));
                    }
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (Utilse.xsCustno.matches("")) {

            } else {
                for (int i = 0; i < xCustList.length(); i++) {
                    if (Utilse.xsCustno.equalsIgnoreCase(xCustList.getJSONObject(i).getString("cust_no"))) {
                        //  salesman_name,addr,cust_name,cust_no,cont_off_tel
                        custmDetai = new ArrayList<>();
                        custmDetai.add(Utilse.xsCustno);
                        custmDetai.add(xCustList.getJSONObject(i).getString("salesman_name"));
                        String bse4 = "";
                        try {
                            String ps2 = xCustList.getJSONObject(i).getString("cust_name");
                            byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                            bse4 = new String(tmp2, "UTF-8");
                        } catch (Exception e) {
                        }
                        custmDetai.add(bse4);
                        custmDetai.add(xCustList.getJSONObject(i).getString("addr"));
                        for (int it = 0; it < xczEdit.length; it++) {
                            ((EditText) findViewById(xczEdit[it])).setText(custmDetai.get(it));
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {

        }


    }

    private ArrayList<Orderin> ntOrdt() {
        try {
            ordrArray = new ArrayList<Orderin>();

            Orderin vS;
            for (int x = 0; x < xListItems.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = xListItems.getJSONObject(x);
                vS = new Orderin();
                vS.setqty_in("1.00");

                double dt = Double.parseDouble(eO.getString("price"));
                vS.setprice("$ " + String.format("%.2f", dt));
                vS.setitem_no(eO.getString("item_no"));
                vS.setitem_desc(eO.getString("item_descr"));


                String bse4 = "";
                try {
                    String ps2 = eO.getString("flang_descr");
                    byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                    bse4 = new String(tmp2, "UTF-8");
                } catch (Exception e) {
                }

                vS.setprod_date(bse4);
                vS.setdisCount("0");

                ordrArray.add(vS);
            }
        } catch (Exception e) {

        }

        return ordrArray;
    }

    private void methd() {
        if (searcgView.getVisibility() == View.VISIBLE) {
            searcgView.setVisibility(View.GONE);
            eText2.setVisibility(View.GONE);
            __dtlSearchView.setVisibility(View.VISIBLE);
            xdInt = 0;

        } else {

            __dtlSearchView.setVisibility(View.GONE);
            searcgView.setVisibility(View.VISIBLE);
            eText2.setVisibility(View.VISIBLE);
            //eText2.onActionViewExpanded();
            eText2.requestFocus();
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    toggleSoftInput(InputMethodManager.SHOW_FORCED,
                            InputMethodManager.HIDE_IMPLICIT_ONLY);
            xdInt = 1;

        }
    }


    private boolean userexists(JSONArray jsonArray, String usernameToFind) {
        return jsonArray.toString().contains("\"sono\":\"" + usernameToFind + "\"");
    }

    public void txsM() {
        eText2.setQuery("", false);
        tArray = new ArrayList<Orderin>();
        try {
//            if (getCurrentFocus() != null) {
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//            }
            ttlqtyion = 0;

            for (Map.Entry<String, String[]> entry : Utilse.newItemOrder.entrySet()) {
                String key = entry.getKey();
                Orderin vS;
                for (int j = 0; j < xListItems.length(); j++) {

                    if ((xListItems.getJSONObject(j).getString("item_no")).equalsIgnoreCase(key)) {
                        vS = new Orderin();
                        vS.sets_no("");

                        String[] xcd = Utilse.newItemOrder.get(xListItems.getJSONObject(j).getString("item_no"));

                        vS.setqty_in("" + xcd[0]);

                        Log.d("came,GFDFGre", "" + xcd[1]);
                        vS.setreMarks("" + xcd[2]);
                        ttlqtyion += Integer.parseInt(xcd[0]);
                        double xctF = Double.parseDouble(xListItems.getJSONObject(j).getString("price"));
                        vS.setprice("$ " + String.format("%.2f", xctF));
                        vS.setitem_no(xListItems.getJSONObject(j).getString("item_no"));
                        vS.setitem_desc(xListItems.getJSONObject(j).getString("item_descr"));
                        String bse4 = "";
                        try {
                            String ps2 = xListItems.getJSONObject(j).getString("flang_descr");
                            byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                            bse4 = new String(tmp2, "UTF-8");
                        } catch (Exception e) {
                        }


                        vS.setprod_date(bse4);
                        Log.d("came,GFDFGre", "" + xcd[1]);
//                        if (Integer.parseInt(xcd[1]) <= 0)
//                            vS.setdisCount("");
//                        else
                        vS.setdisCount("" + xcd[1]);

                        tArray.add(vS);

                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        detaiAdapter = new OrderviewAdapter(EditActivity.this, tArray, xCounter);
        _mMasterRecy.setAdapter(detaiAdapter);
        ((TextView) findViewById(R.id._ttl)).setText("   Total Qty:       " + ttlqtyion);
        //_dtrel.scrollToPosition(detaiAdapter.getItemCount());

    }

    private void mthd(String SonoTr) {
        String rrdl = Utilse.MAINTAG + Utilse.SONMAN + SonoTr;
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


                } catch (Exception exception) {
                    //progressDialog.dismiss();
                    return;

                    // handleError(exception);
                }                   // Do something with the response
                System.out.println(timeline);
            }

        });
    }

    private void mkeHides() {

        if (txMain.length == 0) {
            ((Button) findViewById(R.id._dtladd)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id._dtlcsave)).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id._dtladd)).setVisibility(View.GONE);
            ((Button) findViewById(R.id._dtlcsave)).setVisibility(View.GONE);
        }


    }


    private void dtlsDetails() {


        String posUrl = "";


        Utilse.get(Utilse.MAINTAG + Utilse.SONMAN + txMain[0], null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonFromNet) {
                // Pull out the first event on the public timeline
                try {
                    tsAddrt = new ArrayList<>();

                    Utilse.newItemOrder = new LinkedHashMap<String, String[]>();

                    for (int k = 0; k < jsonFromNet.length(); k++) {

                        String itmNo = jsonFromNet.getJSONObject(k).getString("item_no");
                        Log.d("Stringrem", "" + (jsonFromNet.getJSONObject(k).getString("qty_order")) + "" + (jsonFromNet.getJSONObject(k).getString("disc_value")) + "" + (jsonFromNet.getJSONObject(k).getString("remarks")));
                        String[] xySds = {Integer.toString((int) (Double.parseDouble(jsonFromNet.getJSONObject(k).getString("qty_order")))), Integer.toString((int) (Double.parseDouble(jsonFromNet.getJSONObject(k).getString("disc_value")))), (jsonFromNet.getJSONObject(k).getString("remarks"))};


                        Utilse.newItemOrder.put(itmNo, xySds);

                        txsM();
                    }

//                    _slesOrder = new SalesviewAdapter(LaunchActivity.this, stcOutArr);
//                    _mSalesOrder.setAdapter(_slesOrder);


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
                // Utilse.alertDialogShow(LaunchActivity.this, "Eroor", " Connection  refused", 3);
            }


            @Override
            public void onFinish() {

            }


        });


    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
