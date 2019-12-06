import static java.lang.Math.*;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name = "DumbDrive", group = "Autos")
public class DumbDrive extends LinearOpMode {
    DcMotor front_left_motor, front_right_motor, rear_left_motor, rear_right_motor;

    @Override
    public void runOpMode() {
        DcMotor[] motorArray = new DcMotor[4];
        front_left_motor = hardwareMap.dcMotor.get("front left drive");
        front_right_motor = hardwareMap.dcMotor.get("front right drive");
        rear_left_motor = hardwareMap.dcMotor.get("back left drive");
        rear_right_motor = hardwareMap.dcMotor.get("back right drive");

        front_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        front_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rear_left_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rear_right_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorArray[0] = front_left_motor;
        motorArray[1] = front_right_motor;
        motorArray[2] = rear_left_motor;
        motorArray[3] = rear_right_motor;
        motorArray[0].setDirection(DcMotorSimple.Direction.REVERSE);
        motorArray[2].setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        for (DcMotor motor : motorArray) {
            motor.setPower(.4);
        }
        sleep(500);
        for (DcMotor motor : motorArray) {
            motor.setPower(0);
        }
    }
}