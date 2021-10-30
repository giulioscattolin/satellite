package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;

public class RinexBeidouEphemeris extends RinexQuasiKeplerianEphemeris {
    public static final LocalDateTime EPOCH = LocalDateTime.of(2006, 1, 1, 0, 0, 0);

    public RinexBeidouEphemeris(String ephemeris) {
        super(ephemeris);
        itsPositionModel.itsMu = 3.986004418E14;
        itsTolerance = 1E2;
    }

    protected LocalDateTime getReferenceEpoch() {
        return EPOCH.plusDays(7 * itsWeekNumber).plusSeconds(14);
    }
}
