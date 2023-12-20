package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.drivebase.HDrive;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import lombok.Setter;

public class DriveSubsystem extends SubsystemBase { // TODO: 12/13/2022 Rewrite to use RoadRunner.

    private static final double ANGLE_CORRECTION = 0.5;

    private final MotorEx frontLeft, frontRight, backLeft, backRight;

    private final MecanumDrive mecanumDrive;

    private final RevIMU imu;
    private Orientation angles;
    private double angleOffset;
    private double targetAngle;

    private boolean moveTurbo;

    private boolean turnTurbo;

    private final PDController angleController;

    public DriveSubsystem(HardwareMap hardwareMap, MotorEx frontLeft, MotorEx frontRight, MotorEx backLeft, MotorEx backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.mecanumDrive = new MecanumDrive(backRight, backLeft, frontRight, frontLeft);

        imu = new RevIMU(hardwareMap);
        imu.init();
        angles = null;

        angleController = new PDController(1, 0);
    }

    public DriveSubsystem(HardwareMap hMap, String frontLeft, String frontRight, String backLeft, String backRight) {
        this(
                hMap,
                new MotorEx(hMap, frontLeft),
                new MotorEx(hMap, frontRight),
                new MotorEx(hMap, backLeft),
                new MotorEx(hMap, backRight)
        );
    }

    public void drive(double strafeSpeed, double forwardSpeed, double turn) {
        double moveReduction = moveTurbo ? 1.0 : 0.5;
        double turnReduction = turnTurbo ? 1.0 : 0.33;

        double heading = imu.getRotation2d().getDegrees() - angleOffset;
        targetAngle = heading + turn * turnReduction;

        mecanumDrive.driveFieldCentric(
                strafeSpeed * moveReduction,
                forwardSpeed * moveReduction,
                turn * turnReduction,
                heading
        );  // (targetAngle - heading) * ANGLE_CORRECTION

        //return;

        //mecanumDrive.driveFieldCentric(strafeSpeed, forwardSpeed, angleController.calculate(targetAngle, heading), heading);
    }

    public void resetGyro() {
        angleOffset = imu.getRotation2d().getDegrees();
    }

    public void stop() { mecanumDrive.stop(); }

    public boolean isMoveTurbo() {
        return moveTurbo;
    }

    public boolean isTurnTurbo() {
        return turnTurbo;
    }

    public void setMoveTurbo(boolean moveTurbo) {
        this.moveTurbo = moveTurbo;
    }

    public void setTurnTurbo(boolean turnTurbo) {
        this.turnTurbo = turnTurbo;
    }
}
