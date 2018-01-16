package com.yundin.amocrm_api_test.view.DI;

import android.support.annotation.NonNull;
import com.yundin.amocrm_api_test.model.api.ApiFactory;
import com.yundin.amocrm_api_test.model.api.ApiInterface;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * @author Yundin Vladislav
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiFactory.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
}
