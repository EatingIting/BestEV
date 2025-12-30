package com.ysc.bestev;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ev_api {

    public ArrayList<ev_item> getXmlData() {

        ArrayList<ev_item> items = new ArrayList<>();

        String queryUrl =
                "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo"
                        + "?serviceKey=" + BuildConfig.EV_API_KEY
                        + "&numOfRows=100"
                        + "&pageNo=1";

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int eventType = xpp.getEventType();
            String tag = "";

            ev_item evItem = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) {
                            evItem = new ev_item();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        String text = xpp.getText();

                        if (evItem == null) break;

                        switch (tag) {
                            case "statNm":
                                evItem.setStatNm(text);
                                break;
                            case "statId":
                                evItem.setStatId(text);
                                break;
                            case "chgerId":
                                evItem.setChgerId(text);
                                break;
                            case "chgerType":
                                evItem.setChgerType(text);
                                break;
                            case "addr":
                                evItem.setAddr(text);
                                break;
                            case "lat":
                                evItem.setLat(Double.parseDouble(text));
                                break;
                            case "lng":
                                evItem.setLng(Double.parseDouble(text));
                                break;
                            case "useTime":
                                evItem.setUseTime(text);
                                break;
                            case "busiNm":
                                evItem.setBusiNm(text);
                                break;
                            case "busiCall":
                                evItem.setBusiCall(text);
                                break;
                            case "stat":
                                evItem.setStat(Integer.parseInt(text));
                                break;
                            case "output":
                                evItem.setOutput(Integer.parseInt(text));
                                break;
                            case "method":
                                evItem.setMethod(text);
                                break;
                            case "parkingFree":
                                evItem.setParkingFree(text);
                                break;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("item")) {
                            items.add(evItem);
                            evItem = null;
                        }
                        tag = "";
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
