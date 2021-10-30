package com.github.giulioscattolin.satellite;

public class PolynomialSatelliteClockCorrection {
    private double itsClockBias;
    private double itsClockDrift;
    private double itsClockDriftRate;
    private double itsTocInSecondsSinceTheBeginningOfTheWeek;

    public double getClockBias() {
        return itsClockBias;
    }

    public void setClockBias(double itsClockBias) {
        this.itsClockBias = itsClockBias;
    }

    public double getClockDrift() {
        return itsClockDrift;
    }

    public void setClockDrift(double itsClockDrift) {
        this.itsClockDrift = itsClockDrift;
    }

    public double getClockDriftRate() {
        return itsClockDriftRate;
    }

    public void setClockDriftRate(double itsClockDriftRate) {
        this.itsClockDriftRate = itsClockDriftRate;
    }

    public double getTocInSecondsSinceTheBeginningOfTheWeek() {
        return itsTocInSecondsSinceTheBeginningOfTheWeek;
    }

    public void setTocInSecondsSinceTheBeginningOfTheWeek(double itsTocInSecondsSinceTheBeginningOfTheWeek) {
        this.itsTocInSecondsSinceTheBeginningOfTheWeek = itsTocInSecondsSinceTheBeginningOfTheWeek;
    }

    public double getCorrectionInSeconds(double secondsSinceTheBeginningOfTheWeek) {
        double dt = secondsSinceTheBeginningOfTheWeek - itsTocInSecondsSinceTheBeginningOfTheWeek;
        if (dt > 302400)
            dt -= 604800;
        if (dt < -302400)
            dt += 604800;
        return itsClockBias + itsClockDrift * dt + itsClockDriftRate * dt * dt;
    }
}
