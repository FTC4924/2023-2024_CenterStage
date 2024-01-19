package org.firstinspires.ftc.teamcode.autonomous.rrsimple;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public abstract class RRSimpleSouthBase extends AutoBase {
    @Override
    protected Command getCommands() {//new Pose2d(-40, 66, -90)
        Pose2d startPos = getStartPose();
        setRoadRunnerStart(startPos);
        teamProp.useAlt(alliance.negation != 1);

        TrajectorySequence traj1 = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX(),22 * alliance.negation, Math.toRadians(-180)), Math.toRadians(-90 * alliance.negation))
                .splineToConstantHeading(new Vector2d(60, 18 * alliance.negation), Math.toRadians(0))
                .build();

        TrajectorySequence traj2 = roadRunner.trajectorySequenceBuilder(traj1.end())
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 4,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .forward(4)
                .build();

        return new SequentialCommandGroup(
                new RRDrive(roadRunner, traj1),
                new InstantCommand(transfer::deposit),
                new WaitCommand(4000),
                new RRDrive(roadRunner, traj2),
                new TelemetryCommand(telemetry, "Finished with RR", 100)
        );
    }

    protected abstract Pose2d getStartPose();
}
