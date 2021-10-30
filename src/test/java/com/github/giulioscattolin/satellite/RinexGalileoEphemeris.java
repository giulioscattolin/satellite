package com.github.giulioscattolin.satellite;

import java.time.LocalDateTime;

public class RinexGalileoEphemeris extends RinexQuasiKeplerianEphemeris {
    public RinexGalileoEphemeris(String ephemeris) {
        super(ephemeris);
        itsPositionModel.itsMu = 3.986004418E14;
        itsTolerance = 1E-2;
    }

    protected LocalDateTime getEpoch() {
        return LocalDateTime.of(1980, 1, 6, 0, 0, 0);
    }
}
