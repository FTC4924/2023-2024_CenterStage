package org.firstinspires.ftc.teamcode.autonomous.rrcamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeAction;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeRead;
import org.firstinspires.ftc.teamcode.ftclib.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public abstract class RRCameraSouthBase extends AutoBase {  // x 4, y -26
    @Override
    protected Command getCommands() {//new Pose2d(-40, 66, -90)
        Pose2d startPos = getStartPose();
        setRoadRunnerStart(startPos);
        teamProp.useAlt(alliance.negation != 1);

        TrajectorySequence traj1L = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() + 4,38 * alliance.negation, Math.toRadians(90 + 90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence traj1C = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() + 4,16 * alliance.negation, Math.toRadians(-90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence traj1R = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() + 5,38 * alliance.negation, Math.toRadians(90 - 90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .build();


        TrajectorySequence traj2L = roadRunner.trajectorySequenceBuilder(traj1L.end(), Math.toRadians(-90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
//                .splineToConstantHeading(new Vector2d(startPos.getX(), 38 * alliance.negation), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(startPos.getX() + 12,10 * alliance.negation), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(60, 18 * alliance.negation, Math.toRadians(-180)), Math.toRadians(0))
                .build();
        TrajectorySequence traj2C = roadRunner.trajectorySequenceBuilder(traj1C.end(), Math.toRadians(-90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToConstantHeading(new Vector2d(startPos.getX() + 12,10 * alliance.negation), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(60, 18 * alliance.negation, Math.toRadians(-180)), Math.toRadians(0))
                .build();
        TrajectorySequence traj2R = roadRunner.trajectorySequenceBuilder(traj1R.end(), Math.toRadians(-90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToConstantHeading(new Vector2d(startPos.getX() + 12,10 * alliance.negation), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(60, 18 * alliance.negation, Math.toRadians(-180)), Math.toRadians(0))
                .build();


        TrajectorySequence traj3 = roadRunner.trajectorySequenceBuilder(traj2L.end())
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 4,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .forward(4)
                .build();

        return new SequentialCommandGroup(
                new StrikeRead(teamProp, 20),
                new StrikeAction(teamProp,
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1L),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(500),
                                new InstantCommand(transfer::idle),

                                new RRDrive(roadRunner, traj2L)
                        ),
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1C),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(500),
                                new InstantCommand(transfer::idle),

                                new RRDrive(roadRunner, traj2C)
                        ),
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1R),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(500),
                                new InstantCommand(transfer::idle),

                                new RRDrive(roadRunner, traj2R)
                        )
                ),
                new InstantCommand(transfer::deposit),
                new WaitCommand(4000),

                new RRDrive(roadRunner, traj3),
                new TelemetryCommand(telemetry, "Finished with RR", 100)
        );
    }

    protected abstract Pose2d getStartPose();
}
