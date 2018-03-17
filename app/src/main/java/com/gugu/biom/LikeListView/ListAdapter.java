package com.gugu.biom.LikeListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.gugu.biom.Data.ItemData;
import com.gugu.biom.R;

import java.util.ArrayList;

/**
 * Created by Dandy on 2018-02-17.
 */

public class ListAdapter extends BaseAdapter {

    private static final int ITEM_VIEW_TYPE_STRS = 0;
    private static final int ITEM_VIEW_TYPE_STRS_2 = 1;
    private static final int ITEM_VIEW_TYPE_STRS_3=2;
    private static final int ITEM_VIEW_TYPE_STRS_4=3;
    private static final int ITEM_VIEW_TYPE_STRS_5=4;
    private static final int ITEM_VIEW_MAX = 5;

    private ArrayList<ItemData> listViewItemList = new ArrayList<ItemData>();

    TextView titleTextView;
    TextView titleTextView_;


    Switch alarmSwitch;

    public ListAdapter(){}

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_MAX;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return listViewItemList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        int viewType = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            ItemData listViewItem = listViewItemList.get(position);
            switch (viewType) {
                case ITEM_VIEW_TYPE_STRS:
                    convertView = inflater.inflate(R.layout.list_item3, parent, false);
                    titleTextView = (TextView) convertView.findViewById(R.id.tvText);
                    titleTextView.setText(listViewItem.getStrTitle());
                    break;
                case ITEM_VIEW_TYPE_STRS_2:
                    convertView = inflater.inflate(R.layout.list_item2, parent, false);
                    titleTextView = (TextView) convertView.findViewById(R.id.tvText);
                    titleTextView.setText(listViewItem.getStrTitle_2());
                    alarmSwitch = (Switch) convertView.findViewById(R.id.switchAlarm);
                    break;
                case ITEM_VIEW_TYPE_STRS_3:
                    convertView=inflater.inflate(R.layout.list_item4,parent,false);
                    titleTextView_=(TextView) convertView.findViewById(R.id.tvText);
                    titleTextView_.setText(listViewItem.getStrTitle_3());

                    break;
                case ITEM_VIEW_TYPE_STRS_4:
                    convertView=inflater.inflate(R.layout.list_item3,parent,false);
                    titleTextView=(TextView)convertView.findViewById(R.id.tvText);
                    titleTextView.setText(listViewItem.getStrTitle_4());
                    break;
                case ITEM_VIEW_TYPE_STRS_5:
                    convertView=inflater.inflate(R.layout.list_item5,parent,false);
                    titleTextView=(TextView)convertView.findViewById(R.id.tvText);
                    titleTextView.setText(listViewItem.getStrTitle_5());
                    break;

            }

        }

        return convertView;


    }




    public void addItem(String title, String desc) {
        ItemData item = new ItemData();

        item.setType(ITEM_VIEW_TYPE_STRS);
        item.setstrTitle(title);
        item.setstrDecs(desc);

        listViewItemList.add(item);
    }

    public void addItem(String title) {
        ItemData item = new ItemData();
        item.setType(ITEM_VIEW_TYPE_STRS_2);
        item.setstrTitle_2(title);

        listViewItemList.add(item);
    }
    public void addItem_a(String title){
        ItemData item = new ItemData();
        item.setType(ITEM_VIEW_TYPE_STRS_3);
        item.setstrTitle_3(title);



        listViewItemList.add(item);
    }

    public void addItem_aa(String title){
        ItemData item = new ItemData();
        item.setType(ITEM_VIEW_TYPE_STRS_4);
        item.setstrTitle_4(title);

        listViewItemList.add(item);
    }
    public void addItem_aaa(String title){
        ItemData item = new ItemData();
        item.setType(ITEM_VIEW_TYPE_STRS_5);
        item.setstrTitle_5(title);

        listViewItemList.add(item);
    }




    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData) {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }
}

