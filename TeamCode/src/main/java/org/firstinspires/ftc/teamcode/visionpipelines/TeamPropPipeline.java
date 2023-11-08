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

    @NotNull
    private volatile StrikePos strikePos = StrikePos.LEFT;

    private Rect LEFT_REGION = new Rect();
    private Rect CENTER_REGION = new Rect();
    private Rect RIGHT_REGION = new Rect();

    private final static Scalar GREEN = new Scalar(0,1,0);
    private final static Scalar YELLOW = new Scalar(1,1,0);

    public TeamPropPipeline(AllianceColor allianceColor, Telemetry telemetry) {
        this.allianceColor = allianceColor;
        this.telemetry = telemetry;
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

        Mat outputFrame = new Mat();
        //Core.multiply(YCrCb, maskedChannels, outputFrame);
        Core.extractChannel(input, outputFrame, allianceColor.colorChannel);
        outputFrame = input;

        //Imgproc.cvtColor(outputFrame, outputFrame, Imgproc.COLOR_YCrCb2RGB);

        switch (strikePos) {
            case LEFT:
                Imgproc.rectangle(outputFrame, LEFT_REGION, GREEN, 2);
                Imgproc.rectangle(outputFrame, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(outputFrame, RIGHT_REGION, YELLOW, 2);
                break;

            case CENTER:
                Imgproc.rectangle(outputFrame, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(outputFrame, CENTER_REGION, GREEN, 2);
                Imgproc.rectangle(outputFrame, RIGHT_REGION, YELLOW, 2);
                break;

            case RIGHT:
                Imgproc.rectangle(outputFrame, LEFT_REGION, YELLOW, 2);
                Imgproc.rectangle(outputFrame, CENTER_REGION, YELLOW, 2);
                Imgproc.rectangle(outputFrame, RIGHT_REGION, GREEN, 2);
                break;
        }

        telemetry.addData("x", LEFT_REGION.x);
        telemetry.addData("y", LEFT_REGION.y);
        telemetry.addData("w", LEFT_REGION.width);
        telemetry.addData("h", LEFT_REGION.height);

        return outputFrame;
    }

    public StrikePos getStrikePos()
    {
        return strikePos;
    }

    public void setboxes(double x1, double y1, double x2, double y2) {
        LEFT_REGION = new Rect((int) (LEFT_REGION.x + x1), (int) (LEFT_REGION.y + y1), (int) (LEFT_REGION.width + x2), (int) (LEFT_REGION.height + y2));
    }
}