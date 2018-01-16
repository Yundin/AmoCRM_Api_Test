package com.yundin.amocrm_api_test.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yundin Vladislav
 */
public class LeadItem {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("created_at")
    private long timestamp;
    @SerializedName("sale")
    private int sale;
    @SerializedName("pipeline")
    private PipelineItem pipeline;
    @SerializedName("status_id")
    private int statusId;

    private String statusTitle;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getSale() {
        return sale;
    }

    public PipelineItem getPipeline() {
        return pipeline;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }
}
