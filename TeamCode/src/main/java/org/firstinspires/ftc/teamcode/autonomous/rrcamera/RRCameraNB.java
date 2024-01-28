package org.firstinspires.ftc.teamcode.autonomous.rrcamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;

@Autonomous
public class RRCameraNB extends RRCameraNorthBase {
    @Override
    protected Pose2d getStartPose() {
        return new Pose2d(18, 62, Math.toRadians(-90));
    }  // TODO: 1/19/2024 Start positions should be constant and referenced here, not instantiated here.

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.BLUE;
    }
}
