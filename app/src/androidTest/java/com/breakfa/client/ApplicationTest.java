package com.breakfa.client;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private org.apache.commons.logging.Log log= LogFactory.getLog(ApplicationTest.class);
  public ApplicationTest() {
    super(Application.class);
  }


  @Test
  public void Date()
  {
      Date date= new Date(1495774800000L);
      log.debug(date);
  }
}