package com.github.giulioscattolin.satellite;

public class PolynomialSatelliteClockCorrection {
    public double itsClockBias;
    public double itsClockDrift;
    public double itsClockDriftRate;
    public double itsTocInSecondsSinceTheBeginningOfTheWeek;

    public double getCorrectionInSeconds(double secondsSinceTheBeginningOfTheWeek) {
        double dt = secondsSinceTheBeginningOfTheWeek - itsTocInSecondsSinceTheBeginningOfTheWeek;
        if (dt > 302400)
            dt -= 604800;
        if (dt < -302400)
            dt += 604800;
        return itsClockBias + itsClockDrift * dt + itsClockDriftRate * dt * dt;
    }
}
