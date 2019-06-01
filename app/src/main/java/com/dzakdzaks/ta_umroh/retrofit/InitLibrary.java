package com.dzakdzaks.ta_umroh.retrofit;


import com.dzakdzaks.ta_umroh.global.GlobalVariable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitLibrary {


        public static Retrofit setInit() {
            return new Retrofit.Builder().baseUrl(GlobalVariable.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static ApiService getInstance() {
            return setInit().create(ApiService.class);
        }
}
