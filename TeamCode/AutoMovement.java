import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;

import org.firstinspires.ftc.Autos.SkyStoneDetection;
import org.firstinspires.ftc.Autos.SkystoneNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Drivetrain_;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "AutoMovement", group = "Autos")
public class AutoMovement extends LinearOpMode {
    // TODO: Create the methods that are being called in this code
    // !: Implement this image scanning correctly
    /*
     * I do not know if we want to create an object for this class. This is a rough
     * outline using the given images and not a physical field. This will determine
     * what autonomous path the robot takes depending on where it starts. The code
     * that stores the names of the pictures in SkystoneNavigation is
     * "trackable.getName()". Parse that information, then store the image number as
     * an int.
     * 
     */
    // String scannedImage;
    // !: MEASUREMENTS ARE MADE IN DEGREES AND CENTIMETERS
    // drive is driving forward, and when passed a negative parameter, it means
    // drive backwards.
    // turn right turns clockwise.
    // Try to change turnright into setHeading
    // I'm pretty sure setHeading does not just allow you to turn xº, you have to
    // use the angle you want with respect to robot's current heading
    @Override
    public void runOpMode() {
        // !: Use the turnAt(heading, primary, lateral, power) method for
        //! turning.
        /* 
        *Use the driveAtheading(heading, primary, lateral, power) method for driving.
        *heading = 0, primary = forward/backwardDistance * calibration, lateral = lateralDistance * calibration, power = power.
        */
        /*
         * !: in the turnAtHeading(heading, primary, lateral, power) method,
         * primary*calibration is the amount of 1/2 degrees that you want to turn.
         */
        //! Clockwise is negative, counterclockwise is positive. Measures relative to the
        //! current position of the robot.
        final double calibration = 35;
        final double angleCalibration = 1.0/2.0;
        SkystoneNavigation sn = new SkystoneNavigation();
        // String scannedImage = sn.getImageName();
        String scannedImage = "blah";
        Drivetrain_ r = new Drivetrain_(this);
        r.calibrate();
        // wait for init
        waitForStart();
        if (scannedImage.equals("Front Perimeter 1")) {
        // Start the robot turned 15º facing image 1.
        // execute image 1 commands
        r.moveGrabs(1);
       r.lowerRotator();
       r.moveGrabs(0.5);
        // place the skystone on the foundation, preferably on top of the other skystone
        r.driveAtHeading(0, 200*calibration, 0, 0.4);
        r.turnAtHeading(0, -90 * calibration * angleCalibration, 0, 0.4);
        r.driveAtHeading(0, -60 * calibration, 0, 0.4);
        r.moveGrabs(1);
        // lower the hook things and grab onto the foundation
        r.driveAtHeading(0, 100 * calibration, 0, 0.4);
        // raise the hook things
        r.moveGrabs(0.5);
        r.driveAtHeading(0, 0, 130 * calibration, 0.4);

        }
        else if (scannedImage.equals("Front Perimeter 2")) {
        // Start the robot turned 15º facing image 2.
        // execute image 2 commands
        
         r.rotate(15); 
         r.drive(60); 
         r.rotate(-90); 
         r.drive(57.785); 
         // then while moving laterally, scan the stones until we scan a skystone. Once 
         // we do,pick up the skystone. 
         r.rotate(180); 
         r.drive(98.2345); 
         r.rotate(90);
         r.drive(250.7869); 
         r.rotate(90); 
         r.drive(98.2345); 
         // place the skystone on the foundation, preferably on top of the other skystone 
         // lower the hook things and grab onto the foundation 
         r.drive(-75.1205);
          // raise the hook things
         r.driveLateralRight(100); 
         } 
         else if (scannedImage.equals("Red Perimeter 2")) { 
        // execute image 3 commands
        } else if (scannedImage.equals("Blue Perimeter 1")) { 
            // execute image 4mcommands
         } else if (scannedImage.equals("Red Perimeter 1")) { 
        // execute image 5 commands
         } else if (scannedImage.equals("Blue Perimeter 2")) { 
             // execute image 6 commands
         } else if (scannedImage.equals("Rear Perimeter 2")) { 
             // execute image 7 commands 
            // black path red 
         r.turnAtHeading(0, calibration * 90 * angleCalibration, 0, 0.4); 
         r.driveAtHeading(0, 41.275 * calibration, 0, 0.4); 
         r.turnAtHeading(0, 90 * calibration * angleCalibration, 0, 0.4);
         r.driveAtHeading(0, 75 * calibration, 0, 0.4); 
         r.turnAtHeading(0, 90 * calibration * angleCalibration, 0, 0.4); 
         // then while moving laterally, scan the stones until we scan a skystone. 
         // Once we do, pick up the skystone. 
         r.turnAtHeading(0, -90 * calibration * angleCalibration, 0, 0.4);
         r.driveAtHeading(0, calibration * 213.8045, 0, 0.4); 
         r.turnAtHeading(0, 90 * calibration * angleCalibration 0, 0.4); 
         // You are at and facing the foundation 
         //place the skystone onto the foundation 
         r.turnAtHeading(0, 90 * calibration * angleCalibration, 0, 0.4); 
         r.driveAtHeading(0, 110 * calibration, 0, 0.4); 
             
         } 
         else if (scannedImage.equals("Rear Perimeter 1")) { 
         // black path blue 
         // execute image 8 commands
        // one square is 57.785cm 
         r.rotate(90);
         r.drive(38.5233333333); 
         // in centimeters 
         r.rotate(90); 
         r.drive(86.6775);
         r.rotate(-90); 
         // then while moving laterally, scan the stones until we scan a skystone. Once 
         // we do, pick up the skystone. 
         r.rotate(-90);
         r.drive(213.8045); 
         r.rotate(-90); 
         // place the skystone onto the foundation
         r.rotate(90); 
         r.drive(110); }
    }
}