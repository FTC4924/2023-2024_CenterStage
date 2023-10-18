package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HangingSubsystem extends SubsystemBase {
    private final MotorEx leftWinch, rightWinch;
    private final ServoEx leftHook, rightHook;


    public HangingSubsystem(HardwareMap hardwareMap, MotorEx leftWinch, MotorEx rightWinch, ServoEx leftHook, ServoEx rightHook) {
        this.leftWinch = leftWinch;
        this.rightWinch = rightWinch;
        this.leftHook = leftHook;
        this.rightHook = rightHook;
    }

    public HangingSubsystem(HardwareMap hMap, String leftWinch, String rightWinch, String leftHook, String rightHook) {
        this(
                hMap,
                new MotorEx(hMap, leftWinch),
                new MotorEx(hMap, rightWinch),
                new SimpleServo(hMap,leftHook, 0,180),
                new SimpleServo(hMap,rightHook, 0,180)

        );
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
