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
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain_{
    DcMotor front_left_motor, front_right_motor, rear_left_motor, rear_right_motor;
    double internalHeading;
    DcMotor[] motorArray = new DcMotor[4];
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, startAngle;
    final static double DEGREES_AT_FULL_POWER = 30;
    final static double STANDARD_VOLTAGE = 13;
    LinearOpMode op;
    long wait = 500;
    final static double[][] MOTOR_SIGNS = {
        //   fl fr bl br
        { 1, 1, 1, 1},    //Forward
        { 1,-1,-1, 1},    //Right
        {-1, 1,-1, 1},    //Counterclockwise
};
final static double SET_HEADING_THRESHOLD = 5;

    public Drivetrain_(LinearOpMode op) {
        this.op = op;
        front_left_motor = op.hardwareMap.dcMotor.get("front left drive");
        front_right_motor = op.hardwareMap.dcMotor.get("front right drive");
        rear_left_motor = op.hardwareMap.dcMotor.get("back left drive");
        rear_right_motor = op.hardwareMap.dcMotor.get("back right drive");

        front_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rear_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rear_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorArray[0] = front_left_motor;
        motorArray[1] = front_right_motor;
        motorArray[2] = rear_left_motor;
        motorArray[3] = rear_right_motor;
        // TODO: get it to work with encoders
        // front_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // front_left_motor.setPower(1);

        // front_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // front_right_motor.setPower(1);

        // rear_left_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // rear_left_motor.setPower(1);

        // rear_right_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // rear_right_motor.setPower(1);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = op.hardwareMap.get(BNO055IMU.class, "imu");
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

    /*public double tareHeading() {
        internalHeading = 0;
    }*/

    /*private correctHeading() {
        internalHeading = internalHeading % (2*Math.PI);
    }*/

    /*
     * drive method takes in distance you want to travel as a parameter, and it
     * drives in the current direction that distance
     */
    public void drive(double distance) {
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
            dm.setTargetPosition((int)(dm.getCurrentPosition() + (1440 * sign * distance) / (Math.PI * 10.16)));
        }
    }
    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
    public double getAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
                deltaAngle += 360;
        else if (deltaAngle > 180)
                deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle+startAngle;
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
        // turn the motors off.
        front_right_motor.setPower(0);
        front_left_motor.setPower(0);

        // wait for rotation to stop.
        

        // reset angle tracking on new heading.
        resetAngle();
    }
    public boolean setHeading(double heading, double power) {
        op.telemetry.addData("Phase", "setHeading");
        op.telemetry.update();
        boolean turned = false;
        power *= STANDARD_VOLTAGE/14;
        while(!op.isStopRequested()) {
                double error = getAngle()-heading;
                if(Math.abs(error) < SET_HEADING_THRESHOLD) {
                        for(DcMotor m:motorArray)
                                m.setPower(0);
                        break;
                }
                for(int i = 0; i < 4; i++)
                        motorArray[i].setPower(power*MOTOR_SIGNS[2][i]* -Math.signum(error));
                turned = true;
        }
        op.telemetry.addData("Turned", turned);
        op.telemetry.update();
        if(turned)
                op.sleep(wait);
        return turned;
}
public void driveAtHeading(double heading, double primary, double lateral, double power) {
        setHeading(heading, power);
        op.telemetry.addData("Phase", "driveAtHeading");
        op.telemetry.update();
        power *= STANDARD_VOLTAGE/14;

        double distance = Math.hypot(primary, lateral);
        int[] directions = new int[4];
        double[] targets = new double[4];
        boolean[] keyMotors = new boolean[4];
        for(int i = 0; i < 4; i++) {
                double delta = primary*MOTOR_SIGNS[0][i] + lateral*MOTOR_SIGNS[1][i];
                directions[i] = (int) Math.signum(delta);
                targets[i] = delta + motorArray[i].getCurrentPosition();
                keyMotors[i] = Math.abs(delta) > .5*distance;
        }

drive:
        while(!op.isStopRequested()) {
                double error = getAngle()-heading;
                for(int i = 0; i < 4; i++)
                        motorArray[i].setPower(power* (
                                                   MOTOR_SIGNS[0][i]*primary/distance +
                                                   MOTOR_SIGNS[1][i]*lateral/distance +
                                                   MOTOR_SIGNS[2][i]* -error/DEGREES_AT_FULL_POWER));

                for(int i = 0; i < 4; i++)
                        if(keyMotors[i] && motorArray[i].getCurrentPosition()*directions[i] > targets[i]*directions[i])
                                break drive;
        }
        for(DcMotor m:motorArray)
                m.setPower(0);
        op.sleep(wait);
        for(int i = 0; i < 4; i++)
                op.telemetry.addData("Encoder Status " + i, (motorArray[i].getCurrentPosition()*directions[i] > targets[i]*directions[i]) ? "ok" : "BAD");
        op.telemetry.update();
}
}
