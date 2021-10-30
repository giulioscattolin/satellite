package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Integer.parseInt;

public class RinexEphemerisReader {
    private static double parseDouble(String field) {
        return Double.parseDouble(field.replace('D', 'E'));
    }

    public static class GpsEphemeris extends RinexQuasiKeplerianEphemeris {
        public static final LocalDateTime GPS_EPOCH = LocalDateTime.of(1980, 1, 6, 0, 0, 0);

        public GpsEphemeris(String ephemeris) {
            super(ephemeris);
            itsPositionModel.itsMu = 3.986005E14;
        }

        protected LocalDateTime getReferenceEpoch() {
            return GPS_EPOCH.plusDays(7 * itsWeekNumber);
        }
    }

    public static abstract class RinexQuasiKeplerianEphemeris {
        public QuasiKeplerianSatellitePositionModel itsPositionModel = new QuasiKeplerianSatellitePositionModel();
        public QuasiKeplerianVelocityPositionModel itsVelocityModel = new QuasiKeplerianVelocityPositionModel();
        public PolynomialSatelliteClockCorrection itsPolynomialCorrection = new PolynomialSatelliteClockCorrection();
        public LocalDateTime itsReferenceEpoch;
        protected long itsWeekNumber;
        private int itsYear;
        private int itsMonth;
        private int itsDay;
        private int itsHour;
        private int itsMinute;
        private int itsSecond;

        public RinexQuasiKeplerianEphemeris(String ephemeris) {
            readLines(ephemeris.split("\n"));
            itsPositionModel.itsMu = 3.986005E14;
            itsVelocityModel.itsPositionModel = itsPositionModel;
        }

        private void readLines(String[] lines) {
            readSvEpochSvClk(lines[0]);
            readBroadcastOrbit1(lines[1]);
            readBroadcastOrbit2(lines[2]);
            readBroadcastOrbit3(lines[3]);
            readBroadcastOrbit4(lines[4]);
            readBroadcastOrbit5(lines[5]);
            itsReferenceEpoch = getReferenceEpoch();
            itsPolynomialCorrection.itsSatelliteClockEpoch = getSatelliteClockEpoch();
        }

        protected abstract LocalDateTime getReferenceEpoch();

        private double getSatelliteClockEpoch() {
            LocalDateTime toc = LocalDateTime.of(itsYear, itsMonth, itsDay, itsHour, itsMinute, itsSecond);
            return itsReferenceEpoch.until(toc, ChronoUnit.SECONDS);
        }

        private void readSvEpochSvClk(String line) {
            itsYear = parseInt(line.substring(4, 8));
            itsMonth = parseInt(line.substring(9, 11));
            itsDay = parseInt(line.substring(12, 14));
            itsHour = parseInt(line.substring(15, 17));
            itsMinute = parseInt(line.substring(18, 20));
            itsSecond = parseInt(line.substring(21, 23));
            itsPolynomialCorrection.itsClockBias = parseDouble(line.substring(23, 42));
            itsPolynomialCorrection.itsClockDrift = parseDouble(line.substring(42, 61));
            itsPolynomialCorrection.itsClockDriftRate = parseDouble(line.substring(61, 80));
        }

        private void readBroadcastOrbit1(String line) {
            itsPositionModel.itsCrs = parseDouble(line.substring(23, 42));
            itsPositionModel.itsDeltaN = parseDouble(line.substring(42, 61));
            itsPositionModel.itsM0 = parseDouble(line.substring(61, 80));
        }

        private void readBroadcastOrbit2(String line) {
            itsPositionModel.itsCuc = parseDouble(line.substring(4, 23));
            itsPositionModel.itsE = parseDouble(line.substring(23, 42));
            itsPositionModel.itsCus = parseDouble(line.substring(42, 61));
            itsPositionModel.itsSqrtA = parseDouble(line.substring(61, 80));
        }

        private void readBroadcastOrbit3(String line) {
            itsPositionModel.itsSecondsSinceReferenceEpoch = parseDouble(line.substring(4, 23));
            itsPositionModel.itsCic = parseDouble(line.substring(23, 42));
            itsPositionModel.itsOmega0 = parseDouble(line.substring(42, 61));
            itsPositionModel.itsCis = parseDouble(line.substring(61, 80));
        }

        private void readBroadcastOrbit4(String line) {
            itsPositionModel.itsI0 = parseDouble(line.substring(4, 23));
            itsPositionModel.itsCrc = parseDouble(line.substring(23, 42));
            itsPositionModel.itsOmega = parseDouble(line.substring(42, 61));
            itsPositionModel.itsOmegaDot = parseDouble(line.substring(61, 80));
        }

        private void readBroadcastOrbit5(String line) {
            itsPositionModel.itsIDot = parseDouble(line.substring(4, 23));
            itsWeekNumber = (long) parseDouble(line.substring(42, 61));
        }
    }
}
