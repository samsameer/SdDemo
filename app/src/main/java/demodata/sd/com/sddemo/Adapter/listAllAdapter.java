package demodata.sd.com.sddemo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demodata.sd.com.sddemo.Activity.EditActivity;
import demodata.sd.com.sddemo.Activity.ItemviewActivity;
import demodata.sd.com.sddemo.Model.Orderin;
import demodata.sd.com.sddemo.R;
import demodata.sd.com.sddemo.Utils.Utilse;

/**
 * Created by jabbir on 8/6/15.
 */
public class listAllAdapter extends RecyclerView.Adapter<listAllAdapter.ViewHolder> implements Filterable {
    Context context;
    private int xPostu;
    List<Orderin> mItems;
    private int xCunt = 0;
    ArrayList<Boolean> getStatus;
    private ArrayList<Integer> selectedItems;
    List<Orderin> mStringFilterList;
    ValueFilter valueFilter;
    private int year;

    public listAllAdapter(Context context, ArrayList<Orderin> layoutResourceId, int posT) {
        super();
        this.mItems = new ArrayList<Orderin>();
        getStatus = new ArrayList<Boolean>();
        this.context = context;
        xCunt = posT;
        this.xPostu = xPostu;
        selectedItems = new ArrayList<Integer>();
        mItems = layoutResourceId;
        Log.i("thusuus", "" + mItems.size());
        mStringFilterList = layoutResourceId;
        for (int i = 0; i < mItems.size(); i++) {
            getStatus.add(false);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.allord_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Orderin nature = mItems.get(i);

        viewHolder._Qty.setText("1");
        viewHolder._Itmno.setText(nature.getitem_no());
       // viewHolder._ItmDesc.setText(nature.getitem_desc());
//        String bse4="";
       // try{
//            String ps2 = ;
//            byte[] tmp2 = Base64.decode(ps2, Base64.DEFAULT);
//            bse4 = new String(tmp2, "UTF-8");
//        }catch (Exception e){}


        viewHolder._ItmDesc.setText(nature.getitem_desc()+"- "+nature.getprod_date());

        viewHolder._Itmprice.setText(nature.getprice());
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

        private TextView _ItmDesc, _Itmprice, _Itmno, _Itmmin, _itemplus, _Itmadd;
        private EditText _Qty, _dicsqty;
        int[] txtR = {R.id._itmdes, R.id._itmprice, R.id._itmno, R.id._itmmin, R.id._itmqty, R.id._itmplus, R.id._itmadd, R.id._dicsqty};

        public ViewHolder(View itemView) {
            super(itemView);
            _ItmDesc = (TextView) itemView.findViewById(txtR[0]);
            _Itmprice = (TextView) itemView.findViewById(txtR[1]);
            _Itmno = (TextView) itemView.findViewById(txtR[2]);
            _Itmmin = (TextView) itemView.findViewById(txtR[3]);
            _itemplus = (TextView) itemView.findViewById(txtR[5]);
            _Itmadd = (TextView) itemView.findViewById(txtR[6]);
            _dicsqty = (EditText) itemView.findViewById(txtR[7]);
            _Qty = (EditText) itemView.findViewById(txtR[4]);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {

                        case R.id._itmplus:
                            int xc = Integer.parseInt(_Qty.getText().toString()) + 1;
                            _Qty.setText("" + xc);
                            break;
                        case R.id._itmmin:
                            int xct = Integer.parseInt(_Qty.getText().toString()) - 1;
                            if (xct < 1) {
                                xct = 1;
                            }
                            _Qty.setText("" + xct);
                            break;
                        case R.id._itmadd:

                            if (Utilse.newItemOrder.containsKey(_Itmno.getText().toString())) {

                                AlertDialog.Builder alertDel = new AlertDialog.Builder(context);
                                alertDel.setMessage("This Item already exists. Would you like to add again?");
                                alertDel.setTitle("Add Item ");
                                alertDel.setPositiveButton("         Yes              ", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        String[] xsz = Utilse.newItemOrder.get(_Itmno.getText().toString());

                                        String xcd = Integer.toString(Integer.parseInt(xsz[0])+ Integer.parseInt(_Qty.getText().toString()));
                                        String disc = Integer.toString(Integer.parseInt(xsz[1]) + 0);
//                                int disc=xsz[0]+Integer.parseInt(_dicsqty.getText().toString().replace("%","0"));
                                        String[] xcf = {xcd, (disc),""};
                                        Utilse.newItemOrder.put(_Itmno.getText().toString(), xcf);
                                        _Qty.setText("" + 1);
                                        if (xCunt == 1)
                                            ((EditActivity) context).txsM();

                                    }
                                });

                                alertDel.setNegativeButton("NO                          ", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        _Qty.setText("" + 1);
                                    }
                                });

                                alertDel.show();


                            } else {

                                String[] xsz = Utilse.newItemOrder.get(_Itmno.getText().toString());

                                String xcd = (_Qty.getText().toString());
//                                // String disc=(_dicsqty.getText().toString());
//                                String tt = "";
//                                try {
//                                    tt = (_dicsqty.getText().toString());
//                                } catch (Exception e) {
//                                    tt = "0";
//                                }


                                String tt = "0";
                                String remark="";

                                Log.d("Stttt", "" + tt);
                                String[] xcf = {xcd, tt,remark};
                                Utilse.newItemOrder.put(_Itmno.getText().toString(), xcf);
                                if (xCunt == 1)
                                    ((EditActivity) context).txsM();


                                _Qty.setText("" + 1);
                            }
                            _dicsqty.setText("");


                            Log.d("thiiiii", "" + Utilse.newItemOrder.get(_Itmno.getText().toString()));
                            break;


                    }
                    // notifyItemRangeChanged(0, mItems.size());
                }
            };
            // notifyItemRangeChanged(0, mItems.size());
            _Itmmin.setOnClickListener(clickListener);
            _itemplus.setOnClickListener(clickListener);
            _Itmadd.setOnClickListener(clickListener);

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

    private void lisAL() {
        Orderin tst = new Orderin();
        ItemviewActivity.txArray = new JSONArray();
        try {
            for (int i = 0; i < getStatus.size(); i++) {
                if (getStatus.get(i)) {
                    Orderin nature = mItems.get(i);
                    tst = new Orderin();
                    Log.d("getId", "" + i);
                    JSONObject jdObjc = new JSONObject();
                    jdObjc.put("s_no", "" + i);
                    jdObjc.put("item_id", nature.getitem_id());
                    jdObjc.put("item_no", nature.getitem_no());
                    jdObjc.put("item_desc", nature.getitem_desc());
                    jdObjc.put("qty_in", nature.getqty_in());
                    jdObjc.put("ref_qty_in", nature.getqty_in());
                    jdObjc.put("uom", "G");
                    jdObjc.put("prod_date", nature.getprod_date());
                    jdObjc.put("serial_lot_no", "");
                    jdObjc.put("from_wh_id", "10");
                    jdObjc.put("to_wh_id", "11");
                    ItemviewActivity.txArray.put(jdObjc);
                }
            }
        } catch (Exception e) {


        }


    }

}
