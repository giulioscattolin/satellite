package com.github.giulioscattolin.satellite;

public class SatelliteClockCorrectionModel {
    private SatelliteClockCorrection[] itsCorrections;
    private PolynomialSatelliteClockCorrectionModel itsPolynomialCorrection;

    public void setCorrections(SatelliteClockCorrection[] itsCorrections) {
        this.itsCorrections = itsCorrections;
    }

    public double getSeconds() {
        double seconds = 0;
        for (SatelliteClockCorrection correction : itsCorrections)
            seconds += getSeconds(correction);
        return seconds;
    }

    public void setSecondsSinceTheBeginningOfTheWeek(double secondsSinceTheBeginningOfTheWeek) {
        itsPolynomialCorrection.setSecondsSinceTheBeginningOfTheWeek(secondsSinceTheBeginningOfTheWeek);
    }

    private double getSeconds(SatelliteClockCorrection correction) {
        switch (correction) {
            case POLYNOMIAL:
                return itsPolynomialCorrection.getSeconds();
        }
        throw new UnsupportedOperationException("Unsupported correction: " + correction);
    }
}
