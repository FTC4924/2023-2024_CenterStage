package org.firstinspires.ftc.teamcode.autonomous.simpleauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;

@Autonomous
public class SimpleAutoNorthBlue extends SimpleAutoProgram {
    @Override
    protected StartPos getStartPos() {
        return StartPos.NORTH;
    }

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }
}
