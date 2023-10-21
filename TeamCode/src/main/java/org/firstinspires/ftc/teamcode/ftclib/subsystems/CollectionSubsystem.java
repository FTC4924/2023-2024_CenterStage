package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CollectionSubsystem extends SubsystemBase {
    private final MotorEx collectionMotor;

    public CollectionSubsystem(MotorEx collectionMotor) {
        this.collectionMotor = collectionMotor;
        this.collectionMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }

    public CollectionSubsystem(HardwareMap hMap, String collectionMotor) {
        this(new MotorEx(hMap, collectionMotor));
    }

    public void intake() {
        collectionMotor.set(1.0);
    }

    public void output() {
        collectionMotor.set(-1.0);
    }

    public void idle() {
        collectionMotor.stopMotor();
    }

    public double getPower() {
        return collectionMotor.get();
    }
}
