package com.betterwifisignal.betterwifisignal;

import java.io.Serializable;

public class WifiElementModel implements Serializable {

    private String ssdi, pass;
    private String frequency;
    private int strength, strengthRating;

    public WifiElementModel(String ssdi, int frequency, int strength, int strengthRating) {
        this.ssdi = ssdi;
        this.frequency = frequency >= 5000 ? "5GHz" : "2.4GHz";
        ;
        this.strength = strength;
        this.strengthRating = strengthRating;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrengthRating() {
        return strengthRating;
    }

    public void setStrengthRating(int strengthRating) {
        this.strengthRating = strengthRating;
    }

    public String getSSDI() {

        return ssdi;
    }

    public String getPass() {

        return pass;
    }

    public void setPass(String pass) {

        this.pass = pass;
    }
}
