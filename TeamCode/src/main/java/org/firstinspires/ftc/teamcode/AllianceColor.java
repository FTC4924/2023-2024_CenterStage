package org.firstinspires.ftc.teamcode;

/**
 * An enum representing the color of the robot's current alliance and values that change based on the alliance.
 */
public enum AllianceColor {
    BLUE(2), RED(0);

    public int colorChannel;

    AllianceColor(int colorChannel) {
        this.colorChannel = colorChannel;
    }
}
