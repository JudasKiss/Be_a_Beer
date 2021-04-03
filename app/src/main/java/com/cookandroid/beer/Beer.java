package com.cookandroid.beer;
public class Beer {

    public String 상품명;
    public String 생산지역;

    public Beer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Beer(String beerName, String beerCountry ) {
        this.상품명 = beerName;
        this.생산지역=beerCountry;
    }
    //이름
    public String getBeerName() {
        return 상품명;
    }

    public void setBeerName(String beerName) {
        this.상품명 = beerName;
    }

    //원산지
    public String getBeerCountry() {
        return 생산지역;
    }

    public void setBeerCountry(String beerCountry) {
        this.생산지역 = beerCountry;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "beerName='" + 상품명 + '\'' +
                ",beerCountry ='" + 생산지역 + '\'' +
                '}';
    }
}