package demodata.sd.com.sddemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Adapter.listAllAdapter;

/**
 * Created by jabbir on 31/12/15.
 */
public class ListOfItemsActivity extends Activity implements SearchView.OnQueryTextListener  {
    public RecyclerView ntOrder;
    public RecyclerView.LayoutManager ntOrderLayoutManager;
    public listAllAdapter ntOrderOrdAdapter;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_itemlist);
        //getWindow().setLayout(2000, 1600);
        ntOrder = (RecyclerView) findViewById(R.id.netdt);
        ntOrderLayoutManager = new GridLayoutManager(this, 2);
        ntOrder.setLayoutManager(ntOrderLayoutManager);
        ntOrder.setHasFixedSize(true);
        mSearchView = (SearchView) findViewById(R.id.nesrc);

        ntOrderOrdAdapter = new listAllAdapter(ListOfItemsActivity.this, ItemviewActivity.tstOrder,0);
        ntOrder.setAdapter(ntOrderOrdAdapter);
        ((Button)findViewById(R.id.nent_amnd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent xyz = new Intent(ListOfItemsActivity.this, NewOrderActivity.class);
                xyz.putExtra("inxr", "bck");
                startActivity(xyz);
                finish();

            }
        });

        setupSearchView();

}
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        ntOrderOrdAdapter.getFilter().filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
