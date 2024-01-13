package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.util.Timing.Timer;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.commands.defaultcommands.DefaultDrive;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.triggers.AxisTrigger;
import org.firstinspires.ftc.teamcode.ftclib.triggers.JoystickTrigger;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.concurrent.TimeUnit;

import static org.firstinspires.ftc.teamcode.RobotConstants.CONTROLLER_TOLERANCE;

@TeleOp
public class TeleopDebug extends CommandOpMode {
    // TODO: 6/27/2023 Declare subsystems here

    private GamepadEx gpad1;
    private GamepadEx gpad2;

    //private AprilTagSubsystem aprilTag;
    private TeamPropSubsystem teamProp;

    @Override
    public void initialize() {
        // TODO: 6/27/2023 Construct subsystems here

        //aprilTag = new AprilTagSubsystem(hardwareMap, telemetry);
//        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, alliance);

        RoadRunnerSubsystem roadRunner = new RoadRunnerSubsystem(hardwareMap, telemetry);
        Timer timer = new Timer(100, TimeUnit.MILLISECONDS);

        Pose2d startPos = new Pose2d(18, 66, -90);
        timer.start();
        TrajectorySequenceBuilder trajectorySequenceBuilder = roadRunner.trajectorySequenceBuilder(startPos)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0);
        telemetry.addData("Trajectory Construction", timer.elapsedTime() / 1000000.0);
        trajectorySequenceBuilder.build();
        telemetry.addData("Trajectory Build Time", timer.elapsedTime() / 1000000.0);


        // Initialize the gamepads and gamepad event triggers
        gpad1 = new GamepadEx(gamepad1);
        gpad2 = new GamepadEx(gamepad2);

        JoystickTrigger gpad1LeftStick = new JoystickTrigger(gpad1::getLeftX, gpad1::getLeftY);
        JoystickTrigger gpad1RightStick = new JoystickTrigger(gpad1::getRightX, gpad1::getRightY);
        JoystickTrigger gpad2LeftStick = new JoystickTrigger(gpad2::getLeftX, gpad2::getLeftY);
        JoystickTrigger gpad2RightStick = new JoystickTrigger(gpad2::getRightX, gpad2::getRightY);

        AxisTrigger gpad1LeftTrigger = new AxisTrigger(() -> gpad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER), CONTROLLER_TOLERANCE);
        AxisTrigger gpad1RightTrigger = new AxisTrigger(() -> gpad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), CONTROLLER_TOLERANCE);
        AxisTrigger gpad2LeftTrigger = new AxisTrigger(() -> gpad2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER), CONTROLLER_TOLERANCE);
        AxisTrigger gpad2RightTrigger = new AxisTrigger(() -> gpad2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER), CONTROLLER_TOLERANCE);


        Command driveCommand = new DefaultDrive(
                drive,
                gpad1::getLeftX,
                gpad1::getLeftY,
                () -> gpad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> gpad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
        );
        // TODO: 6/27/2023 Create commands for later execution here


        ///////////////////////////// Gamepad 1 keybindings /////////////////////////////
        gpad1.getGamepadButton(GamepadKeys.Button.B)  // Reset the Gyro
                .whenActive(drive::resetGyro);

        // TODO: 6/27/2023 Add keybindings for driver 1

        ///////////////////////////// Gamepad 2 keybindings /////////////////////////////

        gpad2LeftStick.y.whileActiveContinuous(() -> hanging.setRawLeftHook(hanging.getRawLeftHook() + gpad2.getLeftY() / 200) );

        gpad2RightStick.y.whileActiveContinuous(() -> hanging.setRawRightHook(hanging.getRawRightHook() + gpad2.getRightY() / 200) );

        gpad2.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> hanging.setRawRightHook(0.5));
        gpad2.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> hanging.setRawLeftHook(0.5));


        // TODO: 6/27/2023 Add keybindings for driver 2

        register();  // TODO: 6/27/2023 Register subsystems here

        ///////////////////////////// Subsystem Default Commands /////////////////////////////

        drive.setDefaultCommand(driveCommand);  // Handles controller input when no other driving command is running.
    }

    @Override
    public void run() {
        telemetry.addData("Left Hook", hanging.getRawLeftHook());
        telemetry.addData("Left Hook Raw", hardwareMap.get(Servo.class, "leftHook").getPosition());
        telemetry.addData("Right Hook", hanging.getRawRightHook());
    }

    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }

}
