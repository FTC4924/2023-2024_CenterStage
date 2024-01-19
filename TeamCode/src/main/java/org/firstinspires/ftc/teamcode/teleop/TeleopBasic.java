package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.util.Timing.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

import java.util.concurrent.TimeUnit;

@TeleOp
public class TeleopBasic extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private TeamPropSubsystem teamProp;

    private Timer timer;
    private double lastTime;
    @Override
    public void init() {
        /*frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");*/

        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, AllianceColor.RED);
        teamProp.useAlt(true);
        timer = new Timer(10000, TimeUnit.MICROSECONDS);
        timer.start();
    }

    @Override
    public void init_loop() {
        /*frontLeft.setPower(gamepad1.right_stick_x);
        frontRight.setPower(gamepad1.right_stick_y);
        backLeft.setPower(gamepad1.left_stick_x);
        backRight.setPower(gamepad1.left_stick_y);
*/
       /* telemetry.addData("frontLeft", frontLeft.getPower());
        telemetry.addData("frontRight", frontRight.getPower());
        telemetry.addData("backLeft", backLeft.getPower());
        telemetry.addData("backRight", backRight.getPower());*/

        double time = (double) timer.elapsedTime() / 1000000;
        double deltatime = time - lastTime;
        telemetry.addData("time", time);
        telemetry.addData("deltatime", deltatime);
        double scalar = 25;
//        teamProp.editBoxes(
//                gamepad1.left_stick_x * deltatime * scalar,
//                gamepad1.left_stick_y * deltatime * scalar,
//                gamepad1.right_stick_x * deltatime * scalar,
//                gamepad1.right_stick_y * deltatime * scalar
//        );
//        teamProp.periodic();
        lastTime = time;
    }

    @Override
    public void loop() {

    }
}
