package com.example.parcouractivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcouractivity.bean.Parcours;
import com.example.parcouractivity.bean.Parcoursss;
import com.example.parcouractivity.bean.User;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Locale;

public class MapFragment extends Fragment {
    LocationManager locationManager = null;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private MyLocationNewOverlay mLocationOverlay;
    private DirectedLocationOverlay myLocationOverlay;
    private boolean IsFirst = true, IsGo = false, IsEtape = false, IsFin = false, IsDep = false, IsPause = false;
    private Button btnGo, btnEtape, btnTerminer;
    private int iNbEtape = 0;
    private String sAction="";
    private String sEtat="";
    private Geocoder geocoder;
    Location locationDepart;
    private String fournisseur;
    SwitchCompat switchMetric;
    TextView textViewSpeed,textViewLatitude,textViewLongitude, textViewAltitude ;
    TextView textViewDenivele,textViewPente, textViewDistance;
    private ArrayList<GeoPoint> geoTrajet;
    private ArrayList<GeoPoint> geoEtape;
    // comprend le début et la fin
    ArrayList<GeoPoint> trajet;
    TextView txtAddress;
    GeoPoint cunrrentGeoPoint;
    Location cunrrentLoaction;

    private static final String PREFS_NAME = "org.andnav.osm.prefs";
    private static final String PREFS_TILE_SOURCE = "tilesource";
    private static final String PREFS_LATITUDE_STRING = "latitudeString";
    private static final String PREFS_LONGITUDE_STRING = "longitudeString";
    private static final String PREFS_ORIENTATION = "orientation";
    private static final String PREFS_ZOOM_LEVEL_DOUBLE = "zoomLevelDouble";

    private static final int MENU_ABOUT = Menu.FIRST + 1;
    private static final int MENU_LAST_ID = MENU_ABOUT + 1; // Always set to last unused id
    private SharedPreferences mPrefs;
    private MapView mMapView;
    //   private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay = null;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private CopyrightOverlay mCopyrightOverlay;

    User user = new User();
    Parcours parcours = new Parcours();

