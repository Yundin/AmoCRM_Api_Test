package com.yundin.amocrm_api_test.view.activity;

import com.yundin.amocrm_api_test.model.dto.LeadItem;
import com.yundin.amocrm_api_test.view.LoadingView;

import java.util.List;

/**
 * @author Yundin Vladislav
 */
public interface LeadsView extends LoadingView {

    void showLeads(List<LeadItem> leads);

    void showError(Throwable t);
}
