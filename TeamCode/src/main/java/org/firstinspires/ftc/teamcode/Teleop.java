package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    private IMU imu;
    private GoBildaPinpointDriver pinpoint;
    private double headingOffset;
    private DcMotorEx Back_right;
    private DcMotorEx Front_right;
    private DcMotorEx Front_left;
    private DcMotorEx Back_left;
    private DcMotor shooter1;
    private DcMotor shooter2;
    private DcMotorEx intake;
    // private VisionPortal visionPortal;
    //private AprilTagProcessor aprilTag;
    private boolean previousA = false;
    ElapsedTime tick = new ElapsedTime();
    private double angleOffset = 0;
    private Gamepad driver;
    private Gamepad operator;


    @Override
    public void runOpMode() {
        Back_right = hardwareMap.get(DcMotorEx.class, "Back_right");
        Front_right = hardwareMap.get(DcMotorEx.class, "Front_right");
        Front_left = hardwareMap.get(DcMotorEx.class, "Front_left");
        Back_left = hardwareMap.get(DcMotorEx.class, "Back_left");

        intake = hardwareMap.get(DcMotorEx.class, "intake");

        shooter1 = hardwareMap.get(DcMotor.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotor.class, "shooter2");

        driver = gamepad1;
        operator = gamepad2;

        imu = hardwareMap.get(IMU.class, "imu");

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        //pinpoint.resetPosAndIMU();

        /*
        // Create the AprilTag processor
        aprilTag = new AprilTagProcessor.Builder().build();

        // Get the webcam from the hardware map
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        // Build the vision portal with the camera and AprilTag processor
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .addProcessor(aprilTag)
                .build();

        // Wait for the camera to start streaming
        while (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING && !isStopRequested()) {
            sleep(20);
        }

        setManualExposure(8);  // exposure ms

        telemetry.addLine("Ready for start; initializing AprilTag detection...");
        telemetry.update();
        */
        imu.resetYaw();

        waitForStart();


        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        intake.setDirection(DcMotor.Direction.REVERSE);

        Back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Back_left.setDirection(DcMotor.Direction.REVERSE);
        Front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Front_left.setDirection(DcMotor.Direction.REVERSE);

        shooter1.setDirection(DcMotor.Direction.REVERSE);
        shooter2.setDirection(DcMotor.Direction.REVERSE);

        RevHubOrientationOnRobot orientationOnRobot =
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                DriveTrain();
                Shooter();
                Telemetry();
                Intake();
                pinpoint.update();
            }
        }
    }

    /*private void setManualExposure(int exposureMS) {
        if (visionPortal == null) return;
        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        if (exposureControl == null) return;
        // Turn off auto exposure
        exposureControl.setMode(ExposureControl.Mode.Manual);
        // Set exposure time
        exposureControl.setExposure(exposureMS, TimeUnit.MILLISECONDS);
    }*/

    private void Intake() {
        if (operator.right_trigger > 0) {
            intake.setPower(1);
        } else if (operator.right_bumper) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
    }

    private void Shooter() {
        if (operator.left_trigger > 0) {
            shooter1.setPower(0.6);
            shooter2.setPower(0.6);
        } else if (operator.left_bumper) {
            shooter1.setPower(-0.4);
            shooter2.setPower(-0.4);
        } else {
            shooter1.setPower(0);
            shooter2.setPower(0);
        }

    }

    private void TankDrive() {
        Back_left.setPower(driver.left_stick_y);
        Front_left.setPower(driver.left_stick_y);
        Front_right.setPower(driver.left_stick_y);
        Back_right.setPower(driver.left_stick_y);
    }

    private void DumbTrain() {
        float Strafe;
        float Turn;
        float forward;
        double denominator;

        Turn = driver.right_stick_x * 0.8f;
        Strafe = driver.left_stick_x;
        forward = driver.left_stick_y;
        denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Turn + forward + Strafe));
        Back_left.setPower((forward + (Strafe - Turn)) * denominator);
        Front_left.setPower((forward - (Strafe + Turn)) * denominator);
        Back_right.setPower((forward - (Strafe - Turn)) * denominator);
        Front_right.setPower((forward + Strafe + Turn) * denominator);
    }

    private void DriveTrain() {

        double y = driver.left_stick_y;
        double x = driver.left_stick_x;
        double direction = driver.right_stick_x * 0.5f;

        //double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double heading = -pinpoint.getHeading(AngleUnit.RADIANS) - angleOffset;

        if (driver.a && !previousA) {
            angleOffset = -pinpoint.getHeading(AngleUnit.RADIANS);
        }
        previousA = driver.a;

        double dx = x * Math.cos(-heading) - y * Math.sin(-heading);
        double dy = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, direction + dy + dx));
        Back_left.setPower((dy + (dx - direction)) * denominator);
        Front_left.setPower((dy - (dx + direction)) * denominator);
        Back_right.setPower((dy - (dx - direction)) * denominator);
        Front_right.setPower((dy + dx + direction) * denominator);
    }

    private void Telemetry() {
        telemetry.addData("BACK_LEFT", Back_left.getPower());
        telemetry.addData("BACK_RIGHT", Back_right.getPower());
        telemetry.addData("FRONT_RIGHT", Front_right.getPower());
        telemetry.addData("FRONT_LEFT", Front_left.getPower());

        telemetry.addData("Heading", -pinpoint.getHeading(AngleUnit.DEGREES));

        //List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        //telemetry.addData("# Detected", currentDetections.size());

        telemetry.update();

    }

}