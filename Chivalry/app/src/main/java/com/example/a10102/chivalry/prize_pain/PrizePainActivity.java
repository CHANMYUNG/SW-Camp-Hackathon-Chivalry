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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by NooHeat on 27/07/2017.
 */

public class PrizePainActivity extends AppCompatActivity {

    AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prize_pain);

        aq = new AQuery(this);

        final TextView name = (TextView) findViewById(R.id.name);
        final TextView stuNum = (TextView) findViewById(R.id.stuNum);
        final TextView prize = (TextView) findViewById(R.id.prize);
        final TextView pain = (TextView) findViewById(R.id.pain);

        int stuNumValue = User.getStuNum();

        aq.ajax("http://13.124.15.202:8081/student/"+stuNumValue, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                try {
                    JSONObject student = new JSONObject(response);

                    name.setText(student.getString("name"));
                    stuNum.setText(Integer.toString(student.getInt("stuNum")));
                    prize.setText(Integer.toString(student.getInt("prize")));
                    pain.setText(Integer.toString(student.getInt("pain")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
