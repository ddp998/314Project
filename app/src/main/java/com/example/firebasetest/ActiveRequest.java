package com.example.firebasetest;

public class ActiveRequest {
    //TODO actual attributes: request id 6 digits, location, problem, details, user, urgency level, activity status

    String clientId, locationLat, locationLong;

    public ActiveRequest() {

    }

    public ActiveRequest(String clientId, String locationLat, String locationLong) {
        this.clientId = clientId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }
}
