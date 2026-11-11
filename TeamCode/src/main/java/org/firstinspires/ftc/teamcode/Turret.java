package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Turret {

    public DcMotor turret;
    public Servo hoodRight;
    public Servo hoodLeft;
    public Gamepad gamepad;

    public Turret(DcMotor turret, Servo hoodRight, Servo hoodLeft, Gamepad gamepad) {
        this.turret = turret;
        this.hoodRight = hoodRight;
        this.hoodLeft = hoodLeft;

        this.gamepad = gamepad;

        this.turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        this.turret.setDirection(DcMotor.Direction.FORWARD);
    }

    public void Simple() {
        if (gamepad.dpad_up) {
            this.turret.setPower(0.25);
        } else if (gamepad.dpad_down) {
            this.turret.setPower(-0.25);
        } else {
            this.turret.setPower(0);
        }

        if (gamepad.dpad_right) {
            this.hoodRight.setPosition(1);
            this.hoodLeft.setPosition(0);
        } else if (gamepad.dpad_left) {
            this.hoodRight.setPosition(0);//about 90: 120.0 * (1.0 /180.0): 2/3
            this.hoodLeft.setPosition(1);// about 90: 60.0 * (1.0 /180.0): 1/3
        }


        // limelight angle + arcTan(pose Z, pose D) + a few more degrees


    }

}
