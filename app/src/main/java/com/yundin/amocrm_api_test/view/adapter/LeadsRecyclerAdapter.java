package com.yundin.amocrm_api_test.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yundin.amocrm_api_test.R;
import com.yundin.amocrm_api_test.model.dto.LeadItem;

import java.util.Collections;
import java.util.List;

/**
 * @author Yundin Vladislav
 */
public class LeadsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<LeadItem> data = Collections.emptyList();

    public LeadsRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataWithNotify(List<LeadItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lead_item, parent, false);
        return new LeadsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LeadItem item = data.get(position);
        LeadsViewHolder castedHolder = (LeadsViewHolder) holder;

        String statusTitle = item.getStatusTitle();
        String statusTitleText = context.getString(R.string.statusText);
        castedHolder.statusTitle.setText(String.format("%s: %s", statusTitleText, statusTitle));

        String name = item.getName();
        String nameText = context.getResources().getString(R.string.name_text);
        castedHolder.name.setText(String.format("%s: %s", nameText, name));

        String date = new java.text.SimpleDateFormat("dd/MM/yy HH:mm").format(new java.util.Date(item.getTimestamp() * 1000));
        String dateText = context.getResources().getString(R.string.date_text);
        castedHolder.creationDate.setText(String.format("%s: %s", dateText, date));

        String sale;
        if (item.getSale() == 0) {
            sale = context.getResources().getString(R.string.unknown);
        } else {
            sale = String.valueOf(item.getSale());
        }
        String saleText = context.getResources().getString(R.string.sale_text);
        castedHolder.sale.setText(String.format("%s: %s", saleText, sale));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LeadsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.statusTitle)
        TextView statusTitle;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.creation_date)
        TextView creationDate;
        @BindView(R.id.sale)
        TextView sale;

        public LeadsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
