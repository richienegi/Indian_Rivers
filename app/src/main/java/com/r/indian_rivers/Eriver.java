package com.r.indian_rivers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Eriver {
    String BASE_URL = "http://ritikanegi.com/";

    @GET("Eriversystem.php")
    Call<List<RiverSystem>> getRivers();
}
