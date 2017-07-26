package com.example.a10102.chivalry.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.a10102.chivalry.R;

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

        aq.ajax("http://13.124.15.202:8080/room/" + roomNum, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                try {
                    JSONObject resultObject = new JSONObject(response);
                    String roomNum = resultObject.getString("roomNum");
                    boolean isNice = resultObject.getBoolean("isNice");
                    JSONArray members = resultObject.getJSONArray("members");

                    System.out.println(roomNum);
                    System.out.println(isNice);
                    System.out.println(members);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
