package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;

public class DriveSubsystem extends SubsystemBase { // TODO: 12/13/2022 Rewrite to use RoadRunner.

    private static final double ANGLE_CORRECTION = 0.5;

    protected static double savedAngleOffset;

    private final MotorEx frontLeft, frontRight, backLeft, backRight;

    private final MecanumDrive mecanumDrive;

    private final IMU imu;
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

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                DriveConstants.LOGO_FACING_DIR, DriveConstants.USB_FACING_DIR));
        imu.initialize(parameters);
        imu.resetYaw();

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

        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - angleOffset;
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
        angleOffset = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void setSavedAngleOffset(double offset) {
        savedAngleOffset = offset;
    }

    public double getSavedAngleOffset() {
        return savedAngleOffset;
    }

    public void clearAngleOffset() {
        savedAngleOffset = 0;
    }

    public void saveAngleOffset() {
        savedAngleOffset = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180;
    }

    public void loadAngleOffset() {
        angleOffset = savedAngleOffset;
        clearAngleOffset();
    }

    public void setAngleOffset(double angleOffset) {
        this.angleOffset = angleOffset;
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
