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

@Autonomous(name = "SensorTester", group = "Autos")
public class SensorTester extends LinearOpMode {
    New_Drivetrain_ r;



    @Override
    public void runOpMode() {
        
        r = new New_Drivetrain_(this);
        
        // lower down the slide
        // calibrates the imu
        r.calibrate();

        //Init loop until started
        
        waitForStart();

        while (this.opModeIsActive()){
            telemetry.addData("Sees block", r.isSkyStone());
            telemetry.update();
        }
    }
}