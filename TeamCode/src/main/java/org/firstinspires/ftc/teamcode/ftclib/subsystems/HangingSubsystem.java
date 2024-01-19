package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HangingSubsystem extends SubsystemBase {
    private static final double RIGHT_HOOK_OFFSET = 0.03;

    public enum HooksState {
        HOOKS_UP(0.3), HOOKS_DOWN(0.0);

        final double pos;
        HooksState(double pos) {
            this.pos = pos;
        }
    }

    private final DcMotorEx leftWinch, rightWinch;
    private final ServoEx leftHook, rightHook;

    private HooksState hooksState;

    public HangingSubsystem(DcMotorEx leftWinch, DcMotorEx rightWinch, ServoEx leftHook, ServoEx rightHook) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftHook = leftHook;
        this.rightHook = rightHook;

//        this.leftWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        this.rightWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        this.leftWinch.setTargetPosition(0);
//        this.rightWinch.setTargetPosition(0);
//        this.leftWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        this.rightWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        this.leftWinch.setDirection(DcMotorSimple.Direction.REVERSE);

        this.leftWinch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightWinch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftWinch.setPower(0);
        this.rightWinch.setPower(0);

        this.leftHook.setInverted(true);
    }

    public HangingSubsystem(HardwareMap hMap, String leftWinch, String rightWinch, String leftHook, String rightHook) {
        this(
                hMap.get(DcMotorEx.class, leftWinch),
                hMap.get(DcMotorEx.class, rightWinch),
                new SimpleServo(hMap,leftHook, 0,0),
                new SimpleServo(hMap,rightHook, 0,0)
        );
    }

    public void winchL(double amount) {
//        leftWinch.setTargetPosition(leftWinch.getCurrentPosition() + (int) Math.round(amount * 100));
        leftWinch.setPower(amount);
    }

    public void winchR(double amount) {
//        rightWinch.setTargetPosition(rightWinch.getCurrentPosition() + (int) Math.round(amount * 100));
        rightWinch.setPower(amount);
    }

    public void stopL() {
//        leftWinch.setTargetPosition(leftWinch.getCurrentPosition());
        leftWinch.setPower(0);
    }

    public void stopR() {
//        rightWinch.setTargetPosition(rightWinch.getCurrentPosition());
        rightWinch.setPower(0);
    }

    public void hooksUp() {
        setHooksState(HooksState.HOOKS_UP);
    }

    public void hooksDown() {
        setHooksState(HooksState.HOOKS_DOWN);
    }

    public void setHooksState(HooksState hooksState) {
        this.hooksState = hooksState;
        leftHook.setPosition(hooksState.pos);
        rightHook.setPosition(hooksState.pos + RIGHT_HOOK_OFFSET);
    }

    public HooksState getHooksState() {
        return hooksState;
    }

    public void setRawLeftHook(double position) {
        leftHook.setPosition(position);
    }

    public void setRawRightHook(double position) {
        rightHook.setPosition(position);
    }

    public double getRawLeftHook() {
        return leftHook.getPosition();
    }

    public double getRawRightHook() {
        return rightHook.getPosition();
    }

}
