package org.firstinspires.ftc.teamcode.autonomous.BlueAuto;

public class NorthBlueAuto extends BlueAuto {
    @Override
    protected StartPos getStartPos() {
        return StartPos.NORTH;
    }

    @Override
    protected EndPoint getEndPoint() {
        return EndPoint.RIGHT;
    }
}
