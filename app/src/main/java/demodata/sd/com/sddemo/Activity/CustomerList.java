package demodata.sd.com.sddemo.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import demodata.sd.com.sddemo.Adapter.CustomerAdapter;
import demodata.sd.com.sddemo.Model.CustomerModel;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.RecyclerItemClickListener;
import demodata.sd.com.sddemo.Utils.Utilse;

/**
 * Created by jabbir on 19/5/16.
 */
public class CustomerList extends Activity implements SearchView.OnQueryTextListener {
    public RecyclerView ntOrder;
    public RecyclerView.LayoutManager ntOrderLayoutManager;
    public CustomerAdapter ntOrderOrdAdapter;
    private SearchView mSearchView;
    private ProgressDialog pdDilog;
    private Button cancel;
    private ArrayList<CustomerModel> tsArray = new ArrayList<CustomerModel>();
    //private String cUrl = "http://192.168.0.21:8084/kaiser/web_ar_customer?compcode=01&addrref=2";
    private int xIntt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_custmlist);
        getWindow().setLayout(1600, 2000);
        //getWindow().setLayout(2000, 1600);
        ntOrder = (RecyclerView) findViewById(R.id.cnetdt);
        ntOrderLayoutManager = new GridLayoutManager(this, 2);
        ntOrder.setLayoutManager(ntOrderLayoutManager);
        ntOrder.setHasFixedSize(true);
//        xIntt=  Integer.parseInt(getIntent().getStringExtra("ixr"));
        mSearchView = (SearchView) findViewById(R.id.cnesrc);
        cancel = (Button) findViewById(R.id.cnecls);
        pdDilog = new ProgressDialog(this);
        pdDilog.setMessage("Loding Data.....");
        pdDilog.setCancelable(false);
        pdDilog.setIndeterminate(true);
        try {
            JSONArray jsd = Utilse.loadJSONArray(CustomerList.this, "customerlist", "key");
            mtJsarry(jsd);

        } catch (Exception exception) {
            Log.e("myappname", "exception", exception);
            // handleError(exception);
        }


        setupSearchView();


        ntOrder.setItemAnimator(new DefaultItemAnimator());
        ntOrder.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            Utilse.xsCustno = ((TextView) view.findViewById(R.id.cust_no)).getText().toString();
                            finish();
//                            Intent ix = new Intent(getApplicationContext(), NewOrderActivity.class);
//                            ix.putExtra("intvalue", "" + xIntt);
//                            startActivity(ix);

                        } catch (Exception e) {

                        }

                    }
                }
                )
        );
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inti = new Intent(CustomerList.this, LaunchActivity.class);
                inti.putExtra("thisMan", "1");
                startActivity(inti);
            }
        });

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (ntOrderOrdAdapter.getFilter() != null)
            ntOrderOrdAdapter.getFilter().filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private void mtJsarry(JSONArray timeline) {

        try {
            tsArray = new ArrayList<CustomerModel>();
            CustomerModel vIS;
            for (int x = 0; x < timeline.length(); x++) {
                JSONObject eO = new JSONObject();
                eO = timeline.getJSONObject(x);
                vIS = new CustomerModel();
                vIS.setcust_no(eO.getString("cust_no"));
                String bse4="";
                try{
                    String ps2 = eO.getString("cust_name");
                    byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
                    bse4 = new String(tmp2, "UTF-8");
                }catch (Exception e){}


                vIS.setcust_name(bse4);
                tsArray.add(vIS);

            }
            ntOrderOrdAdapter = new CustomerAdapter(CustomerList.this, tsArray);
            ntOrder.setAdapter(ntOrderOrdAdapter);
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Runtime.getRuntime().gc();
    }
}
