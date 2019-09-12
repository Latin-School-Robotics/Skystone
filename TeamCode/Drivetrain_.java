package org.firstinspires.ftc.teamcode;

import static java.lang.Math.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain_ {
	DcMotor front_left_motor, front_right_motor, rear_left_motor, rear_right_motor;
	double internalHeading;

	public Drivetrain_(HardwareMap hm) {
		front_left_motor = hm.get(DcMotor.class, "front_left_motor");
		front_right_motor = hm.get(DcMotor.class, "front_right_motor");
		rear_left_motor = hm.get(DcMotor.class, "rear_left_motor");
		rear_right_motor = hm.get(DcMotor.class, "rear_right_motor");

		front_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_left_motor.setPower(1);

        front_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_right_motor.setPower(1);

        rear_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rear_left_motor.setPower(1);

        rear_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rear_right_motor.setPower(1);
	}

	/**
		driveInDirection
		takes in calculatedRobotAngle and rotation and speed

		rotation is in radians
	*/
	public void driveInDirection(double speed, double robotAngle, double rotation) {
		//speed is between 0 and 1
		final double v1 = speed * Math.cos(robotAngle) + rotation;
		final double v2 = speed * Math.sin(robotAngle) - rotation;
		final double v3 = speed * Math.sin(robotAngle) + rotation;
		final double v4 = speed * Math.cos(robotAngle) - rotation;

		front_left_motor.setPower(v1*speed);
		front_right_motor.setPower(v2*speed);
		rear_left_motor.setPower(v3*speed);
		rear_right_motor.setPower(v4*speed);
	}

	public void setHeading(double heading) {
		double difHeading = internalHeading  - heading;
		driveInDirection(.7, 0, difHeading);
	}

	public double getHeading() {
		return internalHeading;
	}

	public double tareHeading() {
		internalHeading = 0;
	}

	private correctHeading() {
		internalHeading = internalHeading % (2*Math.PI);
	}
 }

