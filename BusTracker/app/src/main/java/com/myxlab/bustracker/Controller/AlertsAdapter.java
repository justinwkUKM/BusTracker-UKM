package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.AlertsData;
import com.myxlab.bustracker.R;

import java.util.List;


/**
 * The type Alerts adapter.
 */
public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.ViewHolder> {

    private List<AlertsData> alertsData;
    private View view;
    private FontChangeCrawler fontChanger;
    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new Alerts adapter.
     *
     * @param alertsData the alerts data
     * @param activity   the activity
     */
    public AlertsAdapter(List<AlertsData> alertsData, FragmentActivity activity) {
        this.alertsData = alertsData;
        this.context = activity;
    }

    @Override
    public AlertsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_item, null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");
        //fontChanger.replaceFonts(parent);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AlertsAdapter.ViewHolder holder, int position) {
        AlertsData alertsData = this.alertsData.get(position);
        fontChanger.replaceFonts((ViewGroup) this.view);

        holder.title.setText(alertsData.getSubject());
        holder.title.setTextColor(context.getResources().getColor(R.color.colorAccent));
        holder.issue.setText(alertsData.getMessage() +" - CreatedAt "+ alertsData.getCreatedAt()+" - ID "+alertsData.getReport_id() +" - RID "+alertsData.getReporter_id());
    }

    @Override
    public int getItemCount() {
        return alertsData.size();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Title.
         */
        TextView title, /**
         * The Issue.
         */
        issue;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.titleAlertItem);
            issue = (TextView) view.findViewById(R.id.issueAlertItem);
        }
    }
}
