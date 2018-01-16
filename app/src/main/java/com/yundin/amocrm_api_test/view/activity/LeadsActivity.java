package com.yundin.amocrm_api_test.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yundin.amocrm_api_test.AmoCRMapp;
import com.yundin.amocrm_api_test.R;
import com.yundin.amocrm_api_test.model.api.ApiInterface;
import com.yundin.amocrm_api_test.model.dto.LeadItem;
import com.yundin.amocrm_api_test.presenter.LeadsPresenter;
import com.yundin.amocrm_api_test.view.LoadingDialog;
import com.yundin.amocrm_api_test.view.LoadingView;
import com.yundin.amocrm_api_test.view.adapter.LeadsRecyclerAdapter;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Yundin Vladislav
 */
public class LeadsActivity extends AppCompatActivity implements LeadsView {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.error_view)
    TextView errorView;
    @Inject
    ApiInterface apiInterface;
    private LoadingView loadingView;
    private LeadsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);

        loadingView = LoadingDialog.view(getSupportFragmentManager());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new LeadsRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        AmoCRMapp.getAppComponent().injectLeadsActivity(this);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        LeadsPresenter presenter = new LeadsPresenter(this, lifecycleHandler, apiInterface);
        presenter.init();
    }

    @Override
    public void showLoading() {
        loadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        loadingView.hideLoading();
    }

    @Override
    public void showLeads(List<LeadItem> leads) {
        adapter.setDataWithNotify(leads);
        hideLoading();
    }

    @Override
    public void showError(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
