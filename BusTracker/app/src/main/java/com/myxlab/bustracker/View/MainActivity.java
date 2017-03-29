package com.myxlab.bustracker.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.model.LatLng;
import com.myxlab.bustracker.Controller.PagerAdapter;
import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String BUS_STOP_KEY = "BUS STOP KEY";
    public static final String BUS_STOP = "busStop";
    public static final String BUS = " ";
    public TabLayout tabLayout;
    private ArrayList<String> tabs;
    private ViewPager viewPager = null;
    private FloatingActionMenu fab_menu;
    private BottomSheetBehavior infoBottomSheet, bottomSheetBusStop,bottomSheetBus,bottomSheetETA;
    private LinearLayout infoHeader;
    private TextView tvInfoTitle, tvInfoTitleExpand, busTitle, busStopTitle, tv_chip_text,busETAName,busETAFrom,busETATV, etaText;
    private NetworkImageView ivInfoIMG;
    public Context context;
    public EditText search;
    public ImageView search_cancel, iv_chip_close;
    public RelativeLayout rv_search_chip, rv_bsBusStopIco;
    public String category;
    public boolean expand_toggle = false;
    private ProgressBar etaProgress;
    private Auth auth;
    private int busStopIndex;
    public MapsFragment mapsFragment;
    public SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        UserInstance.getInstance().setMainActivity(this);
        auth = new Auth();

        initTabs();
        initViewPager();

        tabLayout.setupWithViewPager(viewPager);

        initBottomSheet();
        infoHeader = (LinearLayout) findViewById(R.id.infoHeader);

        ivInfoIMG = (NetworkImageView) findViewById(R.id.infoIMG);

        initTabsIcons();
        initFab();
        fabMenuIconChange();
        infoBottomSheetListener();
        initSearch();
        textWatcher();
    }

    private void initSearch() {
        search = (EditText) findViewById(R.id.search);
        search_cancel = (ImageView) findViewById(R.id.clear_search);
        rv_search_chip = (RelativeLayout) findViewById(R.id.search_chip);
        iv_chip_close = (ImageView) findViewById(R.id.chip_close);
        tv_chip_text = (TextView) findViewById(R.id.chip_text);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (search.hasFocus()) {

                    Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_layout);

                    if (fragment == null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, new SearchFragment()).addToBackStack(null).commit();
                    }

                }
            }
        });

        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.getText().clear();
                search_cancel.setVisibility(View.GONE);
            }
        });

        iv_chip_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_search_chip.setVisibility(View.GONE);
                search.getText().clear();
                searchFragment.populateData();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (search.isFocused()) {
                Rect outRect = new Rect();
                search.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    search.clearFocus();
                    hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void hideKeyboard() {
        if (search != null) {
            InputMethodManager imanager = (InputMethodManager) getApplication()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imanager.hideSoftInputFromWindow(search.getWindowToken(), 0);

        }

    }

    private void initBottomSheet() {
        infoBottomSheet = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
        infoBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        tvInfoTitle = (TextView) findViewById(R.id.infoTitle);
        tvInfoTitleExpand = (TextView) findViewById(R.id.infoTitleExpand);

        bottomSheetBus = BottomSheetBehavior.from(findViewById(R.id.bottomSheetBus));
        bottomSheetBus.setState(BottomSheetBehavior.STATE_HIDDEN);
        busTitle = (TextView) findViewById(R.id.busTitle);

        bottomSheetETA = BottomSheetBehavior.from(findViewById(R.id.bottomSheetETA));
        bottomSheetETA.setState(BottomSheetBehavior.STATE_HIDDEN);
        busETATV = (TextView) findViewById(R.id.busETATV);
        busETAFrom = (TextView) findViewById(R.id.busETAFrom);
        busETAName = (TextView) findViewById(R.id.busETAName);
        etaText = (TextView) findViewById(R.id.etaText);
        etaProgress = (ProgressBar) findViewById(R.id.etaProgress);

        bottomSheetBusStop = BottomSheetBehavior.from(findViewById(R.id.bottomSheetBusStop));
        bottomSheetBusStop.setState(BottomSheetBehavior.STATE_HIDDEN);
        busStopTitle = (TextView) findViewById(R.id.busStopTitle);
        rv_bsBusStopIco = (RelativeLayout) findViewById(R.id.bsBusStopIco);
        rv_bsBusStopIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.putExtra(BUS_STOP_KEY,busStopIndex);
                startActivity(intent);
                bottomSheetBusStop.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void initFab() {
        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_main);
        fab_menu.setVisibility(View.VISIBLE);
        fab_menu.setClosedOnTouchOutside(true);

        fab_menu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fab_menu.isOpened()) {
                    if (tabLayout.getSelectedTabPosition() == 1) {
                        tabLayout.getTabAt(0).select();
                    }
                    mapsFragment.mapCamera();
                } else {
                    fab_menu.open(true);
                }
            }
        });

        FloatingActionButton fab_schedule = (FloatingActionButton) findViewById(R.id.fab_schedule);
        fab_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInstance.getInstance().getVolleyApp().updateDB(getString(R.string.url_poi_list),getApplicationContext());
            }
        });

        FloatingActionButton fab_report = (FloatingActionButton) findViewById(R.id.fab_report);
        fab_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                auth.checkOutAuth(getApplicationContext());
            }
        });
    }

    private void initTabs() {
        tabs = new ArrayList<>();
        tabs.add("Map");
        tabs.add("Issue");
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
    }

    private void initTabsIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_map);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_priority_high);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FFD43A2F"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void fabMenuIconChange() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fab_menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab_menu.getMenuIconView().setImageResource(fab_menu.isOpened()
                        ? R.drawable.ic_gps : R.drawable.ic_directions_bus_white);
            }
        });
        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fab_menu.setIconToggleAnimatorSet(set);
    }

    public void infoBottomSheetCall(Double lat, Double lon, String name, String type, Boolean focus) {
        closeBottomSheet();
        fab_menu.setVisibility(View.GONE);
        infoBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        tvInfoTitle.setText(name);
        tvInfoTitleExpand.setText(name);
        mapsFragment.addMarker(lat,lon, name, type, focus);
//        imageLoaders(pic);
    }

    public void BusBottomSheetCall(String title) {
        closeBottomSheet();
        fab_menu.setVisibility(View.GONE);
        bottomSheetBus.setState(BottomSheetBehavior.STATE_COLLAPSED);
        busTitle.setText(title);
    }

    public void BusStopBottomSheetCall(int busStopIndex) {
        closeBottomSheet();
        fab_menu.setVisibility(View.GONE);
        this.busStopIndex = busStopIndex;
        bottomSheetBusStop.setState(BottomSheetBehavior.STATE_COLLAPSED);
        busStopTitle.setText(UserInstance.getInstance().getBusStopList().get(busStopIndex).getName());
    }

    public void ETABottomSheetCall(String bustop, String bus) {
        closeBottomSheet();
        fab_menu.setVisibility(View.GONE);
        bottomSheetETA.setState(BottomSheetBehavior.STATE_EXPANDED);
        busETAName.setText(bus);

        etaText.setVisibility(View.GONE);
        etaProgress.setVisibility(View.VISIBLE);
        busETATV.setVisibility(View.GONE);
        busETAFrom.setText(R.string.loading);
        UserInstance.getInstance().getVolleyApp().getETA(getString(R.string.url_eta),bustop, bus, this);
    }

    private void infoBottomSheetListener() {

        infoBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tabLayout.setVisibility(View.GONE);
                    infoHeader.setVisibility(View.GONE);
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tabLayout.setVisibility(View.VISIBLE);
                    infoHeader.setVisibility(View.VISIBLE);
                    fab_menu.setVisibility(View.GONE);
                }

                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab_menu.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetBusStop.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab_menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        bottomSheetBus.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab_menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetETA.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab_menu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        if (search.isFocused()) {
            search.clearFocus();
        }

        if (infoBottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED || infoBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED ||
                bottomSheetBusStop.getState() == BottomSheetBehavior.STATE_COLLAPSED
                || bottomSheetBus.getState() == BottomSheetBehavior.STATE_COLLAPSED
                || bottomSheetETA.getState() == BottomSheetBehavior.STATE_EXPANDED) {

            closeBottomSheet();
            tabLayout.setVisibility(View.VISIBLE);

        } else {
            super.onBackPressed();
        }
    }

    private void imageLoaders(String string) {
        ImageLoader imageLoader = UserInstance.getInstance().getVolleyApp().getInstance(context).getImageLoader();
        ivInfoIMG.setImageUrl("http://faizhasan.info/pic/" + string, imageLoader);
    }

    private void textWatcher() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (charSequence != null && charSequence.length() != 0 && rv_search_chip.getVisibility() == View.GONE) {

                    DBHandler dbHandler = new DBHandler(MainActivity.this, null);
                    List<POI> poiList = dbHandler.searchPOIs(charSequence.toString());
                    searchFragment.refreshList(poiList, charSequence.toString());

                }

                if (search.getText().toString().length() != 0) {

                    search_cancel.setVisibility(View.VISIBLE);

                }

                if (rv_search_chip.getVisibility() == View.VISIBLE) {

                    DBHandler dbHandler = new DBHandler(MainActivity.this, null);
                    List<POI> poiList;
                    if (charSequence != null) {
                        poiList = dbHandler.searchPOICat(charSequence.toString(), category);
                        searchFragment.refreshList(poiList, charSequence.toString());
                    }

                    search_cancel.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (search.getText().toString().length() == 0){

                    search_cancel.setVisibility(View.GONE);

                    if (rv_search_chip.getVisibility() != View.VISIBLE)
                    {
                        searchFragment.populateData();
                    }

                }

            }
        });
    }

    public void chipSearch(View view) {

        switch (view.getId()){

            case R.id.faculty_expand :
                category = "faculty";
                tv_chip_text.setText(getString(R.string.faculty));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.hostel_expand :
                category = "hostel";
                tv_chip_text.setText(getString(R.string.hostel));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.hall_expand :
                category = "hall";
                tv_chip_text.setText(getString(R.string.hall));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.building_expand :
                category = "building";
                tv_chip_text.setText(getString(R.string.building));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.recreation_expand :
                category = "recreation";
                tv_chip_text.setText(getString(R.string.recreation));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.health_center_expand :
                category = "health center";
                tv_chip_text.setText(getString(R.string.health_center));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.library_expand :
                if (!expand_toggle){

                    searchFragment.ll_expand_layout_category.setVisibility(View.VISIBLE);
                    searchFragment.iv_library_expand_ico.setImageResource(R.drawable.ic_local_library);
                    searchFragment.tv_library_expand_text.setText(R.string.library);

                }else {
                    category = "library";
                    tv_chip_text.setText(getString(R.string.library));
                    rv_search_chip.setVisibility(View.VISIBLE);
                }
                break;

        }

        if (view.getId() == R.id.library_expand){

            if (expand_toggle){
                searchCatRefreshList(category, search.getText().toString(), getApplicationContext());
            } else {
                expand_toggle = true;
            }

        } else  {
            searchCatRefreshList(category, search.getText().toString(), getApplicationContext());
        }

    }

    public void searchCatRefreshList(String category, String key, Context context){

        if (key.length() == 0){
            key = "";
        }

        DBHandler dbHandler = new DBHandler(context, null);
        List<POI> poiList = dbHandler.searchPOICat(key, category);
        searchFragment.refreshList(poiList, key);

    }

    private void closeBottomSheet(){

        infoBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBusStop.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBus.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetETA.setState(BottomSheetBehavior.STATE_HIDDEN);

        if (fab_menu.getVisibility() == View.GONE){
            fab_menu.setVisibility(View.VISIBLE);
        }

    }

    public void setETA(String busETA, String busETAto, Double lat, Double lon, Boolean status){

        etaText.setVisibility(View.VISIBLE);
        etaProgress.setVisibility(View.GONE);
        busETATV.setText(busETA);
        busETATV.setVisibility(View.VISIBLE);
        busETAFrom.setText(busETAto);

        if (status){
            mapsFragment.focusCamera(new LatLng(lat,lon));
        }

    }
}
