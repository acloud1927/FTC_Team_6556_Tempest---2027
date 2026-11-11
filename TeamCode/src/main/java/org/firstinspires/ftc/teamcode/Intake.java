package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {

    public DcMotor intake;
    public Servo rightBlocker;
    public Servo leftBlocker;
    public Gamepad gamepad;

    public Intake(DcMotor intake, Servo rightBlocker, Servo leftBlocker, Gamepad gamepad) {
        this.intake = intake;
        this.rightBlocker = rightBlocker;
        this.leftBlocker = leftBlocker;

        this.gamepad = gamepad;

        this.intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        this.intake.setDirection(DcMotor.Direction.FORWARD);
    }

    public void Simple() {
        if (gamepad.right_trigger > 0) {
            this.intake.setPower(1);
        } else if (gamepad.right_bumper) {
            this.intake.setPower(-1);
        } else {
            this.intake.setPower(0);
        }

        if (gamepad.y) {
            this.rightBlocker.setPosition(0.3);
            this.leftBlocker.setPosition(0);
        } else if (gamepad.b) {
            this.rightBlocker.setPosition(0.13);
            this.leftBlocker.setPosition(0.17);
        } else {
            this.rightBlocker.setPosition(0);
            this.leftBlocker.setPosition(0.3);
        }
    }

}
