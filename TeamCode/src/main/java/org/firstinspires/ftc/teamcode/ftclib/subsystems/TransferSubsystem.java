package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TransferSubsystem extends SubsystemBase {
    private final ServoEx transferServo;


    public TransferSubsystem(ServoEx transferServo) {
        this.transferServo = transferServo;
    }

    public TransferSubsystem(HardwareMap hMap, String transferServo) {
        this(new SimpleServo(hMap, transferServo, 0, 0));
    }

    public void collect() {
        transferServo.setPosition(0.5);
    }
    public void deposit() {
        transferServo.setPosition(0.5);
    }
}
