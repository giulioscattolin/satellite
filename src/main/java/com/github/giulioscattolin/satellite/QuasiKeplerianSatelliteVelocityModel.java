package com.github.giulioscattolin.satellite;

import static java.lang.Double.NaN;

public class QuasiKeplerianSatelliteVelocityModel {
    private QuasiKeplerianSatellitePositionModel itsPositionModel;
    private double itsSecondsSinceTheBeginningOfTheWeek = NaN;

    public void setPositionModel(QuasiKeplerianSatellitePositionModel itsPositionModel) {
        this.itsPositionModel = itsPositionModel;
    }

    public void setSecondsSinceTheBeginningOfTheWeek(double newSecondsSinceTheBeginningOfTheWeek) {
        itsSecondsSinceTheBeginningOfTheWeek = newSecondsSinceTheBeginningOfTheWeek;
    }

    public double[] getVelocity() {
        return new Algorithm().velocity;
    }

    class Algorithm {
        final double[] velocity;

        Algorithm() {
            double dt = 1E-4;
            itsPositionModel.setSecondsSinceTheBeginningOfTheWeek(itsSecondsSinceTheBeginningOfTheWeek);
            double[] p = itsPositionModel.getPosition();
            itsPositionModel.setSecondsSinceTheBeginningOfTheWeek(itsSecondsSinceTheBeginningOfTheWeek + dt);
            double[] q = itsPositionModel.getPosition();
            double xk = (q[0] - p[0]) / dt;
            double yk = (q[1] - p[1]) / dt;
            double zk = (q[2] - p[2]) / dt;
            velocity = new double[]{xk, yk, zk};
        }
    }
}
