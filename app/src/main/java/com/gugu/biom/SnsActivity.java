package com.gugu.biom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugu.biom.LikeListView.SnsListViewItem;

import java.util.ArrayList;

public class SnsActivity extends AppCompatActivity {

    TextView tvPosition;
    ListView lv_Item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns);

        tvPosition = (TextView)findViewById(R.id.tvPosition);
        lv_Item = (ListView)findViewById(R.id.lv_Item);

        CustomAdapter adapter = new CustomAdapter();
        lv_Item.setAdapter(adapter);

        adapter.addItem(getDrawable(R.drawable.ic_launcher), "Seoul");
        adapter.addItem(getDrawable(R.drawable.ic_launcher), "Busan");
        adapter.addItem(getDrawable(R.drawable.ic_launcher), "Daegu");

        lv_Item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SnsListViewItem item = (SnsListViewItem)adapterView.getItemAtPosition(i);

                Toast.makeText(getApplicationContext(), ""+i+"번째", Toast.LENGTH_LONG).show();
            }
        });
    }


    //커스텀리스트뷰 어댑터
    private class CustomAdapter extends BaseAdapter{
        private  ArrayList<SnsListViewItem> list_item = new ArrayList<SnsListViewItem>();

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if(v==null){
                LayoutInflater vi= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, parent, false);
            }

            ImageView ivPhoto = (ImageView)v.findViewById(R.id.ivPhoto);
            TextView tvSNS = (TextView)v.findViewById(R.id.tvSNS);

            SnsListViewItem item = list_item.get(position);

            //리스트뷰의 아이템에 이미지를 변경

            tvSNS.setText(item.getTvSnsContent());
            ivPhoto.setImageDrawable(item.getIvSnsPhoto());

            return v;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return list_item.size();
        }

        @Override
        public Object getItem(int i) {
            return list_item.get(i);
        }

        public void addItem(Drawable image, String tvContent) {
            SnsListViewItem item = new SnsListViewItem();

            item.setIvSnsPhoto(image);
            item.setTvSnsContent(tvContent);

            list_item.add(item);
        }
    }
}
