package org.firstinspires.ftc.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Drivetrain_;
@Autonomous(name = "UnderBridgeAuto", group = "Autos")

public class UnderBridgeAuto extends LinearOpMode{

	@Override
	public void runOpMode() {
	    final double calibration = 35;
        SkystoneNavigation sn = new SkystoneNavigation();
        // String scannedImage = sn.getImageName();
        String scannedImage = "blah";
        Drivetrain_ r = new Drivetrain_(this);
        r.calibrate();
        // wait for init
        waitForStart();
        r.lowerRotator();
        // r.driveAtHeading(0, 10 * calibration, 0 * calibration, 0.4);
        
	}
}