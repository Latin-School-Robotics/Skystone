import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "AutoMovement", group = "Auto")
public class AutoMovement extends LinearOpMode {

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

    @Override
    public void runOpMode() {
        HardwareMap hm = new HardwareMap();
        Robot r = new Robot(hm);
        
        if (scannedImage.equals("Front Perimeter 1")) {
            // execute image 1 commands


        } else if (scannedImage.equals("Front Perimeter 2")) {
            // execute image 2 commands

        } else if (scannedImage.equals("Red Perimeter 2")) {
            // execute image 3 commands

        } else if (scannedImage.equals("Blue Perimeter 1") {
            // execute image 4 commands

        } else if (scannedImage.equals("Red Perimeter 1")) {
            // execute image 5 commands

        } else if (scannedImage.equals("Blue Perimeter 2")) {
            // execute image 6 commands

        } else if (scannedImage.equals("Rear Perimeter 2")) {
            // execute image 7 commands

        } else if (scannedImage.equals("Rear Perimeter 1")){
            // execute image 8 commands
            // one square is 57.785cm
            r.turnRight(90);
            r.drive(38.5233333333);
            // in centimeters
            r.turnRight(90);
            r.drive(86.6775);
            r.turnRight(270);
            // then while moving laterally, scan the stones until we scan a skystone. Once we do, pick up the skystone.
            r.turnRight(270);
            r.drive(213.8045);
            r.turnRight(90);
            // lower down the hook things and latch on to the foundation
            r.drive(-28.8925);
            // then raise the hook things
            r.driveLateral(58.42);
            r.drive(23.495);
            r.turnRight(270);
            // lower down the hook things
            r.drive(-115.57);s

        }
    }
}