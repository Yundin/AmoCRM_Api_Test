package com.yundin.amocrm_api_test.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yundin Vladislav
 */
public class PipelineItem {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }
}
