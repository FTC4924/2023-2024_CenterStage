package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PixelPlacerSubsystem {

    public enum PlacerState {
        UP(0.0), DOWN(0.45);

        final double pos;
        PlacerState(double pos) {
            this.pos = pos;
        }
    }

    private PlacerState state;

    private ServoEx pixelPlacer;

    public PixelPlacerSubsystem(ServoEx pixelPlacer) {
        this.pixelPlacer = pixelPlacer;
    }
    public PixelPlacerSubsystem(HardwareMap hMap, String pixelPlacer) {
        this(
                new SimpleServo(hMap, pixelPlacer, 0,0)
        );
    }

    public void placerUp() {
        setHooksState(PlacerState.UP);
    }

    public void placerDown() {
        setHooksState(PlacerState.DOWN);
    }

    public void setHooksState(PlacerState state) {
        this.state = state;
        pixelPlacer.setPosition(state.pos);
    }

    public PlacerState getPlacerState() {
        return state;
    }

    public double getRawPlacerPos() {
        return pixelPlacer.getPosition();
    }
}
