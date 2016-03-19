package com.tellerpoint.app.rest.services;

import com.tellerpoint.app.model.Merchant;
import com.tellerpoint.app.model.Product;
import com.tellerpoint.app.rest.responses.MerchantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by eit on 1/27/16.
 */
public interface APIService {

    // all_merchants
    @GET("merchants")
    Call<MerchantResponse> getMerchants();

    // merchant_profile and all her products
    @GET("merchants/{id}")
    Call<Merchant> getMerchant(@Path("id") int merchantId);

    // get product detail of a merchant
    @GET("merchants/{mId}/{pId}")
    Call<Product> getProduct(@Path("mId") int merchantId, @Path("pId") int postId);

//    //Edit User Profile with no file upload
//    @FormUrlEncoded
//    @PATCH("users/{id}")
//    Call<User> updateUserWithNoAvatarUpload(@Header("Authorization") String authentication_token, @Path("id") int userId,
//                                            @Field("email") String email,
//                                            @Field("username") String username,
//                                            @Field("firstname") String firstname,
//                                            @Field("lastname") String lastname);
//
//    //Edit User Profile with file upload
//    @Multipart
//    @PATCH("users/{id}")
//    Call<User> updateUserWithAvatarUpload(@Header("Authorization") String authentication_token, @Path("id") int userId,
//                                          @Part("avatar\"; filename=\"avatar_name\" ") RequestBody avatar,
//                                          @Part("email") RequestBody email,
//                                          @Part("username") RequestBody username,
//                                          @Part("firstname") RequestBody firstname,
//                                          @Part("lastname") RequestBody lastname);
//
//    // log_out
//    @DELETE("users/sign_out")
//    Call<Response<Void>> logOut(@Header("Authorization") String authentication_token);
//
//
//    // change_password
//    @PATCH("users/{id}/update_password")
//    Call<User> changePassword(@Header("Authorization") String authentication_token, @Path("id") int userId, @Body JsonObject passwordsObject);
//
//    // reset_password
//    @POST("users/password")
//    Call<PasswordResetResponse> resetPassword(@Body JsonObject emailObject);
//
//



}


