package com.yundin.amocrm_api_test.model.api;

import com.google.gson.JsonElement;
import com.yundin.amocrm_api_test.model.api.Response.LeadsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Yundin Vladislav
 */
public interface ApiInterface {

    @GET("api/v2/leads")
    Observable<LeadsResponse> getLeads(@Query("USER_LOGIN") String login, @Query("USER_HASH") String hash);

    @GET("api/v2/account")
    Observable<JsonElement> getAccount(@Query("USER_LOGIN") String login, @Query("USER_HASH") String hash,
                                       @Query("with") String with);
}
