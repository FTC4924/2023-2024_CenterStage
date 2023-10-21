package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HangingSubsystem extends SubsystemBase {
    private final MotorEx leftWinch, rightWinch;
    private final ServoEx leftHook, rightHook;


    public HangingSubsystem(MotorEx leftWinch, MotorEx rightWinch, ServoEx leftHook, ServoEx rightHook) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftHook = leftHook;
        this.rightHook = rightHook;

        this.leftWinch.setRunMode(Motor.RunMode.PositionControl);
        this.rightWinch.setRunMode(Motor.RunMode.PositionControl);
    }

    public HangingSubsystem(HardwareMap hMap, String leftWinch, String rightWinch, String leftHook, String rightHook) {
        this(
                new MotorEx(hMap, leftWinch),
                new MotorEx(hMap, rightWinch),
                new SimpleServo(hMap,leftHook, 0,180),
                new SimpleServo(hMap,rightHook, 0,180)
        );
    }

    public void setPower(double power) {
        leftWinch.set(power);
        rightWinch.set(power);
    }

    public void hooksUp() {
        leftHook.setPosition(1.0);  // adjust as needed
        rightHook.setPosition(1.0);
    }

    public void hooksDown() {
        leftHook.setPosition(0.0);  // adjust as needed
        rightHook.setPosition(0.0);
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
