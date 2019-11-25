//NOTE: If you are making any changes to controls, please update the list here :)
/*
Controls:

Universal:

Right Trigger : Will slow movement for your controller (NOTE: WILL NOT SLOW BUTTON PRESS ACTIONS ex. A, B, etc.)


Gamepad 1

Left Stick Y : Move forward and back
Left Stick X : Strafe left and right
Right Stick X : Rotate



Gamepad 2

Left Stick Y : Raise/Lower elevator
Right Stick Y : Lift/Lower claw linier Slide
A : Open and close the claw
Y : Flip tray grabbers

*/

package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

//Created by Tushar, Charlie G, and Jackson  :)

@TeleOp(name = "TusharBot", group = "Stuff")
public class TusharBot extends OpMode {
    // Create motors
    DcMotor driveFL;
    DcMotor driveFR;
    DcMotor driveBL;
    DcMotor driveBR;

    DcMotor rotator;
    DcMotor rope;

    // Create servos
    Servo lift;
    Servo wrist;
    Servo clawL;
    Servo clawR;
    Servo grabL;
    Servo grabR;

    // Create vars used for input
    boolean lastX = false;
    boolean lastA = false;

    boolean[] lastButtons = new boolean[10];
    boolean[] buttonPresses = new boolean[10];

    boolean clawClosed = false;
    boolean grab = false;

    public void init() {

        // Get motors
        driveFL = hardwareMap.dcMotor.get("front left drive");
        driveFR = hardwareMap.dcMotor.get("front right drive");
        driveBL = hardwareMap.dcMotor.get("back left drive");
        driveBR = hardwareMap.dcMotor.get("back right drive");

        // reverse direction of FL and BL motors
        driveFL.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBL.setDirection(DcMotorSimple.Direction.REVERSE);

       // elevatorTiltLeft = hardwareMap.dcMotor.get("left rotator");
        //elevatorTiltRight = hardwareMap.dcMotor.get("right rotator");

        // Set zero power behavior for elevator tilt
       // elevatorTiltLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //elevatorTiltRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotator = hardwareMap.dcMotor.get("rotator");
        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotator.setTargetPosition(0);
        rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reverse direction of one elevator tilt motor to run both motors in same
        // direction
        //elevatorTiltLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        rope = hardwareMap.dcMotor.get("vertical elevator");
        rope.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lift = hardwareMap.servo.get("vertical lift servo");
        wrist = hardwareMap.servo.get("wrist");
        clawL = hardwareMap.servo.get("claw left");
        clawR = hardwareMap.servo.get("claw right");
        grabL = hardwareMap.servo.get("hand servo left");
        grabR = hardwareMap.servo.get("hand servo right");

        // run the rope without an encoder
        rope.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // set zero power behavior for rope
        rope.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotator.setPower(1);
    }

    public void loop() {

        //brake
        float moveSlow = (1 - gamepad1.right_trigger * 0.6f);

        // Drive controls
        float x = gamepad1.left_stick_x;
        float y = -gamepad1.left_stick_y;
        float r = gamepad1.right_stick_x;

        driveBL.setPower((y - x + r) * moveSlow);
        driveFL.setPower((y + x + r) * moveSlow);
        driveBR.setPower((y + x - r) * moveSlow);
        driveFR.setPower((y - x - r) * moveSlow);

        // Tilt elevator
        
        telemetry.addData("elevator: ", rope.getCurrentPosition());
        telemetry.addData("tilt: ", rotator.getCurrentPosition());
        telemetry.addData("bools: ", buttonPresses[0]);
        telemetry.addData("lastBools: ", lastButtons[0]);
        telemetry.update();
        

        // Registers when buttons are first pressed on gamepad2
        boolean[] buttons = new boolean[10];
        buttons[0] = gamepad2.a;
        buttons[1] = gamepad2.y;
        buttons[2] = gamepad2.x;
        buttons[3] = gamepad2.b;
        buttons[4] = gamepad2.dpad_up;
        buttons[5] = gamepad2.dpad_left;
        buttons[6] = gamepad2.dpad_down;
        buttons[7] = gamepad2.dpad_right;
        buttons[8] = gamepad2.left_bumper;
        buttons[9] = gamepad2.right_bumper;

        for (int i = 0; i < 10; i++) {
            buttonPresses[i] = (buttons[i] && (!lastButtons[i]));
        }

        rope.setPower(gamepad2.left_stick_y);

        //Percision movements with right joystick
        wrist.setPosition(gamepad2.right_stick_x/2 + 0.5);
        lift.setPosition(gamepad2.right_stick_y/2 + 0.5);
        
        // Open and close claw with A
        if (buttonPresses[0]) {
            clawClosed = !clawClosed;
        }
        clawL.setPosition(clawClosed ? 0 : 0.5);
        clawR.setPosition(clawClosed ? 1 : 0.5);

        // Flip tray grabber things with Y
        if (buttonPresses[1]) {
            grab = !grab;
        }
        grabL.setPosition(grab ? 1 : 0);
        grabR.setPosition(grab ? 0 : 1);
    
        if(gamepad2.dpad_up){
            rotator.setTargetPosition(rotator.getCurrentPosition() + 10);
        }
        else if (gamepad2.dpad_down){
            rotator.setTargetPosition(rotator.getCurrentPosition() - 10);
        }
        


        lastButtons = buttons;
    }

 
    public double clamp(double num, double min, double max){ 
        if(num > max){ 
            return max; 
        }
        if(num < min){ 
            return min; 
        }
        return num;
    }
      
     

}
