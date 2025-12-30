package com.ysc.bestev.ui.home;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.ysc.bestev.R;
import com.ysc.bestev.ev_api;
import com.ysc.bestev.ev_item;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnMapReadyCallback, NaverMap.OnCameraChangeListener{

    //    private UserInformationViewModel userViewModel;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private ArrayList<Marker> markerList = new ArrayList();
    public static ArrayList<ev_item> item = new ArrayList();
    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private boolean isCameraAnimated = false;
    private Thread Parsing;
    private InfoWindow infoWindow;
    private SlidingUpPanelLayout mLayout;
    TextView tv_statNm, tv_addr, tv_chgerType, station_name, station_addresss, StatUpdDt;
    ImageView im_chgerAC3,im_chgerChademo,im_chgerCombo, im_chgerLow ,chargetype_dccombo_x, chargetype_acthree_x, chargetype_dcchademo_x, chargetype_lowspeed_x, icon_copy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        userViewModel = new ViewModelProvider(requireActivity()).get(UserInformationViewModel.class);
//        Home에서 해당 내용 안쓸거면 지우기

        getActivity().setTitle("전기충전소 지도");
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            NaverMapOptions options = new NaverMapOptions().locationButtonEnabled(false);
            mapFragment = MapFragment.newInstance(options);
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        mLocationSource = new FusedLocationSource(getActivity(), PERMISSION_REQUEST_CODE);
        mLayout = (SlidingUpPanelLayout)root.findViewById(R.id.sliding_layout);

//        locationButtonView = (LocationButtonView)root.findViewById(R.id.locationbuttonview);
        mLayout.setPanelState(PanelState.HIDDEN);
        tv_statNm = (TextView)root.findViewById(R.id.tv_statNm);
        tv_addr = (TextView)root.findViewById(R.id.tv_addr);
//        tv_chgerType = (TextView)root.findViewById(R.id.tv_chgerType);
        im_chgerAC3 = (ImageView)root.findViewById(R.id.im_chgerAC3);
        im_chgerChademo = (ImageView)root.findViewById(R.id.im_chgerChademo);
        im_chgerCombo = (ImageView)root.findViewById(R.id.im_chgerCombo);
        im_chgerLow = (ImageView)root.findViewById(R.id.im_chgerLow);
        chargetype_dccombo_x = (ImageView)root.findViewById(R.id.chargetype_dccombo_x);
        chargetype_acthree_x = (ImageView)root.findViewById(R.id.chargetype_acthree_x);
        chargetype_dcchademo_x = (ImageView)root.findViewById(R.id.chargetype_dcchademo_x);
        chargetype_lowspeed_x = (ImageView)root.findViewById(R.id.chargetype_lowspeed_x);
        station_name = (TextView)root.findViewById(R.id.station_name);
        station_addresss = (TextView)root.findViewById(R.id.station_address);
        icon_copy = (ImageView)root.findViewById(R.id.icon_copy);
        StatUpdDt = (TextView)root.findViewById(R.id.StatUpdDt);
        return root;
    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        removeMarkerAll();

        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setScaleBarEnabled(true);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setLogoClickEnabled(false);

        ActivityCompat.requestPermissions(
                getActivity(),
                PERMISSIONS,
                PERMISSION_REQUEST_CODE
        );

        new Thread(() -> {
            ArrayList<ev_item> result = new ev_api().getXmlData();

            // UI 스레드로 전달
            new Handler(Looper.getMainLooper()).post(() -> {
                item = result;
                drawMarker(item);
            });

        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (mLocationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onCameraChange(int reason, boolean animated) {
        isCameraAnimated = animated;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0)
                drawMarker(item);
        }
    };

    // https://hashcode.co.kr/questions/9739/%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A7%80%EB%8F%84-%EB%8B%A4%EC%A4%91-%EB%A7%88%EC%BB%A4-100%EA%B0%9C-%EC%9D%B4%EC%83%81 참고
    private void drawMarker(final ArrayList<ev_item> data) {

        for (int i = 0; i < data.size(); i++) {

            ev_item item = data.get(i);

            // 좌표 없는 데이터 방어
            if (item.getLat() == 0 || item.getLng() == 0) continue;

            Marker marker = new Marker();
            marker.setPosition(new LatLng(item.getLat(), item.getLng()));
            marker.setMap(mNaverMap);
            markerList.add(marker);

            marker.setFlat(true);
            marker.setWidth(350);
            marker.setHeight(200);
            marker.setIconPerspectiveEnabled(true);
            marker.setHideCollidedSymbols(true);
            marker.setHideCollidedMarkers(true);

            // 충전 상태에 따른 마커 아이콘
            if (item.getStat() == 1) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.err));
            } else if (item.getStat() == 2) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.able));
            } else if (item.getStat() == 3) {
                marker.setIcon(OverlayImage.fromResource(R.drawable.ing));
            } else {
                marker.setIcon(OverlayImage.fromResource(R.drawable.system));
            }

            final int index = i;

            marker.setOnClickListener(overlay -> {

                if (mLayout == null) return true;

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                tv_statNm.setText(item.getStatNm());
                tv_addr.setText(item.getAddr());
                station_name.setText(item.getStatNm());
                station_addresss.setText(item.getAddr());

                // 날짜 안전 처리
                String upd = item.getStatUpdDt();
                if (upd != null && upd.length() >= 12) {
                    StringBuilder sb = new StringBuilder(upd.substring(0, 12));
                    sb.insert(4, ".");
                    sb.insert(7, ".");
                    sb.insert(10, " ");
                    sb.insert(13, ":");
                    StatUpdDt.setText("최근상태변경\n" + sb);
                } else {
                    StatUpdDt.setText("최근상태변경\n정보 없음");
                }

                // 아이콘 초기화
                resetChargeTypeIcons();

                // 충전기 타입 (문자열 기준)
                String type = item.getChgerType();
                if (type != null) {
                    switch (type) {
                        case "01": // DC 차데모
                            im_chgerChademo.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            chargetype_dcchademo_x.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            break;

                        case "02": // AC 완속
                            im_chgerLow.setImageResource(R.drawable.info_chargetype_lowspeed_o);
                            chargetype_lowspeed_x.setImageResource(R.drawable.info_chargetype_lowspeed_o);
                            break;

                        case "03": // 차데모 + AC3상
                            im_chgerChademo.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            im_chgerAC3.setImageResource(R.drawable.info_chargetype_acthree_o);
                            chargetype_dcchademo_x.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            chargetype_acthree_x.setImageResource(R.drawable.info_chargetype_acthree_o);
                            break;

                        case "04": // DC 콤보
                            im_chgerCombo.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            chargetype_dccombo_x.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            break;

                        case "05": // 차데모 + DC 콤보
                            im_chgerChademo.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            im_chgerCombo.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            chargetype_dcchademo_x.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            chargetype_dccombo_x.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            break;

                        case "06": // 차데모 + AC3상 + DC 콤보
                            im_chgerChademo.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            im_chgerAC3.setImageResource(R.drawable.info_chargetype_acthree_o);
                            im_chgerCombo.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            chargetype_dcchademo_x.setImageResource(R.drawable.info_chargetype_dcchademo_o);
                            chargetype_acthree_x.setImageResource(R.drawable.info_chargetype_acthree_o);
                            chargetype_dccombo_x.setImageResource(R.drawable.info_chargetype_dccombo_o);
                            break;

                        case "07": // AC 3상
                            im_chgerAC3.setImageResource(R.drawable.info_chargetype_acthree_o);
                            chargetype_acthree_x.setImageResource(R.drawable.info_chargetype_acthree_o);
                            break;
                    }
                }

                icon_copy.setOnClickListener(v -> {
                    ClipboardManager clipboard =
                            (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("addr", item.getAddr());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), "주소가 복사되었습니다.", Toast.LENGTH_SHORT).show();
                });

                return true;
            });
        }
    }

    private void resetChargeTypeIcons() {
        im_chgerAC3.setImageResource(R.drawable.info_chargetype_acthree_x);
        im_chgerChademo.setImageResource(R.drawable.info_chargetype_dcchademo_x);
        im_chgerCombo.setImageResource(R.drawable.info_chargetype_dccombo_x);
        im_chgerLow.setImageResource(R.drawable.info_chargetype_lowspeed_x);

        chargetype_acthree_x.setImageResource(R.drawable.info_chargetype_acthree_x);
        chargetype_dcchademo_x.setImageResource(R.drawable.info_chargetype_dcchademo_x);
        chargetype_dccombo_x.setImageResource(R.drawable.info_chargetype_dccombo_x);
        chargetype_lowspeed_x.setImageResource(R.drawable.info_chargetype_lowspeed_x);
    }

    private void removeMarkerAll() {
        for(Marker marker : markerList) {
            marker.setMap(null);
        }
    }

}