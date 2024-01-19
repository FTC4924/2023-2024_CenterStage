package org.firstinspires.ftc.teamcode.autonomous.complexblueauto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeAction;
import org.firstinspires.ftc.teamcode.ftclib.commands.StrikeRead;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import java.util.HashMap;

//@Autonomous
public abstract class ComplexBlueAuto extends AutoBase {
    protected enum StartPos {
        NORTH(new Pose2d(), new Vector2d()),
        SOUTH(new Pose2d(), new Vector2d());

        final Pose2d pose2d;
        final Vector2d vector2d;
        final Vector2d strikeMergePoint;

        StartPos(Pose2d pose2d, Vector2d strikeMergePoint) {
            this.pose2d = pose2d;
            this.vector2d = pose2d.vec();
            this.strikeMergePoint = strikeMergePoint;
        }
    }

    protected enum EndPoint {LEFT, RIGHT}

    protected abstract StartPos getStartPos();
    protected abstract EndPoint getEndPoint();

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }

    @Override
    protected Command getCommands() {
        StartPos startPos = getStartPos();
        setRoadRunnerStart(startPos.pose2d);
        Pose2d backdropStartPos = new Pose2d();

        TrajectorySequence approachStrikeMarks = roadRunner.trajectorySequenceBuilder(startPos.pose2d)  // TODO: 11/17/2023 Finish writing
                .forward(9)
//                .lineTo(startPos.vector2d.plus(new Vector2d()))
                .build();

        return new SequentialCommandGroup(
                new StrikeRead(teamProp, 10),
                new RRDrive(roadRunner, approachStrikeMarks),
                new StrikeAction(teamProp,  // Move to the targeted strike position and to a common point. Get that common point from StartPos and lineTo it.
                        new SequentialCommandGroup(),  // TODO: 11/17/2023 Fill with commands
                        new SequentialCommandGroup(),  // TODO: 11/17/2023 Fill with commands
                        new SequentialCommandGroup()  // TODO: 11/17/2023 Fill with commands
                ),

                new SelectCommand(new HashMap<Object, Command>() {{  // Move to the backdropStartPos from each start point
                    put(StartPos.NORTH, new RRDrive(roadRunner, null));  // TODO: 11/17/2023 Write trajectories
                    put(StartPos.SOUTH, new RRDrive(roadRunner, null));  // TODO: 11/17/2023 Write trajectories
                }}, this::getStartPos),

                new StrikeAction(teamProp,  // Deposit the pixel on the backdrop in the correct place
                        new SequentialCommandGroup(),  // TODO: 11/17/2023 Fill with commands
                        new SequentialCommandGroup(),  // TODO: 11/17/2023 Fill with commands
                        new SequentialCommandGroup()),  // TODO: 11/17/2023 Fill with commands

                new SelectCommand(new HashMap<Object, Command>() {{  // Park in the predetermined side
                    put(EndPoint.LEFT, new RRDrive(roadRunner, null));  // TODO: 11/17/2023 Write trajectories
                    put(EndPoint.RIGHT, new RRDrive(roadRunner, null));  // TODO: 11/17/2023 Write trajectories
                }}, this::getEndPoint)
        );
    }
}
