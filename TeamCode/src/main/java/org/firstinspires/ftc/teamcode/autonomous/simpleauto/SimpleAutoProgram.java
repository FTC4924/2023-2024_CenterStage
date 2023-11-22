package org.firstinspires.ftc.teamcode.autonomous.simpleauto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.Drive;

public abstract class SimpleAutoProgram extends AutoBase {
    protected enum StartPos {
        NORTH, SOUTH
    }

    @Override
    protected Command getCommands() {
        StartPos startPos = getStartPos();

        return new SequentialCommandGroup(
                new Drive(drive, 0.5, 0, 0, (startPos == StartPos.NORTH ? 0.5 : 5)),
                new Drive(drive, 0, 0.5 * getAllianceColor().negation, 0, (startPos == StartPos.NORTH ? 5 : 10))
        );
    }

    protected abstract StartPos getStartPos();
}
