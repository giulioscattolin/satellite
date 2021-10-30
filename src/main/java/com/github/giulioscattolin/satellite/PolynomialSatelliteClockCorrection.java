package com.github.giulioscattolin.satellite;

public class PolynomialSatelliteClockCorrection {
    public double itsClockBias;
    public double itsClockDrift;
    public double itsClockDriftRate;
    public double itsSatelliteClockEpoch;

    public double getCorrectionInSeconds(double secondsSinceReferenceEpoch) {
        double dt = secondsSinceReferenceEpoch - itsSatelliteClockEpoch;
        if (dt > 302400)
            dt -= 604800;
        if (dt < -302400)
            dt += 604800;
        return itsClockBias + itsClockDrift * dt + itsClockDriftRate * dt * dt;
    }
}
