package com.github.giulioscattolin.satellite;

public class QuasiKeplerianVelocityPositionModel {
    private QuasiKeplerianSatellitePositionModel itsPositionModel;

    public void setPositionModel(QuasiKeplerianSatellitePositionModel itsPositionModel) {
        this.itsPositionModel = itsPositionModel;
    }

    public double[] getVelocityAt(double secondsSinceReferenceEpoch) {
        return new Algorithm(secondsSinceReferenceEpoch).velocity;
    }

    class Algorithm {
        final double[] velocity;

        Algorithm(double secondsSinceTheBeginningOfTheWeek) {
            double dt = 1E-4;
            itsPositionModel.setSecondsSinceTheBeginningOfTheWeek(secondsSinceTheBeginningOfTheWeek);
            double[] p = itsPositionModel.getPosition();
            itsPositionModel.setSecondsSinceTheBeginningOfTheWeek(secondsSinceTheBeginningOfTheWeek + dt);
            double[] q = itsPositionModel.getPosition();
            double xk = (q[0] - p[0]) / dt;
            double yk = (q[1] - p[1]) / dt;
            double zk = (q[2] - p[2]) / dt;
            velocity = new double[]{xk, yk, zk};
        }
    }
}
