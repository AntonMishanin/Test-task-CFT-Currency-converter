package com.example.cft_converter.data.network

class RetrofitService {

    fun provideRetrofit(): Retrofit {
        return Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}