/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breakfast.client.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.base.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity(@NonNull BaseActivity activity,
                                             @NonNull Fragment fragment) {
        checkNotNull(activity.getSupportFragmentManager());
        checkNotNull(fragment);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(com.breakfast.client.R.id.contentFrame, fragment);
        transaction.commit();
        StatisticsUtils.touch(activity, fragment.getClass().getName(),"addFragmentToActivity");

    }


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void replaceFragmentNotBack(@NonNull BaseActivity activity,
                                                 @NonNull Fragment fragment) {
        checkNotNull(activity.getSupportFragmentManager());
        checkNotNull(fragment);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(com.breakfast.client.R.id.contentFrame, fragment);
        transaction.commit();
        StatisticsUtils.touch(activity, fragment.getClass().getName(),"replaceFragmentNotBack");

    }


    public static void addFragment(@NonNull BaseActivity activity, @NonNull BaseFragment oldFragment, @NonNull BaseFragment newFragment) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);

        String tag=newFragment.getClass().getName();
        if (!newFragment.isAdded()) {
            ft.hide(oldFragment).add(com.breakfast.client.R.id.contentFrame, newFragment, tag);
            ft.addToBackStack(tag);
        } else {
            ft.hide(oldFragment).show(newFragment);
        }
        StatisticsUtils.touch(activity, oldFragment.getClass().getName(),"addFragment-"+ newFragment.getClass().getName());

        //ft.commitAllowingStateLoss();
        ft.commit();
    }

}
