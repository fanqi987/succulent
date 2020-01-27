package com.fanqi.succulent.network;

import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.User;
import com.fanqi.succulent.bean.UserFavorite;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RequestInterface {

    String baseUrlHeroku = "https://android-server-fanqi.herokuapp.com/";
    String baseUrlBaidu = " https://baike.baidu.com/item/";
    String baseUrlBaiduPic="https://baike.baidu.com/pic/";

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
    Observable<ResponseBody> getPage(@Path("item") String itemName);

}
