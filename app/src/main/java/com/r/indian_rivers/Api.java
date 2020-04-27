package com.r.indian_rivers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://ritikanegi.com/";

    @GET("Egetriverlist.php")
    Call<List<rivers>> getRivers();
}
