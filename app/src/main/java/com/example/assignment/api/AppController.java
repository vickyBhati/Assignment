package com.example.assignment.api;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private ApiInterface service;


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        service = createRetrofitService(ApiInterface.class, ApiInterface.BASE_URL);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        mSharedPreferences = this.getSharedPreferences(AppConstants.sharedPrefName, Context.MODE_PRIVATE);

        try {

            /*IO.Options opts ;

            opts = new IO.Options();
            opts.transports = new String[]{WebSocket.NAME};
            mSocket = IO.socket(ChatConstant.CHAT_SERVER_URL,opts);
            mSocket.io().open(new Manager.OpenCallback() {
                @Override
                public void call(Exception e) {
                    Log.e("gf", "call: ");

                }
            });*/

            SSLContext mySSLContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            } };

            mySSLContext.init(null, trustAllCerts, null);

            HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .hostnameVerifier(myHostnameVerifier)
                    .sslSocketFactory(mySSLContext.getSocketFactory(), new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[] {};
                        }

                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }
                    })
                    .build();




        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
    private TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    } };



    X509TrustManager tm = new X509TrustManager()  {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };


    public static class RelaxedHostNameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }



    public <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {

        Gson gson = new GsonBuilder().create();

        File httpCacheDirectory = new File(getCacheDir(), "cache_file");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(0, 5 * 60 * 1000, TimeUnit.SECONDS))
                .addInterceptor(new CustomInterceptor(getInstance(), Locale.getDefault().getLanguage(), ""))
                .cache(cache)
                .build();


        //init retrofit
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(endPoint)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(clazz);
    }




    public ApiInterface getApiInterface() {
        return service;

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        mInstance=this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        //SharedPreferencesUtility.getInstance().save(Constant.IS_DRAWER, false);
        super.onTrimMemory(level);

    }



}
