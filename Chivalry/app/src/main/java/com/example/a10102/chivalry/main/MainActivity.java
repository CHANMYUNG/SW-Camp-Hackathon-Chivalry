package com.example.a10102.chivalry.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.a10102.chivalry.R;
import com.example.a10102.chivalry.room.Room;
import com.example.a10102.chivalry.room.RoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //툴바 타이틀 없애기
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ListView
        final ListView listview;
        final ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter();

        // ListView 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.roomlistView);


        aq = new AQuery(this);
        int floor = Floor.getFloor();
        aq.ajax("http://13.124.15.202:8080/room/floor/" + floor, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String response, AjaxStatus status) {
                try {
                    JSONArray rooms = new JSONArray(response);
                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject room = rooms.getJSONObject(i);
                        Drawable drawable = room.getBoolean("isNice") ? ContextCompat.getDrawable(getApplicationContext(), R.drawable.clean_green) : ContextCompat.getDrawable(getApplicationContext(), R.drawable.clean_red);
                        String roomNum = room.getInt("roomNum") + "호";
                        JSONArray members = room.getJSONArray("members");
                        String roomMembers = "";
                        for (int j = 0; j < members.length(); j++) {
                            roomMembers += members.get(j);

                            if (j != members.length() - 1) roomMembers += ", ";
                        }

                        adapter.addItem(drawable, roomNum, roomMembers);
                    }
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String roomNum_String = ((TextView) view.findViewById(R.id.textView_roomNum)).getText().toString();
                            int roomNum = Integer.parseInt(roomNum_String.substring(0, roomNum_String.length() - 1));
                            Room.setRoomNum(roomNum);

                            Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        // 첫 번째 아이템 추가
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.clean_green), "301호", "김가나, 나다라, 도마바");
//
//        // 두 번째 아이템 추가
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.clean_green), "302호", "류사아, 마자차, 박카타");
//
//        // 세 번째 아이템 추가
//        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.clean_red), "303호", "송파하, 임거너, 정더러");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            // Handle the camera action
        } else if (id == R.id.nav_alarm) {

        } else if (id == R.id.nav_patrol) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
