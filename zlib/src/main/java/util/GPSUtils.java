package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import base.BApp;
import base.PermissionFragment;

/**
 * 获取用户的地理位置
 */
public class GPSUtils {

    private static GPSUtils instance;
    private Context mContext;
    private LocationManager locationManager;

    private GPSUtils() {
        this.mContext = BApp.app().currentActivity();
    }

    public static GPSUtils location(OnLocationListener listener) {
        if (instance == null) {
            instance = new GPSUtils();
        }
        instance.get(listener, false);
        return instance;
    }

    public static GPSUtils updates(OnLocationListener listener) {
        if (instance == null) {
            instance = new GPSUtils();
        }
        instance.get(listener, true);
        return instance;
    }

    private void get(OnLocationListener listener, boolean updates) {
        mOnLocationListener = listener;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, PermissionFragment.精确位置) != 0 &&
                ActivityCompat.checkSelfPermission(mContext, PermissionFragment.大致位置) != 0) {
            PermissionFragment.request(new PermissionFragment.OnPermissionListener() {
                @Override
                public void onPermissionSuccess() {
                    start(updates);
                }
            }, PermissionFragment.精确位置, PermissionFragment.大致位置);
            return;
        } else start(updates);
    }

    @SuppressLint("MissingPermission")
    private void start(boolean updates) {
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location == null)
            location = locationManager.getLastKnownLocation(locationProvider = LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            if (mOnLocationListener != null && !updates) {
                mOnLocationListener.location(location);
                removeListener();
                return;
            }
        }else mOnLocationListener.location(null);
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 5000, 1, locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

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
            if (mOnLocationListener != null) {
                mOnLocationListener.location(location);
            }
        }
    };

    public void removeListener() {
        locationManager.removeUpdates(locationListener);
    }

    private OnLocationListener mOnLocationListener;

    public interface OnLocationListener {
        void location(Location location);
    }
}