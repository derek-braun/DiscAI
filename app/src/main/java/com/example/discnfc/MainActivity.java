package com.example.discnfc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Switch to game activity on button press
    protected void toFrontNineActivity(View view){
        Intent myIntent = new Intent(getBaseContext(), GameActivity.class);
        myIntent.putExtra("holeValue", 1);
        startActivity(myIntent);
    }

    protected void toBackNineActivity(View view){
        Intent myIntent = new Intent(getBaseContext(), GameActivity.class);
        myIntent.putExtra("holeValue", 10);
        startActivity(myIntent);
    }
}
