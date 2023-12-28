package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TransferSubsystem extends SubsystemBase {
    public enum TransferState {
        COLLECT(0.35), DEPOSIT(0.60), TRANSPORT(0.37);

        final double pos;

        TransferState(double pos) {
            this.pos = pos;
        }
    }

    private final ServoEx transferServo;

    private TransferState transferState;

    public TransferSubsystem(ServoEx transferServo) {
        this.transferServo = transferServo;
    }

    public TransferSubsystem(HardwareMap hMap, String transferServo) {
        this(new SimpleServo(hMap, transferServo, 0, 0));
    }

    public void collect() {
        setTransferState(TransferState.COLLECT);
    }

    public void deposit() {
        setTransferState(TransferState.DEPOSIT);
    }

    public void transport() {
        setTransferState(TransferState.TRANSPORT);
    }

    public void setTransferState(TransferState transferState) {
        this.transferState = transferState;
        transferServo.setPosition(transferState.pos);
    }

    public TransferState getTransferState() {
        return transferState;
    }

    public void setRawPosition(double position) {
        transferServo.setPosition(position);
    }

    public double getPosition() {
        return transferServo.getPosition();
    }
}
