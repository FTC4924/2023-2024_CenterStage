package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.CollectionSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.HangingSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TransferSubsystem;

import java.util.Collection;

public abstract class AutoBase extends CommandOpMode {
    protected DriveSubsystem drive;
    protected RoadRunnerSubsystem roadRunner;
    protected HangingSubsystem hanging;
    protected TransferSubsystem transfer;
    protected CollectionSubsystem collection;
    protected TeamPropSubsystem teamProp;
    protected AllianceColor allianceColor;
    // TODO: 6/27/2023 Declare subsystems here

    @Override
    public void initialize() {
        allianceColor = getAllianceColor();

        drive = new DriveSubsystem(
                hardwareMap,
                "leftFront",
                "rightFront",
                "leftBack",
                "rightBack"
        );

        //roadRunner = new RoadRunnerSubsystem(hardwareMap);

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

        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, allianceColor);
        // TODO: 6/27/2023 Construct subsystems here

        schedule(new InstantCommand().andThen(getCommands()));  // Schedules commmands with the command scheduler.

        register(drive, roadRunner);  // TODO: 6/27/2023 Register subsystems with the command scheduler here
    }

    public void setRoadRunnerStart(Pose2d pose2d) {
        roadRunner.setPoseEstimate(pose2d);
    }

    protected abstract AllianceColor getAllianceColor();

    protected abstract Command getCommands();
}
