package com.github.giulioscattolin.satellite;

import static com.github.giulioscattolin.satellite.Constant.OMEGA_E_DOT;
import static java.lang.Math.*;

public class QuasiKeplerianSatellitePositionModel {
    public double itsCrs;
    public double itsDeltaN;
    public double itsM0;
    public double itsCuc;
    public double itsE;
    public double itsCus;
    public double itsSqrtA;
    public double itsCic;
    public double itsOmega0;
    public double itsCis;
    public double itsI0;
    public double itsCrc;
    public double itsOmega;
    public double itsOmegaDot;
    public double itsIDot;
    public double itsMu;
    public double itsSecondsSinceReferenceEpoch;

    public double[] getPositionAt(double secondsSinceReferenceEpoch) {
        return new Algorithm(secondsSinceReferenceEpoch).position;
    }

    class Algorithm {
        final double tk;
        final double A;
        final double N0;
        final double n;
        final double Mk;
        final double Ek;
        final double vk;
        final double phik;
        final double omegadotk;
        final double xkfirst;
        final double ykfirst;
        final double uk;
        final double rk;
        final double ik;
        final double omegak;
        final double xk;
        final double yk;
        final double zk;
        final double[] position;

        Algorithm(double secondsSinceReferenceEpoch) {
            tk = getTk(secondsSinceReferenceEpoch);
            A = getA();
            N0 = getN0();
            n = getN();
            Mk = getMeanAnomaly();
            Ek = getEccentricAnomaly();
            vk = getTrueAnomaly();
            phik = getPhik();
            omegadotk = getLongitudeOfAscendingNodeRate();
            uk = getArgumentOfLatitude();
            rk = getRadialDistance();
            ik = getInclinationOfOrbitalPlane();
            xkfirst = getInPlaneXPosition();
            ykfirst = getInPlaneYPosition();
            omegak = getLongitudeOfAscendingNode();
            xk = getX();
            yk = getY();
            zk = getZ();
            position = new double[]{xk, yk, zk};
        }

        /**
         * Returns the seconds since the ephemeris reference epoch frame.
         */
        double getTk(double secondsSinceReferenceEpoch) {
            double tk = secondsSinceReferenceEpoch - itsSecondsSinceReferenceEpoch;
            if (tk > 302400)
                return tk - 604800;
            if (tk < -302400)
                return tk + 604800;
            return tk;
        }

        double getA() {
            return itsSqrtA * itsSqrtA;
        }

        double getN0() {
            return sqrt(itsMu / (A * A * A));
        }

        double getN() {
            return N0 + itsDeltaN;
        }

        double getMeanAnomaly() {
            return itsM0 + n * tk;
        }

        double getEccentricAnomaly() {
            double Ej = Mk;
            for (int i = 0; i < 4; i++)
                Ej += ((Mk - Ej) + itsE * sin(Ej)) / (1 - itsE * cos(Ej));
            return Ej;
        }

        double getTrueAnomaly() {
            return 2 * atan(sqrt((1 + itsE) / (1 - itsE)) * tan(Ek / 2));
        }

        double getPhik() {
            return vk + itsOmega;
        }

        double getLongitudeOfAscendingNodeRate() {
            return itsOmegaDot - OMEGA_E_DOT;
        }

        double getArgumentOfLatitude() {
            return phik + itsCus * sin(2 * phik) + itsCuc * cos(2 * phik);
        }

        double getRadialDistance() {
            return A * (1 - itsE * cos(Ek)) + itsCrs * sin(2 * phik) + itsCrc * cos(2 * phik);
        }

        double getInclinationOfOrbitalPlane() {
            return itsI0 + itsCis * sin(2 * phik) + itsCic * cos(2 * phik) + itsIDot * tk;
        }

        double getLongitudeOfAscendingNode() {
            return itsOmega0 + omegadotk * tk - OMEGA_E_DOT * itsSecondsSinceReferenceEpoch;
        }

        double getInPlaneXPosition() {
            return rk * cos(uk);
        }

        double getInPlaneYPosition() {
            return rk * sin(uk);
        }

        double getX() {
            return (xkfirst * cos(omegak)) - (ykfirst * cos(ik) * sin(omegak));
        }

        double getY() {
            return (xkfirst * sin(omegak)) + (ykfirst * cos(ik) * cos(omegak));
        }

        double getZ() {
            return rk * sin(uk) * sin(ik);
        }
    }
}
