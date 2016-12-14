package com.lq.yl.product.count.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.lq.yl.product.count.app.other.gest.view.LockPatternUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class ProductApp extends Application{

    private static ProductApp mInstance;
    private LockPatternUtils mLockPatternUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        mLockPatternUtils = new LockPatternUtils(this);

        this.initImageLoader(this);
        mInstance = this;
    }

    public static ProductApp getInstance() {
        return mInstance;
    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }

    protected String loadAssetsFileCtnt(String fileName) throws IOException {
        InputStream inputStream;
        inputStream = this.getResources().getAssets().open(fileName);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(buffer);
        outputStream.close();
        inputStream.close();

        return outputStream.toString();
    }

    public void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);// 设置缓存空间在内存的1/5
        MemoryCache memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).memoryCache(memoryCache)
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                        // for
                        // release
                        // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!imageLoader.isInited()){
            imageLoader.init(config);
        }
    }
}