    public MapFragment() {
        // Required empty public constructor
    }
    public static MapFragment newInstance(Bundle bundle) {
        MapFragment fragment = new MapFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    LocationListener ecouteurGPS = new LocationListener() {

        @RequiresApi(api = Build.VERSION_CODES.R)
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String TAG = "INFOPAUL";
            Log.i(TAG, "onLocationChanged : debut " + IsFirst);

            if(location!=null) {
                if (IsFirst) {
                    locationDepart = new Location(location);
                }
                GeoPoint nouvelleLocalisation = new GeoPoint(location);
                cunrrentGeoPoint = new GeoPoint(nouvelleLocalisation);
                cunrrentLoaction = new Location(location);

                // la sauvegarde des etapes et debut et fin sont sauvegarde dans la methode addTrace
                // si Go on trace la route et on sauvegarde les pointGeos
                // sinon on ne sauvegarde pas et on traçe pas

                if (IsGo) {
                    geoTrajet.add(nouvelleLocalisation);
                    addMTrace("ROUTE");
                }
                // si depart on ajoute le marqueur départ
                if (sAction == "Depart") {
//                    addMarker(nouvelleLocalisation, "DEPART");
//                    Log.i(TAG, "onLocationChanged : depart");
                    sAction = "EnCour";
                }

                // si le parcour est en cour on peut sauvegarder les point d'etape et ajouter le marqueur
                if ((IsGo) && (IsEtape)) {
                    iNbEtape += 1;
                    //                   addMarker(nouvelleLocalisation, "ETAPE" + iNbEtape);
                    IsEtape = false;
                }

                if (IsFin) {
                    geoTrajet.add(nouvelleLocalisation);
                    //                   addMarker(nouvelleLocalisation, "FIN");
                    iNbEtape = 0;
                    IsGo = false;
                }

                if (!mLocationOverlay.isEnabled()) {
                    mLocationOverlay.setEnabled(true);
                    map.getController().animateTo(nouvelleLocalisation);
                }
                //GeoPoint localisationPrecedente = myLocationOverlay.getLocation();


                if (!myLocationOverlay.isEnabled()) {
                    myLocationOverlay.setEnabled(true);
                    map.getController().animateTo(nouvelleLocalisation);
                }
                GeoPoint localisationPrecedente = myLocationOverlay.getLocation();
                myLocationOverlay.setLocation(nouvelleLocalisation);//
                //        mLocationOverlay.setAccuracy((int)location.getAccuracy());

                float mAzimuthAngleSpeed = 0;
                if (localisationPrecedente != null && location.getProvider().equals(myLocationOverlay.getLocation())) {
                    double mSpeed = location.getSpeed() * 3.6;
                    long speedInt = Math.round(mSpeed);

                    if (mSpeed >= 0.1) {
                        mAzimuthAngleSpeed = location.getBearing();
                        myLocationOverlay.setBearing(mAzimuthAngleSpeed);
                    }
                }
                map.getController().animateTo(nouvelleLocalisation);
                map.setMapOrientation(-mAzimuthAngleSpeed);
                map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
                map.getMapCenter();
            }
            //            if (IsFirst) {
//                map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
//                map.getMapCenter();
//            } else {
//                map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
//                map.getMapCenter();
//            }

            map.invalidate();
 /*           List<Address> adresses = null;
            Log.i(TAG,"Latitude " + location.getLatitude() + " longitude " +location.getLongitude());
            try
            {
             //   adresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            }
            catch (IOException ioException)
            {
                Log.e("GPS", "erreur", ioException);
            } catch (IllegalArgumentException illegalArgumentException)
            {
                Log.e("GPS", "erreur " + location.getLatitude() + " " + location.getLongitude(), illegalArgumentException);
            }

            if (adresses == null || adresses.size()  == 0)
            {
                Log.e("GPS", "erreur aucune adresse !");
            }
            else
            {
                Address adresse = adresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                String strAdresse = adresse.getAddressLine(0) + ", " + adresse.getLocality();
                Log.d("GPS", "adresse : " + strAdresse);

                for(int i = 0; i <= adresse.getMaxAddressLineIndex(); i++)
                {
                    addressFragments.add(adresse.getAddressLine(i));
                }
                Log.d("GPS", TextUtils.join(System.getProperty("line.separator"), addressFragments));
                txtAddress.setText(TextUtils.join(System.getProperty("line.separator"), addressFragments));
            }*/
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            Toast.makeText(getContext().getApplicationContext(), provider + " activé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            Toast.makeText(getContext().getApplicationContext(), provider + " désactivé !", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = (MapView) view.findViewById(R.id.mapview);
        btnGo = (Button) view.findViewById(R.id.btnGo);
        btnEtape = (Button) view.findViewById(R.id.btnEtape);
        btnTerminer = (Button) view.findViewById(R.id.btnTerminer);

        //Bundle bundle = new Bundle();
        Bundle bundle = getArguments();
        user.setId(bundle.getString("userid"));
        user.setPseudo(bundle.getString("pseudo"));
        user.setNom(bundle.getString("nom"));
        user.setPrenom(bundle.getString("prenom"));
        user.setLienavatar(bundle.getString("lienvavatar"));
        user.setEmail(bundle.getString("email"));
        parcours.setParcourid(bundle.getString("parcoursid"));
        parcours.setNom(bundle.getString("parcoursnom"));
        parcours.setDescription(bundle.getString("parcoursdes"));

        Log.i("PAULINFO","onCreateView");
        Configuration.getInstance().setUserAgentValue("github-firefishy-map/0.1");

        map.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                if (0 != (event.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_SCROLL:
                            if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f)
                                map.getController().zoomOut();
                            else {
                                //this part just centers the map on the current mouse location before the zoom action occurs
                                IGeoPoint iGeoPoint = map.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                                map.getController().animateTo(iGeoPoint);
                                map.getController().zoomIn();
                            }
                            return true;
                    }
                }
                return false;
            }
        });




/*
        map.setClickable(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom((double) 18);

        GeoPoint startPoint = new GeoPoint(48.8583, 2.2844);
        mapController.setCenter(startPoint);
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(map);
        map.getOverlays().add(myScaleBarOverlay);
        myLocationOverlay = new DirectedLocationOverlay(map.getContext());
        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(map.getContext());
        this.mLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider,map);
        this.mLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.mLocationOverlay);
        initialiserLocalisation();*/





