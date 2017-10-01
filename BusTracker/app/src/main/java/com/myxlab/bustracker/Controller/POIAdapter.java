package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.MainActivity;
import com.myxlab.bustracker.View.SearchFragment;

import java.util.List;

/**
 * The type Poi adapter.
 */
public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder> {

    private List<POI> poiData;
    private View view;
    private Context context;
    private MainActivity mainActivity;
    private SearchFragment searchFragment;
    private FontChangeCrawler fontChanger;


    /**
     * Instantiates a new Poi adapter.
     *
     * @param poiData        the poi data
     * @param context        the context
     * @param mainActivity   the main activity
     * @param searchFragment the search fragment
     */
    public POIAdapter(List<POI> poiData, Context context, MainActivity mainActivity, SearchFragment searchFragment) {
        this.poiData = poiData;
        this.context = context;
        this.mainActivity = mainActivity;
        this.searchFragment = searchFragment;
    }

    @Override
    public POIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new POIAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final POIAdapter.ViewHolder holder, int position) {

        fontChanger.replaceFonts((ViewGroup) this.view);

        POI poi = poiData.get(position);
        holder.name.setText(poi.getName());

        String type = poi.getType();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(context, null);
                POI poi = dbHandler.getPOIs(poiData.get(holder.getAdapterPosition()).getCode());
                //BusStop bs = dbHandler.getBusStops(poiData.get(holder.getAdapterPosition()).getCode());
               // Log.e("bs.getName()",bs.getName());
                mainActivity.infoBottomSheetCall(Double.parseDouble(poi.getLat()), Double.parseDouble(poi.getLon()),poi.getCode(),poi.getType(),true);
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

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Name.
         */
        TextView name;
        /**
         * The Icon.
         */
        ImageView icon;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) view.findViewById(R.id.poi_name);
            icon = (ImageView) view.findViewById(R.id.place_icon);
        }
    }
}
