package com.r.indian_rivers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiQuizH {
    String BASE_URL = "http://ritikanegi.com/";

    @GET("QuizHindi.php")
    Call<List<Quiz>> getQuestion();
}
