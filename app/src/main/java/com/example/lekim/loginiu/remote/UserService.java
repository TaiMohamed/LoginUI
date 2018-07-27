package com.example.lekim.loginiu.remote;

import com.example.lekim.loginiu.SignIn;
import com.example.lekim.loginiu.model.ResObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("login/{username}/{password}")
    Call<ResObj> login(@Path("username") String username, @Path("password") String password);
}