package com.example.discnfc;

import android.annotation.TargetApi;
import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import static android.nfc.NdefRecord.createTextRecord;

public class GameActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView txtTagContent;
    TextView scoreDisplay;
    TextView holeDisplay;
    int holeNumber;
    int holeScore;
    String currentDisc;
    LinkedList<ThrowData> throwDataList;
    HoleData[] holeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //final TextView coordinates = findViewById(R.id.coordinates);
        initiateGPSRequests();

        //txtTagContent = findViewById(R.id.nfcTag);
        holeDisplay = findViewById(R.id.holeText);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        initiateNFC(nfcAdapter);

        holeScore = 0;
        Bundle extras = getIntent().getExtras();
        holeNumber = extras.getInt("holeValue");
        holeDisplay.setText(Integer.toString(holeNumber));

        scoreDisplay = findViewById(R.id.scoreText);

        holeData = new HoleData[18];
        throwDataList = new LinkedList<>();
    }

    protected void startNextHole(View view){
        holeData[holeNumber-1] = new HoleData(throwDataList);
        throwDataList = new LinkedList<>();
        holeNumber++;
        if(holeNumber == 19){
            //Go to end activity
        }
        holeDisplay.setText("Hole " + Integer.toString(holeNumber));
        holeScore = 0;
        scoreDisplay.setText(Integer.toString(holeScore));
    }

    protected void initiateNFC(NfcAdapter nfcAdapter){
        //Toast NFC availability to user
        if(nfcAdapter!=null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC available", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "NFC not available", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initiateGPSRequests(){
        //Create location manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        System.out.println("Connecting Location Manager to Location Listener");

        //Location listener responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //coordinates.setText(location.getLongitude() + " " + location.getLatitude());
                throwDataList.add(new ThrowData(holeNumber, holeScore, currentDisc, location));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Request GPS permission for app from user
        ActivityCompat.requestPermissions(GameActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(GameActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    //Toast user gps status
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(GameActivity.this, "GPS Available", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onNewIntent (Intent intent){
        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(this, "Scanned", Toast.LENGTH_SHORT).show();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables != null && parcelables.length > 0){
                readTextFromMessage((NdefMessage) parcelables[0]);
            }
            else{
                Toast.makeText(this, "No NDEF Messages Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readTextFromMessage(NdefMessage ndefMessage){
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0){
            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = getTextFromNdefRecord(ndefRecord);
            //txtTagContent.setText(tagContent);
            currentDisc = tagContent;

            if(tagContent.equals("end-hole")){
                holeScore = 0;
                holeNumber++;
                holeDisplay.setText("Hole " + Integer.toString(holeNumber));
            }
            else{
                holeScore++;
            }

            scoreDisplay.setText(Integer.toString(holeScore));

            // Get instance of Vibrator from current Context
            Vibrator nfcVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            nfcVibrate.vibrate(3000);

            //If GPS permission is not enabled, exit
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("Failed to get gps permission");
                return;
            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

        }
        else{
            Toast.makeText(this, "No NDEF Records Found!", Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private NdefMessage createNdefMessage(String content){
        NdefRecord ndefRecord = createTextRecord("en", content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] {ndefRecord});

        return ndefMessage;
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try{
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize -1, textEncoding);

        }catch(UnsupportedEncodingException e){
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    private void enableForegroundDispatchSystem(){

        Intent intent = new Intent(this, GameActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[] {};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    private void disableForegroundDispatchSystem(){
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onBackPressed(){
        //Do nothing
    }
}
