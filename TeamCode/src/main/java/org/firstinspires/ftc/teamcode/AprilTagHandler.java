package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;

import java.util.concurrent.TimeUnit;
public class AprilTagHandler {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private WebcamName webcam;
    public AprilTagClusterDetection targetCluster;
    public enum Color {RED, BLUE};
    public Color teamColor = Color.RED;

    public AprilTagHandler(WebcamName webcam) {

        this.webcam = webcam;

        aprilTag = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(this.webcam)
                .addProcessor(aprilTag)
                .build();

        ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure(8, TimeUnit.MILLISECONDS);

    }

    public void getTargetCluster() {
        AprilTagClusterDetection cluster;
        String checkCharacter;
        if (this.teamColor == Color.RED) {
            checkCharacter = "R";
        } else {
            checkCharacter = "B";
        }

        for (AprilTagDetection detection : aprilTag.getDetections()) {
            cluster = (AprilTagClusterDetection) detection;
            if (Math.abs(cluster.ftcPose.roll) > 90 && cluster.metadata.name.contains(checkCharacter)) {
                this.targetCluster = cluster;
            }
        }
    }

}
