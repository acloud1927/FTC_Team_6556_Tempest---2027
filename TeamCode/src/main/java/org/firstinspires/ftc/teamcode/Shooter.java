package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
//import org.firstinspires.ftc.robotcore.external.JavaUtil;

public class Shooter {

    public DcMotorEx shooter1;
    public DcMotorEx shooter2;
    public Gamepad gamepad;

    public Shooter(DcMotorEx shooter1, DcMotorEx shooter2, Gamepad gamepad) {
        this.shooter1 = shooter1;
        this.shooter2 = shooter2;

        this.gamepad = gamepad;

        this.shooter1.setDirection(DcMotorEx.Direction.REVERSE);
        this.shooter2.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void Simple() {
        if (gamepad.left_trigger > 0) {
            //this.shooter1.setPower(0.6);
            //this.shooter2.setPower(0.6);
            this.shooter1.setVelocity(2100);
            this.shooter2.setVelocity(2100);
        } else if (gamepad.left_bumper) {
            //this.shooter1.setPower(-0.4);
            //this.shooter2.setPower(-0.4);
            this.shooter1.setVelocity(-700);
            this.shooter2.setVelocity(-700);
        } else {
            this.shooter1.setPower(0);
            this.shooter2.setPower(0);
        }

    }

}
