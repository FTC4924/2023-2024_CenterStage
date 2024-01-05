package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TransferSubsystem extends SubsystemBase {
    public enum TransferState {
        COLLECT(0.65, 1.00, 0.00),
        DEPOSIT(0.00, 1.00, 1.00),
        REVERSE(-0.55, -1.00, -1.00),
        IDLE(0.00, 0, 0);

        double collectionPower;
        final double transferFrontPower;
        final double transferBackPower;

        TransferState(double collectionPower, double transferFrontPower, double transferBackPower) {
            this.collectionPower = collectionPower;
            this.transferFrontPower = transferFrontPower;
            this.transferBackPower = transferBackPower;
        }
    }

    private final MotorEx collectionMotor;
    private final CRServo transferFront;
    private final CRServo transferBack;

    private TransferState state;

    public TransferSubsystem(MotorEx collectionMotor, CRServo transferFront, CRServo transferBack) {
        this.collectionMotor = collectionMotor;
        this.transferFront = transferFront;
        this.transferBack = transferBack;

        this.collectionMotor.setRunMode(Motor.RunMode.RawPower);
        this.collectionMotor.setInverted(true);
        this.transferFront.setInverted(true);
        this.transferBack.setInverted(true);

        setState(TransferState.IDLE);
    }

    public TransferSubsystem(HardwareMap hMap, String collection, String transferFront, String transferBack) {
        this(
                new MotorEx(hMap, collection),
                new CRServo(hMap, transferFront),
                new CRServo(hMap, transferBack)
        );
    }

    public double getMotorPower() {
        return collectionMotor.get();
    }

    public TransferState getState() {
        return state;
    }

    public void setState(TransferState state) {
        if (this.state == state) state = TransferState.IDLE;

        this.state = state;
        collectionMotor.set(state.collectionPower);
        transferFront.set(state.transferFrontPower);
        transferBack.set(state.transferBackPower);
    }

    public void collect() {
        setState(TransferState.COLLECT);
    }

    public void deposit() {
        setState(TransferState.DEPOSIT);
    }

    public void reverse() {
        setState(TransferState.REVERSE);
    }

    public void idle() {
        setState(TransferState.IDLE);
    }
}
