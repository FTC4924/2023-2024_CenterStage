package org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

public class DefaultDrive extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier x;
    private final DoubleSupplier y;
    private final DoubleSupplier left;
    private final DoubleSupplier right;
    private final BooleanSupplier moveTurbo;
    private final BooleanSupplier turnTurbo;

    public DefaultDrive(DriveSubsystem drive, DoubleSupplier x, DoubleSupplier y, DoubleSupplier left, DoubleSupplier right, BooleanSupplier moveTurbo, BooleanSupplier turnTurbo) {
        this.drive = drive;
        this.x = x;
        this.y = y;
        this.left = left;
        this.right = right;
        this.moveTurbo = moveTurbo;
        this.turnTurbo = turnTurbo;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        double xVal = (Math.abs(x.getAsDouble()) >= CONTROLLER_TOLERANCE ? x.getAsDouble() : 0);  // If x is outside the threshold, return x, else return 0
        double yVal = (Math.abs(y.getAsDouble()) >= CONTROLLER_TOLERANCE ? y.getAsDouble() : 0);
        double leftVal = (Math.abs(left.getAsDouble()) >= CONTROLLER_TOLERANCE ? left.getAsDouble() : 0);
        double rightVal = (Math.abs(right.getAsDouble()) >= CONTROLLER_TOLERANCE ? right.getAsDouble() : 0);
        double reduction = (moveTurbo.getAsBoolean() ? 1.0 : 0.5);

        drive.drive(
                xVal * reduction,
                yVal * reduction,
                (leftVal - rightVal) / (turnTurbo.getAsBoolean() ? 1 : 3)
        );
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
