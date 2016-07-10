package lefty.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Example {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("lang")
    @Expose
    private String lang;
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
     * @return The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The device_id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
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


    public class Banner {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("banner_thumb_image")
        @Expose
        private String bannerThumbImage;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("slot_id")
        @Expose
        private String slotId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The bannerThumbImage
         */
        public String getBannerThumbImage() {
            return bannerThumbImage;
        }

        /**
         * @param bannerThumbImage The banner_thumb_image
         */
        public void setBannerThumbImage(String bannerThumbImage) {
            this.bannerThumbImage = bannerThumbImage;
        }

        /**
         * @return The bannerImage
         */
        public String getBannerImage() {
            return bannerImage;
        }

        /**
         * @param bannerImage The banner_image
         */
        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The time
         */
        public String getTime() {
            return time;
        }

        /**
         * @param time The time
         */
        public void setTime(String time) {
            this.time = time;
        }

        /**
         * @return The slotId
         */
        public String getSlotId() {
            return slotId;
        }

        /**
         * @param slotId The slot_id
         */
        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

    }

    public class Cpa {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("banner_thumb_image")
        @Expose
        private String bannerThumbImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The category
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return The bannerThumbImage
         */
        public String getBannerThumbImage() {
            return bannerThumbImage;
        }

        /**
         * @param bannerThumbImage The banner_thumb_image
         */
        public void setBannerThumbImage(String bannerThumbImage) {
            this.bannerThumbImage = bannerThumbImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

    }

    public class Data {

        @SerializedName("video")
        @Expose
        private List<Video> video = new ArrayList<Video>();
        @SerializedName("weather")
        @Expose
        private List<Weather> weather = new ArrayList<Weather>();
        @SerializedName("twitter")
        @Expose
        private List<Twitter> twitter = new ArrayList<Twitter>();
        @SerializedName("wallpaper")
        @Expose
        private List<Wallpaper> wallpaper = new ArrayList<Wallpaper>();
        @SerializedName("cpa")
        @Expose
        private List<Cpa> cpa = new ArrayList<>();
        @SerializedName("banner")
        @Expose
        private List<Banner> banner = new ArrayList<>();
        @SerializedName("games")
        @Expose
        private List<Game> games = new ArrayList<>();
        @SerializedName("entertainment")
        @Expose
        private List<Entertainment> entertainment = new ArrayList<>();
        @SerializedName("trendingNews")
        @Expose
        private List<Object> trendingNews = new ArrayList<>();
        @SerializedName("languages")
        @Expose
        private List<Language> languages = new ArrayList<>();
        @SerializedName("news")
        @Expose
        private List<News> news = new ArrayList<>();
        @SerializedName("yahoo_search")
        @Expose
        private List<YahooSearch> yahooSearch = new ArrayList<>();
        @SerializedName("top_advertisement")
        @Expose
        private List<TopAdvertisement> topAdvertisement = new ArrayList<>();
        @SerializedName("bottom_advertisement")
        @Expose
        private List<BottomAdvertisement> bottomAdvertisement = new ArrayList<>();
        @SerializedName("titles")
        @Expose
        private List<Title> titles = new ArrayList<>();


        /**
         * @return The video
         */
        public List<Video> getVideo() {
            return video;
        }

        /**
         * @param video The video
         */
        public void setVideo(List<Video> video) {
            this.video = video;
        }

        /**
         * @return The weather
         */
        public List<Weather> getWeather() {
            return weather;
        }

        /**
         * @param weather The weather
         */
        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        /**
         * @return The twitter
         */
        public List<Twitter> getTwitter() {
            return twitter;
        }

        /**
         * @param twitter The twitter
         */
        public void setTwitter(List<Twitter> twitter) {
            this.twitter = twitter;
        }

        /**
         * @return The wallpaper
         */
        public List<Wallpaper> getWallpaper() {
            return wallpaper;
        }

        /**
         * @param wallpaper The wallpaper
         */
        public void setWallpaper(List<Wallpaper> wallpaper) {
            this.wallpaper = wallpaper;
        }

        /**
         * @return The cpa
         */
        public List<Cpa> getCpa() {
            return cpa;
        }

        /**
         * @param cpa The cpa
         */
        public void setCpa(List<Cpa> cpa) {
            this.cpa = cpa;
        }

        /**
         * @return The banner
         */
        public List<Banner> getBanner() {
            return banner;
        }

        /**
         * @param banner The banner
         */
        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        /**
         * @return The games
         */
        public List<Game> getGames() {
            return games;
        }

        /**
         * @param games The games
         */
        public void setGames(List<Game> games) {
            this.games = games;
        }

        /**
         * @return The entertainment
         */
        public List<Entertainment> getEntertainment() {
            return entertainment;
        }

        /**
         * @param entertainment The entertainment
         */
        public void setEntertainment(List<Entertainment> entertainment) {
            this.entertainment = entertainment;
        }

        /**
         * @return The trendingNews
         */
        public List<Object> getTrendingNews() {
            return trendingNews;
        }

        /**
         * @param trendingNews The trendingNews
         */
        public void setTrendingNews(List<Object> trendingNews) {
            this.trendingNews = trendingNews;
        }

        /**
         * @return The languages
         */
        public List<Language> getLanguages() {
            return languages;
        }

        /**
         * @param languages The languages
         */
        public void setLanguages(List<Language> languages) {
            this.languages = languages;
        }

        /**
         * @return The news
         */
        public List<News> getNews() {
            return news;
        }

        /**
         * @param news The news
         */
        public void setNews(List<News> news) {
            this.news = news;
        }

        public List<YahooSearch> getYahooSearch() {
            return yahooSearch;
        }

        public void setYahooSearch(List<YahooSearch> yahooSearch) {
            this.yahooSearch = yahooSearch;
        }

        public List<TopAdvertisement> getTopAdvertisement() {
            return topAdvertisement;
        }

        public void setTopAdvertisement(List<TopAdvertisement> topAdvertisement) {
            this.topAdvertisement = topAdvertisement;
        }

        public List<BottomAdvertisement> getBottomAdvertisement() {
            return bottomAdvertisement;
        }

        public void setBottomAdvertisement(List<BottomAdvertisement> bottomAdvertisement) {
            this.bottomAdvertisement = bottomAdvertisement;
        }

        public List<Title> getTitles() {
            return titles;
        }

        public void setTitles(List<Title> titles) {
            this.titles = titles;
        }
    }

    public class Entertainment {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("banner_thumb_image")
        @Expose
        private String bannerThumbImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The bannerThumbImage
         */
        public String getBannerThumbImage() {
            return bannerThumbImage;
        }

        /**
         * @param bannerThumbImage The banner_thumb_image
         */
        public void setBannerThumbImage(String bannerThumbImage) {
            this.bannerThumbImage = bannerThumbImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

    }

    public class Game {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("banner_thumb_image")
        @Expose
        private String bannerThumbImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The category
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return The bannerThumbImage
         */
        public String getBannerThumbImage() {
            return bannerThumbImage;
        }

        /**
         * @param bannerThumbImage The banner_thumb_image
         */
        public void setBannerThumbImage(String bannerThumbImage) {
            this.bannerThumbImage = bannerThumbImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

    }

    public class Language {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private String value;

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value The value
         */
        public void setValue(String value) {
            this.value = value;
        }

    }

    public class Twitter {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("widget_id")
        @Expose
        private String widgetId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;
        @SerializedName("collection_id")
        @Expose
        private String collection_id;

        private String tweet_count;

        /**
         * @return The type
         */
        public String getCollectionId() {
            return collection_id;
        }

        /**
         */
        public void setCollectionId(String collection_id) {
            this.collection_id = collection_id;
        }

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return The widgetId
         */
        public String getWidgetId() {
            return widgetId;
        }

        /**
         * @param widgetId The widget_id
         */
        public void setWidgetId(String widgetId) {
            this.widgetId = widgetId;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

        public String getTweet_count() {
            return tweet_count;
        }

        public void setTweet_count(String tweet_count) {
            this.tweet_count = tweet_count;
        }
    }

    public class Video {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("youtube_id")
        @Expose
        private String youtubeId;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("featured")
        @Expose
        private String featured;

        private String thumb;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The youtubeLink
         */
        public String getYoutubeLink() {
            return youtubeLink;
        }

        /**
         * @param youtubeLink The youtube_link
         */
        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
        }

        /**
         * @return The youtubeId
         */
        public String getYoutubeId() {
            return youtubeId;
        }

        /**
         * @param youtubeId The youtube_id
         */
        public void setYoutubeId(String youtubeId) {
            this.youtubeId = youtubeId;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public class Wallpaper {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("banner_thumb_image")
        @Expose
        private String bannerThumbImage;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("featured")
        @Expose
        private String featured;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The bannerThumbImage
         */
        public String getBannerThumbImage() {
            return bannerThumbImage;
        }

        /**
         * @param bannerThumbImage The banner_thumb_image
         */
        public void setBannerThumbImage(String bannerThumbImage) {
            this.bannerThumbImage = bannerThumbImage;
        }

        /**
         * @return The bannerImage
         */
        public String getBannerImage() {
            return bannerImage;
        }

        /**
         * @param bannerImage The banner_image
         */
        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        /**
         * @return The action
         */
        public String getAction() {
            return action;
        }

        /**
         * @param action The action
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        /**
         * @return The featured
         */
        public String getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(String featured) {
            this.featured = featured;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

    }

    /**
     * date: "16 Jun 2016",
     * temperature: "38°C",
     * location: "Delhi,India",
     * type: "Thunderstorms",
     * icon: "http://lefty.mobi/application/views/dist/images/weather/4.png",
     * image: "http://lefty.mobi/application/views/dist/images/weather/thunderstorms.jpg"
     */

    public class Weather {
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("type_id")
        @Expose
        private String typeId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("icon")
        @Expose
        private String icon;

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("temperature")
        @Expose
        private String temperature;
        @SerializedName("location")
        @Expose
        private String location;


        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The typeId
         */
        public String getTypeId() {
            return typeId;
        }

        /**
         * @param typeId The type_id
         */
        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public class News {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("datetime")
        @Expose
        private String datetime;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;

        /**
         * @return The image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         * @return The heading
         */
        public String getHeading() {
            return heading;
        }

        /**
         * @param heading The heading
         */
        public void setHeading(String heading) {
            this.heading = heading;
        }

        /**
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The datetime
         */
        public String getDatetime() {
            return datetime;
        }

        /**
         * @param datetime The datetime
         */
        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

    }

    public class YahooSearch {

        @SerializedName("url")
        @Expose
        private String url;

        /**
         * @return The url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

    }

    public class Title {

        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("news")
        @Expose
        private String news;
        @SerializedName("wallpaper")
        @Expose
        private String wallpaper;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("cpa")
        @Expose
        private String cpa;
        @SerializedName("games")
        @Expose
        private String games;
        @SerializedName("entertainment")
        @Expose
        private String entertainment;
        @SerializedName("trendingNews")
        @Expose
        private String trendingNews;

        /**
         * @return The video
         */
        public String getVideo() {
            return video;
        }

        /**
         * @param video The video
         */
        public void setVideo(String video) {
            this.video = video;
        }

        /**
         * @return The news
         */
        public String getNews() {
            return news;
        }

        /**
         * @param news The news
         */
        public void setNews(String news) {
            this.news = news;
        }

        /**
         * @return The wallpaper
         */
        public String getWallpaper() {
            return wallpaper;
        }

        /**
         * @param wallpaper The wallpaper
         */
        public void setWallpaper(String wallpaper) {
            this.wallpaper = wallpaper;
        }

        /**
         * @return The twitter
         */
        public String getTwitter() {
            return twitter;
        }

        /**
         * @param twitter The twitter
         */
        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        /**
         * @return The cpa
         */
        public String getCpa() {
            return cpa;
        }

        /**
         * @param cpa The cpa
         */
        public void setCpa(String cpa) {
            this.cpa = cpa;
        }

        /**
         * @return The games
         */
        public String getGames() {
            return games;
        }

        /**
         * @param games The games
         */
        public void setGames(String games) {
            this.games = games;
        }

        /**
         * @return The entertainment
         */
        public String getEntertainment() {
            return entertainment;
        }

        /**
         * @param entertainment The entertainment
         */
        public void setEntertainment(String entertainment) {
            this.entertainment = entertainment;
        }

        /**
         * @return The trendingNews
         */
        public String getTrendingNews() {
            return trendingNews;
        }

        /**
         * @param trendingNews The trendingNews
         */
        public void setTrendingNews(String trendingNews) {
            this.trendingNews = trendingNews;
        }

    }

    public class BottomAdvertisement {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The bannerImage
         */
        public String getBannerImage() {
            return bannerImage;
        }

        /**
         * @param bannerImage The banner_image
         */
        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

    }

    public class TopAdvertisement {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;
        @SerializedName("redirect_link")
        @Expose
        private String redirectLink;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The bannerImage
         */
        public String getBannerImage() {
            return bannerImage;
        }

        /**
         * @param bannerImage The banner_image
         */
        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

        /**
         * @return The redirectLink
         */
        public String getRedirectLink() {
            return redirectLink;
        }

        /**
         * @param redirectLink The redirect_link
         */
        public void setRedirectLink(String redirectLink) {
            this.redirectLink = redirectLink;
        }

    }

}