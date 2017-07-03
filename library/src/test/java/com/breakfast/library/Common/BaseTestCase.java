package com.breakfast.library.Common;

import com.breakfast.library.BuildConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.net.URISyntaxException;

/**
 * Created by Steven on 16/8/6.
 */
@RunWith(iPmsRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplication.class,
        shadows = TestNetworkSecurityPolicy.class)
public class BaseTestCase {


    @Before
    public void setUp() throws URISyntaxException {
        //输出日志
        ShadowLog.stream = System.out;
    }
}
