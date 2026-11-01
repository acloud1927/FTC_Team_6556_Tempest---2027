package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class DriveTrain {

    public GoBildaPinpointDriver pinpoint;
    public DcMotorEx Back_right;
    public DcMotorEx Front_right;
    public DcMotorEx Front_left;
    public DcMotorEx Back_left;
    private boolean previousA = false;
    private Gamepad gamepad;
    private double angleOffset = 0;

    public DriveTrain(DcMotorEx Back_right, DcMotorEx Front_right, DcMotorEx Back_left, DcMotorEx Front_left, Gamepad gamepad, GoBildaPinpointDriver pinpoint) {
        this.Back_right = Back_right;
        this.Front_right = Front_right;
        this.Back_left = Back_left;
        this.Front_left = Front_left;

        this.gamepad = gamepad;

        this.pinpoint = pinpoint;

        this.pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        this.pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);


        this.Back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.Front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.Back_left.setDirection(DcMotor.Direction.REVERSE);
        this.Front_left.setDirection(DcMotor.Direction.REVERSE);

    }

    public void Mechanum() {

        this.pinpoint.update();

        double y = gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double direction = gamepad.right_stick_x * 0.5f;

        double heading = -this.pinpoint.getHeading(AngleUnit.RADIANS) - angleOffset;

        if (gamepad.a && !this.previousA) {
            this.angleOffset = -this.pinpoint.getHeading(AngleUnit.RADIANS);
        }
        this.previousA = gamepad.a;

        double dx = x * Math.cos(-heading) - y * Math.sin(-heading);
        double dy = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, direction + dy + dx));

        this.Back_left.setPower((dy + (dx - direction)) * denominator);
        this.Front_left.setPower((dy - (dx + direction)) * denominator);
        this.Back_right.setPower((dy - (dx - direction)) * denominator);
        this.Front_right.setPower((dy + dx + direction) * denominator);
    }

}
