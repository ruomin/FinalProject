package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * For Fragment page
 */
public class MovieFragment extends Fragment {
    /**
     * declare image view for a picture
     */
    ImageView moviePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        /**
         * add fragment layout to view
         */
        View view = inflater.inflate(R.layout.activity_movie_fragment, container, false);
        /**
         * get movie image when click it go back to main page
         */
        moviePic = view.findViewById(R.id.AuthorRecommended);
        moviePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}
