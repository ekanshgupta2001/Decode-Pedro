package org.firstinspires.ftc.teamcode.pedroPathing.decode_teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Cascade Teleop", group = "Robot")
public class cascadeTeleop extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private DcMotor lfMotor = null;
    private DcMotor lrMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor rrMotor = null;
    private DcMotor scoreMotor = null;
    private DcMotor intakeMotorL = null;
    private DcMotor intakeMotorR = null;
    private DcMotorEx parallelEncoder;
    private DcMotorEx perpendicularEncoder;
    private Servo rotateRampL;
    private Servo rotateRampR;
    private double currentPower = 0;
    private double adjustSpeed = 1.0;
    public scoreFar farScore;
    public mediumScore scoreMedium;
    public closeScore scoreClose;

    @Override
    public void init() {

        follower = Constants.createFollower(hardwareMap);

        rfMotor = hardwareMap.dcMotor.get("frontRightMotor");
        lfMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        rrMotor = hardwareMap.dcMotor.get("backRightMotor");
        lrMotor = hardwareMap.dcMotor.get("backLeftMotor");

        intakeMotorL = hardwareMap.get(DcMotor.class, "intakeMotorLeft");
        intakeMotorR = hardwareMap.get(DcMotor.class, "intakeMotorRight");
        intakeMotorL.setDirection(DcMotor.Direction.FORWARD);
        intakeMotorR.setDirection(DcMotor.Direction.FORWARD);

        scoreMotor = hardwareMap.get(DcMotor.class, "ScoreMotor");
        scoreMotor.setDirection(DcMotor.Direction.FORWARD);

        rotateRampL = hardwareMap.get(Servo.class, "RotateRampLeft");
        rotateRampR = hardwareMap.get(Servo.class, "RotateRampRight");

        parallelEncoder = hardwareMap.get(DcMotorEx.class, "parallelEncoder");
        perpendicularEncoder = hardwareMap.get(DcMotorEx.class, "perpendicularEncoder");


        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        telemetry.addLine("Auto complete");
        telemetry.update();

    }

    @Override
    public void loop() {
        movement();
        intake();
        farTriangle();
        mediumTriangle();
        closeTriangle();

        telemetry.addData("Front Left Power", lfMotor.getPower());
        telemetry.addData("Front Right Power", rfMotor.getPower());
        telemetry.addData("Back Left Power", lrMotor.getPower());
        telemetry.addData("Back Right Power", rrMotor.getPower());
        telemetry.addData("Intake Left Power", intakeMotorL.getPower());
        telemetry.addData("Intake Right Power", intakeMotorR.getPower());
        telemetry.update();
    }

    public void movement(){
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double lf = drive + strafe + turn;
        double lr = drive - strafe + turn;
        double rf = drive - strafe - turn;
        double rr = drive + strafe - turn;

//        double max = Math.max(Math.abs(lf), Math.max(Math.abs(lr), Math.max(Math.abs(rf), Math.abs(rr))));
//        if (max > 1) {
//            lf /= max;
//            lr /= max;
//            rf /= max;
//            rr /= max;
//        }

        lf *= adjustSpeed;
        lr *= adjustSpeed;
        rf *= adjustSpeed;
        rr *= adjustSpeed;

        if (gamepad1.dpad_up){
            adjustSpeed = 1.0;
        }


        if (gamepad1.right_bumper) {
            adjustSpeed += 0.2;
        }

        if (gamepad1.left_bumper) {
            adjustSpeed -= 0.2;
        }

        lfMotor.setPower(lf);
        lrMotor.setPower(lr);
        rfMotor.setPower(rf);
        rrMotor.setPower(rr);
        follower.update();
    }

    public void intake(){
        if (gamepad2.left_bumper) {
                intakeMotorR.setPower(1.0);
            intakeMotorL.setPower(-1.0);
        } else if (gamepad2.right_bumper) {
            intakeMotorL.setPower(1.0);
            intakeMotorR.setPower(-1.0);
        } else {
            intakeMotorR.setPower(0);
            intakeMotorL.setPower(0);
        }
    }

    public void farTriangle(){
        if (gamepad2.y){
            farScore.start();
        }

    }

    public void mediumTriangle(){
        if(gamepad2.x){
            scoreMedium.start();
        }
    }
    public void closeTriangle(){
        if (gamepad2.a){
            scoreClose.start();
        }

    }
}