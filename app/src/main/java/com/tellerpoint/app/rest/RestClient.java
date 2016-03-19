package com.tellerpoint.app.rest;

import com.tellerpoint.app.rest.services.APIService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * Created by eit on 1/27/16.
 */
public class RestClient {


    private static String baseUrl = "http://tellerpoint.herokuapp.com/index.php/api/";

    private static APIService apiService;



    public static APIService getClient() {
        if (apiService == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.interceptors().add(new LoggingInterceptor());
            httpClient.interceptors().add(logging);  // <-- this is the important line!

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = client.create(APIService.class);
        }
        return apiService ;
    }

}
