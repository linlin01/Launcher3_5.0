package lefty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("ui_version")
        @Expose
        private String uiVersion;
        @SerializedName("application_type")
        @Expose
        private String applicationType;

        @SerializedName("ga_key")
        @Expose
        private String ga_key;
        @SerializedName("youtube_key")
        @Expose
        private String youtube_key;

        /**
         * @return The uiVersion
         */
        public String getUiVersion() {
            return uiVersion;
        }

        /**
         * @param uiVersion The ui_version
         */
        public void setUiVersion(String uiVersion) {
            this.uiVersion = uiVersion;
        }

        /**
         * @return The applicationType
         */
        public String getApplicationType() {
            return applicationType;
        }

        /**
         * @param applicationType The application_type
         */
        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public String getYoutube_key() {
            return youtube_key;
        }

        public void setYoutube_key(String youtube_key) {
            this.youtube_key = youtube_key;
        }

        public String getGa_key() {
            return ga_key;
        }

        public void setGa_key(String ga_key) {
            this.ga_key = ga_key;
        }
    }
}

