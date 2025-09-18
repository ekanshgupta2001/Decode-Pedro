package org.firstinspires.ftc.teamcode.pedroPathing.decode_teleop;


import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class closeScore extends OpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private DcMotor slideMotor;
    private DcMotor armMotor;
    private DcMotor lfMotor = null;
    private DcMotor lrMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor rrMotor = null;
    private final ElapsedTime runtime = new ElapsedTime();
    private final ElapsedTime sequenceTimer = new ElapsedTime();
    private final ElapsedTime sampleTimer = new ElapsedTime();
    private Servo rotateRampL;
    private Servo rotateRampR;
    private DcMotor scoreMotor = null;
    private DcMotor intakeMotorL;
    private DcMotor intakeMotorR;

    private Limelight3A limelight;

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

        scoreMotor = hardwareMap.get(DcMotor.class, "Score Motor");
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
        rotateRampL.setPosition(0.98);
        rotateRampR.setPosition(0.98);
        scoreMotor.setPower(1.0);

        scoreMotor.setPower(0);
        rotateRampL.setPosition(1.0);
        rotateRampR.setPosition(1.0);

    }

}