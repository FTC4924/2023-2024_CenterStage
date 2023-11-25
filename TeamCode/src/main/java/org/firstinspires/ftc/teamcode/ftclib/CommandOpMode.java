package org.firstinspires.ftc.teamcode.ftclib;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class CommandOpMode extends LinearOpMode {
    public Telemetry telemetry = new MultipleTelemetry(super.telemetry, FtcDashboard.getInstance().getTelemetry());;
    /**
     * Cancels all previous commands
     */
    public void reset() {
        CommandScheduler.getInstance().reset();
    }

    public void started() {}

    public void run() {}

    public void stopped() {}

    /**
     * Schedules {@link com.arcrobotics.ftclib.command.Command} objects to the scheduler
     */
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    /**
     * Registers {@link com.arcrobotics.ftclib.command.Subsystem} objects to the scheduler
     */
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        if (isStarted()) started();
        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            CommandScheduler.getInstance().run();
            run();
            telemetry.update();
        }

        stopped();
        reset();
    }

    public abstract void initialize();

    public static void disable() {
        Robot.disable();
    }

    public static void enable() {
        Robot.enable();
    }


}

