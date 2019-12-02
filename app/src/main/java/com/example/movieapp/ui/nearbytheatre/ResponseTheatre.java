package com.example.movieapp.ui.nearbytheatre;

public class ResponseTheatre {

    String placeName;
    String address;
    String imageUrl;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getPeopleRatting() {
        return peopleRatting;
    }

    public void setPeopleRatting(String peopleRatting) {
        this.peopleRatting = peopleRatting;
    }

    String ratting;
    String peopleRatting;
}
