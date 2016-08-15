package demodata.sd.com.sddemo.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jabbir on 9/6/15.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private int selectedItem = -1;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            Log.d("postion", "" + view.getChildPosition(childView));
            mListener.onItemClick(childView, view.getChildPosition(childView));
//            childView.setBackgroundColor(Color.BLUE);
//            Log.d("selectedItem", "" + selectedItem);
//            if (selectedItem != -1 && selectedItem != view.getChildPosition(childView)) {
//                View childViewt = null;
//                 // childViewt= getLayoutInflater().inflate(R.layout.text_list, parent,false);
//                 childViewt = view.getChildAt(selectedItem);
//                if(childViewt!=null)
//                childViewt.setBackgroundColor(Color.WHITE);
////                else{
////                    for(int i=0;i< view.getChildCount();i++){
////                        View chilt = view.findViewById(i);
////                        chilt.setBackgroundColor(Color.WHITE);
////                    }
////                    childView.setBackgroundColor(Color.BLUE);
////                }
//
//            }
//            selectedItem = view.getChildPosition(childView);

        }


        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }
}