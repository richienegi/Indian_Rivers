package com.r.indian_rivers.network;

import com.r.indian_rivers.model.Quiz;
import com.r.indian_rivers.model.RiverSystem;
import com.r.indian_rivers.model.Rivers;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("Egetriverlist.php")
    Call<List<Rivers>> getEngRiversList();

    @GET("Hgetriverlist.php")
    Call<List<Rivers>> getHindiRiversList();

    @GET("QuizEnglish.php")
    Call<List<Quiz>> getEngQuestion();

    @GET("QuizHindi.php")
    Call<List<Quiz>> getHindiQuestion();

    @GET("Eriversystem.php")
    Call<List<RiverSystem>> getEngRiversSystem();

    @GET("Hriversystem.php")
    Call<List<RiverSystem>> getHindiRiversSystem();

    @POST("one.php")
    Call<ResponseBody> sendSuggestion(@Query("from") String from, @Query("suggetion") String suggestion);

}
