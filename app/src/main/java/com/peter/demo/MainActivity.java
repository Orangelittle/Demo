package com.peter.demo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   public BluetoothTempetureFragment bluetoothTempetureFragment;
   public BodyTempetureFragment bodyTempetureFragment;
   private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        bluetoothTempetureFragment =new BluetoothTempetureFragment();
        bodyTempetureFragment = new BodyTempetureFragment();

        fragmentManager = getSupportFragmentManager();
        ActivityUtils.addFragment(fragmentManager,bluetoothTempetureFragment,R.id.tempeturecontainer);


    }

    public static FragmentManager getManager() {
        return fragmentManager;
    }

    public static void popFragmentStack() {
            fragmentManager.popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popFragmentStack();
    }
}
