package com.example.discnfc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    TextView hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9;
    TextView hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18;
    TextView front9, back9, total;

    int front9Value;
    int back9Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        hole1 = findViewById(R.id.hole1);
        hole2 = findViewById(R.id.hole2);
        hole3 = findViewById(R.id.hole3);
        hole4 = findViewById(R.id.hole4);
        hole5 = findViewById(R.id.hole5);
        hole6 = findViewById(R.id.hole6);
        hole7 = findViewById(R.id.hole7);
        hole8 = findViewById(R.id.hole8);
        hole9 = findViewById(R.id.hole9);
        hole10 = findViewById(R.id.hole10);
        hole11 = findViewById(R.id.hole11);
        hole12 = findViewById(R.id.hole12);
        hole13 = findViewById(R.id.hole13);
        hole14 = findViewById(R.id.hole14);
        hole15 = findViewById(R.id.hole15);
        hole16 = findViewById(R.id.hole16);
        hole17 = findViewById(R.id.hole17);
        hole18 = findViewById(R.id.hole18);
        back9 = findViewById(R.id.back9);
        front9 = findViewById(R.id.front9);
        total = findViewById(R.id.total);

        Bundle extras = getIntent().getExtras();
        int[] values = (int[]) extras.get("roundData");

        if(values[0] != 0)
            hole1.setText("Hole 1: " + values[0]);
        else
            hole1.setText("Hole 1: DNP");
        if(values[1] != 0)
        hole2.setText("Hole 2: " + values[1]);
        else
            hole2.setText("Hole 2: DNP");
        if(values[2] != 0)
        hole3.setText("Hole 3: " + values[2]);
        else
            hole3.setText("Hole 3: DNP");
        if(values[3] != 0)
        hole4.setText("Hole 4: " + values[3]);
        else
            hole4.setText("Hole 4: DNP");
        if(values[4] != 0)
        hole5.setText("Hole 5: " + values[4]);
        else
            hole5.setText("Hole 5: DNP");
        if(values[5] != 0)
        hole6.setText("Hole 6: " + values[5]);
        else
            hole6.setText("Hole 6: DNP");
        if(values[6] != 0)
        hole7.setText("Hole 7: " + values[6]);
        else
            hole7.setText("Hole 7: DNP");
        if(values[7] != 0)
        hole8.setText("Hole 8: " + values[7]);
        else
            hole8.setText("Hole 8: DNP");
        if(values[8] != 0)
        hole9.setText("Hole 9: " + values[8]);
        else
            hole9.setText("Hole 9: DNP");
        if(values[9]!= 0)
        hole10.setText("Hole 10: " + values[9]);
        else
            hole10.setText("Hole 10: DNP");
        if(values[10] != 0)
        hole11.setText("Hole 11: " + values[10]);
        else
            hole11.setText("Hole 11: DNP");
        if(values[11] != 0)
        hole12.setText("Hole 12: " + values[11]);
        else
            hole12.setText("Hole 12: DNP");
        if(values[12] != 0)
        hole13.setText("Hole 13: " + values[12]);
        else
            hole13.setText("Hole 13: DNP");
        if(values[13] != 0)
        hole14.setText("Hole 14: " + values[13]);
        else
            hole14.setText("Hole 14: DNP");
        if(values[14] != 0)
        hole15.setText("Hole 15: " + values[14]);
        else
            hole15.setText("Hole 15: DNP");
        if(values[15] != 0)
        hole16.setText("Hole 16: " + values[15]);
        else
            hole16.setText("Hole 16: DNP");
        if(values[16] != 0)
        hole17.setText("Hole 17: " + values[16]);
        else
            hole17.setText("Hole 17: DNP");
        if(values[17] != 0)
        hole18.setText("Hole 18: " + values[17]);
        else
            hole18.setText("Hole 18: DNP");

        boolean completedFront9 = true;
        for(int i = 0; i < 9; i++){
            if(values[i] == 0){
                completedFront9 = false;
            }
            front9Value += values[i];
        }

        if(completedFront9)
            front9.setText("Front 9: " + Integer.toString(front9Value - 28));
        else
            front9.setText("Front 9: N/A");

        boolean completedBack9 = true;
        for(int i = 9; i < 18; i++){
            if(values[i] == 0){
                completedBack9 = false;
            }
            back9Value += values[i];
        }

        if(completedBack9)
            back9.setText("Front 9: " + Integer.toString(back9Value - 28));
        else
            back9.setText("Back 9: N/A");

        if(completedBack9 && completedFront9)
            total.setText("Total: " + Integer.toString(front9Value + back9Value - 56));
        else
            total.setText("Total: N/A");
    }
}
