package com.myxlab.bustracker.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myxlab.bustracker.Model.AlertsData;
import com.myxlab.bustracker.R;

import java.util.List;


public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.ViewHolder> {

    private List<AlertsData> alertsData;
    private View view;

    public AlertsAdapter(List<AlertsData> alertsData) {
        this.alertsData = alertsData;
    }

    @Override
    public AlertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertsAdapter.ViewHolder holder, int position) {
        AlertsData alertsData = this.alertsData.get(position);
        holder.title.setText(alertsData.getTitle());
        holder.issue.setText(alertsData.getIssue());
    }

    @Override
    public int getItemCount() {
        return alertsData.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,issue;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.titleAlertItem);
            issue = (TextView) view.findViewById(R.id.issueAlertItem);
        }
    }
}
