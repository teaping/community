package com.ksd.community.provider;

import com.alibaba.fastjson.JSON;
import com.ksd.community.dto.AccessTokenDTO;
import com.ksd.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public  String gitAccessToken(AccessTokenDTO accessTokenDTO){
     MediaType mediaType = MediaType.get("application/json; charset=utf-8");
     OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String s=response.body().string();
                String token= s.split("&")[0].split("=")[1];
                return token;
            }catch (IOException e){
                e.printStackTrace();
            }
            return  null;
        }


    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return  githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }






}
