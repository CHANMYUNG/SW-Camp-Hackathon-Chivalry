package com.example.a10102.chivalry.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a10102.chivalry.R;

import java.util.ArrayList;

/**
 * Created by PTH on 2017-07-27.
 */

public class AlarmActivity extends AppCompatActivity{
    String floor_name;
    String time_name;
    Spinner floor;
    Spinner time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> arrayList1 = new ArrayList<String>();
        arrayList1.add(floor_name);
        arrayList1.add(time_name);
        setContentView(R.layout.alarm);
        //층 보이게하는 코드
        floor = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter floorAdapter = ArrayAdapter.createFromResource(this, R.array.floors, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floor.setAdapter(floorAdapter);

        time = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(timeAdapter);

        Button finish = (Button) findViewById(R.id.button1);
        finish.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                floor_name = floor.getSelectedItem().toString();
                time_name = time.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "예약되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
