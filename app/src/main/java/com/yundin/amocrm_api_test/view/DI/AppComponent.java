package com.yundin.amocrm_api_test.view.DI;

import com.yundin.amocrm_api_test.view.activity.LeadsActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * @author Yundin Vladislav
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void injectLeadsActivity(LeadsActivity leadsActivity);
}
