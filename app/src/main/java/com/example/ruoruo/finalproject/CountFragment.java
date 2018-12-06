package com.example.ruoruo.finalproject;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountFragment extends Fragment {
    private TextView count;
    MyDbUtil myDbUtil;

    @Nullable
    @Override
    /**
     * use the inflater to get the counted number
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_cbc_count, container, false);
        init(view);
        return view;


    }

    /**
     * count the number of the articles, and the words of the article.
     * @param view
     */
    private void init(View view) {
        count = (TextView) view.findViewById(R.id.count);
        myDbUtil = new MyDbUtil(getActivity());
        List<NewsItem> newsList = new ArrayList<NewsItem>();
        Cursor c = myDbUtil.query();
        while (c.moveToNext()) {
            String title = c.getString(c.getColumnIndex("title"));
            String link = c.getString(c.getColumnIndex("link"));
            String guid = c.getString(c.getColumnIndex("guid"));
            String pubDate = c.getString(c.getColumnIndex("pubDate"));
            String author = c.getString(c.getColumnIndex("author"));
            String category = c.getString(c.getColumnIndex("category"));
            String description = c.getString(c.getColumnIndex("description"));
            NewsItem news = new NewsItem(title, link, guid, pubDate, author, category, description);
            newsList.add(news);
        }
        for (int i = 0; i < newsList.size() - 1; i++) {
            for (int j = 0; j < newsList.size() - 1 - i; j++) {
                if (newsList.get(j).description.length() > newsList.get(j + 1).description.length()) {
                    NewsItem temp = newsList.get(j);
                    newsList.set(j, newsList.get(j + 1));
                    newsList.set(j + 1, temp);
                }
            }
        }
        int totals = newsList.size();
        int min = newsList.get(0).description.length();
        int max = newsList.get(newsList.size() - 1).description.length();

        int sum = 0;
        for (NewsItem n : newsList) {
            sum += n.description.length();
        }
        int avg = sum / totals;
        //outpur the result as String
        String result = "save news count:" + totals + "\n************************\naverage:" + avg + "\n************************\n" + "max:" + max + "\n************************\n" + "min:" + min;
        count.setText(result);
    }

}
