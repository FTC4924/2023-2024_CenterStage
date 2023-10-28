package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultGyroCorrectDrive;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.CollectionSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveGyroCorrectSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.HangingSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TransferSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;
import org.firstinspires.ftc.teamcode.ftclib.triggers.JoystickTrigger;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

@TeleOp
public class TeleopDebug extends CommandOpMode {
    protected DriveGyroCorrectSubsystem drive;
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

        drive = new DriveGyroCorrectSubsystem(
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
                "rightHook"
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


        Command driveCommand = new DefaultGyroCorrectDrive(
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

        gpad2LeftStick.y.whileActiveContinuous(() -> hanging.setLeftHook(hanging.getLeftHook() + gpad2.getLeftY() / 200) );

        gpad2RightStick.y.whileActiveContinuous(() -> hanging.setRightHook(hanging.getRightHook() + gpad2.getRightY() / 200) );

        gpad2LeftTrigger.whileActiveContinuous(() -> transfer.setPosition(transfer.getPosition() - gpad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 200) );

        gpad2RightTrigger.whileActiveContinuous(() -> transfer.setPosition(transfer.getPosition() + gpad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 200) );

        gpad2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(() -> transfer.setPosition(0.5));
        gpad2.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> hanging.setRightHook(0.5));
        gpad2.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> hanging.setLeftHook(0.5));




        // TODO: 6/27/2023 Add keybindings for driver 2

        register(drive, hanging, transfer, collection);  // TODO: 6/27/2023 Register subsystems here

        ///////////////////////////// Subsystem Default Commands /////////////////////////////

        drive.setDefaultCommand(driveCommand);  // Handles controller input when no other driving command is running.
    }

    @Override
    public void run() {
        super.run();

        telemetry.addData("Left Hook", hanging.getLeftHook());
        telemetry.addData("Left Hook Raw", hardwareMap.get(Servo.class, "leftHook").getPosition());
        telemetry.addData("Right Hook", hanging.getRightHook());
        telemetry.addData("Transfer", transfer.getPosition());

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

    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }

}
