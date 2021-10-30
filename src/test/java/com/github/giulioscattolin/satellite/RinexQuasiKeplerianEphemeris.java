package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Integer.parseInt;

public abstract class RinexQuasiKeplerianEphemeris {
    public QuasiKeplerianSatellitePositionModel itsPositionModel = new QuasiKeplerianSatellitePositionModel();
    public QuasiKeplerianSatelliteVelocityModel itsVelocityModel = new QuasiKeplerianSatelliteVelocityModel();
    public PolynomialSatelliteClockCorrection itsPolynomialCorrection = new PolynomialSatelliteClockCorrection();
    public LocalDateTime itsReferenceEpoch;
    public double itsTolerance;
    private long itsWeekNumber;
    private int itsYear;
    private int itsMonth;
    private int itsDay;
    private int itsHour;
    private int itsMinute;
    private int itsSecond;

    public RinexQuasiKeplerianEphemeris(String ephemeris) {
        readLines(ephemeris.split("\n"));
        itsVelocityModel.setPositionModel(itsPositionModel);
    }

    private static double parseDouble(String field) {
        return Double.parseDouble(field.replace('D', 'E'));
    }

    private void readLines(String[] lines) {
        readSvEpochSvClk(lines[0]);
        readBroadcastOrbit1(lines[1]);
        readBroadcastOrbit2(lines[2]);
        readBroadcastOrbit3(lines[3]);
        readBroadcastOrbit4(lines[4]);
        readBroadcastOrbit5(lines[5]);
        itsReferenceEpoch = getReferenceEpoch();
        setTocInSecondsSinceTheBeginningOfTheWeek();
    }

    private LocalDateTime getReferenceEpoch() {
        return getEpoch().plusDays(7 * itsWeekNumber);
    }

    protected abstract LocalDateTime getEpoch();

    private void setTocInSecondsSinceTheBeginningOfTheWeek() {
        LocalDateTime toc = LocalDateTime.of(itsYear, itsMonth, itsDay, itsHour, itsMinute, itsSecond);
        itsPolynomialCorrection.setTocInSecondsSinceTheBeginningOfTheWeek(itsReferenceEpoch.until(toc, ChronoUnit.SECONDS));
    }

    private void readSvEpochSvClk(String line) {
        itsYear = parseInt(line.substring(4, 8));
        itsMonth = parseInt(line.substring(9, 11));
        itsDay = parseInt(line.substring(12, 14));
        itsHour = parseInt(line.substring(15, 17));
        itsMinute = parseInt(line.substring(18, 20));
        itsSecond = parseInt(line.substring(21, 23));
        itsPolynomialCorrection.setClockBias(parseDouble(line.substring(23, 42)));
        itsPolynomialCorrection.setClockDrift(parseDouble(line.substring(42, 61)));
        itsPolynomialCorrection.setClockDriftRate(parseDouble(line.substring(61, 80)));
    }

    private void readBroadcastOrbit1(String line) {
        itsPositionModel.setCrs(parseDouble(line.substring(23, 42)));
        itsPositionModel.setDeltaN(parseDouble(line.substring(42, 61)));
        itsPositionModel.setM0(parseDouble(line.substring(61, 80)));
    }

    private void readBroadcastOrbit2(String line) {
        itsPositionModel.setCuc(parseDouble(line.substring(4, 23)));
        itsPositionModel.setE(parseDouble(line.substring(23, 42)));
        itsPositionModel.setCus(parseDouble(line.substring(42, 61)));
        itsPositionModel.setSqrtA(parseDouble(line.substring(61, 80)));
    }

    private void readBroadcastOrbit3(String line) {
        itsPositionModel.setToeInSecondsSinceTheBeginningOfTheWeek(parseDouble(line.substring(4, 23)));
        itsPositionModel.setCic(parseDouble(line.substring(23, 42)));
        itsPositionModel.setOmega0(parseDouble(line.substring(42, 61)));
        itsPositionModel.setCis(parseDouble(line.substring(61, 80)));
    }

    private void readBroadcastOrbit4(String line) {
        itsPositionModel.setI0(parseDouble(line.substring(4, 23)));
        itsPositionModel.setCrc(parseDouble(line.substring(23, 42)));
        itsPositionModel.setOmega(parseDouble(line.substring(42, 61)));
        itsPositionModel.setOmegaDot(parseDouble(line.substring(61, 80)));
    }

    private void readBroadcastOrbit5(String line) {
        itsPositionModel.setIDot(parseDouble(line.substring(4, 23)));
        itsWeekNumber = (long) parseDouble(line.substring(42, 61));
    }
}
