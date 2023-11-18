package org.firstinspires.ftc.teamcode.visionpipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.StrikePos;
import org.jetbrains.annotations.NotNull;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TeamPropPipeline extends OpenCvPipeline {
    private int leftMean;
    private int centerMean;
    private int rightMean;

    private final AllianceColor allianceColor;
    private Telemetry telemetry;
    private final int resolutionHeight;
    private final int resolutionWidth;

    @NotNull
    private volatile StrikePos strikePos = StrikePos.LEFT;

    private Rect LEFT_REGION = new Rect();
    private Rect CENTER_REGION = new Rect();
    private Rect RIGHT_REGION = new Rect();

    private final static Scalar GREEN = new Scalar(0,1,0);
    private final static Scalar YELLOW = new Scalar(1,1,0);

    Mat output;

    public TeamPropPipeline(AllianceColor allianceColor, Telemetry telemetry, int resolutionHeight, int resolutionWidth) {
        this.allianceColor = allianceColor;
        this.telemetry = telemetry;
        this.resolutionHeight = resolutionHeight;
        this.resolutionWidth = resolutionWidth;
    }

    private void maxAverage() {
        int max = leftMean;
        strikePos = StrikePos.LEFT;
        if (centerMean > max) {
            max = centerMean;
            strikePos = StrikePos.CENTER;
        }
        if (rightMean > max) {
            strikePos = StrikePos.RIGHT;
        }
    }

    @Override
    public void init(Mat firstFrame) {
        processFrame(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        leftMean = (int) Core.mean(input.submat(LEFT_REGION)).val[allianceColor.colorChannel];
        centerMean = (int) Core.mean(input.submat(CENTER_REGION)).val[allianceColor.colorChannel];
        rightMean = (int) Core.mean(input.submat(RIGHT_REGION)).val[allianceColor.colorChannel];

        maxAverage();
        //Core.multiply(YCrCb, maskedChannels, output);
        //Core.extractChannel(input, output, allianceColor.colorChannel);
        output = input;

        //Imgproc.cvtColor(output, output, Imgproc.COLOR_YCrCb2RGB);

        switch (strikePos) {
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

        telemetry.addData("x", LEFT_REGION.x);
        telemetry.addData("y", LEFT_REGION.y);
        telemetry.addData("w", LEFT_REGION.width);
        telemetry.addData("h", LEFT_REGION.height);

        return output;
    }

    public StrikePos getStrikePos()
    {
        return strikePos;
    }

    public void setboxes(double x1, double y1, double x2, double y2) {
        x1 = x1 < 0 ? 0 : x1;
        x2 = x2 > resolutionWidth ? resolutionWidth : x2;
        y1 = y1 < 0 ? 0 :y1;
        y2 = y2 > resolutionHeight ? resolutionHeight : y2;

        LEFT_REGION = new Rect((int) (LEFT_REGION.x + x1), (int) (LEFT_REGION.y + y1), (int) (LEFT_REGION.width + x2), (int) (LEFT_REGION.height + y2));


    }
}