package com.yundin.amocrm_api_test.model.api.Response;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yundin Vladislav
 */
public class LeadsResponse {

    @SerializedName("_embedded")
    private LeadsResponseEmbedded data;

    public LeadsResponseEmbedded getEmbedded() {

        return data;
    }
}
