package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.NavigationActivity;

import java.util.List;

/**
 * The type Current buses adapter.
 */
public class CurrentBusesAdapter extends RecyclerView.Adapter<CurrentBusesAdapter.ViewHolder> {

    /**
     * The constant TAG used for Logging.
     */
    public static final String TAG = CurrentBusesAdapter.class.getSimpleName();
    private BusStop busStop;
    private View view;
    private NavigationActivity navigationActivity;
    private FontChangeCrawler fontChanger;
    /**
     * The Context.
     */
    Context context;
    private List<Bus> busList;
    private List<Bus> busListToAdd;
    private String busName;


    /**
     * Instantiates a new Current buses adapter.
     *
     * @param buses              the buses
     * @param context            the context
     * @param busName            the bus name
     * @param navigationActivity the navigation activity
     * @param busStop            the bus stop
     */
    public CurrentBusesAdapter(List<Bus> buses, Context context, String busName, NavigationActivity navigationActivity, BusStop busStop) {
        this.busList = buses;
        this.context = context;
        this.busName = busName;
        this.navigationActivity = navigationActivity;
        this.busStop = busStop;
        Log.d(TAG, "CurrentBusesAdapter()");
        Log.e(TAG, "size:"+busList.size());


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder()");
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item_current,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");
        return new CurrentBusesAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        fontChanger.replaceFonts((ViewGroup) this.view);
        setData(holder, position);
    }

/*    private void setData(final ViewHolder holder, final int position) {
        fontChanger.replaceFonts((ViewGroup) this.view);
        String busPlate = busList.get(position).getPlate();
        String busStopJustPssed = busList.get(position).getName();
        holder.tvPlate.setText(busPlate);
        holder.tvJustPassed.setText(busStopJustPssed);
        holder.locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInstance.getInstance().getMainActivity().ETABottomSheetCall(busStop.getName(), busList.get(holder.getAdapterPosition()).getName());
                navigationActivity.finish();
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, R.string.under_development, Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void setData(final ViewHolder holder, final int position) {

        if (busName.equals(busList.get(position).getName())){
            Log.d(TAG, "onBindViewHolder()");


            if (busList.get(position).getName().equals(busName)){
                String busPlate  = busList.get(position).getPlate();
                String busStopJustPssed  = "Bus Stops Remaining "+ busList.get(position).getId();
                holder.tvPlate.setText(busPlate);
                holder.tvJustPassed.setText(busStopJustPssed);
            }

            holder.locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*TODO Change the holder.getAdapterPosition()).getName() to plate number in Here and in the API for ETA*/
                    UserInstance.getInstance().getMainActivity().ETABottomSheetCall(busStop.getName(),busList.get(holder.getAdapterPosition()).getPlate(), busList.get(holder.getAdapterPosition()).getLat(), busList.get(holder.getAdapterPosition()).getLon());
                    navigationActivity.finish();

                }
            });

            holder.schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, R.string.under_development, Toast.LENGTH_SHORT).show();
                }
            });
        }else {

            simpleAnim(holder.itemView);
            view.setVisibility(View.GONE);
            //holder.itemView.setVisibility(View.GONE);
            /*new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    removeAt(position);
                }
            }, 100);*/
        }

    }



    private void simpleAnim(View itemView) {
        ViewAnimator.animate(itemView)
                .zoomIn().fadeIn().interpolator(new DecelerateInterpolator())
                .duration(1500)
                .start();
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Locate.
         */
        RelativeLayout locate, /**
         * The Schedule.
         */
        schedule;
        /**
         * The Tv plate.
         */
        TextView tvPlate, /**
         * The Tv just passed.
         */
        tvJustPassed;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder()");
            locate = (RelativeLayout) view.findViewById(R.id.locatebtn);
            schedule = (RelativeLayout) view.findViewById(R.id.schedulebtn);
            tvPlate = (TextView) view.findViewById(R.id.tvbusLocate);
            tvJustPassed = (TextView) view.findViewById(R.id.tvbusSchedule);
        }
    }
}
