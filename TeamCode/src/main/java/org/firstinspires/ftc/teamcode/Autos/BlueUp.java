package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.*;

@TeleOp
public class BlueUp extends LinearOpMode {
    @Override
    public void runOpMode() {
 /*       //Since the scheduler is static, we need to reset it before each OpMode
        //so commands don't carry over from one OpMode to the next
        Scheduler.reset();

        // Initialize hardware
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "arm");
        Servo claw = hardwareMap.get(Servo.class, "claw");

        // Define commands
        Command raiseArm = Command.build()
                .setExecute(() -> armMotor.setPower(0.5))
                .setDone(() -> armMotor.getCurrentPosition() > 1000)
                .setEnd(endCondition -> armMotor.setPower(0))
                .requiring(armMotor);

        Command openClaw = instant(() -> claw.setPosition(1.0));

        // Compose: raise the arm, wait 200ms, then open the claw
        Command sequence = sequential(
                raiseArm,
                waitMs(200),
                openClaw
        );

        waitForStart();

        // Schedule the sequence when the OpMode starts
        schedule(sequence);

        while (opModeIsActive()) {
            // Run the scheduler each loop
            Scheduler.execute();
        }*/
    }
}