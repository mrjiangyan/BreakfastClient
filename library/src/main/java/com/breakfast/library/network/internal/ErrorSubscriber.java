package com.breakfast.library.network.internal;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.JsonParseException;
import com.breakfast.library.R;
import com.breakfast.library.util.ResourceUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

import rx.Subscriber;

public class ErrorSubscriber<T> extends Subscriber<T> {

  //对应HTTP的状态码
  private static final int UNAUTHORIZED = 401;
  private static final int FORBIDDEN = 403;
  private static final int NOT_FOUND = 404;
  private static final int REQUEST_TIMEOUT = 408;
  private static final int INTERNAL_SERVER_ERROR = 500;
  private static final int BAD_GATEWAY = 502;
  private static final int SERVICE_UNAVAILABLE = 503;
  private static final int GATEWAY_TIMEOUT = 504;
  private static final int CONFLICT=409;

  @Override public void onError(Throwable e) {
    Throwable throwable = e;
    //获取最根源的异常
    while (throwable.getCause() != null) {
      e = throwable;
      throwable = throwable.getCause();
    }

    if (LogUtils.configAllowLog) {
      e.printStackTrace();
    }

    ApiException ex;
    if (e instanceof IOException) {
      // 网络错误，(反)序列化body错误等
      ex = new ApiException(e, ApiException.ERR_NETWORK,
          ResourceUtils.getString(R.string.err_network));
      onResponseError(ex);
    }
//    else if (e instanceof HttpException) {
//      //HTTP错误
//      HttpException httpException = (HttpException) e;
//      switch (httpException.code()) {
//        case UNAUTHORIZED:
//        case FORBIDDEN:
//          //权限错误，需要实现
//          ex = new ApiException(e, httpException.code());
//          onPermissionError(ex);
//          break;
//        case CONFLICT:
//          //权限错误，需要实现
//          try {
//            String response = httpException.response().errorBody().string();
//            ApiResponse resultResponse = ServiceFactory.getGson().fromJson(response, ApiResponse.class);
//            ex = new ApiException(resultResponse.getCode(), resultResponse.getMessage());
//            onResponseError(ex);
//            break;
//          } catch (IOException e1) {
//            e1.printStackTrace();
//          }
//
//
//        case NOT_FOUND:
//        case REQUEST_TIMEOUT:
//        case GATEWAY_TIMEOUT:
//        case INTERNAL_SERVER_ERROR:
//        case BAD_GATEWAY:
//        case SERVICE_UNAVAILABLE:
//        default:
//          ex = new ApiException(e, httpException.code(),
//              ResourceUtils.getString(R.string.err_server));
//          onResponseError(ex);
//          break;
//      }
//    }
    else if (e instanceof ApiException) {
      onResponseError((ApiException)e);
    } else if (e instanceof JsonParseException
        || e instanceof JSONException
        || e instanceof ParseException) {
      // 解析错误
      ex = new ApiException(e, ApiException.ERR_PARSE,
          ResourceUtils.getString(R.string.err_wrong_data));
      onResponseError(ex);
    } else {
      //未知错误
      ex = new ApiException(e, ApiException.ERR_UNKNOWN,
          ResourceUtils.getString(R.string.err_unknown));
      onResponseError(ex);
    }
  }

  @Override
  public void onNext(T t) {

  }

  public void onResponseError(ApiException ex)
  {

  }

  public void onCompleted() {


  }

  /**
   * 权限错误，需要实现重新登录操作
   */
  protected void onPermissionError(ApiException ex) {
    onResponseError(ex);
  }


}
