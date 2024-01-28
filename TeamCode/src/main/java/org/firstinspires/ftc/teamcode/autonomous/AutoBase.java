package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.PixelPlacerSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

public abstract class AutoBase extends CommandOpMode {
    protected RoadRunnerSubsystem roadRunner;
    protected TeamPropSubsystem teamProp;
    protected PixelPlacerSubsystem pixelPlacer;



    @Override
    public void initialize() {
        telemetry.addLine("Starting init...");

        roadRunner = new RoadRunnerSubsystem(hardwareMap, telemetry);

        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, alliance);

        schedule(new InstantCommand().andThen(getCommands()));  // Schedules commmands with the command scheduler.

        register(roadRunner);
        telemetry.update();

        pixelPlacer = new PixelPlacerSubsystem(hardwareMap,"pixelPlacer");
    }

    @Override
    public void initialize_loop() {
        teamProp.periodic();
    }

    @Override
    public void stopped() {
        driveOffset = Math.toDegrees(roadRunner.getExternalHeading());
    }

    public void setRoadRunnerStart(Pose2d pose2d) {
        roadRunner.setPoseEstimate(pose2d);
    }

    protected abstract Command getCommands();
}
