package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HangingSubsystem extends SubsystemBase {
    private final DcMotorEx leftWinch, rightWinch;
    private final ServoEx leftHook, rightHook;


    public HangingSubsystem(DcMotorEx leftWinch, DcMotorEx rightWinch, ServoEx leftHook, ServoEx rightHook) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftHook = leftHook;
        this.rightHook = rightHook;

        this.leftWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftWinch.setTargetPosition(0);
        this.rightWinch.setTargetPosition(0);
        this.leftWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightWinch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.leftWinch.setPower(1.0);
        this.rightWinch.setPower(1.0);

        this.leftHook.setInverted(true);
    }

    public HangingSubsystem(HardwareMap hMap, String leftWinch, String rightWinch, String leftHook, String rightHook) {
        this(
                hMap.get(DcMotorEx.class, leftWinch),
                hMap.get(DcMotorEx.class, rightWinch),
                new SimpleServo(hMap,leftHook, 0,180),
                new SimpleServo(hMap,rightHook, 0,180)
        );
    }

    public void winch(double amount) {
        leftWinch.setTargetPosition(leftWinch.getCurrentPosition() + (int) Math.round(amount * 100));
        rightWinch.setTargetPosition(rightWinch.getCurrentPosition() + (int) Math.round(amount * 100));
    }

    public void stopWinch() {
        leftWinch.setTargetPosition(leftWinch.getCurrentPosition());
        rightWinch.setTargetPosition(rightWinch.getCurrentPosition());
    }

    public void hooksUp() {
        leftHook.setPosition(0.5);  // adjust as needed
        rightHook.setPosition(0.5);
    }

    public void hooksDown() {
        leftHook.setPosition(0.06);  // adjust as needed
        rightHook.setPosition(0.06);
    }

    public void hooksPast() {
        leftHook.setPosition(0.7);
        rightHook.setPosition(0.7);
    }

    public void setLeftHook(double position) {
        leftHook.setPosition(position);
    }

    public void setRightHook(double position) {
        rightHook.setPosition(position);
    }

    public double getLeftHook() {
        return leftHook.getPosition();
    }

    public double getRightHook() {
        return rightHook.getPosition();
    }

    /*
        Create subsystems: Hanging, collection/transfer
        Hanging: leftWinch, rightWinch, leftHook, rightHook
        Collection/Transfer: collectionMotor, transferServo
        hooksUp and hooksDown
        raise and lower
        collectIdleDeposit and spit
         */

}
