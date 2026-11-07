package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Turret {

    public DcMotor turret;
    public Gamepad gamepad;

    public Turret(DcMotor turret, Gamepad gamepad) {
        this.turret = turret;

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
    }

}
