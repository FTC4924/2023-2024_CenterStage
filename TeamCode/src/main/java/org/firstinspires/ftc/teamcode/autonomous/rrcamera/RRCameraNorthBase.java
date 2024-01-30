package org.firstinspires.ftc.teamcode.autonomous.rrcamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeAction;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeRead;
import org.firstinspires.ftc.teamcode.ftclib.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.visionpipelines.StrikePos;

public abstract class RRCameraNorthBase extends AutoBase {  // x 4, y -26
    @Override
    protected Command getCommands() {//new Pose2d(-40, 66, -90)
        Pose2d startPos = getStartPose();
        setRoadRunnerStart(startPos);
        teamProp.useAlt(alliance == AllianceColor.BLUE );

        TrajectorySequence traj1L = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,34 * alliance.negation, Math.toRadians(90 + 90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence traj1C = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,31 * alliance.negation, Math.toRadians(90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .setTangent(Math.toRadians(90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 4,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,38 * alliance.negation, Math.toRadians(90 * alliance.negation)), Math.toRadians(90 * alliance.negation))
                .build();
        TrajectorySequence traj1R = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 5,34 * alliance.negation, Math.toRadians(90 - 90 * alliance.negation)), Math.toRadians(-90 * alliance.negation))
                .build();


        TrajectorySequence traj2L = roadRunner.trajectorySequenceBuilder(traj1L.end(), Math.toRadians(90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(24,60 * alliance.negation, Math.toRadians(-90)), Math.toRadians(0 * alliance.negation))
                .build();
        TrajectorySequence traj2C = roadRunner.trajectorySequenceBuilder(traj1C.end(), Math.toRadians(90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(24, 60 * alliance.negation, Math.toRadians(90 * alliance.negation)), Math.toRadians(0.0 * alliance.negation))
                .build();
        TrajectorySequence traj2R = roadRunner.trajectorySequenceBuilder(traj1R.end(), Math.toRadians(90 * alliance.negation))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(24, 60 * alliance.negation, Math.toRadians(90)), Math.toRadians(0 * alliance.negation))
                .build();

        TrajectorySequence traj3L = roadRunner.trajectorySequenceBuilder(traj2L.end(), Math.toRadians(0))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(54.5, 35.5 * alliance.negation + 5.5 + 6, Math.toRadians(0)), Math.toRadians(0))
                .build();
        TrajectorySequence traj3C = roadRunner.trajectorySequenceBuilder(traj2C.end(), Math.toRadians(0))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(54.5, 35.5 * alliance.negation + 5.5, Math.toRadians(0)), Math.toRadians(0))
                .build();
        TrajectorySequence traj3R = roadRunner.trajectorySequenceBuilder(traj2R.end(), Math.toRadians(0))
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(54.5, 35.5 * alliance.negation + 5.5 -  6, Math.toRadians(0)), Math.toRadians(0))
                .build();


        return new SequentialCommandGroup(
                new StrikeRead(teamProp, 20),
                new StrikeAction(teamProp,
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1L),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(700),
                                new InstantCommand(transfer::idle),
                                new RRDrive(roadRunner, traj2L),
                                new RRDrive(roadRunner, traj3L),
                                new InstantCommand(() -> hanging.setRawRightHook(0.2)),
                                new WaitCommand(500),
                                new InstantCommand(() -> pixelPlacer.placerDown()),
                                new WaitCommand(1500),
                                new InstantCommand(()-> pixelPlacer.placerUp())

                        ),
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1C),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(700),
                                new InstantCommand(transfer::idle),
                                new RRDrive(roadRunner, traj2C),
                                new RRDrive(roadRunner, traj3C),
                                new InstantCommand(() -> hanging.setRawRightHook(0.2)),
                                new WaitCommand(500),
                                new InstantCommand(() -> pixelPlacer.placerDown()),
                                new WaitCommand(1500),
                                new InstantCommand(()-> pixelPlacer.placerUp())
                        ),
                        new SequentialCommandGroup(
                                new RRDrive(roadRunner, traj1R),
                                new InstantCommand(transfer::deposit),
                                new WaitCommand(700),
                                new InstantCommand(transfer::idle),
                                new RRDrive(roadRunner, traj2R),
                                new RRDrive(roadRunner, traj3R),
                                new InstantCommand(() -> hanging.setRawRightHook(0.2)),
                                new WaitCommand(500),
                                new InstantCommand(() -> pixelPlacer.placerDown()),
                                new WaitCommand(1500),
                                new InstantCommand(()-> pixelPlacer.placerUp())
                        )
                ),

                new TelemetryCommand(telemetry, "Finished with RR", 100)
        );
    }

    protected abstract Pose2d getStartPose();
}
