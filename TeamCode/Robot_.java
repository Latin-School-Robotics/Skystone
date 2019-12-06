import java.util.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drivetrain_;

import com.qualcomm.robotcore.eventloop.opmode.HardwareMap;


/**
 * Main robot object
 * Contains functions for controlling robot
 * Abstracts all motor movement to child objects
 * @author Jackson Bremen
 */
public class Robot_ {
    /**
     * 
     */
    private Arm_ Arm;

    /**
     * 
     */
    private Drivetrain_ Drivetrain;

    /**
     * 
     */
    private HardwareMap hardwareMap;

    /**
     * 
     */
    private Foundationhook_ Foundationhook;




    /**
     * @param double armLen1 
     * @param double armLen2 
     * @param HardwareMap hm
     */
    public Robot_(HardwareMap hm, double armLen1, double armLen2) {
        hardwareMap = hm;

        Arm = Arm_(hm, armLen1, armLen2, 5);
        Drivetrain = Drivetrain_(hm);
        Foundationhook = Foundationhook_(hm);
    }

    /**
     * @param double speed 
     * @param double angle
     */
    public void moveRobot(double speed, double angle) {
        // TODO implement here
        Drivetrain.driveInDirection(speed, angle);
    }

    /**
     * @param double heading
     */
    public void setHeading(double heading) {
        // TODO implement here
        Drivetrain.setHeading(heading);
    }

    /**
     * @param double x 
     * @param double y
     */
    public void setArmPos(double x, double y) {
        // TODO change speed, possibly for jacobean for linear pathing
        speed = 1;

        Arm.setPositionCoords(x,y, speed);
    }

    /**
     * @param double angle
     */
    public void setHeading(double angle) {
        Drivetrain.setHeading(angle);
    }

    /**
     * 
     */
    public void lowerHook() {
        Foundationhook.adjustHook(false);
    }

        /**
     * 
     */
    public void raiseHook() {
        Foundationhook.adjustHook(true);    }

    /**
     * 
     */
    public void openClaw() {
        Arm.adjustClaw(true);
    }

    /**
     * 
     */
    public void closeClaw() {
        Arm.adjustClaw(false);
    }

    /**
     * 
     */
    public double getSpeed() {
        // TODO implement here
        return 0; 
        // empty method for now
    }

    /**
     * 
     */
    public void double getHeading() {
        // TODO implement here
        return Drivetrain.getHeading();
    }
    public Drivetrain_ getDrivetrain() {
        return this.Drivetrain;
    }

}