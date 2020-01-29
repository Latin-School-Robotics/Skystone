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

@Autonomous(name = "AutoThing", group = "Autos")
public class AutoThing extends LinearOpMode {
    New_Drivetrain_ r;

    //Team color
    boolean blueTeam = true;
    //Which 'lane' of the bridge to use: true = closer to center of field, false = closer to wall
    boolean centralLane = true;


    @Override
    public void runOpMode() {
        
        r = new New_Drivetrain_(this);
        
        // lower down the slide
        // calibrates the imu
        r.calibrate();

        //Init loop until started
        while(!isStarted()){

            if(gamepad1.dpad_up){
                centralLane = true;
            }
            if(gamepad1.dpad_down){
                centralLane = false;
            }
            if(gamepad1.b){
                blueTeam = false;
            }
            if(gamepad1.x){
                blueTeam = true;
            }

            telemetry.addData("Team:", (blueTeam?"Blue":"Red") );
            telemetry.addData("Lane:", (centralLane?"central side":"wall side") );

            telemetry.update();


            idle(); //I think we're supposed to keep this method here but I actually have no idea what it does
        }
        //waitForStart();

        // raise the grabs up (not in earlier so we dont move during init)
        r.moveGrabs(false);
        r.claw(false);

        //Start lined up with block closet to bridge, facing towards block
        //Drive forward to block
        r.ropePos(-600,1);
        driveTeam(0, 70 , 0, 0.6);
        
        //Check 6 blocks (grab last one by default) 
        int block = 1;
        while(!r.isSkyStone() && block < 3){
            //Move left to next block
            driveTeam(0,0,26.5,0.5);
            block++;
            telemetry.addData("block", block);
            telemetry.update();
            
        }
        
        //line up centered on block
        ///driveTeam(0,0,11,0.5); //dont mirror this because it moves this way regardless of team, also it needs to move back this far later and also not mirror that to balance out this change
        driveTeam(0,4,0,0.3);
        //Lower and grab
        r.ropePos(-80,0.8);
        sleep(500);
        r.claw(true);
        sleep(300);
        r.ropePos(-200, 0.5);
     
        

        //move back
        if(centralLane){
            driveTeam(0,-17,0,0.7);
        }
        else {
            driveTeam(0,-87,0,0.7);
        }

    
        //Move left to account for blocks checked
        if (block != 1)
            driveTeam(0,0, (block - 1) * -28.5, 0.8);

        //Turn and drive under bridge
        r.ropePos(-50, 0.5);
        driveTeam(90, 188, 0,0.9);
    
        //driveTeam(0,0, -195 - 20 * (block - 1), 0.8);
        //Lift rope and drive towards foundation
        r.ropePos(-1200, 0.5);
        if(centralLane){
            driveTeam(3, 16.2, 0, 0.7);
        }
        else {
            driveTeam(3, 90, 0, 0.7);
        }
        

        //drop block
        r.claw(false);
        //sleep(500);
        
        //Move left
        driveTeam(-6, 10, -35, 0.5);
       // sleep(500);
        
        //Grab tray with claw
        r.ropePos(15, 0.6);
        sleep(1100);
        
        
        //back up with tray to corner
        driveTeam(0, -93, 0, 0.8);
        
        
        //release tray
        r.ropePos(-1000, 0.5);
        sleep(1000);
        
        //Move right and then lower elevator
        driveTeam(6, 0, 100, 0.9);
        r.ropePos(0, 0.9);
        r.claw(true);
        if(centralLane){
        //Drive diagonally back to blocks
        driveTeam(0, 55, 0, 0.9);
        
          driveTeam(-90, 50, 0, 0.9);
          r.claw(false);
        }
        else {
        
        //Drive diagonally back to blocks
        driveTeam(0, 0, 55, 0.9);
        }
       
    }

    //drives based on team color (enter values for blue team)
    public void driveTeam(double ang, double prim, double lat, double power){
        if(blueTeam){
            r.driveAtHeading(ang,prim,lat,power);
        }
        else{
            r.driveAtHeading(-ang,prim,-lat,power);
        }
    }
}