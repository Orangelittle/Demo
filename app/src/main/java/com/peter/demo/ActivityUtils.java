package com.peter.demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.View;

public final class ActivityUtils {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId) {

        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
//            transaction.addToBackStack(fragment.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack) {
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            transaction.commit();
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String fragmentTag) {
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment, fragmentTag);
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            transaction.commit();
        }
    }


    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack,  int tag) {
        if (fragment != null && fragmentManager != null) {
            Bundle args = new Bundle();
            args.putInt("tag", tag);
            fragment.setArguments(args);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            transaction.commit();
        }
    }

}
