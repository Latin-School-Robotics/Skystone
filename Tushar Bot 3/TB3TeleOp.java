/*
TB3TeleOp.java

Made with <3 by Charlie Gray

Player 1 controls:

Right stick x: strafe
Right stick y: forward and back
Left stick x: rotate
Right trigger: slow

Player 2 controls:

Right stick y: spin grab wheels
Right trigger: slow

Hardware Map:

leftFront = lf
rightFront = rf
leftBack = lb
rightBack = rb
lGrabWheel = lgw
rGrabWheel = rgw

Change log:

2/6/2020
Script made
Added basic drive func
Added grab wheel spin
Added slow trigger
 */


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import Servo and Motor
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;



@TeleOp(name = "TB3TeleOp", group = "TeleOp")


public class TB3TeleOp extends LinearOpMode {

    //Create motors
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor lGrabWheel;
    DcMotor rGrabWheel;

    //Create speed multiplier vars
    float speedMultP1=1.0f;
    float speedMultP2=1.0f;

    @Override
    public void runOpMode() {

        //get hardware
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        leftBack = hardwareMap.get(DcMotor.class, "lb");
        rightBack = hardwareMap.get(DcMotor.class, "rb");

        lGrabWheel = hardwareMap.get(DcMotor.class, "lgw");
        rGrabWheel = hardwareMap.get(DcMotor.class, "rgw");



        //disable floating
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Update telem
        telemetry.addData(">", "Press Start to begin :)" );
        telemetry.update();


        //Start loop
        waitForStart();
        while(opModeIsActive()){

            //Player 1

            //Foward Back
            leftFront.setPower(gamepad1.right_stick_y);
            rightFront.setPower(gamepad1.right_stick_y*-1);
            leftBack.setPower(gamepad1.right_stick_y*-1);
            rightBack.setPower(gamepad1.right_stick_y);

            //Rotate

            leftFront.setPower(gamepad1.left_stick_x);
            rightFront.setPower(gamepad1.left_stick_x);
            leftBack.setPower(gamepad1.left_stick_x);
            rightBack.setPower(gamepad1.left_stick_x);

            //Strafe
            leftFront.setPower(gamepad1.right_stick_x);
            rightFront.setPower(gamepad1.right_stick_x);
            leftBack.setPower(gamepad1.right_stick_x*-1);
            rightBack.setPower(gamepad1.right_stick_x*-1);

            //Speed modifier

            speedMultP1 = 1.0f -gamepad1.right_trigger;

            leftFront.setPower(leftFront.getPower()*speedMultP1);
            rightFront.setPower(rightFront.getPower()*speedMultP1);
            leftBack.setPower(leftBack.getPower()*speedMultP1);
            rightBack.setPower(rightBack.getPower()*speedMultP1);

            //telemetry
            telemetry.addData("Speed mult: ", speedMultP1);
            telemetry.addData("Left Front: ", leftFront.getPowerFloat());
            telemetry.addData("Left Back: ", leftBack.getPowerFloat());
            telemetry.addData("Right Front: ", rightFront.getPowerFloat());
            telemetry.addData("Right Back: ", rightBack.getPowerFloat());
            telemetry.update();

            //Player 2

            //Get speed mult
            speedMultP2 = 1.0f -gamepad2.right_trigger;

            //Spin grab wheels

            lGrabWheel.setPower(gamepad2.right_stick_y * speedMultP2);
            rGrabWheel.setPower(-gamepad2.right_stick_y * speedMultP2);
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
