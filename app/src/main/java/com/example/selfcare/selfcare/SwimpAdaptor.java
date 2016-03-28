package com.example.selfcare.selfcare;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sahar fathy on 2/14/2016.
 */
public class SwimpAdaptor extends PagerAdapter {


    private int[] imageResourses ={R.drawable.frag1,R.drawable.fragment2_2,R.drawable.fragment3,R.drawable.fragment4};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public SwimpAdaptor(Context ctx)
    {
        this.ctx=ctx;
    }


    @Override
    public int getCount() {
        return imageResourses.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object ) ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = layoutInflater.inflate(R.layout.swimp_layout,container,false);
        ImageView imageView = (ImageView) itemview.findViewById(R.id.image1);
        imageView.setImageResource(imageResourses[position]);
        container.addView(itemview);
        return itemview;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
