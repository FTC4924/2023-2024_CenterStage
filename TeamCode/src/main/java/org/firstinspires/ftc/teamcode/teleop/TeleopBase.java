package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

public abstract class TeleopBase extends CommandOpMode {

    private GamepadEx gpad1;
    private GamepadEx gpad2;

    private ServoEx airplaneServo;


    @Override
    public void initialize() {

        // TODO: 6/27/2023 Construct subsystems here
        airplaneServo = hardwareMap.get(ServoEx.class, "airplaneServo");

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

                /////////////////////////// Gamepad 1 keybindings /////////////////////////////

        gpad1.getGamepadButton(GamepadKeys.Button.B)  // Reset the Gyro
                .whenActive(drive::resetGyro);
        gpad1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> drive.setMoveTurbo(true))
                .whenReleased(() -> drive.setMoveTurbo(false));
        gpad1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> drive.setTurnTurbo(true))
                .whenReleased(() -> drive.setTurnTurbo(false));

        gpad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).and(gpad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(() -> airplaneServo.setPosition(0.5))
                .whenInactive(() -> airplaneServo.setPosition(0));


        // TODO: 6/27/2023 Add keybindings for driver 1

        ///////////////////////////// Gamepad 2 keybindings /////////////////////////////

        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(hanging::hooksDown);
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(hanging::hooksUp);
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
        hanging.hooksUp();
    }

    @Override
    public void run() {
        // TODO: 6/27/2023 Add telemetries here
    }
}
