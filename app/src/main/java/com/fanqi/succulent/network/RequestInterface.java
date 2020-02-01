package com.fanqi.succulent.network;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.User;
import com.fanqi.succulent.bean.UserFavorite;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RequestInterface {
    
    @GET("succulents.json")
    Observable<Succulent[]> getSucculents();

    @GET("families.json")
    Observable<Family[]> getFamilies();

    @GET("generas.json")
    Observable<Genera[]> getGeneras();

    @GET("users.json")
    Observable<User[]> getUsers();

    @GET("user_favorites.json")
    Observable<UserFavorite[]> getUserFavorites();

    @GET("{item}")
    Observable<ResponseBody> getPage(@Path(value = "item", encoded = true) String itemName);

    @POST("families")
//    @FormUrlEncoded
    Observable<ResponseBody> postFamily(@Body JsonObject requestBody);

    @POST("generas")
//    @FormUrlEncoded
    Observable<ResponseBody> postGenera(@Body JsonObject requestBody);

    @POST("succulents")
//    @FormUrlEncoded
    Observable<ResponseBody> postSucculent(@Body JsonObject requestBody);


}
