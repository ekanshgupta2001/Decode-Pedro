package org.firstinspires.ftc.teamcode.pedroPathing.decode_auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class pickup_auto {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    public static int targetPos = 0;
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime sequenceTimer = new ElapsedTime();
    private ElapsedTime sampleTimer = new ElapsedTime();
    private DcMotor SlideMotor;
    private DcMotor ArmMotor;
    private CRServo intakeMotorL = null;
    private CRServo intakeMotorR = null;
    private CRServo rotateL = null;
    private CRServo rotateR = null;
    private Limelight3A limelight;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        SlideMotor = hardwareMap.get(DcMotor.class, "Slide Motor");
        SlideMotor.setDirection(DcMotor.Direction.FORWARD);
        SlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ArmMotor = hardwareMap.get(DcMotor.class, "Arm Motor");
        ArmMotor.setDirection(DcMotor.Direction.FORWARD);
        ArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotateL = hardwareMap.get(CRServo.class, "rotateIntakeLeft");
        rotateR = hardwareMap.get(CRServo.class, "rotateIntakeRight");
        rotateL.setDirection(DcMotorSimple.Direction.FORWARD);
        rotateR.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotorL = hardwareMap.get(CRServo.class, "intakeMotorLeft");
        intakeMotorR = hardwareMap.get(CRServo.class, "intakeMotorRight");
        intakeMotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotorR.setDirection(DcMotorSimple.Direction.FORWARD);


        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(75);
        limelight.pipelineSwitch(0);
        limelight.start();
    }
}
