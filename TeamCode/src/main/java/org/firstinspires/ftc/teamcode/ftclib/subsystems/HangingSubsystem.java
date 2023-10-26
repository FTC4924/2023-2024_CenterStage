package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HangingSubsystem extends SubsystemBase {
    private final MotorEx leftWinch, rightWinch;
    private final ServoEx leftHook, rightHook;
    private final Telemetry telemetry;


    public HangingSubsystem(MotorEx leftWinch, MotorEx rightWinch, ServoEx leftHook, ServoEx rightHook, Telemetry telemetry) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftHook = leftHook;
        this.rightHook = rightHook;

        this.leftWinch.setRunMode(Motor.RunMode.PositionControl);
        this.rightWinch.setRunMode(Motor.RunMode.PositionControl);
        this.leftWinch.set(1.0);
        this.rightWinch.set(1.0);

        this.leftHook.setInverted(true);

        this.telemetry = telemetry;
    }

    public HangingSubsystem(HardwareMap hMap, String leftWinch, String rightWinch, String leftHook, String rightHook, Telemetry telemetry) {
        this(
                new MotorEx(hMap, leftWinch),
                new MotorEx(hMap, rightWinch),
                new SimpleServo(hMap,leftHook, 0,180),
                new SimpleServo(hMap,rightHook, 0,180),
                telemetry
        );
    }

    @Override
    public void periodic() {
        telemetry.addData("Left winch pos", leftWinch.getCurrentPosition());
        telemetry.addData("Right winch pos", rightWinch.getCurrentPosition());
    }

    public void setPower(double power) {
        leftWinch.setTargetPosition(leftWinch.getCurrentPosition() + (int) Math.round(power * 100));
        rightWinch.setTargetPosition(rightWinch.getCurrentPosition() + (int) Math.round(power * 100));
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
