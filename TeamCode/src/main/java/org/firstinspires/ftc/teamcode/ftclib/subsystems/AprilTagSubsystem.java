package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;

public class AprilTagSubsystem extends SubsystemBase {
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private Telemetry telemetry;

    public AprilTagSubsystem(HardwareMap hMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        //aprilTag.setDecimation(3);

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hMap.get(WebcamName.class, "Webcam 1"));

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(true);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    @Override
    public void periodic() {
        ArrayList<AprilTagDetection> detections = aprilTag.getDetections();
        telemetry.addData("AprilTag Detection Count", aprilTag.getDetections().size());
        for (int i = 0; i < detections.size(); i++) {
            AprilTagDetection detection = detections.get(i);
            telemetry.addData(String.format("Detection %d id", i), detection.id);
            telemetry.addData(String.format("Detection %d bearing", i), detection.ftcPose.bearing);
            telemetry.addData(String.format("Detection %d range", i), detection.ftcPose.range);
            telemetry.addData(String.format("Detection %d fieldPos magnitude", i), detection.metadata.fieldPosition.magnitude());
        }
    }
}
