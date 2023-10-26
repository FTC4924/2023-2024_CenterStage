package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.CollectionSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.HangingSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;
import org.firstinspires.ftc.teamcode.ftclib.triggers.JoystickTrigger;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

public abstract class TeleopBase extends CommandOpMode {
    protected DriveSubsystem drive;
    protected HangingSubsystem hanging;
    protected TransferSubsystem transfer;
    protected CollectionSubsystem collection;

    // TODO: 6/27/2023 Declare subsystems here

    private GamepadEx gpad1;
    private GamepadEx gpad2;

    @SuppressWarnings("FieldCanBeLocal")
    private AllianceColor alliance;

    @Override
    public void initialize() {
        alliance = getAlliance();

        drive = new DriveSubsystem(
                hardwareMap,
                "leftFront",
                "rightFront",
                "leftBack",
                "rightBack"
        );

        hanging = new HangingSubsystem(
                hardwareMap,
                "leftWinch",
                "rightWinch",
                "leftHook",
                "rightHook",
                telemetry
        );

        transfer = new TransferSubsystem(
                hardwareMap,
                "transferServo"
        );

        collection = new CollectionSubsystem(
                hardwareMap,
                "collectionMotor"
        );


        /*
        Create subsystems: Hanging, collection/transfer
        Hanging: leftWinch, rightWinch, leftHook, rightHook
        Collection/Transfer: collectionMotor, transferServo
        hooksUp and hooksDown
        raise and lower
        collectIdleDeposit and spit
         */

        // TODO: 6/27/2023 Construct subsystems here

        // Initialize the gamepads and gamepad event triggers
        gpad1 = new GamepadEx(gamepad1);
        gpad2 = new GamepadEx(gamepad2);

        JoystickTrigger gpad1LeftStick = new JoystickTrigger(gpad1::getLeftX, gpad1::getLeftY);
        JoystickTrigger gpad1RightStick = new JoystickTrigger(gpad1::getRightX, gpad1::getRightY);
        JoystickTrigger gpad2LeftStick = new JoystickTrigger(gpad2::getLeftX, gpad2::getLeftY);
        JoystickTrigger gpad2RightStick = new JoystickTrigger(gpad2::getRightX, gpad2::getRightY);

        AxisTrigger gpad1LeftTrigger = new AxisTrigger(this::getGpad1LeftTrigger, CONTROLLER_TOLERANCE);
        AxisTrigger gpad1RightTrigger = new AxisTrigger(this::getGpad1RightTrigger, CONTROLLER_TOLERANCE);
        AxisTrigger gpad2LeftTrigger = new AxisTrigger(this::getGpad2LeftTrigger, CONTROLLER_TOLERANCE);
        AxisTrigger gpad2RightTrigger = new AxisTrigger(this::getGpad2RightTrigger, CONTROLLER_TOLERANCE);


        Command driveCommand = new DefaultDrive(
                drive,
                gpad1::getLeftX,
                gpad1::getLeftY,
                this::getGpad1LeftTrigger,
                this::getGpad1RightTrigger,
                gpad1.getGamepadButton(GamepadKeys.Button.Y)::get
        );
        // TODO: 6/27/2023 Create commands for later execution here


        ///////////////////////////// Gamepad 1 keybindings /////////////////////////////
        gpad1.getGamepadButton(GamepadKeys.Button.B)  // Reset the Gyro
                .whenActive(drive::resetGyro);

        // TODO: 6/27/2023 Add keybindings for driver 1

        ///////////////////////////// Gamepad 2 keybindings /////////////////////////////


        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(hanging::hooksDown);
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(hanging::hooksUp);  // TODO: 10/20/2023 add hanging hooks past to dislodge the hooks
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(hanging::hooksPast);
        gpad2RightStick.y
                .whileActiveContinuous(() -> hanging.setPower(gpad2.getRightY()))
                .whenInactive(() -> hanging.setPower(0.0));
        gpad2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(() -> {
            if (collection.getPower() <= 0.0) collection.intake();
            else collection.idle();
        });
        gpad2.getGamepadButton(GamepadKeys.Button.X).whenPressed(() -> {
            if (collection.getPower() >= 0.0) collection.output();
            else collection.idle();
        });
        gpad2.getGamepadButton(GamepadKeys.Button.A).toggleWhenPressed(transfer::deposit, transfer::collect);

        // TODO: 6/27/2023 Add keybindings for driver 2

        register(drive, hanging, transfer, collection);  // TODO: 6/27/2023 Register subsystems here

        ///////////////////////////// Subsystem Default Commands /////////////////////////////

        drive.setDefaultCommand(driveCommand);  // Handles controller input when no other driving command is running.
    }

    @Override
    public void run() {
        super.run();

        // TODO: 6/27/2023 Add telemetries here

        telemetry.update();
    }

    private double getGpad1LeftTrigger() {
        return gpad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
    }

    private double getGpad1RightTrigger() {
        return gpad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
    }

    private double getGpad2LeftTrigger() {
        return gpad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
    }

    private double getGpad2RightTrigger() {
        return gpad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
    }

    protected abstract AllianceColor getAlliance();

}
