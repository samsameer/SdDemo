package demodata.sd.com.sddemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demodata.sd.com.sddemo.Activity.EditActivity;
import demodata.sd.com.sddemo.Model.StcOutModel;
import demodata.sd.com.sddemo.R;

/**
 * Created by jabbir on 8/6/15.
 */
public class SalesviewAdapter extends RecyclerView.Adapter<SalesviewAdapter.ViewHolder> implements Filterable {
    Context context;
    private int xPostu;
    List<StcOutModel> mItems;
    ArrayList<Boolean> getStatus;
    private ArrayList<Integer> selectedItems;
    List<StcOutModel> mStringFilterList;
    ValueFilter valueFilter;
    private int day;
    private int month;
    private int year;

    public SalesviewAdapter(Context context, ArrayList<StcOutModel> layoutResourceId) {
        super();
        this.mItems = new ArrayList<StcOutModel>();
        getStatus = new ArrayList<Boolean>();
        this.context = context;
        this.xPostu = xPostu;
        selectedItems = new ArrayList<Integer>();
        mStringFilterList = layoutResourceId;
        mItems = layoutResourceId;
        for (int i = 0; i < mItems.size(); i++) {
            getStatus.add(false);
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sales_itm, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        StcOutModel nature = mItems.get(i);
        viewHolder.orderno.setSelected(getStatus.get(i));
        viewHolder.orderno.setChecked(getStatus.get(i));
        viewHolder.orderno.setText(nature.getitem_no());
        viewHolder.custnae.setText(nature.getitem_desc());
        viewHolder.dat.setText(nature.getuom());
        viewHolder._forLang.setText(nature.getcustNo());
        //viewHolder.statu.setText("NEW");


    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    protected boolean isSelected(int position) {
        return selectedItems.contains(Integer.valueOf(position));
    }

    protected void dataSetChanged() {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView.LayoutManager detailManager;
        private TextView custnae, dat, statu;
        private CheckedTextView orderno;
        private OrderviewAdapter detaiAdapter;
        private EditText sEdit;
        private LinearLayout lincl;
        private TextView _forLang;
        int[] txtR = {R.id._sotxt1, R.id._sotxt2, R.id._sotxt3, R.id._sotxt4};

        public ViewHolder(View itemView) {
            super(itemView);
            orderno = (CheckedTextView) itemView.findViewById(txtR[0]);
            custnae = (TextView) itemView.findViewById(txtR[1]);
            dat = (TextView) itemView.findViewById(txtR[2]);
            statu = (TextView) itemView.findViewById(txtR[3]);
            lincl = (LinearLayout) itemView.findViewById(R.id._solincl);
            _forLang = (TextView) itemView.findViewById(R.id._sonameR);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
// case R.id._sotxt2:
                        case R.id._solincl:
                        case R.id._sotxt2:

                            StcOutModel nature = mItems.get(getAdapterPosition());
                            String[] tsy=nature.gettxar();
                            Log.d("xcccccc",tsy[0]+""+tsy[1]);
                            Bundle b = new Bundle();
                            b.putStringArray("keys", new String[]{orderno.getText().toString(), _forLang.getText().toString(),tsy[0],tsy[1]});

                            Intent ixt = new Intent(context, EditActivity.class);
                            ixt.putExtras(b);
                            ixt.putExtra("intvalue", "" + getAdapterPosition());
                            ixt.putExtra("chr", orderno.getText());
                            ixt.putExtra("pos", 2);
                            context.startActivity(ixt);
                            break;

//                            Bundle eb=new Bundle();
//                            eb.putStringArray("keys", new String[]{orderno.getText().toString(), _forLang.getText().toString()});
//                            Intent ixte = new Intent(context, EditActivity.class);
//                            ixte.putExtras(eb);
//                            ixte.putExtra("intvalue", "" + getAdapterPosition());ixte.putExtra("chr", orderno.getText());
//                            ixte.putExtra("pos", 2);
//                            context.startActivity(ixte);

                      //  break;


                    }
                    // notifyItemRangeChanged(0, mItems.size());
                }
            };

//           // orderno.setOnClickListener(clickListener);
           custnae.setOnClickListener(clickListener);
//            //dat.setOnClickListener(clickListener);
            lincl.setOnClickListener(clickListener);

//            it_splus.setOnClickListener(clickListener);

        }
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<StcOutModel> filterList = new ArrayList<StcOutModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getitem_desc().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    } else if ((mStringFilterList.get(i).getitem_no().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        //StcOutModel StcOutModel=mStringFilterList;
//                        StcOutModel StcOutModel = new StcOutModel(mStringFilterList.get(i)
//                                .getName(),mStringFilterList.get(i).getThumbnail(),mStringFilterList.get(i).getitemNo());

                        filterList.add(mStringFilterList.get(i));
                    } else if ((mStringFilterList.get(i).getcustNo().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        //StcOutModel StcOutModel=mStringFilterList;
//                        StcOutModel StcOutModel = new StcOutModel(mStringFilterList.get(i)
//                                .getName(),mStringFilterList.get(i).getThumbnail(),mStringFilterList.get(i).getitemNo());

                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mItems = (ArrayList<StcOutModel>) results.values;
            notifyDataSetChanged();
        }

    }
}
