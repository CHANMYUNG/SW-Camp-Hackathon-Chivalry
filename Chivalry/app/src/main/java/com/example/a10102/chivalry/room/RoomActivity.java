package com.example.a10102.chivalry.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.a10102.chivalry.R;
import com.example.a10102.chivalry.prize_pain.PrizePainActivity;
import com.example.a10102.chivalry.prize_pain.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NooHeat on 27/07/2017.
 */

public class RoomActivity extends AppCompatActivity {
    AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_detail);

        int roomNum = Room.getRoomNum();

        aq = new AQuery(this);

        aq.ajax("http://13.124.15.202:8081/room/" + roomNum, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                try {
                    System.out.println(url);
                    JSONObject resultObject = new JSONObject(response);
                    String roomNum = resultObject.getString("roomNum") + "호";
                    boolean isNice = resultObject.getBoolean("isNice");

                    ImageView clean = (ImageView) findViewById(R.id.info_clean);

                    clean.setImageDrawable(isNice ? ContextCompat.getDrawable(getApplicationContext(), R.drawable.clean_green) : ContextCompat.getDrawable(getApplicationContext(), R.drawable.clean_red));
                    ((TextView) findViewById(R.id.info_clean_text)).setText(isNice ? "양호" : "불량");
                    ((TextView) findViewById(R.id.info_roomNum)).setText(roomNum);

                    JSONArray members = resultObject.getJSONArray("members");
                    for (int i = 0; i < members.length(); i++) {
                        JSONObject obj = members.getJSONObject(i);
                        switch (i) {
                            case 0:
                                ((TextView) findViewById(R.id.info_stuNum0)).setText(obj.getInt("stuNum") + "");
                                ((TextView) findViewById(R.id.info_name0)).setText(obj.getString("name"));
                                ((TextView) findViewById(R.id.info_prize0)).setText(obj.getInt("prize") + "점");
                                ((TextView) findViewById(R.id.info_pain0)).setText(obj.getInt("pain") + "점");
                                break;
                            case 1:
                                ((TextView) findViewById(R.id.info_stuNum1)).setText(obj.getInt("stuNum") + "");
                                ((TextView) findViewById(R.id.info_name1)).setText(obj.getString("name"));
                                ((TextView) findViewById(R.id.info_prize1)).setText(obj.getInt("prize") + "점");
                                ((TextView) findViewById(R.id.info_pain1)).setText(obj.getInt("pain") + "점");
                                break;
                            case 2:
                                ((TextView) findViewById(R.id.info_stuNum2)).setText(obj.getInt("stuNum") + "");
                                ((TextView) findViewById(R.id.info_name2)).setText(obj.getString("name"));
                                ((TextView) findViewById(R.id.info_prize2)).setText(obj.getInt("prize") + "점");
                                ((TextView) findViewById(R.id.info_pain2)).setText(obj.getInt("pain") + "점");
                                break;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ((ImageView)findViewById(R.id.studentSection0)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stuNum = Integer.parseInt(((TextView) findViewById(R.id.info_stuNum0)).getText().toString());
                User.setStuNum(stuNum);
                Intent intent = new Intent(getApplicationContext(), PrizePainActivity.class);
                startActivity(intent);
            }
        });

        ((ImageView)findViewById(R.id.studentSection1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stuNum = Integer.parseInt(((TextView) findViewById(R.id.info_stuNum1)).getText().toString());
                User.setStuNum(stuNum);
                Intent intent = new Intent(getApplicationContext(), PrizePainActivity.class);
                startActivity(intent);
            }
        });

        ((ImageView)findViewById(R.id.studentSection2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stuNum = Integer.parseInt(((TextView) findViewById(R.id.info_stuNum2)).getText().toString());
                User.setStuNum(stuNum);
                Intent intent = new Intent(getApplicationContext(), PrizePainActivity.class);
                startActivity(intent);
            }
        });
    }
}
