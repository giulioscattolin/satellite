package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Integer.parseInt;

public class RinexEphemerisReader {
    private static String[] getFields(String line) {
        return line.trim().split(" +");
    }

    private static double parseDouble(String field) {
        return Double.parseDouble(field.replace('D', 'E'));
    }

    public static class GpsEphemeris {
        public static final LocalDateTime GPS_EPOCH = LocalDateTime.of(1980, 1, 6, 0, 0, 0);
        public QuasiKeplerianSatellitePositionModel itsPositionModel = new QuasiKeplerianSatellitePositionModel();
        public QuasiKeplerianVelocityPositionModel itsVelocityModel = new QuasiKeplerianVelocityPositionModel();
        public PolynomialSatelliteClockCorrection itsPolynomialCorrection = new PolynomialSatelliteClockCorrection();
        public LocalDateTime itsReferenceEpoch;
        private long itsWeekNumber;
        private int itsYear;
        private int itsMonth;
        private int itsDay;
        private int itsHour;
        private int itsMinute;
        private int itsSecond;

        public GpsEphemeris(String ephemeris) {
            readLines(ephemeris.split("\n"));
            itsPositionModel.itsMu = 3.986005E14;
            itsVelocityModel.itsPositionModel = itsPositionModel;
        }

        private void readLines(String[] lines) {
            readSvEpochSvClk(getFields(lines[0]));
            readBroadcastOrbit1(getFields(lines[1]));
            readBroadcastOrbit2(getFields(lines[2]));
            readBroadcastOrbit3(getFields(lines[3]));
            readBroadcastOrbit4(getFields(lines[4]));
            readBroadcastOrbit5(getFields(lines[5]));
            itsReferenceEpoch = getReferenceEpoch();
            itsPolynomialCorrection.itsSatelliteClockEpoch = getSatelliteClockEpoch();
        }

        private LocalDateTime getReferenceEpoch() {
            return GPS_EPOCH.plusDays(7 * itsWeekNumber);
        }

        private double getSatelliteClockEpoch() {
            LocalDateTime toc = LocalDateTime.of(itsYear, itsMonth, itsDay, itsHour, itsMinute, itsSecond);
            return itsReferenceEpoch.until(toc, ChronoUnit.SECONDS);
        }

        private void readSvEpochSvClk(String[] fields) {
            itsYear = parseInt(fields[1]);
            itsMonth = parseInt(fields[2]);
            itsDay = parseInt(fields[3]);
            itsHour = parseInt(fields[4]);
            itsMinute = parseInt(fields[5]);
            itsSecond = parseInt(fields[6]);
            itsPolynomialCorrection.itsClockBias = parseDouble(fields[7]);
            itsPolynomialCorrection.itsClockDrift = parseDouble(fields[8]);
            itsPolynomialCorrection.itsClockDriftRate = parseDouble(fields[9]);
        }

        private void readBroadcastOrbit1(String[] fields) {
            itsPositionModel.itsCrs = parseDouble(fields[1]);
            itsPositionModel.itsDeltaN = parseDouble(fields[2]);
            itsPositionModel.itsM0 = parseDouble(fields[3]);
        }

        private void readBroadcastOrbit2(String[] fields) {
            itsPositionModel.itsCuc = parseDouble(fields[0]);
            itsPositionModel.itsE = parseDouble(fields[1]);
            itsPositionModel.itsCus = parseDouble(fields[2]);
            itsPositionModel.itsSqrtA = parseDouble(fields[3]);
        }

        private void readBroadcastOrbit3(String[] fields) {
            itsPositionModel.itsSecondsSinceReferenceEpoch = parseDouble(fields[0]);
            itsPositionModel.itsCic = parseDouble(fields[1]);
            itsPositionModel.itsOmega0 = parseDouble(fields[2]);
            itsPositionModel.itsCis = parseDouble(fields[3]);
        }

        private void readBroadcastOrbit4(String[] fields) {
            itsPositionModel.itsI0 = parseDouble(fields[0]);
            itsPositionModel.itsCrc = parseDouble(fields[1]);
            itsPositionModel.itsOmega = parseDouble(fields[2]);
            itsPositionModel.itsOmegaDot = parseDouble(fields[3]);
        }

        private void readBroadcastOrbit5(String[] fields) {
            itsPositionModel.itsIDot = parseDouble(fields[0]);
            itsWeekNumber = (long) parseDouble(fields[2]);
        }
    }
}
