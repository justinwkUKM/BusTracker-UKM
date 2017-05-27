package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.MainActivity;
import com.myxlab.bustracker.View.SearchFragment;

import java.util.List;

public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder> {

    private List<POI> poiData;
    private View view;
    private Context context;
    private MainActivity mainActivity;
    private SearchFragment searchFragment;

    public POIAdapter(List<POI> poiData, Context context, MainActivity mainActivity, SearchFragment searchFragment) {
        this.poiData = poiData;
        this.context = context;
        this.mainActivity = mainActivity;
        this.searchFragment = searchFragment;
    }

    @Override
    public POIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new POIAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final POIAdapter.ViewHolder holder, int position) {
        POI poi = poiData.get(position);
        holder.name.setText(poi.getName());

        String type = poi.getType();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(context, null);
                POI poi = dbHandler.getPOIs(poiData.get(holder.getAdapterPosition()).getCode());
                mainActivity.infoBottomSheetCall(Double.parseDouble(poi.getLat()), Double.parseDouble(poi.getLon()),poi.getName(),poi.getType(),true);
                searchFragment.fragmentManager.popBackStack();
            }
        });

        switch (type){
            case "building" : holder.icon.setImageResource(R.drawable.ic_building);
                break;
            case "faculty" : holder.icon.setImageResource(R.drawable.ic_faculty);
                break;
            case "hall" : holder.icon.setImageResource(R.drawable.ic_account_balance);
                break;
            case "health center" : holder.icon.setImageResource(R.drawable.ic_local_hospital);
                break;
            case "hostel" : holder.icon.setImageResource(R.drawable.ic_hotel);
                break;
            case "library" : holder.icon.setImageResource(R.drawable.ic_local_library);
                break;
            case "recreation" : holder.icon.setImageResource(R.drawable.ic_local_florist);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return poiData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) view.findViewById(R.id.poi_name);
            icon = (ImageView) view.findViewById(R.id.place_icon);
        }
    }
}