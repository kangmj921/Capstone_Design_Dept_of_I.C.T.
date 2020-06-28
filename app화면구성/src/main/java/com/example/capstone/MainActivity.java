package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    SQLiteDatabase db;
    ArrayList<LatLng> loc1list;
    ArrayList<LatLng> loc2list;
    ArrayList<LatLng> loc3list;
    ArrayList<LatLng> loc4list;
    ArrayList<LatLng> loc5list;
    ArrayList<LatLng> loc6list;

    ArrayList arrayList =new ArrayList<String>();
    Spinner spinner;
    Button button;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<ArrayList<String>> mGroupList = null;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    GoogleMap map;
    PolylineOptions polylineOptions;
    /////////////////////
    private GpsTracker gpsTracker;
    Marker marker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    ///////////////////////
    Polygon haehyun;
    Polygon junglim;
    Polygon sogong;
    Polygon myungdong;
    Polygon eulji;
    Polygon kwanghee;
    Polygon pilldong;
    Polygon jangchung;
    Polygon dasan;
    Polygon sindang;
    Polygon sindang5;
    Polygon hwanghak;
    Polygon donghwa;
    Polygon chunggu;
    Polygon yaksu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////

        //////////////////////////////////////
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //////////////////////////////////////
        DBHelper helper = new DBHelper(this);
        db= helper.getWritableDatabase();
        mGroupList = new ArrayList<ArrayList<String>>();
        loc1list = new ArrayList<LatLng>();
        loc2list = new ArrayList<LatLng>();
        loc3list = new ArrayList<LatLng>();
        loc4list = new ArrayList<LatLng>();
        loc5list = new ArrayList<LatLng>();
        loc6list = new ArrayList<LatLng>();
        button = (Button)findViewById(R.id.button);
        //////////////////////////////////////
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkLocationServicesStatus()) {

                    showDialogForLocationServiceSetting();
                }else {
                    if(marker != null){
                        marker.remove();
                    }
                    checkRunTimePermission();
                    gpsTracker = new GpsTracker(MainActivity.this);
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude)));


                    Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                }



//

            }
        });
        //////////////////////////////////// GRID좌표
//        try{
//            InputStream in = getResources().openRawResource(R.raw.location);
//            if(in != null){
//                InputStreamReader stream = new InputStreamReader(in, "utf-8");
//                BufferedReader buffer = new BufferedReader(stream);
//                String read;
//                while((read=buffer.readLine()) !=null){
//                    String[] a = read.split(" ");
//                    db.execSQL("insert into location (x,y,lat,lon) values (?,?,?,?)",
//                            new String[]{a[0],a[1],a[2],a[3]});
//                }
//
//                in.close();
//
//
//            }
//            }catch(Exception e){
//            e.printStackTrace();
//
//        }



