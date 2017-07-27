package com.example.a10102.chivalry.patrol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.a10102.chivalry.R;
import com.example.a10102.chivalry.main.Floor;
import com.example.a10102.chivalry.main.ListViewAdapter;
import com.example.a10102.chivalry.room.Room;
import com.example.a10102.chivalry.room.RoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 10102김동규 on 2017-07-27.
 */

public class PatrolActivity extends AppCompatActivity {

    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_list_view);

        // ListView
        final ListView listview;
        final PatrolListViewAdapter adapter;

        // Adapter 생성
        adapter = new PatrolListViewAdapter();

        // ListView 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.patrol_listView);

        aq = new AQuery(this);
        int floor = PatrolFloor.getFloor();

        aq.ajax("http://13.124.15.202:8081/patrol/" + floor, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                try {
                    JSONArray rooms = new JSONArray(response);
                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject room = rooms.getJSONObject(i);
                        Drawable attention = room.getBoolean("isAttention") ? ContextCompat.getDrawable(getApplicationContext(), R.drawable.attantion_red) : ContextCompat.getDrawable(getApplicationContext(), R.drawable.attantion_green);
                        Drawable checked = room.getBoolean("checked") ? ContextCompat.getDrawable(getApplicationContext(), R.drawable.check) : null;

                        String roomNum = room.getInt("roomNum") + "호";
                        JSONArray members = room.getJSONArray("members");
                        String roomMembers = "";
                        for (int j = 0; j < members.length(); j++) {
                            roomMembers += members.get(j);

                            if (j != members.length() - 1) roomMembers += ", ";
                        }

                        adapter.addItem(checked, attention, roomNum, roomMembers);

                    }
                    listview.setAdapter(adapter);
                    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, Object> param = new HashMap<>();
                            final String roomNum_String = ((TextView) view.findViewById(R.id.textView_roomNum_patrol)).getText().toString();
                            final int roomNum = Integer.parseInt(roomNum_String.substring(0, roomNum_String.length() - 1));
                            Log.d("ASDASD@#U!*@(#)", roomNum + "");
                            aq.ajax("http://13.124.15.202:8081/patrol/" + roomNum, param, String.class, new AjaxCallback<String>() {
                                @Override
                                public void callback(String url, String response, AjaxStatus status) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (status.getCode() == 200) {
                                            for (int i = 0; i < adapter.getCount(); i++) {

                                                if (((PatrolListViewItem) adapter.getItem(i)).getRoom().equals(roomNum_String)) {
                                                    System.out.println("삐삐");
                                                    ((PatrolListViewItem) adapter.getItem(i)).setCheck(obj.getInt("after") == 1 ? ContextCompat.getDrawable(getApplicationContext(), R.drawable.check) : null);
                                                    recreate();
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }.method(AQuery.METHOD_PUT));

                            return true;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

//            // 첫 번째 아이템 추가
//            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.check),ContextCompat.getDrawable(this, R.drawable.attantion_green), "301호", "김가나, 나다라, 도마바");
//
//            // 두 번째 아이템 추가
//            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.check),ContextCompat.getDrawable(this, R.drawable.attantion_red), "302호", "류사아, 마자차, 박카타");
//
//            // 세 번째 아이템 추가
//            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.check),ContextCompat.getDrawable(this, R.drawable.attantion_green), "303호", "송파하, 임거너, 정더러");

        listview.setAdapter(adapter);
    }
}
