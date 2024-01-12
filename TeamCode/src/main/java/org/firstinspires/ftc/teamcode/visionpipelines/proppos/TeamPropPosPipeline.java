package org.firstinspires.ftc.teamcode.visionpipelines.proppos;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TeamPropPosPipeline extends OpenCvPipeline {
    public enum PropPos {
        LEFT, CENTER, RIGHT
    }

    private int leftMean;
    private int centerMean;
    private int rightMean;

    private Telemetry telemetry;
    private final int resolutionHeight;
    private final int resolutionWidth;
    private final Mat YCrCb;
    private final Mat Cb;

    private volatile PropPos strikePos = PropPos.LEFT;

    private Rect LEFT_REGION = new Rect(90, 357, 206, 110);
    private Rect CENTER_REGION = new Rect(543, 355, 177, 91);
    private Rect RIGHT_REGION = new Rect(970, 367, 210, 106);

    private final static Scalar GREEN = new Scalar(0,255,0);
    private final static Scalar YELLOW = new Scalar(255,255,0);

    public TeamPropPosPipeline(Telemetry telemetry, int resolutionHeight, int resolutionWidth) {
        this.telemetry = telemetry;
        this.resolutionHeight = resolutionHeight;
        this.resolutionWidth = resolutionWidth;
        YCrCb = new Mat();
        Cb = new Mat();
    }

    private void maxAverage() {
        int max = leftMean;
        strikePos = PropPos.LEFT;
        if (centerMean > max) {
            max = centerMean;
            strikePos = PropPos.CENTER;
        }
        if (rightMean > max) {
            strikePos = PropPos.RIGHT;
        }
    }

    @Override
    public void init(Mat firstFrame) {
        processFrame(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 2);

        leftMean = (int) Core.mean(input.submat(LEFT_REGION)).val[0];
        centerMean = (int) Core.mean(input.submat(CENTER_REGION)).val[0];
        rightMean = (int) Core.mean(input.submat(RIGHT_REGION)).val[0];

        maxAverage();
        //Core.multiply(YCrCb, maskedChannels, output);
        //Core.extractChannel(input, output, allianceColor.colorChannel);

        //Imgproc.cvtColor(output, output, Imgproc.COLOR_YCrCb2RGB);

        //Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);

        switch (strikePos) {
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

        return input;
    }

    public PropPos getStrikePos()
    {
        return strikePos;
    }

    public void setboxes(Rect box0) {

        LEFT_REGION = box0;

        telemetry.addData("x", LEFT_REGION.x);
        telemetry.addData("y", LEFT_REGION.y);
        telemetry.addData("w", LEFT_REGION.width);
        telemetry.addData("h", LEFT_REGION.height);
        telemetry.addData("StrikePos", getStrikePos());
        telemetry.addData("LeftMean", leftMean);
        telemetry.addData("CenterMean", centerMean);
        telemetry.addData("RightMean", rightMean);
        telemetry.addData("rand", Math.random());
    }
}