package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import base.BApp;
import base.BPermissionFragment;

/**
 * 获取用户的地理位置
 */
public class GPSUtils {

    private static GPSUtils instance;
    private Context mContext;
    private LocationManager locationManager;
    private int time = 5000;
    private int distance = 1;

    public GPSUtils setTime(int time) {
        this.time = time;
        return instance;
    }

    public GPSUtils setDistance(int distance) {
        this.distance = distance;
        return instance;
    }

    private GPSUtils() {
        this.mContext = BApp.app().act();
    }

    public GPSUtils location(OnLocationListener listener) {
        if (instance == null) {
            instance = new GPSUtils();
        }
        instance.get(listener, false);
        return instance;
    }

    public static GPSUtils get() {
        if (instance == null) {
            instance = new GPSUtils();
        }
        return instance;
    }

    public GPSUtils updates(OnLocationListener listener) {
        instance.get(listener, true);
        return instance;
    }

    private void get(OnLocationListener listener, boolean updates) {
        mOnLocationListener = listener;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, BPermissionFragment.精确位置) != 0 &&
                ActivityCompat.checkSelfPermission(mContext, BPermissionFragment.大致位置) != 0) {
            BPermissionFragment.request(new BPermissionFragment.OnPermissionListener() {
                @Override
                public void onPermissionSuccess() {
                    start(updates);
                }
            }, BPermissionFragment.精确位置, BPermissionFragment.大致位置);
            return;
        } else start(updates);
    }

    @SuppressLint("MissingPermission")
    private void start(boolean updates) {
        for (String s : locationManager.getAllProviders()) {
            LogUtils.e("GPSUtils", "\t" + s);
        }
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location == null)
            location = locationManager.getLastKnownLocation(locationProvider = LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            if (mOnLocationListener != null && !updates) {
                mOnLocationListener.location(location);
                removeListener();
            }
        } else {
            mOnLocationListener.location(null);
            return;
        }
        //监视地理位置变化
        if (locationListener == null) {
            locationListener = new LocationListener() {

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onLocationChanged(Location location) {
                    if (!BApp.app().act().equals(mContext)) {
                        removeListener();
                        locationManager = null;
                        return;
                    }
                    if (mOnLocationListener != null) {
                        mOnLocationListener.location(location);
                    }
                }
            };
        }
        locationManager.requestLocationUpdates(locationProvider, time, distance, locationListener);
    }

    private LocationListener locationListener;

    public void removeListener() {
        locationManager.removeUpdates(locationListener);
    }

    private OnLocationListener mOnLocationListener;

    public interface OnLocationListener {
        void location(Location location);
    }
}