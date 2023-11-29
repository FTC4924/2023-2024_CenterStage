package org.firstinspires.ftc.teamcode.autonomous.simpleauto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;

@Autonomous
public class SimpleAutoSouthBlue extends SimpleAutoProgram {
    @Override
    protected StartPos getStartPos() {
        return StartPos.SOUTH;
    }

    @Override
    protected AllianceColor getAllianceColor() {
        return AllianceColor.BLUE;
    }
}
