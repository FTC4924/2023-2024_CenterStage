package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AllianceColor;

@TeleOp
public class RedTeleop extends TeleopBase {
    @Override
    protected AllianceColor getAlliance() {
        return AllianceColor.RED;
    }
}
