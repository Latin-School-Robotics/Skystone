import java.util.*;

/**
 * To be written still
 * This is skeleton code based on UML from 9/11/2019
 */
public class Arm_ {

    /**
     * Default constructor
     */
    private boolean manual;
    private double armLength1;
    private double armLength2;
    private double angle1;
    private double angle2;
    private DcMotor motor1;
    private DcMotor motor2;
    private Servo claw;
    private Servo wrist;
    private Servo twist;

    /**
     * @param double x 
     * @param double y 
     * @param double speed
     */
    public Arm_(HardwareMap hm, double armlen1, double armlen2, double lowestY, boolean manual_arm) {
        manual = manual_arm; //if manual: controller adjusts angles of robot

        shoulder = hardwareMap.dcMotor.get("shoulder_motor");
        elbow = hardwareMap.dcMotor.get("elbow_motor");
        wrist = hardwareMap.servo.get("wrist_servo");
        twist = hardwareMap.servo.get("twist_servo");
        clawL = hardwareMap.servo.get("clawL");
        clawR = hardwareMap.servo.get("clawR");
        armLength1 = armlen;  //= 15
        armLength2 = armlen2; //= 15
        final float maxD = 30;
        


        //motor settings
        elbow.setDirection(DcMotorSimple.Direction.REVERSE);

        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);  

        //setting initial angles
        angle1 = 90*4; //shoulder
        angle2 = 180*4; //elbow

        final float sense = 10;

        Vec hand = new Vec(29,1);
        Vec o = new Vec(0,0);
        shoulder.setTargetPosition(angle1);
        shoulder.setTargetPosition(angle2);


    }

    /**
     * @param double x 
     * @param double y 
     * @param double speed
     */
    public void setPositionCoords(double x, double y, double speed) {
        // shoulder angle1
        // elbow    angle2
        if (manual){
            throw new Exception("in manual mode, not regular");
            telemetry.addData("in manual mode, not regular");
            return;
        }
        Vec newH = hand.sum(new Vec(x/sense, y/sense));
        if (o.distance(newH) < maxD){
            hand = newH;
        }

        float d = o.distance(hand);

        double angle1 = Math.acos( (a*a + d*d - b*b) / (2*a*d)  ) + Math.atan(newH.y/newH.x);
        double angle2 = Math.acos( (a*a - d*d + b*b) / (2*a*d)  );
        angle1 *= 180/Math.PI;
        angle2 *= 180/Math.PI;

        setPositionAngles( (36 * (int)(angle1-90)), (24 * (int) (180 - angle2)) );

        double print_angle1 = 90 + shoulder.getCurrentPosition() / 4 / 9;
        double print_angle2 = 180 - elbow.getCurrentPosition() / 4 / 6;

        double target_angle1 = 90 + shoulder.getTargetPosition() / 4 / 9;
        double target_angle2 = 180 - elbow.getTargetPosition() / 4 / 6;
    }

    /**
     * @param double angle1 
     * @param double angle 2
     */
    public void setPositionAngles(double angle1, double angle2) {
        // angle1 = shoulder
        // angle2 = elbow
        float d = o.distance(hand);
        
        if (manual){
            //run without encoder
            soulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            shoulder.setPower(gamepad2.right_stick_x);
            elbow.setPower(gamepad2.right_stick_y);
        }
        else {
            shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            shoulder.setPower(0.4);
            elbow.setPower(0.4);
        }


        shoulder.setTargetPosition(angle1);
        elbow.setTargetPosition(angle2);
    }

    /**
     * @param boolean open
     */
    public void adjustClaw(boolean open) {
        // TODO implement here
        if (open){
            clawR.setPosition(0.5);
            clawL.setPosition(0.5);
        }
        else {
            clawR.setPosition(0);
            clawL.setPosition(1);
        }
    }

    // public void adjust_wrist(double angle){
    //     do maybe
    // }



}