package org.firstinspires.ftc.teamcode.ftclib.subsystems;

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

public class TeamPropSubsystem extends SubsystemBase {
    public static final int RESOLUTION_WIDTH = 1280;
    public static final int RESOLUTION_HEIGHT = 720;

    private final OpenCvWebcam webcam;
    private final TeamPropPipeline cameraPipeline;

    public TeamPropSubsystem(HardwareMap hardwareMap, Telemetry telemetry, AllianceColor allianceColor) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        cameraPipeline = new TeamPropPipeline(allianceColor, telemetry);

        final boolean[] webcamInit = {false};

        webcam.setPipeline(cameraPipeline);
        webcam.setMillisecondsPermissionTimeout(2500);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, OpenCvCameraRotation.UPRIGHT);
                telemetry.addData("Webcam", "Setup Finished");
                webcamInit[0] = true;
            }

            public void onError(int errorCode) {
                telemetry.speak("The web cam wasn't initialised correctly! Error code: " + errorCode);
                telemetry.addData("Webcam", "Setup Failed! Error code: " + errorCode);
                webcamInit[0] = true;
            }
        });

        //noinspection LoopConditionNotUpdatedInsideLoop,StatementWithEmptyBody
        while (!webcamInit[0]);
    }

    public StrikePos getStrikePos() {
        return cameraPipeline.getStrikePos();
    }

    public void editBoxes(double x1, double y1, double x2, double y2) {
        cameraPipeline.setboxes(x1, y1, x2, y2);
    }
}
