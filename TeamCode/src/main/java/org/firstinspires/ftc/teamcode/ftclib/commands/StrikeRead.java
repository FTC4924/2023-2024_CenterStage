package org.firstinspires.ftc.teamcode.ftclib.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

public class StrikeRead extends CommandBase {
    private TeamPropSubsystem teamProp;
    private int sampleCounts;

    public StrikeRead(TeamPropSubsystem teamProp, int sampleCounts) {
        this.teamProp = teamProp;
        this.sampleCounts = sampleCounts;

        addRequirements(teamProp);
    }

    @Override
    public void initialize() {
        teamProp.clearPos();
    }

    @Override
    public void execute() {
        teamProp.samplePos();
    }

    @Override
    public boolean isFinished() {
        return teamProp.sampleCount() >= sampleCounts;
    }
}
