package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;

public abstract class TeleopBase extends CommandOpMode {

    private GamepadEx gpad1;
    private GamepadEx gpad2;

    private ServoEx airplaneServo;

    private RevBlinkinLedDriver ledLights;

    @Override
    public void initialize() {

        // TODO: 6/27/2023 Construct subsystems here
        airplaneServo = new SimpleServo(hardwareMap, "airplaneServo", 0, 0);
        airplaneServo.setInverted(true);

        drive.loadAngleOffset();

        ledLights = hardwareMap.get(RevBlinkinLedDriver.class, "ledLights");

        // Initialize the gamepads and gamepad event triggers
        gpad1 = new GamepadEx(gamepad1);
        gpad2 = new GamepadEx(gamepad2);

        AxisTrigger gpad2RightStickY = new AxisTrigger(gpad2::getRightY, CONTROLLER_TOLERANCE);
        AxisTrigger gpad2LeftStickY = new AxisTrigger(gpad2::getLeftY, CONTROLLER_TOLERANCE);

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
        gpad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).and(gpad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(() -> airplaneServo.setPosition(1))
                .whenInactive(() -> airplaneServo.setPosition(0));
//        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(() -> ledLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE));
//        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
//                .whenPressed(() -> ledLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET));
//        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
//                .whenPressed(() -> ledLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN));
//        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
//                .whenPressed(() -> ledLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE));
//        gpad1.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
//                .whenPressed(() -> ledLights.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE));
        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(() -> hanging.setRawRightHook(0.2)),
                        new WaitCommand(500),
                        new InstantCommand(() -> pixelPlacer.placerDown())
                ));
        gpad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(() -> pixelPlacer.placerUp()),
                        new WaitCommand(500),
                        new InstantCommand(() -> hanging.hooksDown())
                ));



        // TODO: 6/27/2023 Add keybindings for driver 1

        ///////////////////////////// Gamepad 2 keybindings /////////////////////////////

        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(hanging::hooksDown);
        gpad2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(hanging::hooksUp);
        gpad2RightStickY
                .whileActiveContinuous(() -> hanging.winchR(gpad2.getRightY()))
                .whenInactive(() ->  hanging.stopR());
        gpad2LeftStickY
                .whileActiveContinuous(() -> hanging.winchL(gpad2.getLeftY()))
                .whenInactive(() ->  hanging.stopL());
        gpad2.getGamepadButton(GamepadKeys.Button.B).whenPressed(transfer::collect);
        gpad2.getGamepadButton(GamepadKeys.Button.A).whenPressed(transfer::deposit);
        gpad2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(transfer::reverse);
        gpad2.getGamepadButton(GamepadKeys.Button.X).whenPressed(transfer::collectReverse);

        // TODO: 6/27/2023 Add keybindings for driver 2

//        register();  // TODO: 6/27/2023 Register subsystems here

        ///////////////////////////// Subsystem Default Commands /////////////////////////////

        drive.setDefaultCommand(driveCommand);  // Handles controller input when no other driving command is running.
    }

    @Override
    public void started() {
        hanging.hooksDown();
    }

    @Override
    public void run() {
        // TODO: 6/27/2023 Add telemetries here
        telemetry.addData("Transfer State", transfer.getState());
        telemetry.addData("Motor Power", transfer.getMotorPower());
    }
}