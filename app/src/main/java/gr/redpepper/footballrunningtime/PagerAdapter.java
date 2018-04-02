package gr.redpepper.footballrunningtime;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Integer> images = new ArrayList<>();



    public PagerAdapter(Context context) {

        images.add(R.drawable.gerflag);

        images.add(R.drawable.grflag);

        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_flag_layout,null);

        ImageView imageview = view.findViewById(R.id.flagImage);

        imageview.setBackgroundResource(images.get(position));

        ViewPager vp = (ViewPager) container;

        vp.addView(view,0);

        return view;
    }
}

