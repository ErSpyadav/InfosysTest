package demo.expresso.infosystest.network;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;
    public static Context mContext;

    public static Retrofit getClient(Context context) {
        mContext = context;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient())
                    .build();
        }

        return retrofit;
    }

    //This is used to Store response in Cache and populate last cache data automatic
    public static OkHttpClient getHttpClient(){
        int cacheSize=10*1024*1024;
        Cache cache=new Cache(mContext.getCacheDir(),cacheSize);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(getInterCeptor())
                .build();
        return okHttpClient;

    }

    //This is Used to print request and response
    public static HttpLoggingInterceptor getInterCeptor(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
