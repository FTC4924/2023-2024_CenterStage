package org.firstinspires.ftc.teamcode.autonomous.complexblueauto;

public class NorthComplexBlueAuto extends ComplexBlueAuto {
    @Override
    protected StartPos getStartPos() {
        return StartPos.NORTH;
    }

    @Override
    protected EndPoint getEndPoint() {
        return EndPoint.RIGHT;
    }
}
