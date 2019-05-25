package com.example.kimgyutae.sotobi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_STRS = 0 ;
    private static final int ITEM_VIEW_TYPE_IMGS = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;

    // 아이템 데이터 리스트.
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            ListViewItem listViewItem = listViewItemList.get(position);
            convertView = inflater.inflate(R.layout.list_item,
                    parent, false);
            TextView timeTextView = (TextView) convertView.findViewById(R.id.list_time);
            TextView howTextView = (TextView) convertView.findViewById(R.id.list_how);
            TextView mpTextView = (TextView) convertView.findViewById(R.id.list_movingpoint);
            TextView pointTextView = (TextView) convertView.findViewById(R.id.list_point);


            timeTextView.setText(listViewItem.getTime());
            howTextView.setText(listViewItem.getHow());
            mpTextView.setText(listViewItem.getMp());
            pointTextView.setText(listViewItem.getpoint());
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    //아이템 추가를 위한 함수.
    public void addItem(String time, String how, String mp, String point) {
        ListViewItem item = new ListViewItem() ;

        item.setTime(time);
        item.setHow(how);
        item.setMp(mp);
        item.setpoint(point);

        listViewItemList.add(item) ;
    }
}