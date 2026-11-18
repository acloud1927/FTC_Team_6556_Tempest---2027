package org.firstinspires.ftc.teamcode;

import java.util.List;

public class Control {

    public static enum LoopFactors {P, I, D, FF};

    public static List<Double> Loop(List<LoopFactors> factors, List<Double> constants, double goal, double current, double dTime, double lastError) {
        //long now = System.nanoTime();

        double error = goal - current;

        double pTerm = 0;
        double iTerm = 0;
        double dTerm = 0;
        double ffTerm = 0;


        if (factors.contains(LoopFactors.P)) {
            pTerm = constants.get(factors.indexOf(LoopFactors.P)) * error;
        }

        if (factors.contains(LoopFactors.I)) {
            double integralSum = 0;
            if (dTime > 0) {
                integralSum += error * dTime;
                //integralSum = Math.max(-maxIntegral, Math.min(maxIntegral, integralSum));
            }
            iTerm = constants.get(factors.indexOf(LoopFactors.I)) * integralSum;
        }

        if (factors.contains(LoopFactors.D)) {
            double derivative = (dTime > 0) ? (error - lastError) / dTime : 0.0;
            dTerm = constants.get(factors.indexOf(LoopFactors.D)) * derivative;
        }

        if (factors.contains(LoopFactors.FF)) {
            ffTerm = constants.get(factors.indexOf(LoopFactors.FF)) * goal;
        }

        return List.of(pTerm + iTerm + dTerm + ffTerm, error);
    }

}
