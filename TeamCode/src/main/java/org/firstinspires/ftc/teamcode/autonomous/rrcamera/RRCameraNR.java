package org.firstinspires.ftc.teamcode.autonomous.rrcamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;

@Autonomous
public class RRCameraNR extends RRCameraNorthBase {
    @Override
    protected Pose2d getStartPose() {
        return new Pose2d(16.5, -62, Math.toRadians(90));
    }

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.RED;
    }

}
