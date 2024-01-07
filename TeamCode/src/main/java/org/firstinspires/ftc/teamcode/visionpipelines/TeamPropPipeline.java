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

    private Rect LEFT_REGION = new Rect(90, 357, 206, 110);
    private Rect CENTER_REGION = new Rect(543, 355, 177, 91);
    private Rect RIGHT_REGION = new Rect(970, 367, 210, 106);

    private final static Scalar GREEN = new Scalar(0,1,0);
    private final static Scalar YELLOW = new Scalar(1,1,0);

    private Mat output;

    private double x1, y1, x2, y2;

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
        x1 = LEFT_REGION.x;
        y1 = LEFT_REGION.y;
        x2 = LEFT_REGION.width;
        y2 = LEFT_REGION.height;
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

    public void editBoxes(double x1, double y1, double x2, double y2) {
        this.x1 = this.x1 + x1 < 0 ? 0 : this.x1 + x1;
        this.x2 = this.x2 + x2 > resolutionWidth ? resolutionWidth : this.x2 + x2;
        this.y1 = this.y1 + y1 < 0 ? 0 : this.y1 + y1;
        this.y2 = this.y2 + y2 > resolutionHeight ? resolutionHeight : this.y2 + y2;

        LEFT_REGION = new Rect((int) x1, (int) y1, (int) x2, (int) y2);
    }
}