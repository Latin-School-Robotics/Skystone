/**
Drivetrain_.java
Created by Jackson Bremen

Changelog:
9/12/19
 - Created file
 - Rotation values are almost certainly meaningless


TODO: 
	Get setup for encoders, if wanted. Last year we didn't have them for drivetrain tho

*/

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
	DcMotor[] motorArray;

	public Drivetrain_(HardwareMap hm) {
		front_left_motor = hm.get(DcMotor.class, "front_left_motor");
		front_right_motor = hm.get(DcMotor.class, "front_right_motor");
		rear_left_motor = hm.get(DcMotor.class, "rear_left_motor");
		rear_right_motor = hm.get(DcMotor.class, "rear_right_motor");
		BNO055IMU imu;
		Orientation lastAngles = new Orientation();
		double globalAngle;

		front_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		front_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rear_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rear_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		motorArray={front_left_motor,rear_left_motor,front_right_motor,rear_right_motor};
		// TODO: get it to work with encoders
		// front_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// front_left_motor.setPower(1);

		// front_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// front_right_motor.setPower(1);

		// rear_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// rear_left_motor.setPower(1);

		// rear_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// rear_right_motor.setPower(1);
	}

	/**
	 * driveInDirection takes in calculatedRobotAngle and rotation and speed
	 * 
	 * rotation is in radians
	 */
	public void driveInDirection(double speed, double robotAngle, double rotation) {
		// speed is between 0 and 1
		final double v1 = speed * Math.cos(robotAngle) + rotation;
		final double v2 = speed * Math.sin(robotAngle) - rotation;
		final double v3 = speed * Math.sin(robotAngle) + rotation;
		final double v4 = speed * Math.cos(robotAngle) - rotation;

		front_left_motor.setPower(v1 * speed);
		front_right_motor.setPower(v2 * speed);
		rear_left_motor.setPower(v3 * speed);
		rear_right_motor.setPower(v4 * speed);
	}

	public void setHeading(double heading) {
		double difHeading = internalHeading - heading;
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

	/*
	 * drive method takes in distance you want to travel as a parameter, and it
	 * drives in the current direction that distance
	 */
	public void drive(float distance) {
		// 1440 * d / 10.16pi
		// motorArray order is fL, bL, fR, bR
		// signum just takes the sign (+/-) of the input
		double sign = Math.signum((double) distance);
		// setTargetPosition itself will not go backwards. You must set power to
		// negative. Use the signum for this.
		// TODO: Will this method allow the motors to move concurrently?
		for (DcMotor dm : motorArray) {
			dm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			dm.setPower(sign);
			dm.setTargetPosition(dm.getCurrentPosition() + (1440 * sign * distance) / (Math.PI * 10.16));
		}
	}

	public void initImu() {
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

		parameters.mode = BNO055IMU.SensorMode.IMU;
		parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		parameters.loggingEnabled = false;

		// Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C
		// port
		// on a Core Device Interface Module, configured to be a sensor of type
		// "AdaFruit IMU",
		// and named "imu".
		imu = hardwareMap.get(BNO055IMU.class, "imu");
		imu.initialize(parameters);
	}

	public void resetAngle() {
		lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
		globalAngle = 0;
	}

	public void rotate(int degrees) {
		double leftPower, rightPower;

		// restart imu movement tracking.
		resetAngle();

		// getAngle() returns + when rotating clockwise (right) and - when rotating
		// counter-clockwise (left).

		if (degrees < 0) { // turn right.
			leftPower = -0.5;
			rightPower = 0.5;
		} else if (degrees > 0) { // turn left.
			leftPower = 0.5;
			rightPower = -0.5;
		} else
			return;

		// set power to rotate.
		front_left_motor.setPower(leftPower);
		front_right_motor.setPower(rightPower);

		// rotate until turn is completed.
		if (degrees < 0) {
			// On right turn we have to get off zero first.
			while (opModeIsActive() && getAngle() == 0) {
			}

			while (opModeIsActive() && getAngle() > degrees) {
			}
		} else // left turn.
			while (opModeIsActive() && getAngle() < degrees) {
			}

		// turn the motors off.
		rightMotor.setPower(0);
		leftMotor.setPower(0);

		// wait for rotation to stop.
		sleep(1000);

		// reset angle tracking on new heading.
		resetAngle();
	}
}