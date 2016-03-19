package com.tellerpoint.app.rest;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by eit on 1/27/16.
 */
public class LoggingInterceptor implements Interceptor {
    Gson mGson = new Gson();
    private static APIError apiError = null;


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response;

        try {
            response = chain.proceed(request);
            if (!response.isSuccessful()) {
                response = trackError(request, response);
            }
        } catch (SocketTimeoutException e) {
            trackError(request, 110, "Timeout");
            throw e;
        } catch (IOException e) {
            trackError(request, 110, "Device Connection Failure");
            throw e;
        }

        return response;
    }

    private Response trackError(Request request, Response response) {
        String responseBodyString = null;
        try {
            responseBodyString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.code();
        String errorMessage;
        final Response newResponse;

        try {
            Log.e("response string", responseBodyString);
            APIError errorResponse = mGson.fromJson(responseBodyString, APIError.class);
            errorMessage = errorResponse.message();
        } catch (NullPointerException e) {
            errorMessage = "Unknown error";
        } finally {
            ResponseBody body = ResponseBody.create(response.body().contentType(), responseBodyString);
            newResponse = response.newBuilder()
                    .body(body)
                    .build();
        }

        trackError(request, statusCode, errorMessage);
        return newResponse;
    }

    private void trackError(Request request, int statusCode, String message) {
        String url = request.url().toString();
        String method = request.method();
        if(message != null ){
            Log.e("error msg", message);
            Log.e("error cause", String.valueOf(statusCode));
            Log.e("error url", url);
            Log.e("error method", method);
            apiError = new APIError(statusCode, message, method);
        }
    }

    public static APIError getLastApiError(){
        if (apiError != null){
            return apiError;
        }
        return  null;
    }
}
