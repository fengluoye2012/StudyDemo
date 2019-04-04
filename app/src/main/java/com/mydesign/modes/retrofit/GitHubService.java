package com.mydesign.modes.retrofit;

import java.util.concurrent.CompletableFuture;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;


public interface GitHubService {
    @GET("/hotkey/json")
    Call<Repo> listRepos();

    @GET("/hotkey/json")
    Call<String> getString();

    @GET("/hotkey/json")
    Observable<String> getRxjava2String();

    @GET("/hotkey/json")
    CompletableFuture<String> getJavaString();
}