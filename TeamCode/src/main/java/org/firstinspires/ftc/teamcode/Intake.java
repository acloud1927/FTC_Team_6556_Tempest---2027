package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {

    public DcMotor intake;
    public Gamepad gamepad;

    public Intake(DcMotor intake, Gamepad gamepad) {
        this.intake = intake;

        this.gamepad = gamepad;

        this.intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        this.intake.setDirection(DcMotor.Direction.REVERSE);
    }

    public void Simple() {
        if (gamepad.right_trigger > 0) {
            this.intake.setPower(1);
        } else if (gamepad.right_bumper) {
            this.intake.setPower(-1);
        } else {
            this.intake.setPower(0);
        }
    }

}
