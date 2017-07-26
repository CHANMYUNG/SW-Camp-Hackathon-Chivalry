package com.example.a10102.chivalry.prize_pain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.a10102.chivalry.R;

import org.w3c.dom.Text;

/**
 * Created by NooHeat on 27/07/2017.
 */

public class prize_pain_activity extends AppCompatActivity {

    AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prize_pain);

        aq = new AQuery(this);

        TextView name = (TextView) findViewById(R.id.name);
        TextView stuNum = (TextView) findViewById(R.id.stuNum);
        TextView prize = (TextView) findViewById(R.id.prize);
        TextView pain = (TextView) findViewById(R.id.pain);
        aq.ajax("192.168.1.100:8080/student/20214", String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                Log.d("result","결과옴");
                System.out.println(response);
            }
        });
    }
}
