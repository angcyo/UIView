package com.angcyo.uiview.net;

import android.os.Environment;

import com.angcyo.library.utils.L;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.RCrashHandler;
import com.angcyo.uiview.github.utilcode.utils.NetworkUtils;
import com.angcyo.uiview.net.cookie.CookieJarImpl;
import com.angcyo.uiview.net.cookie.store.PersistentCookieStore;
import com.angcyo.uiview.receiver.NetworkStateReceiver;
import com.github.simonpercic.oklog3.OkLogInterceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by angcyo on 2016-03-20 23:53.
 */
public class RRetrofit {
    public static String DEBUG_URL = "http://192.168.1.35/";
    public static String RELEASE_URL = "http://api.klgwl.com/";

    public static String BASE_URL = RELEASE_URL;

    //    //切换服务器, 1外网 -1内网
//    public static void switchHttp(int type) {
//        if (type >= 1) {
//            BASE = "http://zan.eeniao.com/";
//            DEBUG_URL = "http://zan.eeniao.com/app/";
//            BASE_IMAGE_URL = "http://zan.eeniao.com/upload/";
//            BASE_LOGO_URL = "http://zan.eeniao.com/static/images/reglogo.png";
//        } else if (type <= -1) {
//            BASE = "http://192.168.1.156:8081/";
//            DEBUG_URL = "http://192.168.1.156:8081/app/";
//            BASE_IMAGE_URL = "http://192.168.1.156:8081/upload/";
//            BASE_LOGO_URL = "http://192.168.1.156:8081/static/images/reglogo.png";
//        }
//        HnUrl.SERVER = DEBUG_URL;
//        HnUrl.FILE_UPLOAD_API = BASE_IMAGE_URL + "index.php";
//    }
    public static boolean DEBUG = L.LOG_DEBUG;

    static {
//        if (DEBUG) {
//            BASE_URL = DEBUG_URL;
//        } else {
//            BASE_URL = RELEASE_URL;
//        }
    }

    public static <T> T create(final Class<T> cls) {
        return create(cls, CacheType.NO_CACHE);
    }

