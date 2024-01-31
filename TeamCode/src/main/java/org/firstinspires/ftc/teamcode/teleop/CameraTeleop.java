package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.CommandOpMode;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.RoadRunnerSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

@TeleOp
public class CameraTeleop extends OpMode {
    protected TeamPropSubsystem teamProp;

    @Override
    public void init() {

        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, AllianceColor.BLUE);

    }

    @Override
    public void init_loop() {
        teamProp.periodic();
    }

    @Override
    public void loop() {
        telemetry.addLine("success");
    }
}
