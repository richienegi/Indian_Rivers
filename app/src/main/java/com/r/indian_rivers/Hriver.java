package com.r.indian_rivers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Hriver {
    String BASE_URL = "http://ritikanegi.com/";

    @GET("Hriversystem.php")
    Call<List<RiverSystem>> getRivers();
}
