package com.r.indian_rivers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api1 {
    //String BASE_URL = "https://riversedemo.000webhostapp.com/";
    String BASE_URL = "http://ritikanegi.com/";

    @GET("Hgetriverlist.php")
    Call<List<rivers>> getRivers();
}
