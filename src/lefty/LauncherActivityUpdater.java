package lefty;

/**
 * Created by tajinder on 5/5/16.
 */
public class LauncherActivityUpdater {


    private static LauncherActivityUpdater mInstance;

    public Updater mCallback;

    public interface Updater{
        void refreshActivity();
    }
    public static LauncherActivityUpdater getInstance() {
        if (mInstance == null) {
            mInstance = new LauncherActivityUpdater();
        }
        return mInstance;
    }
    public void setListeners(Updater mCallback) {
        this.mCallback = mCallback;
    }

    public void notifyListener() {
        this.mCallback.refreshActivity();
    }

}
