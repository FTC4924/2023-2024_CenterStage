package org.firstinspires.ftc.teamcode.ftclib;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.HangingSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.subsystems.TransferSubsystem;

public abstract class CommandOpMode extends OpMode {
    public Telemetry telemetry = new MultipleTelemetry(super.telemetry, FtcDashboard.getInstance().getTelemetry());

    protected static double driveOffset;

    protected DriveSubsystem drive;
    protected HangingSubsystem hanging;
    protected TransferSubsystem transfer;

    @SuppressWarnings("FieldCanBeLocal")
    protected AllianceColor alliance;
    /**
     * Cancels all previous commands
     */
    public void reset() {
        CommandScheduler.getInstance().reset();
    }

    /**
     * Schedules {@link Command} objects to the scheduler
     */
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    /**
     * Registers {@link Subsystem} objects to the scheduler
     */
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    @Override
    public void init() {
        alliance = getAlliance();

        drive = new DriveSubsystem(
                hardwareMap,
                "leftFront",
                "rightFront",
                "leftBack",
                "rightBack"
        );

        hanging = new HangingSubsystem(
                hardwareMap,
                "leftWinch",
                "rightWinch",
                "leftHook",
                "rightHook"
        );

        transfer = new TransferSubsystem(
                hardwareMap,
                "collection",
                "transferFront",
                "transferBack"
        );

        register(drive, hanging, transfer);

        initialize();
        driveOffset = 0;
    }

    @Override
    public void init_loop() {
        initialize_loop();
    }

    @Override
    public void start() {
        started();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        run();
    }

    @Override
    public void stop() {
        stopped();
        reset();
    }


    /**
     * Override this method instead of init
     * <p>
     * User-defined initialize method
     * <p>
     * This method will be called once, when the INIT button is pressed.
     */
    public abstract void initialize();

    /**
     * Override this method instead of init_loop
     * <p>
     * User-defined initialize_loop method
     * <p>
     * This method will be called repeatedly during the period between when
     * the init button is pressed and when the play button is pressed (or the
     * OpMode is stopped).
     * <p>
     * This method is optional. By default, this method takes no action.
     */
    public void initialize_loop() {}

    /**
     * Override this method instead of start
     * <p>
     * User-defined started method
     * <p>
     * This method will be called once, when the play button is pressed.
     * <p>
     * This method is optional. By default, this method takes no action.
     * <p>
     * Example usage: Starting another thread.
     */
    public void started() {}

    /**
     * Override this method instead of loop
     * <p>
     * User-defined run method
     * <p>
     * This method will be called repeatedly during the period between when
     * the play button is pressed and when the OpMode is stopped.
     * <p>
     * Unlike loop, this method is optional. By default, this method takes no action.
     */
    public void run() {}

    /**
     * Override this method instead of stop
     * <p>
     * User-defined stopped method
     * <p>
     * This method will be called once, when this OpMode is stopped.
     * <p>
     * Your ability to control hardware from this method will be limited.
     * <p>
     * This method is optional. By default, this method takes no action.
     */
    public void stopped() {}

    public static void disable() {
        Robot.disable();
    }

    public static void enable() {
        Robot.enable();
    }

    protected abstract AllianceColor getAlliance();
}

