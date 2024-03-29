package org.firstinspires.ftc.teamcode.autonomous.rrcamera;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;

@Autonomous
public class RRCameraSR extends RRCameraSouthBase {
    @Override
    protected Pose2d getStartPose() {
        return new Pose2d(-40, -66, Math.toRadians(90));
    }

    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.RED;
    }

}
