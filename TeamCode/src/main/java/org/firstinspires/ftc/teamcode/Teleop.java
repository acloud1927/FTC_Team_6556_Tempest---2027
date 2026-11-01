package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {

    private DriveTrain driveTrain;
    private Shooter shooter;
    private Intake intake;


    @Override
    public void runOpMode() {

        // private VisionPortal visionPortal;
        //private AprilTagProcessor aprilTag;
        Gamepad driver = gamepad1;
        Gamepad operator = gamepad2;

        driveTrain = new DriveTrain(
                hardwareMap.get(DcMotorEx.class, "Back_right"),
                hardwareMap.get(DcMotorEx.class, "Front_right"),
                hardwareMap.get(DcMotorEx.class, "Back_left"),
                hardwareMap.get(DcMotorEx.class, "Front_left"),
                driver,
                hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint")
        );

        shooter = new Shooter(
                hardwareMap.get(DcMotor.class, "shooter1"),
                hardwareMap.get(DcMotor.class, "shooter2"),
                operator
        );

        intake = new Intake(
                hardwareMap.get(DcMotorEx.class, "intake"),
                operator
        );




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

        waitForStart();


        if (opModeIsActive()) {
            while (opModeIsActive()) {
                driveTrain.Mechanum();
                shooter.Simple();
                intake.Simple();
                Telemetry();
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

    private void Telemetry() {
        telemetry.addData("Back_Left", driveTrain.Back_left.getPower());
        telemetry.addData("Back_Right", driveTrain.Back_right.getPower());
        telemetry.addData("Front_Right", driveTrain.Front_right.getPower());
        telemetry.addData("Front_Left", driveTrain.Front_left.getPower());

        telemetry.addData("Heading", -driveTrain.pinpoint.getHeading(AngleUnit.DEGREES));

        telemetry.addData("Intake", intake.intake.getPower());

        telemetry.addData("Shooter1 RPM", shooter.shooter1.getPower());
        telemetry.addData("Shooter2 RPM", shooter.shooter2.getPower());

        //List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        //telemetry.addData("# Detected", currentDetections.size());

        telemetry.update();

    }

}