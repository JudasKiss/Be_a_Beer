package com.cookandroid.beer;

public class DataSample {
    private float twenty;
    private float thirty;
    private float forty;
    private float fifty;
    private float male;
    private float female;

    public float getTwenty() {
        return twenty;
    }

    public void setTwenty(float twenty) {
        this.twenty = twenty;
    }

    public float getThirty() {
        return thirty;
    }

    public void setThirty(float thirty) {
        this.thirty = thirty;
    }

    public float getForty() {
        return forty;
    }

    public void setForty(float forty) {
        this.forty = forty;
    }

    public float getFifty() {
        return fifty;
    }

    public void setFifty(float fifty) {
        this.fifty = fifty;
    }

    public float getMale() {
        return male;
    }

    public void setMale(float male) {
        this.male = male;
    }

    public float getFemale() {
        return female;
    }

    public void setFemale(float female) {
        this.female = female;
    }

    @Override
    public String toString() {
        return "DataSample{" +
                "twenty=" + twenty +
                ", thirty=" + thirty +
                ", forty=" + forty +
                ", fifty=" + fifty +
                ", male=" + male +
                ", female=" + female +
                '}';
    }
}
