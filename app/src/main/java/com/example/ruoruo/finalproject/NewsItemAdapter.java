package com.example.ruoruo.finalproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsItemAdapter extends BaseAdapter {
    private Context context;
    private List<NewsItem> datas;
    private LayoutInflater layoutInflater;

    /**
     *
     * @param context
     * @param datas
     */
    public NewsItemAdapter(Context context, List<NewsItem> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     *
     * @return int dataset size
     */
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item, null);
            holder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        NewsItem newsItem = datas.get(i);
        holder.title.setText(newsItem.title);

        return view;
    }

    class ViewHolder {
        public TextView title;
    }
}
