import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
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
import org.firstinspires.ftc.teamcode.New_Drivetrain_;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "RedFoundationAuto", group = "Autos")
public class RedFoundationAuto extends LinearOpMode {
    // ASSUME THAT TEAMMATE IS A DUD. IF THEY MOVE FORWARDS, USE THE GoodTeammateAuto.
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
    // !: THIS AUTONOMOUS DRAGS THE FOUNDATION IN AND THEN MOVES LATERALLY UNDER THE BRIDGE
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
        final double angleCalibration = 0.95;
        SkystoneNavigation sn = new SkystoneNavigation();
        // String scannedImage = sn.getImageName();
        String scannedImage = "blah";
        New_Drivetrain_ r = new New_Drivetrain_(this);
        // raise the grabs up
        r.moveGrabs(false);
        // lower down the slide
        // calibrates the imu
        r.calibrate();
        // wait for init
        waitForStart();
        // Start the robot turned 15º facing image 1.
        // execute image 1 commands
        // place the skystone on the foundation, preferably on top of the other skystone
        r.driveAtHeading(0, 0, -20 *calibration, 0.4);
        r.driveAtHeading(0, 190*calibration, 0, 1);
        r.driveAtHeading(-90 * angleCalibration, -58 * calibration, 0, 0.7);
        r.moveGrabs(true);
        sleep(1000);
        // lower the hook things and grab onto the foundation
        r.driveAtHeading(-90, 100 * calibration, 0, 0.4);
        // ends
        r.moveGrabs(false);
        sleep(1000);
        r.driveAtHeading(-90, 0, 155 * calibration, 0.4);
}
}