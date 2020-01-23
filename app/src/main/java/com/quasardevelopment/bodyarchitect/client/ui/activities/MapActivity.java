package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.GPSPoint;
import com.quasardevelopment.bodyarchitect.client.model.GPSPointsBag;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.LapsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.GPSPointsProcessor;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 15.07.13
 * Time: 07:41
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends BANormalActivityBase implements IWsdl2CodeEvents,OnMapReadyCallback {
    GPSTrackerEntryDTO entry;
    ProgressDialog progress;
    private TabHost tabHost;
    GoogleMap googleMap;
    BAListView lstLaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_map, null);
        setMainContent(inflate);
        getSupportActionBar().setSubtitle(R.string.gps_tracker_title);
        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.map_activity_tab_map));
        spec1.setContent(R.id.map);
        spec1.setIndicator(getString(R.string.map_activity_tab_map));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.map_activity_tab_laps));
        spec2.setIndicator(getString(R.string.map_activity_tab_laps));
        spec2.setContent(R.id.pnlLaps);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        ((SupportMapFragment)(getSupportFragmentManager().findFragmentById(R.id.map))).getMapAsync(this);
        lstLaps= (BAListView) findViewById(R.id.lstLaps);

        Intent intent =getIntent();
        entry= (GPSTrackerEntryDTO) intent.getSerializableExtra("Item");
    }

    @Override
    public void onMapReady(GoogleMap map) {
//DO WHATEVER YOU WANT WITH GOOGLEMAP
        googleMap=map;
        fillMap();
    }
    @Override
    protected void onResume() {
        super.onResume();

        //fillMap();
    }

    private void fillMap() {
        GPSPointsBag pointsBag = ApplicationState.getCurrent().getTrainingDay().getGpsCoordinates(entry);
        if (pointsBag == null)
        {
            downloadGpsCoordinates();
        }
        else
        {
            drawTrackOnMap(pointsBag.getPoints());
            fillLaps(pointsBag.getPoints());
        }
    }

    void downloadGpsCoordinates()
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(this);
        try {
            service.GetGPSCoordinatesAsync(entry.globalId);
        } catch (Exception e) {
            e.printStackTrace();
            BAMessageBox.ShowToast(R.string.map_activity_err_retrieve_gps_coordinates);
        }
    }


    private void fillLaps(List<GPSPoint> points) {
        double lapLength = ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Cm ? 1000 : 1609.344;
        GPSPointsProcessor processor = new GPSPointsProcessor(points, lapLength);
        LapsAdapter adapter = new LapsAdapter(this,R.id.tbLapNumber,processor.laps);
        lstLaps.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void drawTrackOnMap(List<GPSPoint> points) {
        if(googleMap==null || points.size()==0)
        { //no google play services installed
            return;
        }
        PolylineOptions routeLine=createMapLine();
        boolean sourceAdded=false;
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i =0;i<points.size()-1;i++)
        {
            GPSPoint src= points.get(i);
            GPSPoint dest= points.get(i+1);
            if(src.IsPoint() && dest.IsPoint())
            {
                routeLine.add(new LatLng(src.Latitude, src.Longitude), new LatLng(dest.Latitude, dest.Longitude));

                if(!sourceAdded)
                {
                    builder.include(new LatLng(src.Latitude, src.Longitude));
                    sourceAdded=true;
                }
                builder.include(new LatLng(dest.Latitude, dest.Longitude));
            }
            else if(routeLine.getPoints().size()>0)
            {
                googleMap.addPolyline(routeLine);
                routeLine=createMapLine();
            }
        }
        googleMap.addPolyline(routeLine);

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                try {
                    // Move camera.
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 30));
                    // Remove listener to prevent position reset on camera move.
                    googleMap.setOnCameraChangeListener(null);

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        try {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 30));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    PolylineOptions createMapLine()
    {
        PolylineOptions line=new PolylineOptions()
                .width(2)
                .color(Color.BLUE).geodesic(true) ;

        return line;
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        progress=BAMessageBox.ShowProgressDlg(this,R.string.progress_retrieving_items);
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data)
    {
        ArrayList<GPSPoint> points = (ArrayList<GPSPoint>)Data;
        ApplicationState.getCurrent().getTrainingDay().SetGpsCoordinates(entry, points,true);
        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays()
                        .containsKey(entry.trainingDay.trainingDate))
        {
            ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(entry.trainingDay.trainingDate)
                    .SetGpsCoordinates(entry, points, true);
        }
        drawTrackOnMap(points);
        fillLaps(points);
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex)
    {
        BAMessageBox.ShowToast(R.string.map_activity_err_retrieve_gps_coordinates);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        if(progress!=null)
        {
            progress.dismiss();
            progress=null;
        }
    }
}
