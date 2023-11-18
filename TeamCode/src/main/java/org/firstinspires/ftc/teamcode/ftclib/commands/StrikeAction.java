package org.firstinspires.ftc.teamcode.ftclib.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.StrikePos;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

import java.util.HashMap;

public class StrikeAction extends SelectCommand {

    public StrikeAction(TeamPropSubsystem teamPropSubsystem, Command leftCommand, Command centerCommand, Command rightCommand) {
        super(
                new HashMap<Object, Command>() {{
                    put(StrikePos.LEFT, leftCommand);
                    put(StrikePos.CENTER, centerCommand);
                    put(StrikePos.RIGHT, rightCommand);
                }},
                // the selector
                teamPropSubsystem::getAveragePos
        );

    }
}
