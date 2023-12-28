package org.firstinspires.ftc.teamcode.roadrunner.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class OdometerTelemetry extends OpMode {
    private DcMotor parallelEncoder, perpendicularEncoder;
    @Override
    public void init() {
        parallelEncoder = hardwareMap.get(DcMotor.class, "rightFront");
        perpendicularEncoder = hardwareMap.get(DcMotor.class, "rightBack");
    }

    @Override
    public void loop() {
        telemetry.addData("Parallel Encoder", parallelEncoder.getCurrentPosition());
        telemetry.addData("Perpendicular Encoder", perpendicularEncoder.getCurrentPosition());
    }
}
