//package lefty;
//
//import android.app.Activity;
//
//import com.aucklandapp.home.AucklandApplication;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//
///**
// * Created by tajinder on 16/12/15.
// */
//public class GAUtils {
//
//
//    public static void sendScreenName(String screenName, Activity context) {
//        try {
//            Tracker t = ((AucklandApplication) context.getApplication()).getTracker();
//            t.setScreenName(screenName);
//            t.send(new HitBuilders.AppViewBuilder().build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param actionName
//     * @param screenName
//     * @param context
//     * @param label
//     */
//    public static void sendEvent(String actionName,String screenName,String label,Activity context){
//        try{
//            Tracker t = ((AucklandApplication) context.getApplication()).getTracker();
//            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
//            eventBuilder.setCategory(screenName);
//            eventBuilder.setAction(actionName);
//            if (label != null){
//                eventBuilder.setLabel(label);
//            }
//
//            t.send(eventBuilder.build());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//}
