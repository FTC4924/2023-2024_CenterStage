package org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveGyroCorrectSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultGyroCorrectDrive extends CommandBase {
    private final DriveGyroCorrectSubsystem drive;
    private final DoubleSupplier x;
    private final DoubleSupplier y;
    private final DoubleSupplier left;
    private final DoubleSupplier right;
    private final BooleanSupplier turbo;

    public DefaultGyroCorrectDrive(DriveGyroCorrectSubsystem drive, DoubleSupplier x, DoubleSupplier y, DoubleSupplier left, DoubleSupplier right, BooleanSupplier turbo) {
        this.drive = drive;
        this.x = x;
        this.y = y;
        this.left = left;
        this.right = right;
        this.turbo = turbo;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        double xVal = (Math.abs(x.getAsDouble()) >= CONTROLLER_TOLERANCE ? x.getAsDouble() : 0);  // If x is outside the threshold, return x, else return 0
        double yVal = (Math.abs(y.getAsDouble()) >= CONTROLLER_TOLERANCE ? y.getAsDouble() : 0);
        double leftVal = (Math.abs(left.getAsDouble()) >= CONTROLLER_TOLERANCE ? left.getAsDouble() : 0);
        double rightVal = (Math.abs(right.getAsDouble()) >= CONTROLLER_TOLERANCE ? right.getAsDouble() : 0);
        double reduction = (turbo.getAsBoolean() ? 0.75 : 0.5);

        drive.drive(
                xVal * reduction,
                yVal * reduction,
                (leftVal - rightVal) / 3
        );
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
