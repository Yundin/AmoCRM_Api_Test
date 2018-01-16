package com.yundin.amocrm_api_test;

import android.app.Application;
import com.yundin.amocrm_api_test.view.DI.ApiModule;
import com.yundin.amocrm_api_test.view.DI.AppComponent;
import com.yundin.amocrm_api_test.view.DI.DaggerAppComponent;

/**
 * @author Yundin Vladislav
 */
public class AmoCRMapp extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {

        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }
}
