package com.angcyo.uiview.net;

import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.net.cookie.CookieJarImpl;
import com.angcyo.uiview.net.cookie.store.PersistentCookieStore;
import com.github.simonpercic.oklog3.OkLogInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by angcyo on 2016-03-20 23:53.
 */
public class RRetrofit {
    public static String BASE_URL = "http://192.168.1.156:8081/app/";
    //切换服务器, 1外网 -1内网
    public static void switchHttp(int type) {
        if (type >= 1) {
            BASE = "http://zan.eeniao.com/";
            BASE_URL = "http://zan.eeniao.com/app/";
            BASE_IMAGE_URL = "http://zan.eeniao.com/upload/";
            BASE_LOGO_URL = "http://zan.eeniao.com/static/images/reglogo.png";
        } else if (type <= -1) {
            BASE = "http://192.168.1.156:8081/";
            BASE_URL = "http://192.168.1.156:8081/app/";
            BASE_IMAGE_URL = "http://192.168.1.156:8081/upload/";
            BASE_LOGO_URL = "http://192.168.1.156:8081/static/images/reglogo.png";
        }
        HnUrl.SERVER = BASE_URL;
        HnUrl.FILE_UPLOAD_API = BASE_IMAGE_URL + "index.php";
    }
    public static boolean DEBUG = true;

    public static <T> T create(final Class<T> cls) {
        Converter.Factory factory = getFactory();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(defaultClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        if (factory != null) {
            builder.addConverterFactory(factory);
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return factory;
    }

    private static OkHttpClient defaultClient() {
        // create an instance of OkLogInterceptor using a builder()
        OkLogInterceptor okLogInterceptor;
        OkLogInterceptor.Builder builder = OkLogInterceptor.builder();
        if (DEBUG) {
            okLogInterceptor = builder.withAllLogData().build();
        } else {
            okLogInterceptor = builder.withNoLogData().build();
        }

        // create an instance of OkHttpClient builder
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        // add OkLogInterceptor to OkHttpClient's application interceptors
        okHttpBuilder.addInterceptor(okLogInterceptor);

        okHttpBuilder.cookieJar(new CookieJarImpl(new PersistentCookieStore(RApplication.getApp())));

        //okHttpBuilder.cache()
        // build
        OkHttpClient okHttpClient = okHttpBuilder.build();

        return okHttpClient;
    }

    public static void cancelTag(Object tag) {
        for (Call call : defaultClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : defaultClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
