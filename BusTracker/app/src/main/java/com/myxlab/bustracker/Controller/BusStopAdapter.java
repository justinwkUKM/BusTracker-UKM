package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.Route;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.MapsFragment;
import com.myxlab.bustracker.View.NavigationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Bus stop adapter.
 */
public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    /**
     * The constant TAG.
     */
    public static final String TAG = BusStopAdapter.class.getSimpleName();
    private List<String> bus;
    private BusStop busStop;
    private View view;
    private NavigationActivity navigationActivity;
    private FontChangeCrawler fontChanger;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Maps fragment.
     */
    MapsFragment mapsFragment;
    //List of all the active buses.
    private List<Bus> busListAll;
    //List of all the available buses for the selected journey.
    private List<Bus> busListCurrentAll;
    //List of all the available buses of one route type.
    private List<Bus> busListSelected;

    private List<BusStop> busStopList;
    /**
     * The Route list.
     */
    public List<Route> routeList;

    private CurrentBusesAdapter currentBusesAdapter;
    private RecyclerView recyclerView;


    /**
     * Instantiates a new Bus stop adapter.
     *
     * @param buses              the buses
     * @param busStop            the bus stop
     * @param context            the context
     * @param navigationActivity the navigation activity
     */
    public BusStopAdapter(List<String> buses, BusStop busStop, Context context, NavigationActivity navigationActivity) {
        this.bus = buses;
        this.busStop = busStop;
        this.context = context;
        this.navigationActivity = navigationActivity;
        this.mapsFragment = new MapsFragment();
        busListCurrentAll = new ArrayList<>();
        busListSelected = new ArrayList<>();
        busStopList = new ArrayList<>();


        busStopList = UserInstance.getInstance().getBusStopList();
        busListAll = UserInstance.getInstance().getBuses();
        routeList =  UserInstance.getInstance().getRouteList();



        if (!bus.isEmpty()) {
            for (int i = 0; i < bus.size(); i++) {
                createList(bus.get(i), busListAll, busListCurrentAll,"fromConstructor");
            }
        }
        Log.d(TAG, "currentSize"+ busListCurrentAll.size() +"    allSize"+ busListAll.size());
    }

    private void createList(String busName, List<Bus> busListFrom, List<Bus> busListTo, String Tag) {
        for (int i = 0; i < busListFrom.size(); i++) {
            if (busName.equals(busListFrom.get(i).getName())){
                busListTo.add(busListFrom.get(i));
            }
        }
        if (!busListTo.isEmpty()) {
            String temp = "";
            for (int i = 0; i < busListTo.size(); i++) {
                temp+= busListTo.get(i).getName()+":"+busListTo.get(i).getPlate()+"\n";
            }
            Log.e(Tag , temp);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");

        context = navigationActivity.getApplicationContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");

        if(busListCurrentAll.size()!=0){
            navigationActivity.hideButton();
        }
        return new BusStopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder()");
        fontChanger.replaceFonts((ViewGroup) this.view);
        String busName  = busListCurrentAll.get(position).getName(); // bus.get(position);
        holder.busName.setText("\""+busName+"\"");
        holder.tvLocate.setText(busListCurrentAll.get(position).getPlate());
        simpleBlinkAnim(holder.tvLocate);

        Random random = new Random();

        int rand = random.nextInt(25);


        int currentBusStop = Integer.parseInt(busListCurrentAll.get(position).getCurrentBusStop());

        //Log.e(TAG, "rand:"+rand +" nearest:"+ nearest +" current:"+ currentBusStopp + " Name:"+busStopName);
        //holder.tvSchedule.setAlpha(0.2);

        switch (busName){


            case "Bus Zone 6":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            case "Bus Zone 6 (Malam)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            case "Bus Zone 2:":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            case "Bus Zone 2 (Rabu)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            case "Bus Zone 3U":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            case "Bus Zon 3u (Rabu)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            default:  holder.imageViewBusIcon.setImageResource(R.drawable.ic_directions);
        }

/*
        switch ("" + busName.charAt(9)){


            case "6":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

           *//* case "Bus Zone 6 (Malam)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;*//*

            case "2":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

           *//* case "Bus Zone 2 (Rabu)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;*//*

            case "3":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;

            *//*case "Bus Zone 3U (Rabu)":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                calculateRemainingBusStops(busName, currentBusStop, holder.tvSchedule);
                break;*//*

            default:  holder.imageViewBusIcon.setImageResource(R.drawable.ic_directions);
        }*/

        Log.d ("BusStop" , busName);

        simpleAnim(holder.imageViewBusIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*TODO Change the holder.getAdapterPosition()).getName() to plate number in Here and in the API for ETA*/
                UserInstance.getInstance().getMainActivity().ETABottomSheetCall(busStop.getName(),busListCurrentAll.get(holder.getAdapterPosition()).getName(),busListCurrentAll.get(holder.getAdapterPosition()).getLat(),busListCurrentAll.get(holder.getAdapterPosition()).getLon());
                navigationActivity.finish();

            }
        });

        // comment

        holder.tvSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, R.string.under_development, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private int calculateRemainingBusStops(String busName, int currentBusStop, TextView tvSchedule) {
        String zonName = busName.replace("Bus ", "");
        Log.d(TAG, zonName);
        int remainingBusStops = 0;
        String busStopJustPssed = "";
        String buscurrentBSName = "";
        int nearest = UserInstance.getInstance().getNearestBusStopIndex();
        String nearestStopInCurrentRoute = UserInstance.getInstance().getBusStopList().get(nearest).getCode();
        Log.d("nearestStop", nearestStopInCurrentRoute);


        for (int h = 0; h < routeList.size(); h++) {

            String routeName = routeList.get(h).getRouteName();


                if (zonName.equals(routeName)){
                    List<BusStop> newBusStopList = routeList.get(h).getBusStopList();
                    Log.e(TAG, routeName);
                    buscurrentBSName =  newBusStopList.get(currentBusStop).getName();

                    for (int g = 0; g < newBusStopList.size(); g++) {
                        if(newBusStopList.get(g).getName().equals(nearestStopInCurrentRoute)){
                            remainingBusStops = (g + 1)  - (currentBusStop+1) ;
                            if (remainingBusStops<0){
                                remainingBusStops = 0;
                            }
                            Log.e(TAG," N:"+ (g+1) +"-" + nearestStopInCurrentRoute +" C:"+ (currentBusStop+1) +"-" +newBusStopList.get(currentBusStop).getName()+ " R"+remainingBusStops+"");
                        }
                    }



                }




        }
        busStopJustPssed  = "Just Passed "+ buscurrentBSName +"\nBus Stops Remaining "+ remainingBusStops;
        tvSchedule.setText(busStopJustPssed);
        return remainingBusStops;
    }

    private void simpleAnim(View itemView) {
        ViewAnimator.animate(itemView)
                .zoomIn().fadeIn().interpolator(new DecelerateInterpolator())
                .duration(1500)
                .start();
    }
    private void simpleBlinkAnim(View itemView) {
        ViewAnimator.animate(itemView)
                .flash().interpolator(new DecelerateInterpolator())
                .duration(3000)
                .start();
    }

    /*private void settingAdapter(List<Bus> list, String busName) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_currentBuses);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //currentBusesAdapter = new CurrentBusesAdapter(list, context, busName, navigationActivity, busStop);
        //recyclerView.setAdapter(currentBusesAdapter);
        //currentBusesAdapter.notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return busListCurrentAll.size();
    }

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Bus name.
         */
        TextView busName, /**
         * The Tv locate.
         */
        tvLocate, /**
         * The Tv schedule.
         */
        tvSchedule;

        /**
         * The Image view bus icon.
         */
        ImageView imageViewBusIcon;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder()");
            busName = (TextView) view.findViewById(R.id.titleBusItem);
            imageViewBusIcon = (ImageView) view.findViewById(R.id.busIcon);
            tvLocate = (TextView) view.findViewById(R.id.tvBusLocate);
            tvSchedule = (TextView) view.findViewById(R.id.tvBusSchedule);

        }
    }
}
