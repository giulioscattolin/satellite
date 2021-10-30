package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;

public class RinexGpsEphemeris extends RinexQuasiKeplerianEphemeris {
    public RinexGpsEphemeris(String ephemeris) {
        super(ephemeris);
        itsPositionModel.itsMu = 3.986005E14;
        itsTolerance = 1E-3;
    }

    protected LocalDateTime getEpoch() {
        return LocalDateTime.of(1980, 1, 6, 0, 0, 0);
    }
}
