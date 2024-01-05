package org.firstinspires.ftc.teamcode.autonomous.rrsimple;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;

import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;

public abstract class RRSimpleBase extends AutoBase {
    @Override
    protected Command getCommands() {//new Pose2d(-40, 66, -90)
        Pose2d startPos = getStartPose();
        setRoadRunnerStart(startPos);

        return new RRDrive(roadRunner,
                roadRunner.trajectorySequenceBuilder(startPos)
                        .splineToSplineHeading(new Pose2d(startPos.getX(),startPos.getY()/2 * alliance.negation, -180), -90 * alliance.negation)
                        .splineToConstantHeading(new Vector2d(60, 12 * alliance.negation), 0)
                        .build()
        );
    }

    protected abstract Pose2d getStartPose();
}
