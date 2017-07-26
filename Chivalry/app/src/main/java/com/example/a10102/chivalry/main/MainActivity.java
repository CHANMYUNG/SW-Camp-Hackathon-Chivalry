package com.example.a10102.chivalry.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.util.Log;
import android.view.Menu;
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
import com.example.a10102.chivalry.patrol.PatrolFragment;
import com.example.a10102.chivalry.room.Room;
import com.example.a10102.chivalry.room.RoomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean bLog = false;
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

//        // 첫 번째 아이템 추가
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_alarm) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_patrol) {
            Intent intent = new Intent(this, PatrolFragment.class);
            startActivity(intent);
//            Intent intent = new Intent(this, PatrolFragment.class);
//            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("test", "onPrepareOptionsMenu - 옵션메뉴가 " +
                "화면에 보여질때 마다 호출됨");
        if(bLog){ // 로그인 한 상태: 로그인은 안보이게, 로그아웃은 보이게
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(false);
        }else{ // 로그 아웃 한 상태 : 로그인 보이게, 로그아웃은 안보이게
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
        }

        bLog = !bLog;   // 값을 반대로 바꿈

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // 메뉴버튼이 처음 눌러졌을 때 실행되는 콜백메서드
        // 메뉴버튼을 눌렀을 때 보여줄 menu 에 대해서 정의
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("test", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을 때 호출됨");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");

        int id = item.getItemId();


        switch(id) {
            case R.id.menu_login:
                Toast.makeText(getApplicationContext(), "3층 메뉴 선택",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_logout:
                Toast.makeText(getApplicationContext(), "4층 메뉴 선택",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_a:
                Toast.makeText(getApplicationContext(), "5층 메뉴 선택",
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
