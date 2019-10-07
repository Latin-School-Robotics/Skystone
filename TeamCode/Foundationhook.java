package org.firstinspires.ftc.Autos;

//import statements
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.*;

@Autonomous(name = "Foundationhook", group = "Autonomous")
public class Foundationhook extends LinearOpMode{
  // Constant for default position of hookServo
  final float servoPos = 0;

  HardwareMap hm;

  // The servo for the hook itself
  Servo hookServo;

  // Foundationhook constructor
  public Foundationhook (HardwareMap hm) {
    this.hm = hm;
    hookServo = hm.get(Servo.class, "hookServo");
     hookServo.setPosition(servoPos);
  }

  public void runOpMode () {
    //hookServo.setPosition();
  }

  // Either move the servo up or down depending on the user's desires
  public void adjustHook (boolean up) {
    if (up && hookServo.MAX_POSITION > hookServo.getPosition()) {
      hookServo.setPosition(hookServo.getPosition() + 0.1);
      telemetry.addData("Servo Position: ", hookServo.getPosition());
      telemetry.update();
    }
    // Servo setPosition goes from 0 - 1. 0 being 0º, and 1 being 180º.
    // When gamepad2.right_stick_y == 1, I want it to set position to 1
    // If up is true, make the servo move up. Else keep the servo position the statement
    //hookServo.setPosition();

  }

  public void setPosition (double position) {
    /* Call the built-in setPosition on the servo
    * We want to be ableto initialize the robot once and then not have to interact with the servos again
    */
    hookServo.setPosition(position);
    telemetry.addData("Servo Position: ", hookServo.getPosition());
    telemetry.update();
  }

}
