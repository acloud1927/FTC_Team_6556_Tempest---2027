package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    private DriveTrain driveTrain;
    private Shooter shooter;
    private Intake intake;
    private Turret turret;
    private AprilTagHandler aprilTagHandler;

    @Override
    public void runOpMode() {

        Gamepad driver = gamepad1;
        Gamepad operator = gamepad1;

        driveTrain = new DriveTrain(
                hardwareMap.get(DcMotorEx.class, "Back_right"),
                hardwareMap.get(DcMotorEx.class, "Front_right"),
                hardwareMap.get(DcMotorEx.class, "Back_left"),
                hardwareMap.get(DcMotorEx.class, "Front_left"),
                driver,
                hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint")
        );

        shooter = new Shooter(
                hardwareMap.get(DcMotorEx.class, "shooter1"),
                hardwareMap.get(DcMotorEx.class, "shooter2"),
                operator
        );

        intake = new Intake(
                hardwareMap.get(DcMotorEx.class, "intake"),
                hardwareMap.get(Servo.class, "right_blocker"),
                hardwareMap.get(Servo.class, "left_blocker"),
                operator
        );

        turret = new Turret(
                hardwareMap.get(DcMotor.class, "turret"),
                hardwareMap.get(Servo.class, "right_hood"),
                hardwareMap.get(Servo.class, "left_hood"),
                operator
        );

        aprilTagHandler = new AprilTagHandler(
                hardwareMap.get(WebcamName.class, "Webcam 1")
        );

        waitForStart();


        if (opModeIsActive()) {
            while (opModeIsActive()) {
                aprilTagHandler.getTargetCluster();
                driveTrain.MechanumDriverCentric();
                shooter.Simple();
                intake.Simple();
                turret.WebcamTracking(aprilTagHandler.targetCluster.ftcPose);
                Telemetry();
            }
        }
    }


    private void Telemetry() {

        telemetry.addData("Heading", driveTrain.pinpoint.getHeading(AngleUnit.DEGREES) - driveTrain.angleOffset);

        telemetry.addData("Shooter RPM", shooter.shooter1.getVelocity());

        telemetry.addData("Cluster Range", aprilTagHandler.targetCluster.ftcPose.range);

        telemetry.addData("Cluster Bearing", aprilTagHandler.targetCluster.ftcPose.bearing);

        telemetry.addData("Cluster Yaw", aprilTagHandler.targetCluster.ftcPose.yaw);

        telemetry.update();

    }

}