package org.firstinspires.ftc.teamcode.autonomous.simpleauto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.RRDrive;

@Config
public abstract class RRAuto extends AutoBase {


    protected enum StartPos {
        NORTH, SOUTH
    }

    @Override
    protected Command getCommands() {
        roadRunner.trajectoryBuilder(new Pose2d())
                .forward(24)
                .forward(27)
                //.strafeLeft(70)
                .build();

        StartPos startPos = getStartPos();

        return new SequentialCommandGroup(
                new RRDrive(roadRunner, roadRunner.trajectorySequenceBuilder(new Pose2d())
                        .forward(24)
                        .build())
        );
    }

    protected abstract StartPos getStartPos();
}
