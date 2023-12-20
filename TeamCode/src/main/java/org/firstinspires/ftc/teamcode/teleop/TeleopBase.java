package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.CollectionSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.HangingSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;
import org.firstinspires.ftc.teamcode.ftclib.triggers.JoystickTrigger;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

public abstract class TeleopBase extends CommandOpMode {

    private GamepadEx gpad1;
    private GamepadEx gpad2;

    @Override
    public void initialize() {

        // TODO: 6/27/2023 Construct subsystems here

        // Initialize the gamepads and gamepad event triggers
        gpad1 = new GamepadEx(gamepad1);
        gpad2 = new GamepadEx(gamepad2);

        AxisTrigger gpad2RightStickY = new AxisTrigger(gpad2::getRightY, CONTROLLER_TOLERANCE);

        // TODO: 6/27/2023 Create commands for later execution here

        Command driveCommand = new DefaultDrive(
                drive,
                gpad1::getLeftX,
                gpad1::getLeftY,
                () -> gpad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gpad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
        );

        ///////////////////////////// Gamepad 1 keybindings /////////////////////////////
        gpad1.getGamepadButton(GamepadKeys.Button.B)  // Reset the Gyro
                .whenActive(drive::resetGyro);
        gpad1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> drive.setMoveTurbo(true))
                .whenReleased(() -> drive.setMoveTurbo(false));
        gpad1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> drive.setTurnTurbo(true))
                .whenReleased(() -> drive.setTurnTurbo(false));

        // TODO: 6/27/2023 Add keybindings for driver 1

        ///////////////////////////// Gamepad 2 keybindings /////////////////////////////

        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(hanging::hooksDown);
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(hanging::hooksUp);  // TODO: 10/20/2023 add hanging hooks past to dislodge the hooks
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(hanging::hooksPast);
        gpad2RightStickY
                .whileActiveContinuous(() -> hanging.winch(gpad2.getRightY()))
                .whenInactive(() ->  hanging.stopWinch());
        gpad2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(collection::intake);
        gpad2.getGamepadButton(GamepadKeys.Button.X).whenPressed(collection::output);
        gpad2.getGamepadButton(GamepadKeys.Button.A).toggleWhenPressed(transfer::deposit, transfer::collect);
        gpad2.getGamepadButton(GamepadKeys.Button.B).whenPressed(transfer::transport);

        // TODO: 6/27/2023 Add keybindings for driver 2

        register(drive, hanging, transfer, collection);  // TODO: 6/27/2023 Register subsystems here

        ///////////////////////////// Subsystem Default Commands /////////////////////////////

        drive.setDefaultCommand(driveCommand);  // Handles controller input when no other driving command is running.
    }

    @Override
    public void started() {
        transfer.transport();
        hanging.hooksPast();
    }

    @Override
    public void run() {
        // TODO: 6/27/2023 Add telemetries here
    }
}
