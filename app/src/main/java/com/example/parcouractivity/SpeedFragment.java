package com.example.parcouractivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcouractivity.bean.Parcours;
import com.example.parcouractivity.bean.Parcoursss;
import com.example.parcouractivity.bean.User;
import com.example.parcouractivity.functions.CLocation;

import java.util.Formatter;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeedFragment extends Fragment implements LocationListener {

    SwitchCompat switchMetric;
    TextView txtSpeed,txtLatitude,txtLongitude, txtAltitude ;
    TextView txtDenivelep,txtDenivelem,txtPente, txtDistance;
    boolean isFirst = true;
    Location locationDepart;
    double cumDeniveleP;
    double cumDeniveleN;
    float  Pente;
    User user = new User();
    Parcours parcours = new Parcours();
    public SpeedFragment() {
        // Required empty public constructor
    }
    public static SpeedFragment newInstance() {
        return new SpeedFragment();
    }
    public static SpeedFragment newInstance(Bundle bundle) {
        SpeedFragment fragment = new SpeedFragment();

        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_speed, container, false);
        Bundle bundle = getArguments();
        user.setId(bundle.getString("userid"));
        user.setPassword(bundle.getString("pseudo"));
        user.setNom(bundle.getString("nom"));
        user.setPrenom(bundle.getString("prenom"));
        user.setLienavatar(bundle.getString("lienvavatar"));
        user.setEmail(bundle.getString("email"));
        parcours.setParcourid(bundle.getString("parcoursid"));
        parcours.setNom(bundle.getString("parcoursnom"));
        parcours.setDescription(bundle.getString("parcoursdes"));


        switchMetric = view.findViewById(R.id.switchMetric);
        txtSpeed = view.findViewById(R.id.txtSpeed);
//        txtLatitude = view.findViewById(R.id.txtLatitude);
//        txtLongitude = view.findViewById(R.id.txtLongitude);
        txtAltitude = view.findViewById(R.id.txtAltitude);
        txtDenivelep = view.findViewById(R.id.txtDenivelep);
        txtDenivelem = view.findViewById(R.id.txtDenivelem);
        txtPente = view.findViewById(R.id.txtPente);
        txtDistance = view.findViewById(R.id.txtDistance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{
            //start the program if the permission is granted
            doStuff();
        }
        this.updateSpeed(null);
        this.updateLocation(null);
        switchMetric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSpeed(null);
            }
        });
        return view;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location!=null){
            CLocation myLocation = new CLocation(location,this.useMetricUnits());
            if (isFirst) {
                locationDepart = new Location(location);
            }
            this.updateSpeed(myLocation);
            this.updateLocation(myLocation);

        }
    }
    @SuppressLint("MissingPermission")
    private void doStuff() {

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        }
        Toast.makeText(getActivity(),"Waiting GPS Connection!", Toast.LENGTH_SHORT).show();
    }
    private void updateSpeed(CLocation location){
        float nCurrentSpeed =0;
        if(location!=null){
            location.setUserMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US,"%5.1f",nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed =strCurrentSpeed.replace(" ","0");

        //String strUnits
        if(this.useMetricUnits()){
            txtSpeed.setText(strCurrentSpeed+" km/h");
        }else{
            txtSpeed.setText(strCurrentSpeed+" miles/h");
        }

    }

    private void updateLocation(CLocation location){

        double nLatitude =0,nLongitud =0, nAltitude=0, nDistance=0;
        if(location!=null){
            nLatitude = location.getLatitude();
            nLongitud = location.getLongitude();
            nAltitude = location.getAltitude();
            nDistance = location.distanceTo(locationDepart);

        }

        //       textViewLatitude.setText("Latitude: "+nLatitude);
        //       textViewLongitude.setText("Longitude: "+nLongitud);
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US,"%5.2f",nDistance);

        txtAltitude.setText("Altitude: "+nAltitude);
        txtDistance.setText("Distance: "+ String.valueOf(nDistance) );
    }
    private boolean useMetricUnits(){

        return switchMetric.isChecked();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                this.doStuff();
            }else {
                getActivity().finish();
            }
        }
    }}