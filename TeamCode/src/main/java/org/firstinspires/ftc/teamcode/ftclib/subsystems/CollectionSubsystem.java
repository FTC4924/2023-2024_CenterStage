package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CollectionSubsystem extends SubsystemBase {
    public enum CollectionState {
        INTAKE(0.0), OUTPUT(-0.75), IDLE(0.85);

        final double power;

        CollectionState(double power) {
            this.power = power;
        }
    }

    private final MotorEx collectionMotor;

    private CollectionState state;

    public CollectionSubsystem(MotorEx collectionMotor) {
        this.collectionMotor = collectionMotor;
        this.collectionMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        this.collectionMotor.setInverted(true);
    }

    public CollectionSubsystem(HardwareMap hMap, String collectionMotor) {
        this(new MotorEx(hMap, collectionMotor));
    }

    public void intake() {
        setState(CollectionState.INTAKE);
    }

    public void output() {
        setState(CollectionState.OUTPUT);
    }

    public void idle() {
        setState(CollectionState.IDLE);
    }
    public CollectionState getState() {
        return state;
    }

    public void setState(CollectionState state) {
        if (this.state == state) this.state = CollectionState.IDLE;
        else this.state = state;

        collectionMotor.set(state.power);
    }
}
