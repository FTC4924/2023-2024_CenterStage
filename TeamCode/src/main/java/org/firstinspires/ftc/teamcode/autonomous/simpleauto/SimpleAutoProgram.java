package org.firstinspires.ftc.teamcode.autonomous.simpleauto;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.autonomous.AutoBase;
import org.firstinspires.ftc.teamcode.ftclib.commands.Drive;

@Config
public abstract class SimpleAutoProgram extends AutoBase {
    public static double dr1pow = 0.25;
    public static double dr1Ntime = 0.1;
    public static double dr1Stime = 1.75;
    public static double dr2pow = -0.25;
    public static double dr2Ntime = 5;
    public static double dr2Stime = 13;



    protected enum StartPos {
        NORTH, SOUTH
    }

    @Override
    protected Command getCommands() {
        StartPos startPos = getStartPos();

        return new SequentialCommandGroup(
                new Drive(drive, dr1pow, 0, 0, (startPos == StartPos.NORTH ? dr1Ntime : dr1Stime)),
                new WaitCommand(1000),
                new Drive(drive, 0, dr2pow * getAlliance().negation, 0, (startPos == StartPos.NORTH ? dr2Ntime : dr2Stime)),
                new InstantCommand(transfer::deposit).alongWith(new WaitCommand(4000)),
                new InstantCommand(transfer::collect).alongWith(new WaitCommand(4000))
        );
    }

    protected abstract StartPos getStartPos();
}
