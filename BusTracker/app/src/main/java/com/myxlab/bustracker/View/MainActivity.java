package com.myxlab.bustracker.View;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.myxlab.bustracker.BaseActivity;
import com.myxlab.bustracker.Controller.PagerAdapter;
import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.R.id.input;

public class MainActivity extends BaseActivity {

    public static final String BUS_STOP_KEY = "BUS STOP KEY";
    public static final String BUS_STOP = "busStop";
    public static final String BUS = "bus";
    public TabLayout tabLayout;
    private ArrayList<String> tabs;
    private ViewPager viewPager = null;
    private FloatingActionMenu fab_menu;
    private BottomSheetBehavior infoBottomSheet, bottomSheetBusStop, bottomSheetBus, bottomSheetETA;
    private LinearLayout infoHeader, bottomSheetLLaddButtons;
    private TextView tvInfoTitle, infoSwipeTitle, tvInfoTitleExpand, busTitle, busStopTitle, tv_chip_text, busETAName, busETAFrom, busETATV, etaText, tvpoiBusStops, tvPoiAddress, tvPoiPhone, tvPoiEmail;
    private NetworkImageView ivInfoIMG;
    public Context context;
    public EditText search;
    public ImageView search_cancel, iv_chip_close, icon_search;
    public RelativeLayout rv_search_chip, rv_bsBusStopIco;
    public String category;
    public boolean expand_toggle = false;
    private ProgressBar etaProgress;
    private Auth auth;
    private int busStopIndex;
    public MapsFragment mapsFragment;
    public SearchFragment searchFragment;
    public CardView searchCardView;
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //changing statusbar color

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        context = getApplicationContext();
        UserInstance.getInstance().setMainActivity(this);
        auth = new Auth();

        initTabs();
        initViewPager();

        tabLayout.setupWithViewPager(viewPager);

        initBottomSheet();
        //infoHeader = (LinearLayout) findViewById(R.id.infoHeader);
        ivInfoIMG = (NetworkImageView) findViewById(R.id.infoIMG);
        searchCardView = (CardView) findViewById(R.id.search_card_view);
        searchCardView.setCardElevation(5);
        icon_search = (ImageView) findViewById(R.id.icon_search);

