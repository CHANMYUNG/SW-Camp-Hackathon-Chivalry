package com.example.a10102.chivalry.prize_pain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.example.a10102.chivalry.R;

/**
 * Created by NooHeat on 27/07/2017.
 */

public class prize_pain_activity extends AppCompatActivity{

    AQuery aq;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prize_pain);

        aq = new AQuery(this);


        //aq.ajax("192.168.1.100:8080/")
    }
}
