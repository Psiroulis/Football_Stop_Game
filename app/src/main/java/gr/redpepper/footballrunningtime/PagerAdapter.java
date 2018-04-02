package gr.redpepper.footballrunningtime;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Integer> flagImageList = new ArrayList<>();



    public PagerAdapter(Context context, ArrayList<Integer> list) {

        this.context = context;

        this.flagImageList = list;
    }

    @Override
    public int getCount() {

        return flagImageList.size();

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

        imageview.setBackgroundResource(flagImageList.get(position));

        Button button = view.findViewById(R.id.SelectTeamButton);

        ViewPager vp = (ViewPager) container;

        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;

        View view = (View) object;

        vp.removeView(view);
    }
}

