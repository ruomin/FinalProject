package com.example.ruoruo.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class FragmentPage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup image, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_fragment_page, image, false);

        return view;
    }



}
