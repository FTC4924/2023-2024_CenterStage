package org.firstinspires.ftc.teamcode.visionpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import androidx.annotation.NonNull;

public class TeamPropPipeline extends OpenCvPipeline {
    private static final Rect LEFT_REGION = new Rect(90, 357, 206, 110);
    private static final Rect CENTER_REGION = new Rect(543, 355, 177, 91);
    private static final Rect RIGHT_REGION = new Rect(970, 367, 210, 106);
    private static final Rect LEFT_ALT_REGION = new Rect(624, 390, 210, 106);
    private static final Rect CENTER_ALT_REGION = new Rect(887, 420, 210, 106);
    private static final int RIGHT_ALT_DEFAULT = 127;

    private static final Scalar GREEN = new Scalar(0, 255, 0);
    private static final Scalar YELLOW = new Scalar(255, 255, 0);


    private final AllianceColor allianceColor;
    private final int resolutionHeight;
    private final int resolutionWidth;
    private final Mat YCrCb;
    private final Mat Cb;

    private int leftMean;
    private int centerMean;
    private int rightMean;
    private int max;

    private boolean alt;

    @NotNull
    private volatile StrikePos strikePos;

    public TeamPropPipeline(AllianceColor allianceColor, int resolutionHeight, int resolutionWidth) {
        this.allianceColor = allianceColor;
        this.resolutionHeight = resolutionHeight;
        this.resolutionWidth = resolutionWidth;

        YCrCb = new Mat();
        Cb = new Mat();
        strikePos = StrikePos.LEFT;
    }

    @Override
    public void init(Mat firstFrame) {
        processFrame(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, allianceColor.colorChannel);


        if (!alt) {
            leftMean = (int) Core.mean(Cb.submat(LEFT_REGION)).val[0];
            centerMean = (int) Core.mean(Cb.submat(CENTER_REGION)).val[0];
            rightMean = (int) Core.mean(Cb.submat(RIGHT_REGION)).val[0];
        } else {
            leftMean = (int) Core.mean(Cb.submat(LEFT_ALT_REGION)).val[0];
            centerMean = (int) Core.mean(Cb.submat(CENTER_ALT_REGION)).val[0];
            rightMean = RIGHT_ALT_DEFAULT;
        }


        max = leftMean;
        strikePos = StrikePos.LEFT;
        if (centerMean > max) {
            max = centerMean;
            strikePos = StrikePos.CENTER;
        }
        if (rightMean > max) {
            max = rightMean;
            strikePos = StrikePos.RIGHT;
        }


        if (!alt) switch (strikePos) {
            case LEFT:
                Imgproc.rectangle(input, LEFT_REGION, GREEN, 2);
                Imgproc.rectangle(input, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(input, RIGHT_REGION, YELLOW, 2);
                break;

            case CENTER:
                Imgproc.rectangle(input, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, CENTER_REGION, GREEN, 2);
                Imgproc.rectangle(input, RIGHT_REGION, YELLOW, 2);
                break;

            case RIGHT:
                Imgproc.rectangle(input, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(input, RIGHT_REGION, GREEN, 2);
                break;
        }
        else switch (strikePos) {
            case LEFT:
                Imgproc.rectangle(input, LEFT_ALT_REGION, GREEN, 2);
                Imgproc.rectangle(input, CENTER_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), YELLOW, 2);
                break;

            case CENTER:
                Imgproc.rectangle(input, LEFT_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, CENTER_ALT_REGION, GREEN, 2);
                Imgproc.rectangle(input, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), YELLOW, 2);
                break;

            case RIGHT:
                Imgproc.rectangle(input, LEFT_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, CENTER_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(input, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), GREEN, 2);
                break;
        }

        return input;
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("x", LEFT_ALT_REGION.x);
        telemetry.addData("y", LEFT_ALT_REGION.y);
        telemetry.addData("w", LEFT_ALT_REGION.width);
        telemetry.addData("h", LEFT_ALT_REGION.height);
        telemetry.addData("id", LEFT_ALT_REGION.toString());
        telemetry.addLine();
        telemetry.addData("l mean", leftMean);
        telemetry.addData("c mean", centerMean);
        telemetry.addData("r mean", rightMean);
        telemetry.addLine();
        telemetry.addData("max mean", max);
        telemetry.addData("color channel", allianceColor.colorChannel);
        telemetry.addData("strike pos", strikePos);
        telemetry.addData("alt", alt);
        telemetry.addData("rand", Math.random());
    }

    public void useAlt(boolean alt) {
        this.alt = alt;
    }

    public boolean isAlt() {
        return alt;
    }

    @NonNull
    public StrikePos getStrikePos() {
        return strikePos;
    }
}