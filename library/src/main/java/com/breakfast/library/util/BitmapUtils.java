package com.breakfast.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class BitmapUtils {
  private static final int IMAGE_MAX_WIDTH = 1280;
  private static final int IMAGE_MAX_HEIGHT = 720;

  /**
   * set image src
   */
  public static Bitmap getBitmap(String imagePath) {
    BitmapFactory.Options option = new BitmapFactory.Options();
    option.inSampleSize = getImageScale(imagePath);
    Bitmap bm;
    try {
      bm = BitmapFactory.decodeFile(imagePath, option);
    } catch (OutOfMemoryError err) {
      return null;
    }
    return bm;
  }


  public static Drawable getRoundedBitmap(Context context, int imageId) {
    Bitmap src = BitmapFactory.decodeResource(context.getResources(), imageId);
    Bitmap dst;
    if (src.getWidth() >= src.getHeight()) {
      dst = Bitmap.createBitmap(src, src.getWidth() / 2 - src.getHeight() / 2, 0, src.getHeight(), src.getHeight());
    } else {
      dst = Bitmap.createBitmap(src, 0, src.getHeight() / 2 - src.getWidth() / 2, src.getWidth(), src.getWidth());
    }
    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), dst);
    roundedBitmapDrawable.setCornerRadius(dst.getWidth() / 2);
    roundedBitmapDrawable.setAntiAlias(true);
    return roundedBitmapDrawable;
  }

  /**
   * scale image to fixed height and weight
   */
  private static int getImageScale(String imagePath) {
    BitmapFactory.Options option = new BitmapFactory.Options();
    // set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
    // memory for its pixels.
    option.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, option);
    int scale = 1;
    while (option.outWidth / scale >= IMAGE_MAX_WIDTH
        || option.outHeight / scale >= IMAGE_MAX_HEIGHT) {
      scale *= 2;
    }
    return scale;
  }

  /**
   * get Bitmap
   */
  @SuppressWarnings("resource") public static Bitmap tryGetBitmap(String imgFile, int minSideLength,
      int maxNumOfPixels) throws IOException {
    if (imgFile == null || imgFile.length() == 0) {
      return null;
    }
    BitmapFactory.Options options = new BitmapFactory.Options();

    FileInputStream stream = null;
    try {
      stream = new FileInputStream(imgFile);
      FileDescriptor fd = stream.getFD();
      options.inJustDecodeBounds = true;
      // BitmapFactory.decodeFile(imgFile, options);
      BitmapFactory.decodeFileDescriptor(fd, null, options);

      options.inSampleSize = computeSampleSize(options, minSideLength, maxNumOfPixels);
      // 这里一定要将其设置回false，因为之前我们将其设置成了true
      // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
      options.inJustDecodeBounds = false;
      return BitmapFactory.decodeFile(imgFile, options);
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * compute Sample Size
   */
  public static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

    int roundedSize;
    if (initialSize <= 8) {
      roundedSize = 1;
      while (roundedSize < initialSize) {
        roundedSize <<= 1;
      }
    } else {
      roundedSize = (initialSize + 7) / 8 * 8;
    }

    return roundedSize;
  }

  /**
   * compute Initial Sample Size
   */
  private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    double w = options.outWidth;
    double h = options.outHeight;

    // 上下限范围
    int lowerBound =
        (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
    int upperBound = (minSideLength == -1) ? 128
        : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

    if (upperBound < lowerBound) {
      // return the larger one when there is no overlapping zone.
      return lowerBound;
    }

    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
      return 1;
    } else if (minSideLength == -1) {
      return lowerBound;
    } else {
      return upperBound;
    }
  }
}
