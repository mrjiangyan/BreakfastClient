package com.breakfast.library.Common;

import com.breakfast.library.app.BaseApplication;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

/**
 * Created by Steven on 16/8/6.
 */
@RunWith(RobolectricTestRunner.class)
public class TestApplication extends BaseApplication implements TestLifecycleApplication {



//    protected ParseAPI getParseAPI() {
//        return new ParseAPI() {
//            public void init(Context context, String key, String id) {
//            }
//
//            public void initPush(Context context) {
//            }
//        };
//    }


    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {
     }

    @Override
    public void afterTest(Method method) {

    }
}
