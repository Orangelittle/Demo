package com.peter.demo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity {

   public BluetoothTempetureFragment bluetoothTempetureFragment;
   public BodyTempetureFragment bodyTempetureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        bluetoothTempetureFragment =new BluetoothTempetureFragment();
        bodyTempetureFragment = new BodyTempetureFragment();

        ActivityUtils.addFragment(getSupportFragmentManager(),bluetoothTempetureFragment,R.id.tempeturecontainer);


    }

    public  void popFragmentStack() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popFragmentStack();
    }
}