    public static <T> T create(final Class<T> cls, CacheType cacheType) {
        Converter.Factory factory = getFactory();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(defaultClient(cacheType))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        if (factory != null) {
            builder.addConverterFactory(factory);
        }

//        builder.addConverterFactory(ScalarsConverterFactory.create());

        try {
            saveToSDCard(cls.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Retrofit retrofit = builder.build();
        return retrofit.create(cls);
    }

    private static Converter.Factory getFactory() {
        Converter.Factory gsonFactory = checkFactory("retrofit2.converter.gson.GsonConverterFactory");
        Converter.Factory jacksonFactory = checkFactory("retrofit2.converter.jackson.JacksonConverterFactory");

        //优先使用jackson
        if (jacksonFactory != null) {
            return jacksonFactory;
        }
        return gsonFactory;
    }

    private static Converter.Factory checkFactory(String className) {
        Converter.Factory factory = null;
        try {
            final Class<?> factoryName = Class.forName(className);
            final Method create = factoryName.getMethod("create");
            factory = (Converter.Factory) create.invoke(null);
        } catch (Exception e) {
        }
        return factory;
    }

    private static OkHttpClient defaultClient(CacheType cacheType) {
        // create an instance of OkLogInterceptor using a builder()
        OkLogInterceptor okLogInterceptor;
        OkLogInterceptor.Builder builder = OkLogInterceptor.builder();

//        builder.setBaseUrl(DEBUG_URL);
        builder.useAndroidLog(false);

        if (DEBUG) {
            okLogInterceptor = builder.withAllLogData().build();
        } else {
            okLogInterceptor = builder.withNoLogData().build();
        }

        // create an instance of OkHttpClient builder
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        //缓存支持,okhttp只会对get请求进行缓存，post请求是不会进行缓存
        okHttpBuilder
                .addInterceptor(new CacheInterceptor(false))
                .addNetworkInterceptor(new CacheInterceptor(DEBUG, cacheType))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(new File(RApplication.getApp().getCacheDir(), "ok_cache"), 10240 * 1024))
        ;

        // add OkLogInterceptor to OkHttpClient's application interceptors
        // okHttpBuilder.addInterceptor(okLogInterceptor);

        okHttpBuilder.cookieJar(new CookieJarImpl(new PersistentCookieStore(RApplication.getApp())));

        //okHttpBuilder.cache()
        // build
        OkHttpClient okHttpClient = okHttpBuilder.build();

        return okHttpClient;
    }

    public static void cancelTag(Object tag) {
        for (Call call : defaultClient(CacheType.NO_CACHE).dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : defaultClient(CacheType.NO_CACHE).dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 追加数据到文件
     */
    public static void saveToSDCard(String data) throws Exception {
        String saveFolder = Environment.getExternalStorageDirectory().getAbsoluteFile() +
                File.separator + "DValley" + File.separator + "log";
        File folder = new File(saveFolder);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                return;
            }
        }
        String dataTime = RCrashHandler.getDataTime("yyyy-MM-dd-HH-mm-ss");
        File file = new File(saveFolder, /*dataTime + */"RRetrofit.log");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        pw.print(dataTime);
        pw.print(":");
        pw.print(data);
        // 导出手机信息
        pw.println();
        pw.close();
    }

    public enum CacheType {
        /**
         * 在OKHttp开发中我们常见到的有下面几个：
         * max-age     缓存多长时间, 超过时间才请求
         * no-cache
         * max-stale   在这个时长之内的请求, 使用缓存.
         * */
        NO_CACHE, MAX_AGE, MAX_STALE
    }

    /**
     * 如果服务器返回的Response不包含Header:Cache-Control, max-age=xxx,缓存控制, 就需要手动修改
     * <p>
     * max-age：这个参数告诉浏览器将页面缓存多长时间，超过这个时间后才再次向服务器发起请求检查页面是否有更新。对于静态的页面，比如图片、CSS、Javascript，一般都不大变更，因此通常我们将存储这些内容的时间设置为较长的时间，这样浏览器会不会向浏览器反复发起请求，也不会去检查是否更新了。
     * s-maxage：这个参数告诉缓存服务器(proxy，如Squid)的缓存页面的时间。如果不单独指定，缓存服务器将使用max-age。对于动态内容(比如文档的查看页面)，我们可告诉浏览器很快就过时了(max-age=0)，并告诉缓存服务器(Squid)保留内容一段时间(比如，s-maxage=7200)。一旦我们更新文档，我们将告诉Squid清除老的缓存版本。
     * must-revalidate：这告诉浏览器，一旦缓存的内容过期，一定要向服务器询问是否有新版本。
     * proxy-revalidate：proxy上的缓存一旦过期，一定要向服务器询问是否有新版本。
     * no-cache：不做缓存。
     * no-store：数据不在硬盘中临时保存，这对需要保密的内容比较重要。
     * public：告诉缓存服务器, 即便是对于不该缓存的内容也缓存起来，比如当用户已经认证的时候。所有的静态内容(图片、Javascript、CSS等)应该是public的。
     * private：告诉proxy不要缓存，但是浏览器可使用private cache进行缓存。一般登录后的个性化页面是private的。
     * no-transform: 告诉proxy不进行转换，比如告诉手机浏览器不要下载某些图片。
     * max-stale指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可以接收超出超时期指定值之内的响应消息。
     */
    public static class CacheInterceptor implements Interceptor {

        boolean log;
        CacheType mCacheType;

        public CacheInterceptor(boolean log) {
            this(log, CacheType.NO_CACHE);
        }

        public CacheInterceptor(boolean log, CacheType cacheType) {
            this.log = log;
            mCacheType = cacheType;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//            Response newResponse = response.newBuilder()
//                    .removeHeader("Pragma")
//                    .removeHeader("Cache-Control")
//                    //cache for 30 days
//                    .header("Cache-Control", "public, max-age=" + 3600 * 24 * 30)
//                    .build();
//            if (DEBUG) {
//                L.i("请求URL:" + request.url().url().toString());
//            }

            Request request = chain.request();

            if (log) {
                L.i("请求URL:" + request.method() + ":" + request.url().url().toString());
            }

            Request.Builder builder = request.newBuilder();
            if (NetworkStateReceiver.getNetType().value() <= NetworkUtils.NetworkType.NETWORK_UNKNOWN.value()) {
                //没网络,强制使用缓存
                request = builder
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            } else {
                //有网的情况下
                /**
                 * 在OKHttp开发中我们常见到的有下面几个：
                 * max-age     缓存多长时间, 超过时间才请求
                 * no-cache
                 * max-stale   在这个时长之内的请求, 使用缓存.
                 * */
                CacheControl.Builder cacheBuild = new CacheControl.Builder();
                switch (mCacheType) {
                    case MAX_AGE:
                        cacheBuild.maxAge(1, TimeUnit.HOURS);
                        break;
                    case MAX_STALE:
                        cacheBuild.maxAge(1, TimeUnit.MINUTES);
                        break;
                    case NO_CACHE:
                    default:
                        cacheBuild.noCache();
                        break;

                }
                request = builder
                        .cacheControl(cacheBuild.build())
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkStateReceiver.getNetType().value() > NetworkUtils.NetworkType.NETWORK_UNKNOWN.value()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
