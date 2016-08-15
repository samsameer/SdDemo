package demodata.sd.com.sddemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demodata.sd.com.sddemo.Activity.EditActivity;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

/**
 * Created by jabbir on 8/6/15.
 */
public class OrderviewAdapter extends RecyclerView.Adapter<OrderviewAdapter.ViewHolder> implements Filterable {
    Context context;
    private int xPostu;
    ArrayList<Orderin> mItems;
    ArrayList<Boolean> getStatus;
    private ArrayList<Integer> selectedItems;
    List<Orderin> mStringFilterList;
    ValueFilter valueFilter;
    private int xtr;
public OrderviewAdapter(Context context, ArrayList<Orderin> layoutResourceId,int xCount) {
        super();
        this.mItems = new ArrayList<Orderin>();
        getStatus = new ArrayList<Boolean>();
        this.context = context;
        this.xPostu = xPostu;
    this.xtr=xCount;
        selectedItems = new ArrayList<Integer>();
        mItems = layoutResourceId;
        Log.i("thusuus", "" + mItems.size());
        mStringFilterList = layoutResourceId;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ord_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Orderin nature = mItems.get(i);
        Log.d("vvvvvv", "" + nature.gets_no() + "," + nature.gets_no());

        if(xtr==1){
            viewHolder.imgDel.setVisibility(View.GONE);
            viewHolder.remark.setFocusable(false);
            viewHolder.qty.setFocusable(false);
            viewHolder.disc.setFocusable(false);
        }
        else{
            viewHolder.imgDel.setVisibility(View.VISIBLE);
            viewHolder.qty.setFocusable(true);
            viewHolder.disc.setFocusable(true);
            viewHolder.remark.setFocusable(true);
        }

        viewHolder.sno.setText(""+(i+1));
        viewHolder.qty.setText(nature.getqty_in());
        viewHolder.itmno.setText(nature.getitem_no());
        viewHolder.remark.setText(nature.getreMarkso());

      //

        viewHolder.itmdes.setText(nature.getitem_desc()+"- "+nature.getprod_date());
        viewHolder.price.setText(nature.getprice());


        viewHolder.disc.setText(nature.getdisCount());
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

        private TextView sno;
        private EditText qty, itmno, itmdes, disc, price;
        private ImageView imgDel;
        private EditText remark;
        int[] txtR = {R.id.s_no, R.id.q_ty, R.id.itm_no, R.id.itm_des, R.id.di_sc, R.id.del, R.id.price};

        public ViewHolder(View itemView) {
            super(itemView);
            sno = (TextView) itemView.findViewById(txtR[0]);
            qty = (EditText) itemView.findViewById(txtR[1]);
            itmno = (EditText) itemView.findViewById(txtR[2]);
            itmdes = (EditText) itemView.findViewById(txtR[3]);
            disc = (EditText) itemView.findViewById(txtR[4]);
            imgDel = (ImageView) itemView.findViewById(txtR[5]);
            price = (EditText) itemView.findViewById(txtR[6]);
            remark=(EditText) itemView.findViewById(R.id.remark);
//remark
            qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        Orderin nature = mItems.get(getPosition());

                        if(Integer.parseInt( qty.getText().toString())<=0){
                            nature.setqty_in("" + 1);
                        }
                        else
                        nature.setqty_in("" + qty.getText().toString());
                    } catch (Exception nfe) {
                        Orderin nature = mItems.get(getPosition());
                        nature.setqty_in("" + 1);
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
//
                }
            });

            remark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        Orderin nature = mItems.get(getPosition());
                            nature.setreMarks("" + remark.getText().toString());
                        String[] xyz={nature.getqty_in(),nature.getdisCount(),remark.getText().toString()};
                        Utilse.newItemOrder.put(nature.getitem_no(),xyz);
                    } catch (Exception nfe) {
                        Orderin nature = mItems.get(getPosition());
                        nature.setreMarks("");
                    }
//
                }
            });
//////
            disc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Orderin nature = mItems.get(getPosition());
                    nature.setdisCount("" + disc.getText().toString());
                }
            });
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.del:
                            delTetItem(getAdapterPosition());
                            break;
                    }
                    // notifyItemRangeChanged(0, mItems.size());
                }
            };

            imgDel.setOnClickListener(clickListener);
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
                List<Orderin> filterList = new ArrayList<Orderin>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getitem_desc().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    } else if ((mStringFilterList.get(i).getitem_no().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        //Orderin Orderin=mStringFilterList;
//                        Orderin Orderin = new Orderin(mStringFilterList.get(i)
//                                .getName(),mStringFilterList.get(i).getThumbnail(),mStringFilterList.get(i).getitemNo());

                        filterList.add(mStringFilterList.get(i));
                    }

                    else if ((mStringFilterList.get(i).getprod_date().toUpperCase())
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
            mItems = (ArrayList<Orderin>) results.values;
            notifyDataSetChanged();
        }

    }

    private void delTetItem(final int postion) {


        AlertDialog.Builder alertDel = new AlertDialog.Builder(context);


        alertDel.setMessage("Are you sure you want to del");
        alertDel.setTitle("Delete");


        alertDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                EditActivity trx= new EditActivity();
                Orderin nature = mItems.get(postion);
                Utilse.newItemOrder.remove(nature.getitem_no());
                mItems.remove(postion);
                notifyItemRemoved(postion);
                notifyItemRangeChanged(postion, mItems.size());

            }
        });

        alertDel.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alertDel.show();


    }




    public ArrayList<Orderin> pgetlish()

    {
        return mItems;
    }

}
