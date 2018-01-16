package com.yundin.amocrm_api_test.presenter;

import android.support.annotation.NonNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yundin.amocrm_api_test.R;
import com.yundin.amocrm_api_test.model.api.ApiInterface;
import com.yundin.amocrm_api_test.model.api.Response.LeadsResponse;
import com.yundin.amocrm_api_test.model.api.Response.LeadsResponseEmbedded;
import com.yundin.amocrm_api_test.model.dto.LeadItem;
import com.yundin.amocrm_api_test.view.activity.LeadsView;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.RxUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import java.util.List;

/**
 * @author Yundin Vladislav
 */
public class LeadsPresenter {

    private Func1<List<LeadItem>, List<LeadItem>> addStatusTitle;
    private LeadsView leadsView;
    private LifecycleHandler lifecycleHandler;
    private ApiInterface apiInterface;
    private JsonElement accountJson = null;

    public LeadsPresenter(@NonNull LeadsView view, @NonNull LifecycleHandler lifecycleHandler, ApiInterface apiInterface) {
        leadsView = view;
        this.lifecycleHandler = lifecycleHandler;
        this.apiInterface = apiInterface;
    }

    public void init() {

        addStatusTitle = new Func1<List<LeadItem>, List<LeadItem>>() {
            @Override
            public List<LeadItem> call(List<LeadItem> leadItemList) {

                for (LeadItem item : leadItemList) {
                    String pipelineTitle = String.valueOf(item.getPipeline().getId());
                    String statusId = String.valueOf(item.getStatusId());

                    JsonObject jsonObject = accountJson.getAsJsonObject();

                    JsonObject embedded = jsonObject.get("_embedded").getAsJsonObject();
                    JsonObject pipelines = embedded.get("pipelines").getAsJsonObject();
                    JsonObject pipelineObject = pipelines.get(pipelineTitle).getAsJsonObject();
                    JsonObject statuses = pipelineObject.get("statuses").getAsJsonObject();
                    JsonObject statusObject = statuses.get(statusId).getAsJsonObject();
                    String statusTitle = statusObject.get("name").getAsString();

                    item.setStatusTitle(statusTitle);
                }
                return leadItemList;
            }
        };

        apiInterface.getAccount("yu_va@mail.ru", "f2be8f76f3ea0fd05c759134a3fb6e9f", "pipelines")
                .compose(RxUtils.async())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleHandler.load(R.id.account_request_id))
                .doOnSubscribe(leadsView::showLoading)
                .doOnCompleted(this::getLeads)
                .subscribe(this::saveAccountJson, leadsView::showError);
    }

    private void getLeads() {
        apiInterface.getLeads("yu_va@mail.ru", "f2be8f76f3ea0fd05c759134a3fb6e9f")
                .map(LeadsResponse::getEmbedded)
                .map(LeadsResponseEmbedded::getResponse)
                .takeUntil(leadItemList -> (accountJson == null))
                .map(addStatusTitle)
                .compose(RxUtils.async())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(leadsView::hideLoading)
                //handling lifecycle
                .compose(lifecycleHandler.load(R.id.leads_request_id))
                .subscribe(leadsView::showLeads, leadsView::showError);
    }

    private void saveAccountJson(JsonElement jsonElement) {

        accountJson = jsonElement;
    }
}
