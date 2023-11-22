package org.firstinspires.ftc.teamcode.ftclib.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.StrikePos;
import org.firstinspires.ftc.teamcode.visionpipelines.TeamPropPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;

public class TeamPropSubsystem extends SubsystemBase {
    public static final int RESOLUTION_WIDTH = 1280;
    public static final int RESOLUTION_HEIGHT = 720;

    private final OpenCvWebcam webcam;
    private final TeamPropPipeline cameraPipeline;
    private final ArrayList<StrikePos> samples;

    public TeamPropSubsystem(HardwareMap hardwareMap, Telemetry telemetry, AllianceColor allianceColor) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        cameraPipeline = new TeamPropPipeline(allianceColor, telemetry, RESOLUTION_HEIGHT, RESOLUTION_WIDTH);

        webcam.setPipeline(cameraPipeline);
        webcam.setMillisecondsPermissionTimeout(2500);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(webcam, 0);  // TODO STOPSHIP: 11/21/2023 Competition Illegal
                telemetry.addData("Webcam", "Setup Finished");
            }

            public void onError(int errorCode) {
                telemetry.speak("The web cam wasn't initialised correctly! Error code: " + errorCode);
                telemetry.addData("Webcam", "Setup Failed! Error code: " + errorCode);
            }
        });

        samples = new ArrayList<>();
    }

    public StrikePos getStrikePos() {
        return cameraPipeline.getStrikePos();
    }

    public void clearPos() {
        samples.clear();
    }

    public void samplePos() {
        samples.add(getStrikePos());
    }

    public int sampleCount() {
        return samples.size();
    }

    public StrikePos getAveragePos() {
        int leftCount = 0;
        int centerCount = 0;
        int rightCount = 0;

        for (StrikePos sample : samples) {
            if (sample == StrikePos.LEFT) {
                leftCount++;
            } else if (sample == StrikePos.CENTER) {
                centerCount++;
            } else if (sample == StrikePos.RIGHT) {
                rightCount++;
            }
        }

        int max = leftCount;
        StrikePos strikePos = StrikePos.LEFT;
        if (centerCount > max) {
            max = centerCount;
            strikePos = StrikePos.CENTER;
        }
        if (rightCount > max) {
            strikePos = StrikePos.RIGHT;
        }

        return strikePos;
    }

    public void editBoxes(double x1, double y1, double x2, double y2) {
        cameraPipeline.setboxes(x1, y1, x2, y2);
    }
}
