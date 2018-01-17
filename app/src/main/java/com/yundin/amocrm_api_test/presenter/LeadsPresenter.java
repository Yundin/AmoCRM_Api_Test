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
import rx.Observable;
import rx.functions.Func2;

import java.util.List;

/**
 * @author Yundin Vladislav
 */
public class LeadsPresenter {

    private LeadsView leadsView;
    private LifecycleHandler lifecycleHandler;
    private ApiInterface apiInterface;

    public LeadsPresenter(@NonNull LeadsView view, @NonNull LifecycleHandler lifecycleHandler, ApiInterface apiInterface) {
        leadsView = view;
        this.lifecycleHandler = lifecycleHandler;
        this.apiInterface = apiInterface;
    }

    public void init() {

        Func2<List<LeadItem>, JsonElement, List<LeadItem>> addStatusTitle =
                new Func2<List<LeadItem>, JsonElement, List<LeadItem>>() {
            @Override
            public List<LeadItem> call(List<LeadItem> leadItemList, JsonElement jsonElement) {

                for (LeadItem item : leadItemList) {

                    String pipelineTitle = String.valueOf(item.getPipeline().getId());
                    String statusId = String.valueOf(item.getStatusId());

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

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

        Observable<JsonElement> getAccount = apiInterface.getAccount("yu_va@mail.ru", "f2be8f76f3ea0fd05c759134a3fb6e9f", "pipelines")
                .compose(RxUtils.async())
                //handling lifecycle
                .compose(lifecycleHandler.load(R.id.account_request_id));

        apiInterface.getLeads("yu_va@mail.ru", "f2be8f76f3ea0fd05c759134a3fb6e9f")
                .map(LeadsResponse::getEmbedded)
                .map(LeadsResponseEmbedded::getResponse)
                .zipWith(getAccount, addStatusTitle)
                .compose(RxUtils.async())
                //handling lifecycle
                .compose(lifecycleHandler.load(R.id.leads_request_id))
                .doOnSubscribe(leadsView::showLoading)
                .doAfterTerminate(leadsView::hideLoading)
                .subscribe(leadsView::showLeads, leadsView::showError);
    }
}
