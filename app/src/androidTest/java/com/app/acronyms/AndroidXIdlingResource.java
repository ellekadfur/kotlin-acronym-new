package com.app.acronyms;

import androidx.test.espresso.IdlingResource;

public class AndroidXIdlingResource implements IdlingResource {

    private android.support.test.espresso.IdlingResource mDelegate;

    private AndroidXIdlingResource(android.support.test.espresso.IdlingResource supportIdlingResource) {
        mDelegate = supportIdlingResource;
    }

    @Override
    public String getName() {
        return mDelegate.getName();
    }

    @Override
    public boolean isIdleNow() {
        return mDelegate.isIdleNow();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mDelegate.registerIdleTransitionCallback(new SupportResourceCallback(callback));
    }

    public static AndroidXIdlingResource asAndroidX(android.support.test.espresso.IdlingResource supportIdlingResource) {
        return new AndroidXIdlingResource(supportIdlingResource);
    }


    public class SupportResourceCallback implements android.support.test.espresso.IdlingResource.ResourceCallback {

        private ResourceCallback mDelegate;

        SupportResourceCallback(ResourceCallback androidXResourceCallback) {
            mDelegate = androidXResourceCallback;
        }

        @Override
        public void onTransitionToIdle() {
            mDelegate.onTransitionToIdle();
        }
    }
}