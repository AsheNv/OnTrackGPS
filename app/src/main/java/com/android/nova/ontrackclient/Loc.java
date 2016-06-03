//package com.android.nova.ontrackclient;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.provider.Settings;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Button;
//import android.widget.Chronometer;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class Loc extends AppCompatActivity {
//
//    private Button button;
//    private TextView textView;
//    private TextView textViewSec;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//    String formatted;
//    int trainId=14;
//    double speed;
//    boolean locSpeed;
//    double lon;
//    double lat;
//    String longitude;
//    String latitude;
//    int seconds;
//    int secondsText;
//    long timeWhenStopped = 0;
//    boolean stopClicked;
//    Chronometer chrono;
//
//    public static final String mainUrl ="http://ontrack.16mb.com/app/TrainTrack.php?longitude=";
//    public static final String LatdUrl = "&latitude=";
//    public static final String idUrl = "&trainId=14";
//    String finalUrl;
//
//
//
//
//
//    private static final String URL = "http://ontrack.16mb.com/app/TrainTrack.php?longitude=21&latitude=22&trainId=115";
//    public static final String KEY_TRAINID = "trainId";
//    public static final String KEY_LONGITUDE = "longitude";
//    public static final String KEY_LATITUDE = "latitude";
//    public static final String KEY_SPEED = "speed";
//    public static final String KEY_TIME = "time";
//    public static final String KEY_IDLETIME = "idletime";
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        button = (Button) findViewById(R.id.buttonReq);
//        textView = (TextView) findViewById(R.id.textView);
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//                speed = location.getSpeed();
//                locSpeed = location.hasSpeed();
//                lon = location.getLongitude();
//                lat = location.getLatitude();
//                longitude = String.valueOf(lon);
//                latitude = String.valueOf(lat);
//                finalUrl = mainUrl+longitude+LatdUrl+latitude+idUrl;
//
//
//                DateFormat format = new SimpleDateFormat("HH:mm:ss");
//                Date date = new Date(location.getTime());
//                formatted = format.format(date);
//
//
//                textView.setText("\n" + "LAT : " + lat + "\n" + "LONG: " + lon + "\n" + "Time: " +
//                        formatted + "\n" + "Speed : " + speed );
//
//
//                calTime();
//                sendData();
//                Log.v("GG", finalUrl);
//
//            }
//
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.INTERNET}, 10);
//            return;
//        }
//
//
//        configButton();
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//
//            case 10:
//
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    configButton();
//                return;
//
//        }
//
//    }
//
//    private void configButton() {
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
//            }
//        });
//
//
//
//    }
//
//    private void sendData (){
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//
//    }
//
//    private void calTime() {
//
//        long start = 0;
//        long end = 0;
//        long dif = 0;
//        Button stopBtn;
//
//        chrono = (Chronometer) findViewById(R.id.chronometer);
//        stopBtn = (Button) findViewById(R.id.btnStop);
//        textViewSec = (TextView) findViewById(R.id.textViewSec);
//
//
//        if (speed == 0) {
//
//            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
//            chrono.start();
//            stopClicked = false;
//        }
//
//        stopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
//                seconds = (int) timeWhenStopped / 1000;
//                secondsText = Math.abs(seconds);
//                chrono.stop();
//                stopClicked = true;
//
//                textViewSec.setText("\n" + String.valueOf(secondsText));
//
//
//                Log.v("TT", String.valueOf(secondsText));
//
//            }
//        });
//
//
//
//
//
//
//
//
//    }
//}
//
//
//
//
//
////       String time=  String.format("%d min, %d sec",
////                TimeUnit.MILLISECONDS.toMinutes(dif),
////                TimeUnit.MILLISECONDS.toSeconds(dif) -
////                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dif))
////        );
//
//
//
//
//
//
//
//
