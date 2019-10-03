package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@TeleOp(name="ArmThing", group="Stuff")
public class ArmThing extends OpMode 
{
    final float sense = 10;
    DcMotor shoulder;
    DcMotor elbow;
    final int a = 15;
    final int b = 15;
    final float maxD = 30;
    Servo wrist;
    Servo twist;
    Servo clawL;
    Servo clawR;
    
    boolean manual = true;
    boolean lastX = false;
    boolean lastA = false;
    boolean clawClosed = false;
    
    Vec hand = new Vec(29,1);
    Vec o = new Vec(0,0);
    
    public void init(){
        shoulder = hardwareMap.dcMotor.get("shoulder");
        wrist = hardwareMap.servo.get("wrist");
        twist = hardwareMap.servo.get("twist");
        clawL = hardwareMap.servo.get("clawL");
        clawR = hardwareMap.servo.get("clawR");
        elbow = hardwareMap.dcMotor.get("elbow");
        
        //shoulder.setDirection(DcMotorSimple.Direction.REVERSE);
        elbow.setDirection(DcMotorSimple.Direction.REVERSE);
        
        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
    }
    
    public void loop(){
        Vec newH = hand.sum(new Vec(gamepad1.left_stick_x/sense, -gamepad1.left_stick_y/sense));
        if(o.distance(newH) < maxD){
            hand = newH;
        }
        
        float d = o.distance(hand);
        
        //angles in degrees
        double angle1 = Math.acos( (a*a + d*d - b*b)/(2*a*d)  ) + Math.atan(newH.y/newH.x);
        double angle2 = Math.acos( (a*a - d*d + b*b)/(2*a*b)  );
        angle1 *= 180/Math.PI;
        angle2 *= 180/Math.PI;
        
        
    //    double a1 = angle1 * 720/Math.PI *9;
    //    double a2 = angle2 * 720/ Math.PI * 4;
    
        double p1 = 90 + shoulder.getCurrentPosition() / 4 / 9;
        double p2 = 180 - elbow.getCurrentPosition() / 4 / 6;
        
        
        //Target positions
        shoulder.setTargetPosition(36 * (int)(angle1 - 90));
        elbow.setTargetPosition(24 * (int)(180 - angle2));
        
        double t1 = 90 + shoulder.getTargetPosition() / 4 / 9;
        double t2 = 180 - elbow.getTargetPosition() / 4 / 6;
        
        if(gamepad1.x & !lastX){
            manual = !manual;
            
            if(manual){
                shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            else{
                shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            
        }
        if(manual){
            shoulder.setPower(gamepad2.right_stick_x);
            elbow.setPower(gamepad2.right_stick_y);
        }
        else {
            shoulder.setPower(0.4);
            elbow.setPower(0.4);
        }
        
        lastX = gamepad1.x;
        
        telemetry.addData("coords: ", newH.x + ", " + newH.y);
        telemetry.addData("distance: ", d);

        telemetry.addData("mathAngle1: ", angle1);
        telemetry.addData("mathAngle2: ", angle2);
        
        telemetry.addData("targetAngle1: ", t1);
        telemetry.addData("targetAngle2: ", t2);
        
        telemetry.addData("currentAngle1: ", p1);
        telemetry.addData("currentAngle2: ", p2);
        
        telemetry.addData("manual: ", manual);
         telemetry.addData("mode: ", shoulder.getMode());
        
        

        
        
       
       // wrist.setPosition(angle1 + angle2);
        wrist.setPosition(1/2.0 + gamepad1.right_stick_y/2);
        twist.setPosition(1/2.0 + gamepad1.right_stick_x/2);

        if(gamepad1.a && !lastA){
            clawClosed = ! clawClosed;
        }
        
        if(clawClosed){
            clawL.setPosition(0);
            clawR.setPosition(1);
        }
        else{
            clawL.setPosition(0.5);
            clawR.setPosition(0.5);
        }
        
        
        lastA = gamepad1.a;
        
    }
    

    
}