        initTabsIcons();
        initFab();
        fabMenuIconChange();
        infoBottomSheetListener();
        initSearch();
        textWatcher();
    }
        /*try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 4);
                    return;
                }else {

                    Log.e("Success Stuff here", "" + "Success");
                    recreate();
                }

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length >0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                // Success Stuff here

            }
            else{
                // Failure Stuff
                Log.e("Failure Stuff here", "" + requestCode);
            }
        }

    }*/


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
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scalein);
                    searchCardView.startAnimation(anim);
                    searchCardView.setCardElevation(0);
                    anim.setFillAfter(true);
                    simpleAnimationfadein(icon_search);
                    icon_search.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black));

                    Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_layout);

                    if (fragment == null) {

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_layout, new SearchFragment()).addToBackStack(null).commit();
                    }

                } else {

                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scaleout);
                    searchCardView.startAnimation(anim);
                    anim.setFillAfter(true);
                    searchCardView.setCardElevation(5);
                    simpleAnimationfadein(icon_search);
                    icon_search.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));

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
    private FontChangeCrawler fontChanger;
    private void initBottomSheet() {
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");

        infoBottomSheet = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
        fontChanger.replaceFonts((ViewGroup) findViewById(R.id.bottomSheetLayout) );
        infoBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        tvInfoTitle = (TextView) findViewById(R.id.infoSwipeTitle);
        tvInfoTitleExpand = (TextView) findViewById(R.id.infoTitleExpand);
        infoSwipeTitle = (TextView) findViewById(R.id.infoSwipeTitle);
        tvPoiAddress = (TextView) findViewById(R.id.tvpoiAddress);
        tvPoiPhone = (TextView) findViewById(R.id.tvpoiPhone);
        tvPoiEmail = (TextView) findViewById(R.id.tvpoiEmail);
        tvpoiBusStops = (TextView) findViewById(R.id.tv_poi_nearby_busStops);
        bottomSheetLLaddButtons = (LinearLayout) findViewById(R.id.llBusStopButtons);
        //bottomSheetLLaddButtons.setWeightSum(1);


        bottomSheetBus = BottomSheetBehavior.from(findViewById(R.id.bottomSheetBus));
        fontChanger.replaceFonts((ViewGroup) findViewById(R.id.bottomSheetBus) );
        bottomSheetBus.setState(BottomSheetBehavior.STATE_HIDDEN);
        busTitle = (TextView) findViewById(R.id.busTitle);

        bottomSheetETA = BottomSheetBehavior.from(findViewById(R.id.bottomSheetETA));
        fontChanger.replaceFonts((ViewGroup) findViewById(R.id.bottomSheetETA) );
        bottomSheetETA.setState(BottomSheetBehavior.STATE_HIDDEN);
        busETATV = (TextView) findViewById(R.id.busETATV);
        busETAFrom = (TextView) findViewById(R.id.busETAFrom);
        busETAName = (TextView) findViewById(R.id.busETAName);
        etaText = (TextView) findViewById(R.id.etaText);
        etaProgress = (ProgressBar) findViewById(R.id.etaProgress);

        bottomSheetBusStop = BottomSheetBehavior.from(findViewById(R.id.bottomSheetBusStop));
        fontChanger.replaceFonts((ViewGroup) findViewById(R.id.bottomSheetBusStop) );
        bottomSheetBusStop.setState(BottomSheetBehavior.STATE_HIDDEN);
        busStopTitle = (TextView) findViewById(R.id.busStopTitle);
        rv_bsBusStopIco = (RelativeLayout) findViewById(R.id.bsBusStopIco);
        rv_bsBusStopIco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.putExtra(BUS_STOP_KEY, busStopIndex);
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

        FloatingActionButton fab_alert = (FloatingActionButton) findViewById(R.id.fab_alert);
        fab_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "recreate", Toast.LENGTH_SHORT).show();
                recreate();
            }
        });

        FloatingActionButton fab_schedule = (FloatingActionButton) findViewById(R.id.fab_schedule);
        fab_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInstance.getInstance().getVolleyApp().updatePOIDB(getString(R.string.url_poi_list), getApplicationContext());
                UserInstance.getInstance().getVolleyApp().updateBSDB(getString(R.string.url_bus_stop_list), getApplicationContext());

            }
        });

        FloatingActionButton fab_report = (FloatingActionButton) findViewById(R.id.fab_report);
        fab_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                auth.checkOutAuth(getApplicationContext());*/
                DBHandler dbHandler = new DBHandler(context, null);
                List<BusStop> bs = dbHandler.getBusStopList();
                for(int i = 0; i < bs.size(); i++) {
                    //System.out.println(bs.get(i).getName());
                    Log.e("bs.getName()",bs.get(i).getName());
                }


                List<POI> pois = dbHandler.getPOIs();
                for(int i = 0; i < pois.size(); i++) {
                    //System.out.println(bs.get(i).getName());
                    Log.e("pois.getName()",pois.get(i).getName());
                    Log.e("pois.getEmail()",pois.get(i).getEmail());
                    Log.e("pois.getWebsite()",pois.get(i).getWebsite());
                }

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
        Drawable mapIcon = ContextCompat.getDrawable(context, R.drawable.ic_map);
        tabLayout.getTabAt(0).setIcon(mapIcon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_priority_high);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.SRC_IN);
                Log.e("Tab", tab.getPosition()+"");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#666666"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1){
                    AlertsFragment alertsFragment = new AlertsFragment();
                    alertsFragment.callUrl();
                }
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

    public void infoBottomSheetCall(Double lat, Double lon, String code, String type, Boolean focus) {
        closeBottomSheet();
        UserInstance.getInstance().getVolleyApp().getPoiBusStops(getString(R.string.url_poi_bus_stop_list), code, this);
        DBHandler dbHandler = new DBHandler(MainActivity.this, null);
        POI poiInfo = dbHandler.getPOIs(code);
        String website = poiInfo.getWebsite();
        String phone = poiInfo.getPhone();
        String email = poiInfo.getEmail();
        String name = poiInfo.getName();
        Double nLat = Double.valueOf(poiInfo.getLat());
        Double nLon = Double.valueOf(poiInfo.getLon());



        fab_menu.setVisibility(View.GONE);
        infoBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //tvInfoTitle.setText(name);
        tvInfoTitleExpand.setText(name);
        tvPoiPhone.setText(phone);
        tvPoiAddress.setText(website);
        tvPoiEmail.setText(email);

        mapsFragment.addMarker(lat, lon, code, type, focus);
        imageLoaders(code);




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
        UserInstance.getInstance().getVolleyApp().getETA(getString(R.string.url_eta), bustop, bus, this);
    }

    private void infoBottomSheetListener() {
        infoBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tabLayout.setVisibility(View.GONE);
                    infoSwipeTitle.setVisibility(View.GONE);
                    //infoHeader.setVisibility(View.GONE);
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tabLayout.setVisibility(View.VISIBLE);
                    infoSwipeTitle.setVisibility(View.VISIBLE);
                    //infoHeader.setVisibility(View.VISIBLE);
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
                //Log.e("MainActivity","onSlide");
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
        ivInfoIMG.setImageUrl("http://bt.faizhasan.info/pages/img/poi/" + string+".jpg", imageLoader);
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

                if (search.getText().toString().length() == 0) {

                    search_cancel.setVisibility(View.GONE);

                    if (rv_search_chip.getVisibility() != View.VISIBLE || rv_search_chip != null) {

                        if(searchFragment != null){
                            searchFragment.populateData();
                        }else {
                            Log.e("searchFragment","NULL");
                        }

                    }

                }

            }
        });
    }

    public void chipSearch(View view) {

        switch (view.getId()) {
            case R.id.faculty_expand:
                category = "faculty";
                tv_chip_text.setText(getString(R.string.faculty));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.hostel_expand:
                category = "hostel";
                tv_chip_text.setText(getString(R.string.hostel));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.hall_expand:
                category = "hall";
                tv_chip_text.setText(getString(R.string.hall));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.building_expand:
                category = "building";
                tv_chip_text.setText(getString(R.string.building));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.recreation_expand:
                category = "recreation";
                tv_chip_text.setText(getString(R.string.recreation));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.health_center_expand:
                category = "health center";
                tv_chip_text.setText(getString(R.string.health_center));
                rv_search_chip.setVisibility(View.VISIBLE);
                break;
            case R.id.library_expand:
                if (!expand_toggle) {
                    searchFragment.ll_expand_layout_category.setVisibility(View.VISIBLE);
                    searchFragment.iv_library_expand_ico.setImageResource(R.drawable.ic_local_library);
                    searchFragment.tv_library_expand_text.setText(R.string.library);
                } else {
                    category = "library";
                    tv_chip_text.setText(getString(R.string.library));
                    rv_search_chip.setVisibility(View.VISIBLE);
                }
                break;

        }

        if (view.getId() == R.id.library_expand) {
            if (expand_toggle) {
                searchCatRefreshList(category, search.getText().toString(), getApplicationContext());
            } else {
                expand_toggle = true;
            }

        } else {
            searchCatRefreshList(category, search.getText().toString(), getApplicationContext());
        }

    }

    public void searchCatRefreshList(String category, String key, Context context) {

        if (key.length() == 0) {
            key = "";
        }

        DBHandler dbHandler = new DBHandler(context, null);
        List<POI> poiList = dbHandler.searchPOICat(key, category);
        searchFragment.refreshList(poiList, key);

    }

    private void closeBottomSheet() {

        mapsFragment.onResume();

        infoBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBusStop.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBus.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetETA.setState(BottomSheetBehavior.STATE_HIDDEN);
        if (fab_menu.getVisibility() == View.GONE) {
            fab_menu.setVisibility(View.VISIBLE);
        }



    }
    Polyline line = null ;
    public void setETA(final String bus, String busETA, String busETAto, Double lat, Double lon, final String polyline, Boolean status) {
        etaText.setVisibility(View.VISIBLE);
        etaProgress.setVisibility(View.GONE);
        String readableETA = convertSecsToReadableFormat(busETA);
        busETATV.setText(readableETA);
        busETATV.setVisibility(View.VISIBLE);
        busETATV.setTextColor(getResources().getColor(R.color.green));
        busETATV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line != null){
                    line.remove();
                }


                // Instantiating the class PolylineOptions to plot polyline in the map
                PolylineOptions polylineOptions = new PolylineOptions();

                // Setting the color of the polyline
                polylineOptions.color(getResources().getColor(R.color.black));

                // Setting the width of the polyline
                polylineOptions.width(8);

                // Adding the taped point to the ArrayList
                //points.add(point);
                List<LatLng> latLngs ;
                switch (bus){
                    case "Bus Zone 6" : latLngs = PolyUtil.decode("ic}P}_glRdA^hAj@nBpAfFpDzDjCp@w@|GyHzAyAtAaBZi@b@mANi@Tg@DMVQp@Wn@MvA_@vC}@lBYrCSb@iAJK^QfASvF_@dA[x@_@`A{@\\YLHNFgB|A_@ZkBb@I]yAH}CTk@J[FYLIHYl@Q`@b@Ff@NTRPVLX?R?TG\\k@hBs@~Ai@bAoEvEqEvE|@z@~@jBh@rAPnAFbAAr@Xd@\\Zj@RlATb@?xBL^L^TNLLBP@VEXI`BE|AD\\B`@CTOZg@`@eALQTQz@[^K`@W\\_@HWDO?[Vm@PY`@[l@Ib@A\\DPFPLpA`@x@FnACtBOj@@\\JRRV\\d@t@Nf@Dh@C^GZWj@mAbCs@jA@@BB?B?HEDIBGCCAAAa@ZaBlAgEnCkAf@e@FaAJOFSPs@dAi@v@k@pAs@zBI`@xAvAl@v@`@\\`D|@b@b@L`@P`CFTRVZR~@`@T\\\\fAPp@JG`@GfAM~@c@j@Sp@Gd@J\\TPRLX`@dAz@vArAtBRRj@`@b@RxA\\dAXHDFS`AaDPu@?g@{B_JMe@Cc@HqBTuCGDYAGIAI?IBILGT@HN?NEHWfDGhBD`@j@xBvArFD`@Gj@aA~CSt@SIiBc@s@Sc@Sm@g@Y]eBqCa@w@a@cA[c@c@Ue@Gs@Lo@Vc@T[FcAJ[HEBEQYgA[s@_@Wy@]USMWIe@Es@Es@GUU]SQsA]w@Uk@[a@c@qBwBu@p@YFM@i@I}@Og@SUQWc@WqAQiAMs@QUWOIGg@Ew@FE@{ArBi@x@W\\WUgAeAgAgAuBqBsAkAk@o@uBoC}DaFiCcDmEgEWm@MM{AmA{DkCgFqDoBqAiAk@eA_@");
                        break;
                    case "Bus Zone 3U" : latLngs = PolyUtil.decode("iuzP_vglRu@p@iB\\GO_@EoBHaAN{AJu@XOT]z@@Ht@J`@^^l@Bd@YlAg@`B[j@Wt@k@l@{@~@yA|A}FdGrA`Bl@jAZv@b@hC@jATn@n@`@jAVdADvAJ|@Rf@Xh@Jr@I~AMdBLx@CZY^aAp@_Af@WbA_@n@a@V{@V_A\\o@x@Wx@@vAn@v@P`BFxAQ|AGz@^bA`BL~@Cv@uCtFIRHT]De@JuGtEgBz@cBPk@^cA|AsAbDc@tA?NxC`D^TpCx@f@l@RvBP~@~@f@~@r@r@~B~A]`AWnAi@lANfAzAnAdC`A`BhAdAhBf@bBd@pDhFfAnBj@nCGpBi@x@yDv@y@Me@e@eAqE_AuAeB_@mB@mBtAwAlA[\\a@J_Ay@k@u@@yBGlB\\n@~@~@f@JxEgEnAQxBVlAvAz@dCf@rBr@XdCQnB{@\\kAYmDaAyB_BeCgBcCoDw@mBmAcAcBkA_C_AiB_A[gBh@aDl@Ua@_@kAwBqAc@wAOaBg@cAgDw@wC_DOSv@mC|@iBdAaBd@c@x@QbAMnAm@~A{@tA_Ap@i@b@Yn@i@AUgAiAc@m@Ws@c@i@u@Sw@E}@LaA\\}CdB_BhAuDbCyA|@cBhAo@~@CTDv@i@^y@?k@BcAeA[CwArBgHuGDgHAgAXs@d@_@n@UCiASyBaAgCsAiBiIqHgAkA~AkBn@uAt@oB|@g@nDeApDy@lBQbAK^C\\iA`@_@xE]|BUhBq@bB{AZVeAz@");
                        break;
                    case "Bus Zone 2" : latLngs = PolyUtil.decode("{hyPyaelRY`FJfAt@bCv@zCPlAy@~Da@lAgDy@kBsAuBcDuA{C_A]}@H}Av@mBPQEcAqC_Bk@]eAGaBe@eAoBm@_AU_DgDGM~AgFrBiCh@W`BKvA}@tBmAnA{@hByAAUsBqBY_As@q@_AGaAFmBr@iBfAcDtBcEfCkCdBo@hAF`Ag@\\aA@i@FiAmAYLqA`BqEeEkA_AGkB@iFBy@~@{@h@_@K}Co@sBu@_Bg@q@c@e@~E_FdCqCjByB|@eCKRm@xA{@dBkCrC_EbEqAlAdB`CdArBZdDAl@~@dAjB`@xAAxARf@Zt@Vp@M~AKjCJl@Qn@_Bz@aA~Ae@x@}@PwAt@y@xAUhBt@tBTzBOjBAlA`A^bAFfBeB~Du@nAHN[Na@?iI~FkA\\}APeBbC{BnFJ^xCpCh@^|B`@h@z@LjBVbA|@l@|@j@f@dBV\\tB_@bAk@z@Sz@LnAbBt@bBxA~Bt@z@xAh@jCl@bA_DX_B_CgJQeBRiDJaAc@SP]XF@NERQ~@");
                        break;
                    default:  latLngs = PolyUtil.decode(polyline);
                }


                LatLng startingPoint = latLngs.get(0);
                LatLng endingPoint = latLngs.get(latLngs.size() -1);
                // Setting points of polyline
                polylineOptions.addAll(latLngs);


