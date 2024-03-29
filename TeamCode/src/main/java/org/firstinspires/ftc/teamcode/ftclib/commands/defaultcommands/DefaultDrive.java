package org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DefaultDrive extends CommandBase {
    private final DriveSubsystem drive;
    private final DoubleSupplier x;
    private final DoubleSupplier y;
    private final DoubleSupplier left;
    private final DoubleSupplier right;
    public DefaultDrive(DriveSubsystem drive, DoubleSupplier x, DoubleSupplier y, DoubleSupplier left, DoubleSupplier right) {
        this.drive = drive;
        this.x = x;
        this.y = y;
        this.left = left;
        this.right = right;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        double xVal = (Math.abs(x.getAsDouble()) >= CONTROLLER_TOLERANCE ? x.getAsDouble() : 0);  // If x is outside the threshold, return x, else return 0
        double yVal = (Math.abs(y.getAsDouble()) >= CONTROLLER_TOLERANCE ? y.getAsDouble() : 0);
        double leftVal = (Math.abs(left.getAsDouble()) >= CONTROLLER_TOLERANCE ? left.getAsDouble() : 0);
        double rightVal = (Math.abs(right.getAsDouble()) >= CONTROLLER_TOLERANCE ? right.getAsDouble() : 0);

        drive.drive(
                xVal,
                yVal,
                leftVal - rightVal
        );
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
