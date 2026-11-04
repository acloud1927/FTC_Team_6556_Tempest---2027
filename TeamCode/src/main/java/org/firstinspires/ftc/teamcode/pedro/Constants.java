package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("Front_left");
        c.frontRightName.set("Front_right");
        c.backLeftName.set("Back_left");
        c.backRightName.set("Back_right");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(1.8038692624550166);
        c.yPodOffset.set(-4.07456015038678);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.58122549596069058);
                Controller secondaryTranslationalForward = Controller.proportional(0.2147746143107903);
                Controller primaryTranslationalLateral = Controller.proportional(30.196734711846805);
                Controller secondaryTranslationalLateral = Controller.proportional(11.156886604415526);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.010546573166278233));
                c.brake.set(Controller.proportionalFeedforward(0.008964587191336498));

                c.headingFeedback.set(Controller.proportional(4));//9.874481747213721
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.14613283301763813, 0.010653824034956577));

                c.linearBrakeCoefficients.set(Matrix.diag(0.5123294398291389, 0.035130414145540335));
                c.quadraticBrakeCoefficients.set(Matrix.diag(-0.0031015881464280482, 0.004636596508492678));

                c.maxAchievableForwardVelocity.set(54.126770283889115);
                c.maxAchievableStrafeVelocity.set(37.8534421656041);
                c.naturalForwardDeceleration.set(30.41149475453094);
                c.naturalStrafeDeceleration.set(78.18095658775461);
            }
    );



}