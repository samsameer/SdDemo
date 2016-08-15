package demodata.sd.com.sddemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import demodata.sd.com.sddemo.Activity.LaunchActivity;
import demodata.sd.com.sddemo.R;


/**
 * Created by jabbir on 14/1/16.
 */

public class CustomPagerAdapter extends PagerAdapter {
    int[] mResources = {
            R.drawable.front_banner1,
            R.drawable.front_banner2,
            R.drawable.front_banner3,
            R.drawable.front_banner4

    };

    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i=new Intent(mContext,LaunchActivity.class);
                mContext.startActivity(i);

            }
        });

        return itemView;
    }





    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}