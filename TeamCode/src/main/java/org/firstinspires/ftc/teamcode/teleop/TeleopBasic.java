package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TeamPropSubsystem;

@TeleOp
public class TeleopBasic extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private TeamPropSubsystem teamProp;
    @Override
    public void init() {
        /*frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");
        backLeft = hardwareMap.get(DcMotor.class,"backLeft");
        backRight = hardwareMap.get(DcMotor.class,"backRight");*/

        teamProp = new TeamPropSubsystem(hardwareMap, telemetry, AllianceColor.RED);
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

        teamProp.editBoxes(gamepad1.left_stick_x, gamepad1.left_stick_y,gamepad1.right_stick_x,gamepad1.right_stick_y);
    }

    @Override
    public void loop() {

    }
}
