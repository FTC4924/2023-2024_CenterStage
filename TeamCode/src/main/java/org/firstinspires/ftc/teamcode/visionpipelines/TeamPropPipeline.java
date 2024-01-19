package org.firstinspires.ftc.teamcode.visionpipelines;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TeamPropPipeline extends OpenCvPipeline {
    private static final Rect LEFT_REGION = new Rect(101, 424, 206, 110);
    private static final Rect CENTER_REGION = new Rect(546, 424, 185, 91);
    private static final Rect RIGHT_REGION = new Rect(960, 432, 205, 113);
    private static final Rect LEFT_ALT_REGION = new Rect(491, 428, 203, 100);
    private static final Rect CENTER_ALT_REGION = new Rect(847, 419, 186, 91);
    private static final int RIGHT_ALT_DEFAULT = 138;

    private static final Scalar GREEN = new Scalar(0, 255, 0);
    private static final Scalar YELLOW = new Scalar(255, 255, 0);


    private final AllianceColor allianceColor;
    private final int resolutionHeight;
    private final int resolutionWidth;
    private final Mat YCrCb;
    private final Mat Cb;
    private final Mat output;

    private int leftMean;
    private int centerMean;
    private int rightMean;
    private int max;

    private boolean alt;
    private volatile boolean newSample;

    @NotNull
    private volatile StrikePos strikePos;

    public TeamPropPipeline(AllianceColor allianceColor, int resolutionHeight, int resolutionWidth) {
        this.allianceColor = allianceColor;
        this.resolutionHeight = resolutionHeight;
        this.resolutionWidth = resolutionWidth;

        YCrCb = new Mat();
        Cb = new Mat();
        output = new Mat();
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
        newSample = true;


        Scalar maskedChannels = new Scalar(
                allianceColor.colorChannel == 0 ? 1 : 0,
                allianceColor.colorChannel == 1 ? 1 : 0,
                allianceColor.colorChannel == 2 ? 1 : 0
        );
        Core.multiply(YCrCb, maskedChannels, output);

        Imgproc.cvtColor(output, output, Imgproc.COLOR_YCrCb2RGB);
        if (!alt) switch (strikePos) {
            case LEFT:
                Imgproc.rectangle(output, LEFT_REGION, GREEN, 2);
                Imgproc.rectangle(output, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(output, RIGHT_REGION, YELLOW, 2);
                break;

            case CENTER:
                Imgproc.rectangle(output, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, CENTER_REGION, GREEN, 2);
                Imgproc.rectangle(output, RIGHT_REGION, YELLOW, 2);
                break;

            case RIGHT:
                Imgproc.rectangle(output, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(output, RIGHT_REGION, GREEN, 2);
                break;
        }
        else switch (strikePos) {
            case LEFT:
                Imgproc.rectangle(output, LEFT_ALT_REGION, GREEN, 2);
                Imgproc.rectangle(output, CENTER_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), YELLOW, 2);
                break;

            case CENTER:
                Imgproc.rectangle(output, LEFT_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, CENTER_ALT_REGION, GREEN, 2);
                Imgproc.rectangle(output, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), YELLOW, 2);
                break;

            case RIGHT:
                Imgproc.rectangle(output, LEFT_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, CENTER_ALT_REGION, YELLOW, 2);
                Imgproc.rectangle(output, new Rect(2, 2, resolutionWidth - 4, resolutionHeight - 4), GREEN, 2);
                break;
        }

        return output;
    }

    public void telemetry(Telemetry telemetry) {
//        telemetry.addData("x", LEFT_ALT_REGION.x);
//        telemetry.addData("y", LEFT_ALT_REGION.y);
//        telemetry.addData("w", LEFT_ALT_REGION.width);
//        telemetry.addData("h", LEFT_ALT_REGION.height);
//        telemetry.addData("id", LEFT_ALT_REGION.toString());
//        telemetry.addLine();
//        telemetry.addData("l mean", leftMean);
//        telemetry.addData("c mean", centerMean);
//        telemetry.addData("r mean", rightMean);
//        telemetry.addLine();
//        telemetry.addData("max mean", max);
//        telemetry.addData("color channel", allianceColor.colorChannel);
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
        newSample = false;
        return strikePos;
    }

    public boolean isNewSample() {
        return newSample;
    }
}