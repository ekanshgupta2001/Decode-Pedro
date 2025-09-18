package org.firstinspires.ftc.teamcode.pedroPathing.decode_teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class cascadeTeleop extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private DcMotor slideMotor;
    private DcMotor armMotor;
    private DcMotor lfMotor = null;
    private DcMotor lrMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor rrMotor = null;
    private DcMotor scoreMotor = null;
    private DcMotor intakeMotorL = null;
    private DcMotor intakeMotorR = null;
    private Servo rotateRampL;
    private Servo rotateRampR;
    public scoreFar farScore;
    public mediumScore scoreMedium;
    public closeScore scoreClose;

    @Override
    public void init() {

        follower = Constants.createFollower(hardwareMap);

        slideMotor = hardwareMap.get(DcMotor.class, "Slide Motor");
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor = hardwareMap.get(DcMotor.class, "Arm Motor");
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotorL = hardwareMap.get(DcMotor.class, "intakeMotorLeft");
        intakeMotorR = hardwareMap.get(DcMotor.class, "intakeMotorRight");
        intakeMotorL.setDirection(DcMotor.Direction.FORWARD);
        intakeMotorR.setDirection(DcMotor.Direction.FORWARD);

        scoreMotor = hardwareMap.get(DcMotor.class, "ScoreMotor");
        scoreMotor.setDirection(DcMotor.Direction.FORWARD);

        rotateRampL = hardwareMap.get(Servo.class, "RotateRampLeft");
        rotateRampL = hardwareMap.get(Servo.class, "RotateRampRight");


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
    }

    public void movement(){
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double lf = drive + strafe + turn;
        double lr = drive - strafe + turn;
        double rf = drive - strafe - turn;
        double rr = drive + strafe - turn;

        double max = Math.max(Math.abs(lf), Math.max(Math.abs(lr), Math.max(Math.abs(rf), Math.abs(rr))));
        if (max > 1) {
            lf /= max;
            lr /= max;
            rf /= max;
            rr /= max;
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