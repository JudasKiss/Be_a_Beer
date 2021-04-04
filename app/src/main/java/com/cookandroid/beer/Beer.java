package com.cookandroid.beer;
public class Beer {

    public String beerName;
    public String beerCountry;

    public Beer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Beer(String beerName, String beerCountry ) {
        this.beerName = beerName;
        this.beerCountry=beerCountry;
    }
    //이름
    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    //원산지
    public String getBeerCountry() {
        return beerCountry;
    }

    public void setBeerCountry(String beerCountry) {
        this.beerCountry = beerCountry;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "beerName='" + beerName + '\'' +
                ",beerCountry ='" + beerCountry + '\'' +
                '}';
    }
}