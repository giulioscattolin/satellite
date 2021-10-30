package com.github.giulioscattolin.satellite;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.google.common.truth.Truth.assertThat;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class QuasiKeplerianSatelliteModelTest {
    private RinexEphemerisReader.RinexQuasiKeplerianEphemeris itsGpsEphemeris;
    private RinexEphemerisReader.RinexQuasiKeplerianEphemeris itsGalileoEphemeris;

    @Test
    public void verifyGpsSatelliteModel() {
        givenGpsEphemeris("" +
            "G02 2021 05 10 00 00 00 -.602878164500D-03 -.318323145621D-11  .000000000000D+00\n" +
            "      .490000000000D+02 -.196250000000D+02  .423517641210D-08 -.245341177049D+01\n" +
            "     -.910833477974D-06  .201946522575D-01  .115446746349D-04  .515367399216D+04\n" +
            "      .864000000000D+05  .210478901863D-06  .301450476878D+01  .173225998878D-06\n" +
            "      .963236206586D+00  .152312500000D+03 -.151962909047D+01 -.745316759720D-08\n" +
            "      .326085011321D-09  .100000000000D+01  .215700000000D+04  .000000000000D+00\n" +
            "      .200000000000D+01  .000000000000D+00 -.176951289177D-07  .490000000000D+02\n" +
            "      .792180000000D+05  .400000000000D+01");

        verifyModelAgainstGLabResults("" +
            "SATPVT 2021 129 79500.00 GPS  2  -1133449.7547 -16398197.7914  21508987.7534      2438.2899       848.3593       804.9073    -180731.742079\n" +
            "SATPVT 2021 129 79800.00 GPS  2   -395880.7040 -16148941.2729  21730947.2897      2478.0330       812.8651       674.6164    -180732.028372\n" +
            "SATPVT 2021 129 80100.00 GPS  2    352879.3321 -15910756.6000  21913644.5142      2512.8691       774.5930       543.1959    -180732.314665\n" +
            "SATPVT 2021 129 80400.00 GPS  2   1111334.4387 -15684435.1921  22056774.2270      2542.6423       733.8243       410.8705    -180732.600957\n" +
            "SATPVT 2021 129 80700.00 GPS  2   1877944.5749 -15470682.7131  22160098.7422      2567.2145       690.8493       277.8656    -180732.887250\n" +
            "SATPVT 2021 129 81000.00 GPS  2   2651131.0060 -15270116.4712  22223448.0941      2586.4659       645.9664       144.4071    -180733.173543\n" +
            "SATPVT 2021 129 81300.00 GPS  2   3429281.8748 -15083263.1691  22246720.1726      2600.2955       599.4811        10.7215    -180733.459835\n" +
            "SATPVT 2021 129 81600.00 GPS  2   4210757.8938 -14910557.0110  22229880.7883      2608.6216       551.7044      -122.9649    -180733.746128\n" +
            "SATPVT 2021 129 81900.00 GPS  2   4993898.1389 -14752338.1734  22172963.6684      2611.3816       502.9519      -256.4257    -180734.032420\n" +
            "SATPVT 2021 129 82200.00 GPS  2   5777025.9245 -14608851.6418  22076070.3836      2608.5330       453.5424      -389.4350    -180734.318713\n" +
            "SATPVT 2021 129 82500.00 GPS  2   6558454.7408 -14480246.4204  21939370.2059      2600.0530       403.7967      -521.7675    -180734.605006\n" +
            "SATPVT 2021 129 82800.00 GPS  2   7336494.2342 -14366575.1148  21763099.8977      2585.9388       354.0365      -653.1986    -180734.891298\n" +
            "SATPVT 2021 129 83100.00 GPS  2   8109456.2091 -14267793.8903  21547563.4313      2566.2075       304.5829      -783.5047    -180735.177591\n" +
            "SATPVT 2021 129 83400.00 GPS  2   8875660.6325 -14183762.8066  21293131.6394      2540.8963       255.7553      -912.4638    -180735.463884\n" +
            "SATPVT 2021 129 83700.00 GPS  2   9633441.6211 -14114246.5283  21000241.7941      2510.0624       207.8700     -1039.8551    -180735.750176\n" +
            "SATPVT 2021 129 84000.00 GPS  2  10381153.3896 -14058915.4098  20669397.1156      2473.7826       161.2391     -1165.4597    -180736.036469\n" +
            "SATPVT 2021 129 84300.00 GPS  2  11117176.1416 -14017346.9521  20301166.2075      2432.1533       116.1695     -1289.0607    -180736.322762\n" +
            "SATPVT 2021 129 84600.00 GPS  2  11839921.8823 -13989027.6278  19896182.4187      2385.2901        72.9610     -1410.4434    -180736.609054\n" +
            "SATPVT 2021 129 84900.00 GPS  2  12547840.1318 -13973355.0700  19455143.1299      2333.3274        31.9057     -1529.3956    -180736.895347\n" +
            "SATPVT 2021 129 85200.00 GPS  2  13239423.5218 -13969640.6193  18978808.9630      2276.4180        -6.7134     -1645.7079    -180737.181639\n" +
            "SATPVT 2021 129 85500.00 GPS  2  13913213.2527 -13977112.2217  18468002.9113      2214.7329       -42.6235     -1759.1740    -180737.467932\n" +
            "SATPVT 2021 129 85800.00 GPS  2  14567804.3942 -13994917.6703  17923609.3897      2148.4602       -75.5632     -1869.5908    -180737.754225\n" +
            "SATPVT 2021 129 86100.00 GPS  2  15201851.0081 -14022128.1811  17346573.2013      2077.8049      -105.2835     -1976.7589    -180738.040517\n" +
            "SATPVT 2021 130     0.00 GPS  2  15814071.0756 -14057742.2932  16737898.4187      2002.9881      -131.5491     -2080.4825    -180738.326810\n" +
            "SATPVT 2021 130   300.00 GPS  2  16403251.2095 -14100690.0816  16098647.1790      1924.2460      -154.1392     -2180.5704    -180738.613103\n" +
            "SATPVT 2021 130   600.00 GPS  2  16968251.1333 -14149837.6715  15429938.3881      1841.8297      -172.8483     -2276.8354    -180738.899395\n" +
            "SATPVT 2021 130   900.00 GPS  2  17508007.9096 -14203992.0385  14732946.3335      1756.0036      -187.4876     -2369.0955    -180739.185688\n" +
            "SATPVT 2021 130  1200.00 GPS  2  18021539.8994 -14261906.0822  14008899.2031      1667.0450      -197.8857     -2457.1736    -180739.471981\n" +
            "SATPVT 2021 130  1500.00 GPS  2  18507950.4364 -14322283.9556  13259077.5065      1575.2430      -203.8892     -2540.8978    -180739.758273\n" +
            "SATPVT 2021 130  1800.00 GPS  2  18966431.2007 -14383786.6347  12484812.3988      1480.8973      -205.3637     -2620.1025    -180740.044566\n" +
            "SATPVT 2021 130  2100.00 GPS  2  19396265.2739 -14445037.7091  11687483.9025      1384.3174      -202.1944     -2694.6277    -180740.330858\n" +
            "SATPVT 2021 130  2400.00 GPS  2  19796829.8642 -14504629.3753  10868519.0266      1285.8211      -194.2869     -2764.3200    -180740.617151\n" +
            "SATPVT 2021 130  2700.00 GPS  2  20167598.6847 -14561128.6113  10029389.7817      1185.7337      -181.5677     -2829.0330    -180740.903444\n" +
            "SATPVT 2021 130  3000.00 GPS  2  20508143.9741 -14613083.5125   9171611.0875      1084.3866      -163.9845     -2888.6271    -180741.189736\n" +
            "SATPVT 2021 130  3300.00 GPS  2  20818138.1462 -14659029.7644   8296738.5741       982.1159      -141.5068     -2942.9705    -180741.476029\n" +
            "SATPVT 2021 130  3600.00 GPS  2  21097355.0588 -14697497.2314   7406366.2729       879.2617      -114.1264     -2991.9390    -180741.762322\n" +
            "SATPVT 2021 130  3900.00 GPS  2  21345670.8910 -14727016.6336   6502124.1993       776.1659       -81.8573     -3035.4170    -180742.048614\n" +
            "SATPVT 2021 130  4200.00 GPS  2  21563064.6216 -14746126.2900   5585675.8241       673.1715       -44.7361     -3073.2973    -180742.334907\n" +
            "SATPVT 2021 130  4500.00 GPS  2  21749618.1001 -14753378.8988   4658715.4362       570.6212        -2.8221     -3105.4815    -180742.621200\n" +
            "SATPVT 2021 130  4800.00 GPS  2  21905515.7064 -14747348.3290   3722965.3946       468.8556        43.8029     -3131.8810    -180742.907492\n" +
            "SATPVT 2021 130  5100.00 GPS  2  22031043.5932 -14726636.3956   2780173.2723       368.2123        95.0344     -3152.4164    -180743.193785\n" +
            "SATPVT 2021 130  5400.00 GPS  2  22126588.5095 -14689879.5897   1832108.8930       269.0241       150.7457     -3167.0187    -180743.480077\n" +
            "SATPVT 2021 130  5700.00 GPS  2  22192636.2044 -14635755.7339    880561.2625       171.6180       210.7881     -3175.6292    -180743.766370\n" +
            "SATPVT 2021 130  6000.00 GPS  2  22229769.4108 -14562990.5345    -72664.6026        76.3134       274.9912     -3178.1999    -180744.052663\n" +
            "SATPVT 2021 130  6300.00 GPS  2  22238665.4134 -14470364.0006  -1025750.9455       -16.5790       343.1633     -3174.6938    -180744.338955\n" +
            "SATPVT 2021 130  6600.00 GPS  2  22220093.2028 -14356716.6989  -1976870.6352      -106.7589       415.0926     -3165.0855    -180744.625248\n" +
            "SATPVT 2021 130  6900.00 GPS  2  22174910.2257 -14220955.8161  -2924190.6237      -193.9370       490.5469     -3149.3612    -180744.911541");
    }

    @Test
    public void verifyGalileoSatelliteModel() {
        givenGalileoEphemeris("" +
            "E04 2020 01 11 22 20 00-4.435816081241e-04-7.617018127348e-12 0.000000000000e+00\n" +
            "     1.020000000000e+02 3.090625000000e+01 3.268350425628e-09-9.280353459515e-01\n" +
            "     1.389533281326e-06 6.746326107532e-05 1.017935574055e-05 5.440614864349e+03\n" +
            "     5.988000000000e+05-7.450580596924e-08 1.085935183548e+00 6.332993507385e-08\n" +
            "     9.531954083691e-01 1.184375000000e+02 1.571593639092e+00-5.483085535378e-09\n" +
            "     8.886084426799e-10 0.000000000000e+00 2.087000000000e+03 0.000000000000e+00\n" +
            "     3.120000000000e+00 0.000000000000e+00-5.355104804039e-09-6.053596735001e-09\n" +
            "     5.994640000000e+05   ");

        verifyModelAgainstGLabResults("" +
            "SATPVT 2020 011 81300.00 GAL  4  -6443052.7023  23687366.8104  16537660.0458      -359.8232     -1619.2557      2178.7455    -132984.475795\n" +
            "SATPVT 2020 011 81600.00 GAL  4  -6558814.6329  23193423.1539  17179693.8758      -412.2572     -1673.0711      2100.9868    -132985.160853\n" +
            "SATPVT 2020 011 81900.00 GAL  4  -6690596.8289  22683909.1575  17797961.3756      -466.5889     -1723.0364      2020.3217    -132985.845910\n" +
            "SATPVT 2020 011 82200.00 GAL  4  -6838935.7587  22159998.7204  18391607.2989      -522.5979     -1769.0279      1936.8619    -132986.530967\n" +
            "SATPVT 2020 011 82500.00 GAL  4  -7004300.7767  21622900.7761  18959810.4697      -580.0577     -1810.9358      1850.7229    -132987.216025\n" +
            "SATPVT 2020 011 82800.00 GAL  4  -7187092.2549  21073855.2186  19501784.9171      -638.7353     -1848.6638      1762.0239    -132987.901082\n" +
            "SATPVT 2020 011 83100.00 GAL  4  -7387639.9571  20514128.7250  20016780.9615      -698.3926     -1882.1296      1670.8876    -132988.586140\n" +
            "SATPVT 2020 011 83400.00 GAL  4  -7606201.6617  19945010.4882  20504086.2507      -758.7880     -1911.2654      1577.4401    -132989.271197\n" +
            "SATPVT 2020 011 83700.00 GAL  4  -7842962.0377  19367807.8745  20963026.7433      -819.6759     -1936.0178      1481.8108    -132989.956254\n" +
            "SATPVT 2020 011 84000.00 GAL  4  -8098031.7789  18783842.0211  21392967.6402      -880.8089     -1956.3485      1384.1318    -132990.641312\n" +
            "SATPVT 2020 011 84300.00 GAL  4  -8371446.9979  18194443.3901  21793314.2613      -941.9375     -1972.2336      1284.5383    -132991.326369\n" +
            "SATPVT 2020 011 84600.00 GAL  4  -8663168.8830  17600947.2925  22163512.8661     -1002.8117     -1983.6647      1183.1682    -132992.011426\n" +
            "SATPVT 2020 011 84900.00 GAL  4  -8973083.6192  17004689.3990  22503051.4188     -1063.1815     -1990.6479      1080.1616    -132992.696484\n" +
            "SATPVT 2020 011 85200.00 GAL  4  -9301002.5730  16407001.2531  22811460.2944     -1122.7984     -1993.2045       975.6609    -132993.381541\n" +
            "SATPVT 2020 011 85500.00 GAL  4  -9646662.7411  15809205.8022  23088312.9276     -1181.4155     -1991.3706       869.8109    -132994.066599\n" +
            "SATPVT 2020 011 85800.00 GAL  4 -10009727.4613  15212612.9614  23333226.4009     -1238.7884     -1985.1968       762.7578    -132994.751656\n" +
            "SATPVT 2020 011 86100.00 GAL  4 -10389787.3825  14618515.2264  23545861.9731     -1294.6771     -1974.7486       654.6497    -132995.436713\n" +
            "SATPVT 2020 012     0.00 GAL  4 -10786361.6913  14028183.3506  23725925.5470     -1348.8454     -1960.1055       545.6361    -132996.121771\n" +
            "SATPVT 2020 012   300.00 GAL  4 -11198899.5913  13442862.1004  23873168.0747     -1401.0630     -1941.3612       435.8679    -132996.806828\n" +
            "SATPVT 2020 012   600.00 GAL  4 -11626782.0279  12863766.1054  23987385.9014     -1451.1055     -1918.6229       325.4968    -132997.491885\n" +
            "SATPVT 2020 012   900.00 GAL  4 -12069323.6556  12292075.8156  24068421.0466     -1498.7554     -1892.0111       214.6755    -132998.176943");
    }

    private void givenGpsEphemeris(String ephemeris) {
        itsGpsEphemeris = new RinexEphemerisReader.GpsEphemeris(ephemeris);
    }

    private void givenGalileoEphemeris(String ephemeris) {
        itsGalileoEphemeris = new RinexEphemerisReader.GalileoEphemeris(ephemeris);
    }

    private void verifyModelAgainstGLabResults(String output) {
        for (String result : output.split("\n"))
            verifyModelAgainstGLabSatPvtRecord(result);
    }

    private void verifyModelAgainstGLabSatPvtRecord(String record) {
        int year = parseInt(record.substring(7, 11));
        int dayOfYear = parseInt(record.substring(12, 15));
        int secondsOfDay = (int) parseDouble(record.substring(16, 24));
        String satelliteSystem = record.substring(25, 28);
        double positionX = parseDouble(record.substring(32, 46));
        double positionY = parseDouble(record.substring(47, 61).trim());
        double positionZ = parseDouble(record.substring(62, 76).trim());
        double velocityX = parseDouble(record.substring(77, 91).trim());
        double velocityY = parseDouble(record.substring(92, 106).trim());
        double velocityZ = parseDouble(record.substring(107, 121).trim());
        double correctionInMeters = parseDouble(record.substring(122, 136).trim());
        double[] position = getPosition(satelliteSystem, year, dayOfYear, secondsOfDay);
        double[] velocity = getVelocity(satelliteSystem, year, dayOfYear, secondsOfDay);
        double correctionInSeconds = getCorrectionInSeconds(satelliteSystem, year, dayOfYear, secondsOfDay);
        assertThat(position[0]).isWithin(5E-3).of(positionX);
        assertThat(position[1]).isWithin(5E-3).of(positionY);
        assertThat(position[2]).isWithin(5E-3).of(positionZ);
        assertThat(velocity[0]).isWithin(5E-3).of(velocityX);
        assertThat(velocity[1]).isWithin(5E-3).of(velocityY);
        assertThat(velocity[2]).isWithin(5E-3).of(velocityZ);
        assertThat(correctionInMeters).isWithin(5E-3).of(correctionInSeconds * 299792458);
    }

    private double[] getPosition(String satelliteSystem, int year, int dayOfYear, int secondsOfDay) {
        switch (satelliteSystem) {
            case "GPS":
                return itsGpsEphemeris.itsPositionModel.getPositionAt(getSecondsInReferenceEpoch("GPS",year, dayOfYear, secondsOfDay));
            case "GAL":
                return itsGalileoEphemeris.itsPositionModel.getPositionAt(getSecondsInReferenceEpoch("GAL",year, dayOfYear, secondsOfDay));
        }
        throw new UnsupportedOperationException("Unsupported satellite system: " + satelliteSystem);
    }

    private double[] getVelocity(String satelliteSystem, int year, int dayOfYear, int secondsOfDay) {
        switch (satelliteSystem) {
            case "GPS":
                return itsGpsEphemeris.itsVelocityModel.getVelocityAt(getSecondsInReferenceEpoch("GPS",year, dayOfYear, secondsOfDay));
            case "GAL":
                return itsGalileoEphemeris.itsVelocityModel.getVelocityAt(getSecondsInReferenceEpoch("GAL",year, dayOfYear, secondsOfDay));
        }
        throw new UnsupportedOperationException("Unsupported satellite system: " + satelliteSystem);
    }

    private double getCorrectionInSeconds(String satelliteSystem, int year, int dayOfYear, int secondsOfDay) {
        switch (satelliteSystem) {
            case "GPS":
                return itsGpsEphemeris.itsPolynomialCorrection.getCorrectionInSeconds(getSecondsInReferenceEpoch("GPS",year, dayOfYear, secondsOfDay));
            case "GAL":
                return itsGalileoEphemeris.itsPolynomialCorrection.getCorrectionInSeconds(getSecondsInReferenceEpoch("GAL",year, dayOfYear, secondsOfDay));
        }
        throw new UnsupportedOperationException("Unsupported satellite system: " + satelliteSystem);
    }

    private double getSecondsInReferenceEpoch(String satelliteSystem, int year, int dayOfYear, int secondsOfDay) {
        LocalDateTime time = LocalDateTime.of(year, 1, 1, 0, 0, 0)
            .plusDays(dayOfYear - 1)
            .plusSeconds(secondsOfDay);
        switch (satelliteSystem) {
            case "GPS":
                return itsGpsEphemeris.itsReferenceEpoch.until(time, ChronoUnit.SECONDS);
            case "GAL":
                return itsGalileoEphemeris.itsReferenceEpoch.until(time, ChronoUnit.SECONDS);
        }
        throw new UnsupportedOperationException("Unsupported satellite system: " + satelliteSystem);
    }

    @Before
    public void before() {
        itsGpsEphemeris = null;
    }
}