package com.github.giulioscattolin.satellite;

public class QuasiKeplerianVelocityPositionModel {
    public QuasiKeplerianSatellitePositionModel itsPositionModel;

    public double[] getVelocityAt(double secondsSinceReferenceEpoch) {
        return new Algorithm(secondsSinceReferenceEpoch).velocity;
    }

    class Algorithm {
        final double[] velocity;

        Algorithm(double secondsSinceTheBeginningOfTheWeek) {
            double dt = 1E-4;
            double[] p = itsPositionModel.getPositionAt(secondsSinceTheBeginningOfTheWeek);
            double[] q = itsPositionModel.getPositionAt(secondsSinceTheBeginningOfTheWeek + dt);
            double xk = (q[0] - p[0]) / dt;
            double yk = (q[1] - p[1]) / dt;
            double zk = (q[2] - p[2]) / dt;
            velocity = new double[]{xk, yk, zk};
        }
    }
}
