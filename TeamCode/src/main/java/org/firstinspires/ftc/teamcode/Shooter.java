package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
//import org.firstinspires.ftc.robotcore.external.JavaUtil;

public class Shooter {

    public DcMotor shooter1;
    public DcMotor shooter2;
    public Gamepad gamepad;

    public Shooter(DcMotor shooter1, DcMotor shooter2, Gamepad gamepad) {
        this.shooter1 = shooter1;
        this.shooter2 = shooter2;

        this.gamepad = gamepad;

        this.shooter1.setDirection(DcMotor.Direction.FORWARD);
        this.shooter2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void Simple() {
        if (gamepad.left_trigger > 0) {
            this.shooter1.setPower(0.6);
            this.shooter2.setPower(0.6);
        } else if (gamepad.left_bumper) {
            this.shooter1.setPower(-0.4);
            this.shooter2.setPower(-0.4);
        } else {
            this.shooter1.setPower(0);
            this.shooter2.setPower(0);
        }

    }

}
