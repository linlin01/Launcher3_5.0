package lefty;

import android.location.Address;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyGeocoder {

    public static List<Address> getFromLocation(double lat, double lng, int maxResult) {

        String address = String.format(Locale.ENGLISH, "http://maps.googleapis.com/maps/api/geocode/json?latlng=%1$f,%2$f&sensor=false&language=" + Locale.getDefault().getCountry(), lat, lng);
//        HttpGet httpGet = new HttpGet(address);
//        HttpClient client = new DefaultHttpClient();
//        client.getParams().setParameter(AllClientPNames.USER_AGENT, "Mozilla/5.0 (Java) Gecko/20081007 java-geocoder");
//        client.getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 5 * 1000);
//        client.getParams().setIntParameter(AllClientPNames.SO_TIMEOUT, 25 * 1000);
//        HttpResponse response;
        String respnse = CommonsUtils.requestWebService(address);
        List<Address> retList = null;

        if (TextUtils.isEmpty(respnse)) {
            return null;
        }
        try {
//            response = client.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            String json = EntityUtils.toString(entity, "UTF-8");

            JSONObject jsonObject = new JSONObject(respnse);

            retList = new ArrayList<Address>();

            if ("OK".equalsIgnoreCase(jsonObject.getString("status"))) {
                JSONArray results = jsonObject.getJSONArray("results");
                if (results.length() > 0) {
                    for (int i = 0; i < results.length() && i < maxResult; i++) {
                        JSONObject result = results.getJSONObject(i);
                        //Log.e(MyGeocoder.class.getName(), result.toString());
                        Address addr = new Address(Locale.getDefault());
                        // addr.setAddressLine(0, result.getString("formatted_address"));

                        JSONArray components = result.getJSONArray("address_components");
                        String streetNumber = "";
                        String route = "";
                        for (int a = 0; a < components.length(); a++) {
                            JSONObject component = components.getJSONObject(a);
                            JSONArray types = component.getJSONArray("types");

                            for (int j = 0; j < types.length(); j++) {
                                String type = types.getString(j);

                                switch(type) {
                                    case "locality"://city
                                        addr.setLocality(component.getString("long_name"));
                                        //storableLocation.city = component.long_name;
                                        break;
                                    case "administrative_area_level_1"://state
                                        addr.setAdminArea(component.getString("long_name"));
//                                        storableLocation.state = component.short_name;
                                        break;
                                    case "country"://country
                                        /*storableLocation.country = component.long_name;
                                        storableLocation.registered_country_iso_code = component.short_name;*/
                                        break;
                                }
//                                if (type.equals("locality")) {
//                                    addr.setLocality(component.getString("long_name"));
//                                } else if (type.equals("street_number")) {
//                                    streetNumber = component.getString("long_name");
//                                } else if (type.equals("route")) {
//                                    route = component.getString("long_name");
//                                }
                            }
                        }
                        //addr.setAddressLine(0, route + " " + streetNumber);

                        addr.setLatitude(result.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                        addr.setLongitude(result.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                        retList.add(addr);
                    }
                }
            }


        } catch (Exception e) {
            Log.e(MyGeocoder.class.getName(), "Error parsing Google geocode webservice response.", e);
        }

        return retList;
    }
}