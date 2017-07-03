package com.breakfast.library.Common;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;
import org.robolectric.util.ReflectionHelpers;

public class LibraryTestRunner extends RobolectricTestRunner {
    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 19;

    private static final String BUILD_OUTPUT = "/library/build/intermediates";
    protected static final String projectName = "library";
    public LibraryTestRunner(Class<?> klass) throws org.junit.runners.model.InitializationError {
        super(klass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        //set the project path as the root path
        int nameLength = projectName.length();
        String rootPath = System.getProperty("user.dir", "./");
        int index  = rootPath.indexOf(projectName);         //get the index of projectRootPath of user.dir

        if (index == -1) {
            throw new RuntimeException("project name not found in user.dir");
        }

        rootPath = rootPath.substring(0, index + nameLength);
        String manifestProperty = rootPath + "/src/main/AndroidManifest.xml";
        String resProperty = rootPath + "/src/main/res";
        String assetsProperty = rootPath + "/src/main/assets";
        return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty),
                Fs.fileFromPath(assetsProperty)) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
        };


    }

    private String getType(Config config) {
        try {
            return ReflectionHelpers.getStaticField(config.constants(), "BUILD_TYPE");
        } catch (Throwable e) {
            return null;
        }
    }

    private String getFlavor(Config config) {
        try {
            return ReflectionHelpers.getStaticField(config.constants(), "FLAVOR");
        } catch (Throwable e) {
            return null;
        }
    }

    private String getPackageName(Config config) {
        try {
            final String packageName = config.packageName();
            if (packageName != null && !packageName.isEmpty()) {
                return packageName;
            } else {
                return ReflectionHelpers.getStaticField(config.constants(), "APPLICATION_ID");
            }
        } catch (Throwable e) {
            return null;
        }
    }
}