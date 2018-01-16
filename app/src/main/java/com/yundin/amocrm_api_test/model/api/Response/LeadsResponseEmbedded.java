package com.yundin.amocrm_api_test.model.api.Response;

import com.google.gson.annotations.SerializedName;
import com.yundin.amocrm_api_test.model.dto.LeadItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yundin Vladislav
 */
public class LeadsResponseEmbedded {

    @SerializedName("items")
    private List<LeadItem> data;

    public List<LeadItem> getResponse() {

        if (data == null) {
            return new ArrayList<>();
        } else {
            return data;
        }
    }
}
