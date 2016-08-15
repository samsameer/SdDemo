package demodata.sd.com.sddemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import demodata.sd.com.sddemo.Adapter.listAllAdapter;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

/**
 * Created by jabbir on 31/12/15.
 */
public class NewOrderActivity extends Activity implements SearchView.OnQueryTextListener {
    public RecyclerView newOrder;
    private RecyclerView.LayoutManager newOrderLayoutManager;
    private listAllAdapter newOrderOrdAdapter;
    private String[] objNew = {"doc_no", "doc_date", "doc_time", "str_doc_no", "req_doc_no", "vend_name", "status", "Details"};
    private Button _newsub;
    private TextView _custInfo;
    private String xczInt = "abc";
    SearchView __mSearchView;
    public static JSONArray jdsMainarray;
    private JSONArray xCustList, xListItems;
    private ArrayList<Orderin> ordrArray = new ArrayList<Orderin>();
    private ArrayList<String> custmDetai = new ArrayList<String>();
    private int[] xczEdit = {R.id.td_txt1, R.id.td_txt3, R.id.td_txt4, R.id.td_txt5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        //getWindow().setLayout(2000, 1600);
        newOrder = (RecyclerView) findViewById(R.id.nt_tdt);
        newOrderLayoutManager = new GridLayoutManager(this, 2);
        newOrder.setLayoutManager(newOrderLayoutManager);
        newOrder.setHasFixedSize(true);
        _custInfo = (TextView) findViewById(R.id.td_txt1);
        __mSearchView = (SearchView) findViewById(R.id.newsearch);
        xczInt = getIntent().getStringExtra("inxr").toString();
        if (!xczInt.equalsIgnoreCase("abc")) {
            ((RelativeLayout) findViewById(R.id.nt_rc1)).setVisibility(View.GONE);
        } else {
            ((RelativeLayout) findViewById(R.id.nt_rc1)).setVisibility(View.VISIBLE);
        }


        if(Utilse.xsCustno.matches("")){
            Intent xyz = new Intent(NewOrderActivity.this, CustomerList.class);
            startActivity(xyz);

        }


        try {
            xCustList = Utilse.loadJSONArray(NewOrderActivity.this, "customerlist", "key");
            xListItems = Utilse.loadJSONArray(NewOrderActivity.this, "listitems", "1");
            // mtJsarry(jsd);

        } catch (Exception exception) {
            Log.e("myappname", "exception", exception);
            // handleError(exception);
        }


        _custInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xyz = new Intent(NewOrderActivity.this, CustomerList.class);
                startActivity(xyz);
            }
        });


        _newsub = (Button) findViewById(R.id._newsub);

        _newsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent xInt = new Intent(NewOrderActivity.this, EditActivity.class);
                xInt.putExtra("intvalue",""+ -2);
                startActivity(xInt);
            }        });

        newOrderOrdAdapter = new listAllAdapter(NewOrderActivity.this, ntOrderList(),0);
        newOrder.setAdapter(newOrderOrdAdapter);
        setupSearchView();


        ((Button) findViewById(R.id.cnle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                finish();


            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            if(Utilse.xsCustno.matches("")){

            }
            else{
                for (int i = 0; i < xCustList.length(); i++) {
                    if (Utilse.xsCustno.equalsIgnoreCase(xCustList.getJSONObject(i).getString("cust_no"))) {
                        //  salesman_name,addr,cust_name,cust_no,cont_off_tel
                        custmDetai = new ArrayList<>();
                        custmDetai.add(Utilse.xsCustno);
                        custmDetai.add(xCustList.getJSONObject(i).getString("salesman_name"));
                        custmDetai.add(xCustList.getJSONObject(i).getString("cust_name"));
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

    private void setupSearchView() {
        __mSearchView.setIconifiedByDefault(false);
        __mSearchView.setOnQueryTextListener(this);
        __mSearchView.setSubmitButtonEnabled(true);
        __mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        newOrderOrdAdapter.getFilter().filter(newText);

//
////        if (TextUtils.isEmpty(newText)) {
////           // mListView.clearTextFilter();
////        } else {
//            List<ItemreqModel> filteredModelList = filter(layoutResourceId, newText);
//                mAdapter.animateTo(filteredModelList);
//                mLitmtRecyclerView.scrollToPosition(0);

        // }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private ArrayList<Orderin> ntOrderList() {
        try {
            ordrArray = new ArrayList<Orderin>();

            Orderin vS;
            for (int x = 0; x < xListItems.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = xListItems.getJSONObject(x);
                vS = new Orderin();
                vS.setqty_in("1.00");

                double dt=Double.parseDouble(eO.getString("price"));
                vS.setprice("$ "+String.format("%.2f", dt));
                vS.setitem_no(eO.getString("item_no"));
                vS.setitem_desc(eO.getString("item_descr"));
                vS.setuom("% 0");

                ordrArray.add(vS);
            }
        } catch (Exception e) {

        }

        return ordrArray;
    }


    //ItemviewActivity.txArray.add(tst);

}