//
//        ////////////////////////////
//        int[][] path =
//
//        for(int i=0; i<path.length; i++){
//            int x = path[i][0];
//            int y = path[i][1];
//            Cursor c = db.rawQuery("select * from location where x=? and y=?",new String[]{String.valueOf(x),String.valueOf(y)});
//
//            Log.d("커서개수",String.valueOf(c.getCount()));
//            while(c.moveToNext()){
//                String resultx =c.getString(c.getColumnIndex("x"));
//               String resulty=c.getString(c.getColumnIndex("y"));
//                String resultlat =c.getString(c.getColumnIndex("lat"));
//                String resultlon=c.getString(c.getColumnIndex("lon"));
//
//
//                db.execSQL("insert into loc3 (x,y,lat,lon) values (?,?,?,?)",
//                        new String[]{resultx,resulty,resultlat,resultlon});
//            }
//        }


        /////////////////////////////////////
        arrayList.add("AM 00:00 ~ AM 04:00");
        arrayList.add("AM 04:00 ~ AM 08:00");
        arrayList.add("AM 08:00 ~ AM 12:00");
        arrayList.add("PM 12:00 ~ PM 16:00");
        arrayList.add("PM 16:00 ~ PM 20:00");
        arrayList.add("PM 20:00 ~ PM 24:00");
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getItemAtPosition(i).equals("AM 00:00 ~ AM 04:00")){
                    initMap(loc1list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc1list,"loc1");
                    addPolyLine(loc1list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFFB270"));
                    junglim.setFillColor(Color.parseColor("#7FFFDC8A"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFF0E09"));
                    eulji.setFillColor(Color.parseColor("#7FFFB270"));
                    kwanghee.setFillColor(Color.parseColor("#7FFFB270"));
                    pilldong.setFillColor(Color.parseColor("#7FFF0000"));
                    jangchung.setFillColor(Color.parseColor("#7FFF5D3B"));
                    dasan.setFillColor(Color.parseColor("#7FFF784B"));
                    sindang.setFillColor(Color.parseColor("#7FFFB270"));
                    sindang5.setFillColor(Color.parseColor("#7FFF8A56"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFDC8A"));
                    donghwa.setFillColor(Color.parseColor("#7FFFB270"));
                    chunggu.setFillColor(Color.parseColor("#7FFFDC8A"));
                    yaksu.setFillColor(Color.parseColor("#7FFFB270"));


                }
                else if(spinner.getItemAtPosition(i).equals("AM 04:00 ~ AM 08:00")){
                    initMap(loc2list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc2list,"loc2");
                    addPolyLine(loc2list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFFDC8A"));
                    junglim.setFillColor(Color.parseColor("#7FFFB270"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFFD384"));
                    eulji.setFillColor(Color.parseColor("#7FFF0E09"));
                    kwanghee.setFillColor(Color.parseColor("#7FFF0E09"));
                    pilldong.setFillColor(Color.parseColor("#7FFFB270"));
                    jangchung.setFillColor(Color.parseColor("#7FFFD485"));
                    dasan.setFillColor(Color.parseColor("#7FFF784B"));
                    sindang.setFillColor(Color.parseColor("#7FFF0000"));
                    sindang5.setFillColor(Color.parseColor("#7FFFB270"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFB270"));
                    donghwa.setFillColor(Color.parseColor("#7FFFDC8A"));
                    chunggu.setFillColor(Color.parseColor("#7FFFB270"));
                    yaksu.setFillColor(Color.parseColor("#7FFFDC8A"));



                }
                else if(spinner.getItemAtPosition(i).equals("AM 08:00 ~ AM 12:00")){
                    initMap(loc3list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc3list,"loc3");
                    addPolyLine(loc3list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFF975F"));
                    junglim.setFillColor(Color.parseColor("#7FFF9B61"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFFB270"));
                    eulji.setFillColor(Color.parseColor("#7FFF0E09"));
                    kwanghee.setFillColor(Color.parseColor("#7FFF0E09"));
                    pilldong.setFillColor(Color.parseColor("#7FFFB270"));
                    jangchung.setFillColor(Color.parseColor("#7FFFB270"));
                    dasan.setFillColor(Color.parseColor("#7FFFDC8A"));
                    sindang.setFillColor(Color.parseColor("#7FFF0E09"));
                    sindang5.setFillColor(Color.parseColor("#7FFF8A56"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFDC8A"));
                    donghwa.setFillColor(Color.parseColor("#7FFFB270"));
                    chunggu.setFillColor(Color.parseColor("#7FFFDC8A"));
                    yaksu.setFillColor(Color.parseColor("#7FFF975F"));

                }
                else if(spinner.getItemAtPosition(i).equals("PM 12:00 ~ PM 16:00")){
                    initMap(loc4list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc4list,"loc4");
                    addPolyLine(loc4list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFF975F"));
                    junglim.setFillColor(Color.parseColor("#7FFF9B61"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFFB270"));
                    eulji.setFillColor(Color.parseColor("#7FFF0E09"));
                    kwanghee.setFillColor(Color.parseColor("#7FFF0E09"));
                    pilldong.setFillColor(Color.parseColor("#7FFFB270"));
                    jangchung.setFillColor(Color.parseColor("#7FFFDC8A"));
                    dasan.setFillColor(Color.parseColor("#7FFFDC8A"));
                    sindang.setFillColor(Color.parseColor("#7FFF0E09"));
                    sindang5.setFillColor(Color.parseColor("#7FFF8A56"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFDC8A"));
                    donghwa.setFillColor(Color.parseColor("#7FFFB270"));
                    chunggu.setFillColor(Color.parseColor("#7FFFDC8A"));
                    yaksu.setFillColor(Color.parseColor("#7FFF975F"));

                }
                else if(spinner.getItemAtPosition(i).equals("PM 16:00 ~ PM 20:00")){
                    initMap(loc5list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc5list,"loc5");
                    addPolyLine(loc5list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFF975F"));
                    junglim.setFillColor(Color.parseColor("#7FFF9B61"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFFB270"));
                    eulji.setFillColor(Color.parseColor("#7FFF0E09"));
                    kwanghee.setFillColor(Color.parseColor("#7FFF0E09"));
                    pilldong.setFillColor(Color.parseColor("#7FFFB270"));
                    jangchung.setFillColor(Color.parseColor("#7FFFB270"));
                    dasan.setFillColor(Color.parseColor("#7FFFDC8A"));
                    sindang.setFillColor(Color.parseColor("#7FFF0E09"));
                    sindang5.setFillColor(Color.parseColor("#7FFF8A56"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFDC8A"));
                    donghwa.setFillColor(Color.parseColor("#7FFFB270"));
                    chunggu.setFillColor(Color.parseColor("#7FFFDC8A"));
                    yaksu.setFillColor(Color.parseColor("#7FFF975F"));
                }
                else if(spinner.getItemAtPosition(i).equals("PM 20:00 ~ PM 24:00")){
                    initMap(loc6list);
                    LatLng position = new LatLng(37.566884,126.995696);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,13));
                    pathRecord(loc6list,"loc6");
                    addPolyLine(loc6list);
                    addPolygon();
                    haehyun.setFillColor(Color.parseColor("#7FFF975F"));
                    junglim.setFillColor(Color.parseColor("#7FFF9B61"));
                    sogong.setFillColor(Color.parseColor("#7FFFDC8A"));
                    myungdong.setFillColor(Color.parseColor("#7FFFB270"));
                    eulji.setFillColor(Color.parseColor("#7FFF0E09"));
                    kwanghee.setFillColor(Color.parseColor("#7FFF0E09"));
                    pilldong.setFillColor(Color.parseColor("#7FFFB270"));
                    jangchung.setFillColor(Color.parseColor("#7FFFB270"));
                    dasan.setFillColor(Color.parseColor("#7FFFDC8A"));
                    sindang.setFillColor(Color.parseColor("#7FFF0E09"));
                    sindang5.setFillColor(Color.parseColor("#7FFF8A56"));
                    hwanghak.setFillColor(Color.parseColor("#7FFFDC8A"));
                    donghwa.setFillColor(Color.parseColor("#7FFFB270"));
                    chunggu.setFillColor(Color.parseColor("#7FFFDC8A"));
                    yaksu.setFillColor(Color.parseColor("#7FFF975F"));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
    public void addPolyLine(ArrayList<LatLng> loclist){
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.parseColor("#0054FF"));
        polylineOptions.width(4);
        polylineOptions.addAll(loclist);
        map.addPolyline(polylineOptions);
    }
    public void addCircle(int[][] circle){
        for(int i=0; i<circle.length; i++){
            int x = circle[i][0];
            int y = circle[i][1];
            Cursor c = db.rawQuery("select * from location where x=? and y=?",new String[]{String.valueOf(x),String.valueOf(y)});

            Log.d("커서개수",String.valueOf(c.getCount()));
            while(c.moveToNext()){
                String resultx =c.getString(c.getColumnIndex("x"));
               String resulty=c.getString(c.getColumnIndex("y"));
                String resultlat =c.getString(c.getColumnIndex("lat"));
                String resultlon=c.getString(c.getColumnIndex("lon"));
                map.addCircle(new CircleOptions()
                        .center(new  LatLng(Double.parseDouble(resultlon),Double.parseDouble(resultlat)))
                        .radius(100)
                        .strokeWidth(0f)
                        .fillColor(Color.parseColor("#4CFF0000"))
                );


            }
        }
    }
    public void initMap(ArrayList<LatLng> loclist){
        map.clear();
        loclist.clear();
    }
    public void pathRecord(ArrayList<LatLng> loclist,String loc){
        Cursor c = db.rawQuery("select * from "+loc,null);
        for(int i=1; i<c.getCount(); i++){
            c.moveToNext();
            if(i==2){
                Double startlat = Double.valueOf(c.getString(4));
                Double startlon = Double.valueOf(c.getString(3));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(startlat,startlon));
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.red_b);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 70, 70, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                map.addMarker(markerOptions);
            }
            Double lat = Double.valueOf(c.getString(4));
            Double lon = Double.valueOf(c.getString(3));

            loclist.add(new LatLng(lat,lon));


        }

    }
    public void addPolygon(){
        haehyun = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.5542383883403, 126.98536412773345),
                        new LatLng(37.553744426992886, 126.98542709369815),
                        new LatLng(37.55330443919126, 126.98487525605842),
                        new LatLng(37.55309310303041, 126.98446914066939),
                        new LatLng(37.553067703203126, 126.98414724485824),
                        new LatLng(37.55303585175674, 126.98374355150712),
                        new LatLng(37.55340364998983, 126.9830674398687),
                        new LatLng(37.55359516605728, 126.9825657931835),
                        new LatLng(37.55392390264949, 126.98148019856227),
                        new LatLng(37.55392559223424, 126.98083910613144),
                        new LatLng(37.553415266091974, 126.97982937775562),
                        new LatLng(37.55427319275102, 126.97841458160923),
                        new LatLng(37.55498193760583, 126.97715435141639),
                        new LatLng(37.555016406261984, 126.97705080029189),
                        new LatLng(37.55504659887737, 126.97695661934674),
                        new LatLng(37.55505641403961, 126.97692598220111),
                        new LatLng(37.55502626831344, 126.97673372437902),
                        new LatLng(37.55496270544252, 126.97662802818523),
                        new LatLng(37.55486322576352, 126.97657849539856),
                        new LatLng(37.554614029850704, 126.97656322762094),
                        new LatLng(37.554468891442106, 126.97655435903368),
                        new LatLng(37.553242861939424, 126.97649505680934),
                        new LatLng(37.55313461051957, 126.97649010238855),
                        new LatLng(37.55270834462548, 126.97665865287809),
                        new LatLng(37.5543911047289, 126.97236231894502),
                        new LatLng(37.553896930097174, 126.96907271591886),
                        new LatLng(37.555383763681306, 126.96848897086406),
                        new LatLng(37.55802050617245, 126.96930300710652),
                        new LatLng(37.559314003890286, 126.97057199866777),
                        new LatLng(37.559602732847, 126.97139923762587),
                        new LatLng(37.55963220503933, 126.9719362103378),
                        new LatLng(37.55963626305379, 126.97200617661218),
                        new LatLng(37.55971525405376, 126.9728718496518),
                        new LatLng(37.55982652702355, 126.97407477304803),
                        new LatLng(37.561425917917, 126.98030881374004),
                        new LatLng(37.561516839162884, 126.98050124037191),
                        new LatLng(37.56180104199569, 126.98108737734889),
                        new LatLng(37.5612630369372, 126.98126138975584),
                        new LatLng(37.560092256039724, 126.98222699652324),
                        new LatLng(37.559984613479344, 126.98227674037922),
                        new LatLng(37.559813551644105, 126.98235578821416),
                        new LatLng(37.55944816927764, 126.98251981691409),
                        new LatLng(37.55920313059683, 126.98262923210093),
                        new LatLng(37.55896119397855, 126.98273673542943),
                        new LatLng(37.558668785472044, 126.98285764029531),
                        new LatLng(37.55815594358479, 126.98306922929139),
                        new LatLng(37.557954628389226, 126.98315200348596),
                        new LatLng(37.557657545539676, 126.98323472531855),
                        new LatLng(37.557571969588594, 126.98324663837906),
                        new LatLng(37.55754472017957, 126.98325038055626),
                        new LatLng(37.55751229200868, 126.98325482875988),
                        new LatLng(37.55729494431392, 126.98327827179193),
                        new LatLng(37.55605047903621, 126.98337105991433),
                        new LatLng(37.55574737306877, 126.98339155493973),
                        new LatLng(37.555105308487796, 126.98342500487225),
                        new LatLng(37.554700930196944, 126.983448311028),
                        new LatLng(37.554199016031916, 126.98358268660122),
                        new LatLng(37.553960165346915, 126.98384189938538),
                        new LatLng(37.554174341870684, 126.98501388984263),
                        new LatLng(37.5542383883403, 126.98536412773345)
                ));
        junglim= map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.559602732847, 126.97139923762587),
                        new LatLng(37.559314003890286, 126.97057199866777),
                        new LatLng(37.55802050617245, 126.96930300710652),
                        new LatLng(37.555383763681306, 126.96848897086406),
                        new LatLng(37.55411165026642, 126.96572001807336),
                        new LatLng(37.55374763829929, 126.96520182876334),
                        new LatLng(37.55349478944745, 126.9648491386671),
                        new LatLng(37.551503993982486, 126.96238687439413),
                        new LatLng(37.55192200380381, 126.96213622159183),
                        new LatLng(37.552079431603445, 126.96206857575635),
                        new LatLng(37.55296638811462, 126.961707249935),
                        new LatLng(37.55334171879928, 126.96166184027142),
                        new LatLng(37.55534292838134, 126.96170270446936),
                        new LatLng(37.55540660812221, 126.96183803921899),
                        new LatLng(37.55535206667223, 126.96198419860437),
                        new LatLng(37.55533783023146, 126.96202087010798),
                        new LatLng(37.55532645358582, 126.96205018456746),
                        new LatLng(37.55622661812477, 126.96292711127765),
                        new LatLng(37.55642298000916, 126.96309806743865),
                        new LatLng(37.55715025898734, 126.96321591360271),
                        new LatLng(37.558182581821896, 126.96303662082667),
                        new LatLng(37.55885377187588, 126.96148348639608),
                        new LatLng(37.5592609165609, 126.96206790104068),
                        new LatLng(37.55930961772834, 126.96360950482229),
                        new LatLng(37.559322233592916, 126.96365824755723),
                        new LatLng(37.55933653155207, 126.96371040778432),
                        new LatLng(37.55934972955886, 126.96375853610846),
                        new LatLng(37.55940884226382, 126.96387416639526),
                        new LatLng(37.559567417363624, 126.96418215321245),
                        new LatLng(37.55965012922717, 126.96432828751375),
                        new LatLng(37.5601710326246, 126.96523801390319),
                        new LatLng(37.56038383445967, 126.96553859719009),
                        new LatLng(37.5608457063168, 126.96618746790537),
                        new LatLng(37.56108049991978, 126.96653501986285),
                        new LatLng(37.561132677408, 126.96661249068693),
                        new LatLng(37.56130638041186, 126.96690143193265),
                        new LatLng(37.561495553407404, 126.9673819387459),
                        new LatLng(37.561643341971156, 126.96792228036736),
                        new LatLng(37.561678988700606, 126.96808195972467),
                        new LatLng(37.56186068414217, 126.96889944696413),
                        new LatLng(37.56201362245151, 126.96959246175626),
                        new LatLng(37.559602732847, 126.97139923762587)
                ));
        sogong = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56180104199569, 126.98108737734889),
                        new LatLng(37.561516839162884, 126.98050124037191),
                        new LatLng(37.561425917917, 126.98030881374004),
                        new LatLng(37.55982652702355, 126.97407477304803),
                        new LatLng(37.55971525405376, 126.9728718496518),
                        new LatLng(37.55963626305379, 126.97200617661218),
                        new LatLng(37.55963220503933, 126.9719362103378),
                        new LatLng(37.559602732847, 126.97139923762587),
                        new LatLng(37.56201362245151, 126.96959246175626),
                        new LatLng(37.56579436403002, 126.96677353826367),
                        new LatLng(37.566255827643815, 126.96718646998274),
                        new LatLng(37.56630119278387, 126.96713423029),
                        new LatLng(37.56636085699477, 126.96719305150863),
                        new LatLng(37.56646554636407, 126.96729746629627),
                        new LatLng(37.56650376640693, 126.96733567672948),
                        new LatLng(37.567096953691, 126.96792521742005),
                        new LatLng(37.5671825142215, 126.96801027119919),
                        new LatLng(37.56819441770833, 126.96904837001854),
                        new LatLng(37.56926180051753, 126.97433193562325),
                        new LatLng(37.56931556702156, 126.97537470991254),
                        new LatLng(37.56873421770358, 126.9752753631349),
                        new LatLng(37.56865839132112, 126.97529367787995),
                        new LatLng(37.56775642220665, 126.97563614739646),
                        new LatLng(37.565439850969575, 126.97835066896093),
                        new LatLng(37.56588370468476, 126.97971711380663),
                        new LatLng(37.56595007074264, 126.98059360360978),
                        new LatLng(37.565949285303894, 126.98264628375576),
                        new LatLng(37.56589959842637, 126.98264461070681),
                        new LatLng(37.56557663390231, 126.98263367648507),
                        new LatLng(37.56406882807667, 126.98240527395096),
                        new LatLng(37.56367472366295, 126.98227510611267),
                        new LatLng(37.56295736377829, 126.98203817295538),
                        new LatLng(37.562163040856774, 126.98140894887835),
                        new LatLng(37.56180104199569, 126.98108737734889)
                ));
        myungdong = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.569194530054546, 126.9770344988775),
                        new LatLng(37.56924090399486, 126.97758408247257),
                        new LatLng(37.56924975808179, 126.97780271779051),
                        new LatLng(37.56889046468609, 126.98269213866654),
                        new LatLng(37.56888500509863, 126.98273180965134),
                        new LatLng(37.56868762394625, 126.98397029538161),
                        new LatLng(37.568615237858786, 126.98442232884796),
                        new LatLng(37.568376393740806, 126.98571700615338),
                        new LatLng(37.56821348455564, 126.98659924319325),
                        new LatLng(37.56820459472246, 126.98678729990505),
                        new LatLng(37.5682011032103, 126.98686118820655),
                        new LatLng(37.5681085386568, 126.9888983161631),
                        new LatLng(37.56814951948801, 126.98951624173209),
                        new LatLng(37.56822401178576, 126.99059203232972),
                        new LatLng(37.568243242488776, 126.99086878245063),
                        new LatLng(37.56682682674005, 126.99082883318091),
                        new LatLng(37.56618248434521, 126.99059547464512),
                        new LatLng(37.56613381211586, 126.98976070787944),
                        new LatLng(37.56524875593656, 126.98859336353007),
                        new LatLng(37.564736934192865, 126.98850770623399),
                        new LatLng(37.56458032970966, 126.9885087769927),
                        new LatLng(37.56358464298665, 126.98857856305997),
                        new LatLng(37.56290728134311, 126.98876050863389),
                        new LatLng(37.56286628177801, 126.98941431771651),
                        new LatLng(37.56288048898898, 126.98988622037419),
                        new LatLng(37.56295551238243, 126.99031773914612),
                        new LatLng(37.562893220928494, 126.99032541582211),
                        new LatLng(37.56278211837493, 126.9903390524704),
                        new LatLng(37.561624547044964, 126.99048001924683),
                        new LatLng(37.56150819211315, 126.99049048120555),
                        new LatLng(37.56119884635328, 126.9905181656508),
                        new LatLng(37.5603892181446, 126.98854450653435),
                        new LatLng(37.558385473782245, 126.9873813993399),
                        new LatLng(37.55745389208485, 126.98691508344605),
                        new LatLng(37.55732490462495, 126.98676888338825),
                        new LatLng(37.556761857724574, 126.98582159983376),
                        new LatLng(37.556641820599516, 126.9854233267172),
                        new LatLng(37.55657726894294, 126.98508907210125),
                        new LatLng(37.556299103227495, 126.98485958967991),
                        new LatLng(37.55616040222243, 126.98476422948958),
                        new LatLng(37.556122811390104, 126.9847383914529),
                        new LatLng(37.55588506294212, 126.98475704516987),
                        new LatLng(37.55579796062881, 126.98478894966813),
                        new LatLng(37.555609564455764, 126.9848579735725),
                        new LatLng(37.5542383883403, 126.98536412773345),
                        new LatLng(37.554174341870684, 126.98501388984263),
                        new LatLng(37.553960165346915, 126.98384189938538),
                        new LatLng(37.554199016031916, 126.98358268660122),
                        new LatLng(37.554700930196944, 126.983448311028),
                        new LatLng(37.555105308487796, 126.98342500487225),
                        new LatLng(37.55574737306877, 126.98339155493973),
                        new LatLng(37.55605047903621, 126.98337105991433),
                        new LatLng(37.55729494431392, 126.98327827179193),
                        new LatLng(37.55751229200868, 126.98325482875988),
                        new LatLng(37.55754472017957, 126.98325038055626),
                        new LatLng(37.557571969588594, 126.98324663837906),
                        new LatLng(37.557657545539676, 126.98323472531855),
                        new LatLng(37.557954628389226, 126.98315200348596),
                        new LatLng(37.55815594358479, 126.98306922929139),
                        new LatLng(37.558668785472044, 126.98285764029531),
                        new LatLng(37.55896119397855, 126.98273673542943),
                        new LatLng(37.55920313059683, 126.98262923210093),
                        new LatLng(37.55944816927764, 126.98251981691409),
                        new LatLng(37.559813551644105, 126.98235578821416),
                        new LatLng(37.559984613479344, 126.98227674037922),
                        new LatLng(37.560092256039724, 126.98222699652324),
                        new LatLng(37.5612630369372, 126.98126138975584),
                        new LatLng(37.56180104199569, 126.98108737734889),
                        new LatLng(37.562163040856774, 126.98140894887835),
                        new LatLng(37.56295736377829, 126.98203817295538),
                        new LatLng(37.56367472366295, 126.98227510611267),
                        new LatLng(37.56406882807667, 126.98240527395096),
                        new LatLng(37.56557663390231, 126.98263367648507),
                        new LatLng(37.56589959842637, 126.98264461070681),
                        new LatLng(37.565949285303894, 126.98264628375576),
                        new LatLng(37.56595007074264, 126.98059360360978),
                        new LatLng(37.56588370468476, 126.97971711380663),
                        new LatLng(37.565439850969575, 126.97835066896093),
                        new LatLng(37.56775642220665, 126.97563614739646),
                        new LatLng(37.56865839132112, 126.97529367787995),
                        new LatLng(37.56873421770358, 126.9752753631349),
                        new LatLng(37.56931556702156, 126.97537470991254),
                        new LatLng(37.569336299425764, 126.97597472821249),
                        new LatLng(37.569194530054546, 126.9770344988775)
                ));
        eulji = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56300289844828, 126.9934719028237),
                        new LatLng(37.56329950229422, 126.99142895550477),
                        new LatLng(37.56295551238243, 126.99031773914612),
                        new LatLng(37.56288048898898, 126.98988622037419),
                        new LatLng(37.56286628177801, 126.98941431771651),
                        new LatLng(37.56290728134311, 126.98876050863389),
                        new LatLng(37.56358464298665, 126.98857856305997),
                        new LatLng(37.56458032970966, 126.9885087769927),
                        new LatLng(37.564736934192865, 126.98850770623399),
                        new LatLng(37.56524875593656, 126.98859336353007),
                        new LatLng(37.56613381211586, 126.98976070787944),
                        new LatLng(37.56618248434521, 126.99059547464512),
                        new LatLng(37.56682682674005, 126.99082883318091),
                        new LatLng(37.568243242488776, 126.99086878245063),
                        new LatLng(37.56870989125176, 126.99669662654541),
                        new LatLng(37.568723892004755, 126.99679666835213),
                        new LatLng(37.56874939188403, 126.9969766239248),
                        new LatLng(37.568886373781694, 126.99792406974649),
                        new LatLng(37.56894091593279, 126.99829738997224),
                        new LatLng(37.568950882872976, 126.9983491068369),
                        new LatLng(37.56959157448218, 127.00162224915923),
                        new LatLng(37.5696477611322, 127.00668360999161),
                        new LatLng(37.56931479456103, 127.00546429624715),
                        new LatLng(37.5688204361892, 127.00498771966011),
                        new LatLng(37.56676190799077, 127.00416337977543),
                        new LatLng(37.56654617863747, 127.00403744715268),
                        new LatLng(37.56615997655574, 127.00380368434406),
                        new LatLng(37.565815345249824, 127.00359290389557),
                        new LatLng(37.56565282506402, 127.00347995675092),
                        new LatLng(37.565288776839914, 127.00322688446649),
                        new LatLng(37.565487258617985, 127.00274376676911),
                        new LatLng(37.565882435428676, 127.00152798519167),
                        new LatLng(37.56580508171158, 126.99982265825001),
                        new LatLng(37.56566281566984, 126.9967654471395),
                        new LatLng(37.56547059864012, 126.99589032493469),
                        new LatLng(37.56508622215564, 126.9950543794229),
                        new LatLng(37.56300289844828, 126.9934719028237)
                ));
            kwanghee= map.addPolygon(new PolygonOptions()
                .clickable(true)
                    .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.563025744036686, 127.00956791097168),
                        new LatLng(37.563573028527365, 127.00669918316335),
                        new LatLng(37.56286634360593, 127.00616861043763),
                        new LatLng(37.562499596636776, 127.00522416022022),
                        new LatLng(37.56244273931566, 127.00348931510348),
                        new LatLng(37.56242709644079, 127.00298484051768),
                        new LatLng(37.56242679419468, 127.00264531107821),
                        new LatLng(37.56288031946599, 127.00117312430798),
                        new LatLng(37.56255692535536, 126.99986811657921),
                        new LatLng(37.5615179962955, 126.99531694405356),
                        new LatLng(37.561427445550734, 126.99475438636667),
                        new LatLng(37.56148068747776, 126.99449416943311),
                        new LatLng(37.56152715749887, 126.99429572118171),
                        new LatLng(37.56171594961831, 126.99372141916405),
                        new LatLng(37.56176705699058, 126.99367854976796),
                        new LatLng(37.56185840513056, 126.99363780497889),
                        new LatLng(37.56260925854692, 126.9933635962742),
                        new LatLng(37.562705153711754, 126.99338687771632),
                        new LatLng(37.56300289844828, 126.9934719028237),
                        new LatLng(37.56508622215564, 126.9950543794229),
                        new LatLng(37.56547059864012, 126.99589032493469),
                        new LatLng(37.56566281566984, 126.9967654471395),
                        new LatLng(37.56580508171158, 126.99982265825001),
                        new LatLng(37.565882435428676, 127.00152798519167),
                        new LatLng(37.565487258617985, 127.00274376676911),
                        new LatLng(37.565288776839914, 127.00322688446649),
                        new LatLng(37.56565282506402, 127.00347995675092),
                        new LatLng(37.565815345249824, 127.00359290389557),
                        new LatLng(37.56615997655574, 127.00380368434406),
                        new LatLng(37.56654617863747, 127.00403744715268),
                        new LatLng(37.56676190799077, 127.00416337977543),
                        new LatLng(37.5688204361892, 127.00498771966011),
                        new LatLng(37.56931479456103, 127.00546429624715),
                        new LatLng(37.5696477611322, 127.00668360999161),
                        new LatLng(37.569783784930834, 127.00958523216921),
                        new LatLng(37.5693154104235, 127.0099350962637),
                        new LatLng(37.567889550805724, 127.01169252633863),
                        new LatLng(37.56781435763957, 127.01176378310318),
                        new LatLng(37.56646794444975, 127.01191627446063),
                        new LatLng(37.566237206462205, 127.01190316941147),
                        new LatLng(37.56608894060311, 127.01188628328316),
                        new LatLng(37.56586691109503, 127.01109001671026),
                        new LatLng(37.565765343272865, 127.01050611418508),
                        new LatLng(37.56426467231144, 127.00998092309547),
                        new LatLng(37.563025744036686, 127.00956791097168)
                ));
        pilldong = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56300289844828, 126.9934719028237),
                        new LatLng(37.562705153711754, 126.99338687771632),
                        new LatLng(37.56260925854692, 126.9933635962742),
                        new LatLng(37.56185840513056, 126.99363780497889),
                        new LatLng(37.56176705699058, 126.99367854976796),
                        new LatLng(37.56171594961831, 126.99372141916405),
                        new LatLng(37.56152715749887, 126.99429572118171),
                        new LatLng(37.56148068747776, 126.99449416943311),
                        new LatLng(37.561427445550734, 126.99475438636667),
                        new LatLng(37.5615179962955, 126.99531694405356),
                        new LatLng(37.56255692535536, 126.99986811657921),
                        new LatLng(37.56288031946599, 127.00117312430798),
                        new LatLng(37.56216059578659, 127.00110426844469),
                        new LatLng(37.561060787397196, 127.0009954435768),
                        new LatLng(37.55938989570024, 127.00039662958174),
                        new LatLng(37.55847097051198, 127.00263819991783),
                        new LatLng(37.55881781707909, 127.00340014724723),
                        new LatLng(37.55882617098682, 127.00351249925575),
                        new LatLng(37.55814156499214, 127.00387875203081),
                        new LatLng(37.556696597663944, 127.00347861377148),
                        new LatLng(37.55659255851625, 127.00338731343977),
                        new LatLng(37.55647494585358, 127.00328336630908),
                        new LatLng(37.55629726009495, 127.00309420226425),
                        new LatLng(37.556001611363264, 127.00271829911986),
                        new LatLng(37.55584976706966, 127.00251197177242),
                        new LatLng(37.55547208046729, 127.0013851373215),
                        new LatLng(37.555417868402806, 127.00108760068028),
                        new LatLng(37.555243229505045, 127.00028775314159),
                        new LatLng(37.55425575106481, 126.99857487073199),
                        new LatLng(37.55403106653451, 126.9984921620385),
                        new LatLng(37.5532341233711, 126.99837511230506),
                        new LatLng(37.551276393026946, 126.99740399808546),
                        new LatLng(37.55183536255859, 126.99639478561177),
                        new LatLng(37.55226600236355, 126.99513309858212),
                        new LatLng(37.552282202781555, 126.99444310384315),
                        new LatLng(37.55222909020464, 126.99437877291827),
                        new LatLng(37.5520806067647, 126.99419893054183),
                        new LatLng(37.55068560141915, 126.99258450343652),
                        new LatLng(37.550093126422475, 126.99235997402253),
                        new LatLng(37.551491980275394, 126.98781141729891),
                        new LatLng(37.553744426992886, 126.98542709369815),
                        new LatLng(37.5542383883403, 126.98536412773345),
                        new LatLng(37.555609564455764, 126.9848579735725),
                        new LatLng(37.55579796062881, 126.98478894966813),
                        new LatLng(37.55588506294212, 126.98475704516987),
                        new LatLng(37.556122811390104, 126.9847383914529),
                        new LatLng(37.55616040222243, 126.98476422948958),
                        new LatLng(37.556299103227495, 126.98485958967991),
                        new LatLng(37.55657726894294, 126.98508907210125),
                        new LatLng(37.556641820599516, 126.9854233267172),
                        new LatLng(37.556761857724574, 126.98582159983376),
                        new LatLng(37.55732490462495, 126.98676888338825),
                        new LatLng(37.55745389208485, 126.98691508344605),
                        new LatLng(37.558385473782245, 126.9873813993399),
                        new LatLng(37.5603892181446, 126.98854450653435),
                        new LatLng(37.56119884635328, 126.9905181656508),
                        new LatLng(37.56150819211315, 126.99049048120555),
                        new LatLng(37.561624547044964, 126.99048001924683),
                        new LatLng(37.56278211837493, 126.9903390524704),
                        new LatLng(37.562893220928494, 126.99032541582211),
                        new LatLng(37.56295551238243, 126.99031773914612),
                        new LatLng(37.56329950229422, 126.99142895550477),
                        new LatLng(37.56300289844828, 126.9934719028237)
                ));
        jangchung = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.563025744036686, 127.00956791097168),
                        new LatLng(37.56204704626533, 127.00936420456073),
                        new LatLng(37.5604897693074, 127.00985059656311),
                        new LatLng(37.560429824218815, 127.0098534236055),
                        new LatLng(37.560328649171836, 127.00985809456456),
                        new LatLng(37.556278684936984, 127.0072650223244),
                        new LatLng(37.555611500279724, 127.00645005813372),
                        new LatLng(37.554574492598526, 127.00522561536299),
                        new LatLng(37.554506541586505, 127.005150752117),
                        new LatLng(37.554425080082765, 127.00506102478728),
                        new LatLng(37.554282460788315, 127.0049041678486),
                        new LatLng(37.55393498359103, 127.00452649356842),
                        new LatLng(37.551909426157316, 127.00377572107043),
                        new LatLng(37.55158057411367, 127.00382400007159),
                        new LatLng(37.55135431266967, 127.00385723432821),
                        new LatLng(37.550375639444646, 127.00422527899485),
                        new LatLng(37.55019622503125, 127.0038924990723),
                        new LatLng(37.549600758528975, 127.0026844109379),
                        new LatLng(37.5495082452712, 127.00025315880767),
                        new LatLng(37.54951469894092, 127.0002169569067),
                        new LatLng(37.54956217236035, 126.99995067889738),
                        new LatLng(37.54959449776895, 126.99986838665242),
                        new LatLng(37.5496188183286, 126.99980718201049),
                        new LatLng(37.54979177549393, 126.99936688633525),
                        new LatLng(37.54989093079555, 126.99910431269892),
                        new LatLng(37.54990824810945, 126.99885634010798),
                        new LatLng(37.549931299316114, 126.99852177718344),
                        new LatLng(37.54935788556125, 126.99774306652638),
                        new LatLng(37.547237269992294, 126.99532055586175),
                        new LatLng(37.54905562616137, 126.99319410795592),
                        new LatLng(37.550093126422475, 126.99235997402253),
                        new LatLng(37.55068560141915, 126.99258450343652),
                        new LatLng(37.5520806067647, 126.99419893054183),
                        new LatLng(37.55222909020464, 126.99437877291827),
                        new LatLng(37.552282202781555, 126.99444310384315),
                        new LatLng(37.55226600236355, 126.99513309858212),
                        new LatLng(37.55183536255859, 126.99639478561177),
                        new LatLng(37.551276393026946, 126.99740399808546),
                        new LatLng(37.5532341233711, 126.99837511230506),
                        new LatLng(37.55403106653451, 126.9984921620385),
                        new LatLng(37.55425575106481, 126.99857487073199),
                        new LatLng(37.555243229505045, 127.00028775314159),
                        new LatLng(37.555417868402806, 127.00108760068028),
                        new LatLng(37.55547208046729, 127.0013851373215),
                        new LatLng(37.55584976706966, 127.00251197177242),
                        new LatLng(37.556001611363264, 127.00271829911986),
                        new LatLng(37.55629726009495, 127.00309420226425),
                        new LatLng(37.55647494585358, 127.00328336630908),
                        new LatLng(37.55659255851625, 127.00338731343977),
                        new LatLng(37.556696597663944, 127.00347861377148),
                        new LatLng(37.55814156499214, 127.00387875203081),
                        new LatLng(37.55882617098682, 127.00351249925575),
                        new LatLng(37.55881781707909, 127.00340014724723),
                        new LatLng(37.55847097051198, 127.00263819991783),
                        new LatLng(37.55938989570024, 127.00039662958174),
                        new LatLng(37.561060787397196, 127.0009954435768),
                        new LatLng(37.56216059578659, 127.00110426844469),
                        new LatLng(37.56288031946599, 127.00117312430798),
                        new LatLng(37.56242679419468, 127.00264531107821),
                        new LatLng(37.56242709644079, 127.00298484051768),
                        new LatLng(37.56244273931566, 127.00348931510348),
                        new LatLng(37.562499596636776, 127.00522416022022),
                        new LatLng(37.56286634360593, 127.00616861043763),
                        new LatLng(37.563573028527365, 127.00669918316335),
                        new LatLng(37.563025744036686, 127.00956791097168)
                ));
        dasan = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56058193139436, 127.0128463498962),
                        new LatLng(37.56022186375173, 127.01364206053337),
                        new LatLng(37.55845892551616, 127.01271929433719),
                        new LatLng(37.55553052715992, 127.01105565047062),
                        new LatLng(37.553403062628384, 127.00959916684275),
                        new LatLng(37.5490873206518, 127.00742088841015),
                        new LatLng(37.54892536484815, 127.0074011692726),
                        new LatLng(37.54868749848173, 127.00736877246842),
                        new LatLng(37.54829123780617, 127.00717892894652),
                        new LatLng(37.54813292100664, 127.0070398782396),
                        new LatLng(37.548080599920304, 127.0069799856753),
                        new LatLng(37.54804688147449, 127.00694138929813),
                        new LatLng(37.547973292112786, 127.00685625613949),
                        new LatLng(37.54786962448767, 127.00658382940667),
                        new LatLng(37.547711371601146, 127.0059212032467),
                        new LatLng(37.5477025315019, 127.00569343484842),
                        new LatLng(37.547712171130996, 127.00530573624769),
                        new LatLng(37.54773809546151, 127.00530759696628),
                        new LatLng(37.54777022103047, 127.0053100902793),
                        new LatLng(37.54781352249104, 127.00531345500667),
                        new LatLng(37.54820461733567, 127.0058944050891),
                        new LatLng(37.548225664298364, 127.00578358969784),
                        new LatLng(37.5482345082359, 127.00573661847369),
                        new LatLng(37.5482674903744, 127.00554111914713),
                        new LatLng(37.54828528681733, 127.00542150057807),
                        new LatLng(37.54830036548167, 127.00532014884055),
                        new LatLng(37.54834740546745, 127.00471559662431),
                        new LatLng(37.54856352705784, 127.00463078953523),
                        new LatLng(37.548972814410675, 127.00447019000937),
                        new LatLng(37.5491865376636, 127.0043874011466),
                        new LatLng(37.549562814274346, 127.00437854733603),
                        new LatLng(37.5496644238298, 127.00440224837436),
                        new LatLng(37.550375639444646, 127.00422527899485),
                        new LatLng(37.55135431266967, 127.00385723432821),
                        new LatLng(37.55158057411367, 127.00382400007159),
                        new LatLng(37.551909426157316, 127.00377572107043),
                        new LatLng(37.55393498359103, 127.00452649356842),
                        new LatLng(37.554282460788315, 127.0049041678486),
                        new LatLng(37.554425080082765, 127.00506102478728),
                        new LatLng(37.554506541586505, 127.005150752117),
                        new LatLng(37.554574492598526, 127.00522561536299),
                        new LatLng(37.555611500279724, 127.00645005813372),
                        new LatLng(37.556278684936984, 127.0072650223244),
                        new LatLng(37.560328649171836, 127.00985809456456),
                        new LatLng(37.560429824218815, 127.0098534236055),
                        new LatLng(37.5604897693074, 127.00985059656311),
                        new LatLng(37.56204704626533, 127.00936420456073),
                        new LatLng(37.56198606213606, 127.0096986028635),
                        new LatLng(37.56058193139436, 127.0128463498962)
                ));
        sindang = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56556664980869, 127.0180743572028),
                        new LatLng(37.564122672606004, 127.01599071844261),
                        new LatLng(37.56108043774584, 127.01426196990714),
                        new LatLng(37.56058193139436, 127.0128463498962),
                        new LatLng(37.56198606213606, 127.0096986028635),
                        new LatLng(37.56204704626533, 127.00936420456073),
                        new LatLng(37.563025744036686, 127.00956791097168),
                        new LatLng(37.56426467231144, 127.00998092309547),
                        new LatLng(37.565765343272865, 127.01050611418508),
                        new LatLng(37.56586691109503, 127.01109001671026),
                        new LatLng(37.56608894060311, 127.01188628328316),
                        new LatLng(37.566237206462205, 127.01190316941147),
                        new LatLng(37.56646794444975, 127.01191627446063),
                        new LatLng(37.56781435763957, 127.01176378310318),
                        new LatLng(37.567889550805724, 127.01169252633863),
                        new LatLng(37.5693154104235, 127.0099350962637),
                        new LatLng(37.569783784930834, 127.00958523216921),
                        new LatLng(37.569782558827946, 127.01010401194387),
                        new LatLng(37.56978182425201, 127.01128595164396),
                        new LatLng(37.56978206242719, 127.01161575947448),
                        new LatLng(37.56980657136566, 127.01537585828629),
                        new LatLng(37.56980859794676, 127.01566542077587),
                        new LatLng(37.56980928655595, 127.0157629720032),
                        new LatLng(37.56981531307181, 127.0158654089304),
                        new LatLng(37.56982380100295, 127.01600564056696),
                        new LatLng(37.56986280930545, 127.0161766627633),
                        new LatLng(37.56991209062485, 127.0163801750635),
                        new LatLng(37.56998864893561, 127.01664154663442),
                        new LatLng(37.57001950356381, 127.01674098681009),
                        new LatLng(37.57005943724952, 127.01686968618156),
                        new LatLng(37.5701027859073, 127.01700034533405),
                        new LatLng(37.570149540415414, 127.0171373467301),
                        new LatLng(37.5702024312381, 127.0172783400352),
                        new LatLng(37.57029101296999, 127.0175133756155),
                        new LatLng(37.56998577691102, 127.01769908611097),
                        new LatLng(37.56556664980869, 127.0180743572028)
                ));
        sindang5 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56516886904607, 127.02361524786308),
                        new LatLng(37.56498976245136, 127.02670549541054),
                        new LatLng(37.56428904775203, 127.02675680399146),
                        new LatLng(37.563882238318705, 127.02670755412781),
                        new LatLng(37.563584326857736, 127.02659124942552),
                        new LatLng(37.56338741823071, 127.02649911495773),
                        new LatLng(37.56335578267504, 127.02648158342787),
                        new LatLng(37.563207228501604, 127.02639548425658),
                        new LatLng(37.56299376211291, 127.02626520682036),
                        new LatLng(37.56073011014464, 127.0235298861134),
                        new LatLng(37.560717974090274, 127.0234949744223),
                        new LatLng(37.56074436830169, 127.0233348751008),
                        new LatLng(37.560762821652595, 127.02288256251003),
                        new LatLng(37.56083711087956, 127.02230746792854),
                        new LatLng(37.56087994791072, 127.02203475040136),
                        new LatLng(37.56094543779678, 127.02179159825597),
                        new LatLng(37.561120407378006, 127.02131776051307),
                        new LatLng(37.56132059798269, 127.02095132421596),
                        new LatLng(37.561368797592365, 127.02088090230505),
                        new LatLng(37.56151886076881, 127.02066557240893),
                        new LatLng(37.561659008989494, 127.02049624932538),
                        new LatLng(37.56339160479137, 127.01877396172449),
                        new LatLng(37.564122672606004, 127.01599071844261),
                        new LatLng(37.56556664980869, 127.0180743572028),
                        new LatLng(37.565758078970134, 127.02059257547279),
                        new LatLng(37.56516886904607, 127.02361524786308)
                ));
        hwanghak = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56516886904607, 127.02361524786308),
                        new LatLng(37.565758078970134, 127.02059257547279),
                        new LatLng(37.56556664980869, 127.0180743572028),
                        new LatLng(37.56998577691102, 127.01769908611097),
                        new LatLng(37.57029101296999, 127.0175133756155),
                        new LatLng(37.570984519777085, 127.01881853651845),
                        new LatLng(37.5717398548992, 127.02336812399952),
                        new LatLng(37.565804250082635, 127.02359774948243),
                        new LatLng(37.56516886904607, 127.02361524786308)
                ));
        donghwa = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.56074436830169, 127.0233348751008),
                        new LatLng(37.557868260398386, 127.02285714633344),
                        new LatLng(37.557709410220035, 127.02182429257704),
                        new LatLng(37.557499213548525, 127.01937088600177),
                        new LatLng(37.558425538779794, 127.01886392811282),
                        new LatLng(37.559314106365434, 127.016703243689),
                        new LatLng(37.55930542449374, 127.01664333663366),
                        new LatLng(37.559300596295955, 127.01656482472966),
                        new LatLng(37.559296767142655, 127.01645515861598),
                        new LatLng(37.55932289109422, 127.01633482572794),
                        new LatLng(37.55934984658008, 127.01621068307011),
                        new LatLng(37.55945996526325, 127.01585533188783),
                        new LatLng(37.55948327918231, 127.01578314811344),
                        new LatLng(37.559886479192514, 127.01473715296174),
                        new LatLng(37.560281638133475, 127.01393153226267),
                        new LatLng(37.56104947031874, 127.01434900629027),
                        new LatLng(37.56108043774584, 127.01426196990714),
                        new LatLng(37.564122672606004, 127.01599071844261),
                        new LatLng(37.56339160479137, 127.01877396172449),
                        new LatLng(37.561659008989494, 127.02049624932538),
                        new LatLng(37.56151886076881, 127.02066557240893),
                        new LatLng(37.561368797592365, 127.02088090230505),
                        new LatLng(37.56132059798269, 127.02095132421596),
                        new LatLng(37.561120407378006, 127.02131776051307),
                        new LatLng(37.56094543779678, 127.02179159825597),
                        new LatLng(37.56087994791072, 127.02203475040136),
                        new LatLng(37.56083711087956, 127.02230746792854),
                        new LatLng(37.560762821652595, 127.02288256251003),
                        new LatLng(37.56074436830169, 127.0233348751008)

                ));
        chunggu = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.557499213548525, 127.01937088600177),
                        new LatLng(37.5573396016988, 127.01946390283747),
                        new LatLng(37.557134529739834, 127.01890690849194),
                        new LatLng(37.557073125022896, 127.01876860869),
                        new LatLng(37.55699127006635, 127.01858868515346),
                        new LatLng(37.556869805775825, 127.0183658575566),
                        new LatLng(37.556655386184474, 127.01800423348419),
                        new LatLng(37.5566004734223, 127.01792413351535),
                        new LatLng(37.55657037839764, 127.01788023979678),
                        new LatLng(37.555982657918925, 127.01741143566528),
                        new LatLng(37.553011495638145, 127.01630613748895),
                        new LatLng(37.55349815940938, 127.0157404625123),
                        new LatLng(37.554384965451185, 127.01468327029168),
                        new LatLng(37.55443608371393, 127.01449537939992),
                        new LatLng(37.554395835863744, 127.0142975125095),
                        new LatLng(37.55437219348869, 127.0142076932264),
                        new LatLng(37.55434432986901, 127.01410184738945),
                        new LatLng(37.55426451151434, 127.01407025763052),
                        new LatLng(37.55422188806136, 127.01405416905753),
                        new LatLng(37.55402846099434, 127.01400553666362),
                        new LatLng(37.55364806575889, 127.01353750858426),
                        new LatLng(37.55370657717142, 127.01327402802168),
                        new LatLng(37.55378624025451, 127.01291599936245),
                        new LatLng(37.55394579583076, 127.01253781804547),
                        new LatLng(37.55477714915557, 127.01195710983853),
                        new LatLng(37.55479974215443, 127.01197253544065),
                        new LatLng(37.555063025182676, 127.01215762651972),
                        new LatLng(37.55553052715992, 127.01105565047062),
                        new LatLng(37.55845892551616, 127.01271929433719),
                        new LatLng(37.56022186375173, 127.01364206053337),
                        new LatLng(37.56058193139436, 127.0128463498962),
                        new LatLng(37.56108043774584, 127.01426196990714),
                        new LatLng(37.56104947031874, 127.01434900629027),
                        new LatLng(37.560281638133475, 127.01393153226267),
                        new LatLng(37.559886479192514, 127.01473715296174),
                        new LatLng(37.55948327918231, 127.01578314811344),
                        new LatLng(37.55945996526325, 127.01585533188783),
                        new LatLng(37.55934984658008, 127.01621068307011),
                        new LatLng(37.55932289109422, 127.01633482572794),
                        new LatLng(37.559296767142655, 127.01645515861598),
                        new LatLng(37.559300596295955, 127.01656482472966),
                        new LatLng(37.55930542449374, 127.01664333663366),
                        new LatLng(37.559314106365434, 127.016703243689),
                        new LatLng(37.558425538779794, 127.01886392811282),
                        new LatLng(37.557499213548525, 127.01937088600177)
                ));
        yaksu = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .strokeColor(Color.parseColor("#00000000"))
                .add(
                        new LatLng(37.553011495638145, 127.01630613748895),
                        new LatLng(37.54994422861261, 127.01320761955266),
                        new LatLng(37.548511522986345, 127.01165764754505),
                        new LatLng(37.548416146182724, 127.01147996885422),
                        new LatLng(37.54811841641107, 127.01086705353879),
                        new LatLng(37.54809814744943, 127.01081324310977),
                        new LatLng(37.548086518251225, 127.01078235714807),
                        new LatLng(37.54807821534514, 127.01068194070888),
                        new LatLng(37.5439797142403, 127.00860603570271),
                        new LatLng(37.54381073731256, 127.00725813748895),
                        new LatLng(37.5461822628338, 127.00484260490366),
                        new LatLng(37.547712171130996, 127.00530573624769),
                        new LatLng(37.5477025315019, 127.00569343484842),
                        new LatLng(37.547711371601146, 127.0059212032467),
                        new LatLng(37.54786962448767, 127.00658382940667),
                        new LatLng(37.547973292112786, 127.00685625613949),
                        new LatLng(37.54804688147449, 127.00694138929813),
                        new LatLng(37.548080599920304, 127.0069799856753),
                        new LatLng(37.54813292100664, 127.0070398782396),
                        new LatLng(37.54829123780617, 127.00717892894652),
                        new LatLng(37.54868749848173, 127.00736877246842),
                        new LatLng(37.54892536484815, 127.0074011692726),
                        new LatLng(37.5490873206518, 127.00742088841015),
                        new LatLng(37.553403062628384, 127.00959916684275),
                        new LatLng(37.55553052715992, 127.01105565047062),
                        new LatLng(37.555063025182676, 127.01215762651972),
                        new LatLng(37.55479974215443, 127.01197253544065),
                        new LatLng(37.55477714915557, 127.01195710983853),
                        new LatLng(37.55394579583076, 127.01253781804547),
                        new LatLng(37.55378624025451, 127.01291599936245),
                        new LatLng(37.55370657717142, 127.01327402802168),
                        new LatLng(37.55364806575889, 127.01353750858426),
                        new LatLng(37.55402846099434, 127.01400553666362),
                        new LatLng(37.55422188806136, 127.01405416905753),
                        new LatLng(37.55426451151434, 127.01407025763052),
                        new LatLng(37.55434432986901, 127.01410184738945),
                        new LatLng(37.55437219348869, 127.0142076932264),
                        new LatLng(37.554395835863744, 127.0142975125095),
                        new LatLng(37.55443608371393, 127.01449537939992),
                        new LatLng(37.554384965451185, 127.01468327029168),
                        new LatLng(37.55349815940938, 127.0157404625123),
                        new LatLng(37.553011495638145, 127.01630613748895)
                ));

    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }





    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}


