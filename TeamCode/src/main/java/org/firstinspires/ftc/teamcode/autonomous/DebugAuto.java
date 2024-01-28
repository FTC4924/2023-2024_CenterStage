package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeRead;
import org.firstinspires.ftc.teamcode.ftclib.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous
public class DebugAuto extends AutoBase {
    @Override
    protected Command getCommands() {
        Pose2d startPos = new Pose2d(18, 70, Math.toRadians(-90));
        setRoadRunnerStart(startPos);
        teamProp.useAlt(true);

        TrajectorySequence traj1L = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,40 * alliance.negation, Math.toRadians(180)), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence traj1C = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,12 * alliance.negation, startPos.getHeading()), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence traj1R = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToSplineHeading(new Pose2d(startPos.getX() - 4,40 * alliance.negation, Math.toRadians(0)), Math.toRadians(-90 * alliance.negation))
                .build();

        TrajectorySequence test1 = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToConstantHeading(new Vector2d(startPos.getX(),0), Math.toRadians(-90 * alliance.negation))
                .build();
        TrajectorySequence test2 = roadRunner.trajectorySequenceBuilder(startPos)
                .setVelConstraint(RoadRunnerSubsystem.getVelocityConstraint(
                        DriveConstants.MAX_VEL / 2,
                        DriveConstants.MAX_ANG_VEL,
                        DriveConstants.TRACK_WIDTH
                ))
                .splineToConstantHeading(new Vector2d(0,0), Math.toRadians(-90 * alliance.negation))
                .build();

        return new SequentialCommandGroup(
                new StrikeRead(teamProp, 20),
                new RRDrive(roadRunner, test1),
                new RRDrive(roadRunner, test2),
                new InstantCommand(transfer::deposit)

        );
    }

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }
}
