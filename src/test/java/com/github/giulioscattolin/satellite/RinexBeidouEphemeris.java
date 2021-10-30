package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;

public class RinexBeidouEphemeris extends RinexQuasiKeplerianEphemeris {
    public RinexBeidouEphemeris(String ephemeris) {
        super(ephemeris);
        itsPositionModel.setMu(3.986004418E14);
        itsTolerance = 1E2;
    }

    protected LocalDateTime getEpoch() {
        return LocalDateTime.of(2006, 1, 1, 0, 0, 0).plusSeconds(14);
    }
}