// sAction = "INIT"     l'application ne traça pas et attend
// sAction = "Depart"   démarrage
//                      Positionnement de la borne départ
//                      debut du tarçage
// sAction = "GO"       l'appli sauvegarde le trajet
//         = "Pause"    l'appli stop les traces
//         = "Etape"    l'appli traçe et positionne 1 drapeau étape
//                      Pause (stop) de l'enregistrement
// sAction = "Fin"      fin du traitement
//                      Positionnement de la borne Fin
//                      arret du traçage
//                      envoi de Fragment Validation du parcour




        requestPermissionsIfNecessary(new String[]{
                // if you need to show the current location, uncomment the line below
                Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        IsFirst = true;

        //txtAddress = (TextView) view.findViewById(R.id.txtAdress);
        map.setTileSource(TileSourceFactory.MAPNIK);
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        btnGo.setTag("D");
        geoTrajet = new ArrayList<GeoPoint>();
        geoEtape = new ArrayList<GeoPoint>();
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        //My Location
        //note you have handle the permissions yourself, the overlay did not do it for you
        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), map);

        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(map.getContext());
        this.mLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider,map);
        this.mLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.mLocationOverlay);

        //Mini map
        mMinimapOverlay = new MinimapOverlay(context, map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
        map.getOverlays().add(this.mMinimapOverlay);
        //Copyright overlay
        mCopyrightOverlay = new CopyrightOverlay(context);
        //i hate this very much, but it seems as if certain versions of android and/or
        //device types handle screen offsets differently
        map.getOverlays().add(this.mCopyrightOverlay);
//        geoTrajet.add(new GeoPoint(48.8583,2.2844));
//        geoTrajet.add(new GeoPoint(48.8983,2.2834));
//        geoTrajet.add(new GeoPoint(48.8583,2.2884));
        //On screen compass
        mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);


        //map scale
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);

        map.setClickable(true);

        IMapController mapController = map.getController();
        mapController.setZoom((double) 18);

        GeoPoint startPoint = new GeoPoint(48.8583, 2.2844);
        mapController.setCenter(startPoint);

        //support for map rotation
        mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.getOverlays().add(this.mRotationGestureOverlay);

        myLocationOverlay = new DirectedLocationOverlay(map.getContext());






        //needed for pinch zooms
        map.setMultiTouchControls(true);

        //scales tiles to the current screen's DPI, helps with readability of labels
        map.setTilesScaledToDpi(true);

        //the rest of this is restoring the last map location the user looked at
        final float zoomLevel = mPrefs.getFloat(PREFS_ZOOM_LEVEL_DOUBLE, 1);
        map.getController().setZoom(17.00);
        final float orientation = mPrefs.getFloat(PREFS_ORIENTATION, 0);
        map.setMapOrientation(orientation, false);
        final String latitudeString = mPrefs.getString(PREFS_LATITUDE_STRING, "1.0");
        final String longitudeString = mPrefs.getString(PREFS_LONGITUDE_STRING, "1.0");
        final double latitude = Double.valueOf(latitudeString);
        final double longitude = Double.valueOf(longitudeString);


        map.setExpectedCenter(new GeoPoint(latitude, longitude));
        sAction = "INIT";
        setHasOptionsMenu(true);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// debut la première fois on demarre
                Log.i("PAULINFO","onCreateView btnGo btn "+btnGo.getText());
                if (btnGo.getTag()=="D") {
                    addMTrace("ok");

                    if (cunrrentGeoPoint != null) {
                        Log.i("PAULINFO","onCreateView btnGo Démarrer ");
                        sAction = "Depart";
                        btnGo.setText("Pause");
                        btnGo.setTag("P");
                        btnEtape.setClickable(true);
                        btnTerminer.setClickable(true);

                        addMarker(cunrrentGeoPoint, "DEBUT");
                        Toast.makeText(getContext(),"Marquage du départ", Toast.LENGTH_SHORT);
                        Log.i("INFOPAUL", "onLocationChanged : depart");
                        IsGo = true;
                    }
                    else {
                        Toast.makeText(getContext(),"Gps pas activé", Toast.LENGTH_SHORT);
                    }
                }
                else {
                    if (btnGo.getTag()=="P") {
                        //    IsFirst = false;
                        btnGo.setTag("C");
                        //    IsPause = true;
                        sAction = "Pause";
                        IsGo = false;
                        //    IsDep = false;
                        Log.i("PAULINFO","onCreateView btnGo Pause");
                        Toast.makeText(getContext(),"Pause", Toast.LENGTH_SHORT);
                        btnGo.setText("Continuer");
                    }
                    // pause on arrete pour boire un coup
                    // on continu le parcour
                    else {
                        if (btnGo.getTag() == "C") {
                            Toast.makeText(getContext(),"Reprise du parcour", Toast.LENGTH_SHORT);
                            Log.i("PAULINFO", "onCreateView btnGo CONTINUE");
                            //        IsFirst = false;
                            IsGo = true;
                            btnGo.setTag("P");
                            sAction = "Continue";
                            //        IsDep = false;
                            //        IsPause = false;
                            btnGo.setText("Pause");
                        }
                    }
                }
            }
        });
        btnEtape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PAULINFO","onCreateView btnEtape Etape");
                if (IsGo) {
                    IsEtape = true;
                    addMarker(cunrrentGeoPoint, "ETAPE");
                }
                else IsEtape = false;

            }
        });
        btnTerminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PAULINFO","onCreateView btnTerminer FIN");
                IsFin = true;
                sAction = "Continue";
                addMarker(cunrrentGeoPoint, "FIN");
            }
        });
        initialiserLocalisation();
    }

    // a chaque marque on sauvegarde le point de geolocalisation

    private void addMarker(GeoPoint gpIUT, String stype) {
        Log.i("PAULINFO","addMarker ");
        geoEtape.add(gpIUT);
        Marker mrkIUT = new Marker(map);
        if (stype == "ETAPE") {
            mrkIUT.setIcon(getActivity().getResources().getDrawable(R.drawable.marker_etape));
        }
        else if (stype == "FIN" ) {
            mrkIUT.setIcon(getActivity().getResources().getDrawable(R.drawable.marker_fin));
        }
        else if(stype == "DEBUT")
            mrkIUT.setIcon(getActivity().getResources().getDrawable(R.drawable.marker_debut));

        mrkIUT.setPosition(gpIUT);
        mrkIUT.setSnippet(stype);
        mrkIUT.setAlpha(0.75f);
        mrkIUT.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(mrkIUT);
        map.invalidate();
    }
    public void addMTrace(String stype) {
        if (geoTrajet.size()>0) {
            Log.i("PAULINFO", "addMTrace ");
            Polyline line = new Polyline();
            line.setEnabled(true);
            line.setTitle(stype);
            line.setSubDescription(Polyline.class.getCanonicalName());
            line.setPoints(geoTrajet);
            line.setGeodesic(true);
            line.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, map));
            map.getOverlayManager().add(line);
            map.invalidate();
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        Log.i("PAULINFO","requestPermissionsIfNecessary ");
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void addRoad(GeoPoint geoDebut, GeoPoint geoFin) {

        Polyline line = new Polyline();
        line.setTitle("Parcours");
        line.setSubDescription(Polyline.class.getCanonicalName());
        line.setPoints(trajet);
        line.setGeodesic(true);
        line.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, map));
        map.getOverlayManager().add(line);
    }
    private void initialiserLocalisation() {
        Log.i("PAULINFO", "initialiserLocalisation ");
        if (locationManager == null) {
            locationManager = (LocationManager) getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteres = new Criteria();

            // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
            criteres.setAccuracy(Criteria.ACCURACY_FINE);


            // l'altitude
            criteres.setAltitudeRequired(true);

            // la direction
            criteres.setBearingRequired(true);

            // la vitesse
            criteres.setSpeedRequired(true);

            // la consommation d'énergie demandée
            criteres.setCostAllowed(true);
            //criteres.setPowerRequirement(Criteria.POWER_HIGH);
            criteres.setPowerRequirement(Criteria.POWER_MEDIUM);

            fournisseur = locationManager.getBestProvider(criteres, true);
            Log.d("GPS", "fournisseur : " + fournisseur);
        }

        if (fournisseur != null) {
            // dernière position connue
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("GPS", "no permissions !");
                return;
            }


            Location localisation = locationManager.getLastKnownLocation(fournisseur);
            if (localisation != null) {
                // on notifie la localisation (premiere traitement de la location
                IsFirst = true;
                ecouteurGPS.onLocationChanged(localisation);
                IsFirst = false;
            }
            Toast.makeText(getActivity(),"Waiting GPS Connection!", Toast.LENGTH_SHORT).show();
            // on configure la mise à jour automatique : au moins 10 mètres et 15 secondes
            locationManager.requestLocationUpdates(fournisseur, 1000, 10, ecouteurGPS);
        }
    }
    public void invalidateMapView() {
        map.invalidate();
    }
    public void zoomIn() {
        map.getController().zoomIn();
    }

    public void zoomOut() {
        map.getController().zoomOut();
    }


    private boolean useMetricUnits(){

        return switchMetric.isChecked();
    }
    private void startFragmentValider (long idtrajet) {
        ValiderFragment validerFragment = new ValiderFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idtrajet", idtrajet );
        validerFragment.setArguments(bundle);
        openFragment (validerFragment,"valider");
    }

    public void openFragment (Fragment frag, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.body_holder, frag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}