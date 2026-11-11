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
    public double desiredHeading = 0;
    public double angleOffset = 0;
    public double direction = 0;

    public DriveTrain(DcMotorEx Back_right, DcMotorEx Front_right, DcMotorEx Back_left, DcMotorEx Front_left, Gamepad gamepad, GoBildaPinpointDriver pinpoint) {
        this.Back_right = Back_right;
        this.Front_right = Front_right;
        this.Back_left = Back_left;
        this.Front_left = Front_left;

        this.gamepad = gamepad;

        this.pinpoint = pinpoint;

        this.pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        this.pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        this.Front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.Back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.Back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.Back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.Back_left.setDirection(DcMotor.Direction.REVERSE);
        this.Front_left.setDirection(DcMotor.Direction.REVERSE);

    }

    public void MechanumDriverCentric() {

        this.pinpoint.update();

        double y = gamepad.left_stick_y;
        double x = -gamepad.left_stick_x;
        double direction = gamepad.right_stick_x;

        double heading = this.pinpoint.getHeading(AngleUnit.RADIANS) - angleOffset;

        if (gamepad.a && !this.previousA) {
            this.angleOffset = this.pinpoint.getHeading(AngleUnit.RADIANS);
        }
        this.previousA = gamepad.a;

        double dx = x * Math.cos(-heading) - y * Math.sin(-heading);
        double dy = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, direction + dy + dx));

        this.Back_left.setPower((dy + (-dx - direction)) * denominator);
        this.Front_left.setPower((dy - (-dx + direction)) * denominator);
        this.Back_right.setPower((dy - (dx - direction)) * denominator);
        this.Front_right.setPower((dy + dx + direction) * denominator);
    }

    public void MechanumHeadingLock() {

        this.pinpoint.update();

        double heading = this.pinpoint.getHeading(AngleUnit.RADIANS) - this.angleOffset;

        if (gamepad.a && !this.previousA) {
            this.angleOffset = this.pinpoint.getHeading(AngleUnit.RADIANS);
        }
        this.previousA = gamepad.a;

        double y = gamepad.left_stick_y;
        double x = -gamepad.left_stick_x;
        //this.desiredHeading += gamepad.right_stick_x * 0.01;
//        if (Math.abs((this.desiredHeading-this.angleOffset) - heading) < 5) {
//            this.direction = 0;
//        }
        this.direction = 10 * (heading + Math.pow(this.desiredHeading - this.angleOffset, 3));


        double dx = x * Math.cos(-heading) - y * Math.sin(-heading);
        double dy = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, this.direction + dy + dx));

        this.Back_left.setPower((dy + (-dx - this.direction)) * denominator);
        this.Front_left.setPower((dy - (-dx + this.direction)) * denominator);
        this.Back_right.setPower((dy - (dx - this.direction)) * denominator);
        this.Front_right.setPower((dy + dx + this.direction) * denominator);
    }

    public void MechanumSimple() {
        float Strafe;
        float Turn;
        float forward;
        double denominator;

        Turn = gamepad.right_stick_x;
        Strafe = -gamepad.left_stick_x;
        forward = gamepad.left_stick_y;
        denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Turn + forward + Strafe));
        Back_left.setPower((forward + (-Strafe - Turn)) * denominator);
        Front_left.setPower((forward - (-Strafe + Turn)) * denominator);
        Back_right.setPower((forward - (Strafe - Turn)) * denominator);
        Front_right.setPower((forward + Strafe + Turn) * denominator);
    }


}
