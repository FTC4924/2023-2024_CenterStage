package org.firstinspires.ftc.teamcode;

/**
 * An enum representing the color of the robot's current alliance and values that change based on the alliance.
 */
public enum AllianceColor {
    BLUE(2, 1), RED(0, -1);

    public final int colorChannel;
    public final int negation;

    AllianceColor(int colorChannel, int negation) {
        this.colorChannel = colorChannel;
        this .negation = negation;
    }
}
