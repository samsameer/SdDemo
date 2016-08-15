package demodata.sd.com.sddemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demodata.sd.com.sddemo.Model.CustomerModel;
import demodata.sd.com.sddemo.R;

/**
 * Created by jabbir on 8/6/15.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>  implements Filterable {
    Context context;
    private int xPostu;
    List<CustomerModel> mItems;
    ArrayList<Boolean> getStatus;
    private ArrayList<Integer> selectedItems;
    ValueFilter valueFilter;
    List<CustomerModel> mStringFilterList;
    private int day;
    private int month;
    private int year;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> layoutResourceId) {
        super();
        this.mItems = new ArrayList<CustomerModel>();
        getStatus = new ArrayList<Boolean>();
        this.context = context;
        this.xPostu = xPostu;
        selectedItems = new ArrayList<Integer>();
        mItems = layoutResourceId;
        mStringFilterList = layoutResourceId;
        for (int i = 0; i < mItems.size(); i++) {
            getStatus.add(false);
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custmr_itm, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        CustomerModel nature = mItems.get(i);

        viewHolder._Custmrnm.setText(nature.getcust_name());
        viewHolder._Custmrno.setText(nature.getcust_no());
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
        private TextView _Custmrnm, _Custmrno;
        int[] txtR = {R.id.cust_name, R.id.cust_no};


        public ViewHolder(View itemView) {
            super(itemView);
            _Custmrnm = (TextView) itemView.findViewById(txtR[0]);
            _Custmrno = (TextView) itemView.findViewById(txtR[1]);
           
//            rsCycle = (RecyclerView) itemView.findViewById(R.id.tdt);
//            detailManager = new GridLayoutManager(context.getApplicationContext(), 1);
//            rsCycle.setLayoutManager(detailManager);
//            rsCycle.setHasFixedSize(true);

//
//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    switch (v.getId()) {
////                        case R.id.txt1:
////                            //int index = selectedItems.indexOf(getAdapterPosition());
////                            Log.d("getPostion", "" + getAdapterPosition());
////
////                            if (orderno.isSelected()) {
////                                orderno.setSelected(false);
////                                //ItemviewActivity.xyzInt=0;
////                                for (int i = 0; i < getStatus.size(); i++) {
////                                    if (getAdapterPosition() == i) {
////                                        getStatus.set(getAdapterPosition(), false);
////                                    } else
////                                        getStatus.set(i, false);
////
////                                }
////
////
////                            } else {
////                                orderno.setSelected(true);
////
////                                for (int i = 0; i < getStatus.size(); i++) {
////                                    if (getAdapterPosition() == i) {
////                                        getStatus.set(getAdapterPosition(), true);
////                                        //ItemviewActivity.xyzInt=getAdapterPosition();
////                                    } else{
////                                        //ItemviewActivity.xyzInt=0;
////                                        getStatus.set(i, false);
////                                    }
////
////
////                                }
////                            }
////                            ItemviewActivity.xyzInt=-5;
////                            for (int i = 0; i <getStatus.size() ; i++) {
////                                if(getStatus.get(i)){
////                                    ItemviewActivity.xyzInt=i;
////                                }
////                            }
////                            Log.d("cmeeee5555", ""+ItemviewActivity.xyzInt);
////                            // chkItem();
////                            notifyDataSetChanged();
////                            break;
////                    }
////                    // notifyItemRangeChanged(0, mItems.size());
////                }
////           };
//
////            orderno.setOnClickListener(clickListener);
////            it_splus.setOnClickListener(clickListener);
//
            //}
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
                List<CustomerModel> filterList = new ArrayList<CustomerModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getcust_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    } else if ((mStringFilterList.get(i).getcust_no().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        //Orderin Orderin=mStringFilterList;
//                        Orderin Orderin = new Orderin(mStringFilterList.get(i)
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
            mItems = (ArrayList<CustomerModel>) results.values;
            notifyDataSetChanged();
        }

    }
}