// create marker
                mapsFragment.addMarker(startingPoint.latitude,startingPoint.longitude, "Start","start",true);
                mapsFragment.addMarker(endingPoint.latitude,endingPoint.longitude, "End","end",true);

                // Adding the polyline to the map
                line = mapsFragment.map.addPolyline(polylineOptions);



            }
        });

        busETAFrom.setText(busETAto);

        if (status) {
            mapsFragment.focusCamera(new LatLng(lat, lon));
        }

    }

    private String convertSecsToReadableFormat(String inputEta) {
        int eta = 0;
        boolean isInt = isStringInt(inputEta);
        if (isInt) {
            eta = Integer.parseInt(inputEta);
        }

        int numberOfDays;
        int numberOfHours;
        int numberOfMinutes;
        int numberOfSeconds;

        numberOfDays = eta / 86400;
        numberOfHours = (eta % 86400 ) / 3600 ;
        numberOfMinutes = ((eta % 86400 ) % 3600 ) / 60 ;
        numberOfSeconds = ((eta % 86400 ) % 3600 ) % 60  ;
        return  numberOfHours+"h:"+numberOfMinutes+"m:"+numberOfSeconds+"s";
    }

    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

/*
    //Add line to map
    Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(location.getLatitude(), location.getLongitude()),
                            new LatLng(this.destinationLatitude, this.destinationLongitude))
                    .width(1)
                    .color(Color.DKGRAY)

//Remove the same line from map
            line.remove();*/

    public void setPOIBusStops(List<String> busStops) {

        String s = "";
        String code = "";
        List<BusStop> gLobalBusStops = new LinkedList<>();
        gLobalBusStops = UserInstance.getInstance().getBusStopList();
        bottomSheetLLaddButtons.removeAllViews();
        for (int i = 0; i < busStops.size(); i++) {
            code = busStops.get(i);
            for (int j = 0; j < gLobalBusStops.size(); j++) {
                if (gLobalBusStops.get(j).getCode().equals(code)){
                    Log.e("BusStopsInIn", gLobalBusStops.get(j).getName());
                    String name = gLobalBusStops.get(j).getName();
                    Double lat = Double.valueOf(gLobalBusStops.get(j).getLat());
                    Double lon = Double.valueOf(gLobalBusStops.get(j).getLon());
                    name = name.replace("Bus Stop ", "");
                   /* if(name.length() > 4)
                        name = name.substring(0,3) + "..";*/
                    doAddButton(name,code,lat,lon);
                    s += gLobalBusStops.get(j).getName()+"\n";
                }
            }
        }
        //s = s.replace("Bus Stop ", "");
        //tvpoiBusStops.setText(s);

    }

    private void doAddButton(final String name, String code, final Double lat, final Double lon) {

        LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(
               150, 150
        );
        params.setMargins(10,20,10,20);


        final Button button = new Button(this);
        //button.setText(name);
        button.setTag(code);
        button.setLayoutParams(params);
        button.setBackground(getResources().getDrawable(R.drawable.bus_stop));
        bottomSheetLLaddButtons.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBottomSheet();
                mapsFragment.focusCam(lat,lon,true);
                mapsFragment.selectedMarker(lat,lon,name);
            }
        });

    }

    protected void simpleAnimationfadein(View view) {
        ViewAnimator.animate(view)
                .fadeIn()
                .duration(600)
                .start();
    }

    protected void simpleAnimationfadeout(View view) {
        ViewAnimator.animate(view)
                .fadeOut()
                .duration(600)
                .start();
    }



}
