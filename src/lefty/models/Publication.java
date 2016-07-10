package lefty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Publication {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("publisher")
    @Expose
    private List<Publisher> publisher = new ArrayList<Publisher>();

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
     * @return The publisher
     */
    public List<Publisher> getPublisher() {
        return publisher;
    }

    /**
     * @param publisher The publisher
     */
    public void setPublisher(List<Publisher> publisher) {
        this.publisher = publisher;
    }


    public class Datum {

        @SerializedName("appname")
        @Expose
        private String appname;
        @SerializedName("appname_eng")
        @Expose
        private String appnameEng;

        /**
         * @return The appname
         */
        public String getAppname() {
            return appname;
        }

        /**
         * @param appname The appname
         */
        public void setAppname(String appname) {
            this.appname = appname;
        }

        /**
         * @return The appnameEng
         */
        public String getAppnameEng() {
            return appnameEng;
        }

        /**
         * @param appnameEng The appname_eng
         */
        public void setAppnameEng(String appnameEng) {
            this.appnameEng = appnameEng;
        }

    }

    public class Publisher {

        @SerializedName("language_code")
        @Expose
        private String languageCode;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("data")
        @Expose
        private List<Datum> data = new ArrayList<Datum>();
        private boolean isSelected;
        private boolean isHeader;
        /**
         * @return The languageCode
         */
        public String getLanguageCode() {
            return languageCode;
        }

        /**
         * @param languageCode The language_code
         */
        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        /**
         * @return The language
         */
        public String getLanguage() {
            return language;
        }

        /**
         * @param language The language
         */
        public void setLanguage(String language) {
            this.language = language;
        }

        /**
         * @return The data
         */
        public List<Datum> getData() {
            return data;
        }

        /**
         * @param data The data
         */
        public void setData(List<Datum> data) {
            this.data = data;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public void setHeader(boolean header) {
            isHeader = header;
        }
    }
}