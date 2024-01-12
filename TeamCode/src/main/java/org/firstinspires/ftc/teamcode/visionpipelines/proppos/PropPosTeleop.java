package org.firstinspires.ftc.teamcode.visionpipelines.proppos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@SuppressWarnings("unused")
@TeleOp
public class PropPosTeleop extends OpMode {
    OpenCvWebcam webcam;
    TeamPropPosPipeline pipeline;

    private final int RESOLUTION_HEIGHT = 720, RESOLUTION_WIDTH = 1280;

    private double timestamp;

    private double x, y, w, h;

    @Override
    public void init() {
        x = 90;
        y = 357;
        w = 206;
        h = 110;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeline = new TeamPropPosPipeline(telemetry, RESOLUTION_HEIGHT, RESOLUTION_WIDTH);
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(RESOLUTION_WIDTH,RESOLUTION_HEIGHT, OpenCvCameraRotation.UPSIDE_DOWN);
                telemetry.addData("Webcam", "Initalized successfully");
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Webcam", String.format("Error Code: %s", errorCode));
            }
        });
    }

    @Override
    public void start() {
        timestamp = getRuntime();
    }

    @Override
    public void loop() {
        double scalar = 15;

        double deltatime = getRuntime() - timestamp;
        telemetry.addData("deltaTime", deltatime);
        x += (gamepad1.dpad_right ? 1 : 0) * deltatime * scalar;
        x += (gamepad1.dpad_left ? -1 : 0) * deltatime * scalar;
        y += (gamepad1.dpad_down ? 1 : 0) * deltatime * scalar;
        y += (gamepad1.dpad_up ? -1 : 0) * deltatime * scalar;
        w += (gamepad1.b ? 1 : 0) * deltatime * scalar;
        w += (gamepad1.x ? -1 : 0) * deltatime * scalar;
        h += (gamepad1.a ? 1 : 0) * deltatime * scalar;
        h += (gamepad1.y ? -1 : 0) * deltatime * scalar;

        x = (x < 0 ? 0 : x);
        x = (x > RESOLUTION_WIDTH ? RESOLUTION_WIDTH : x);

        y = (y < 0 ? 0 : y);
        y = (y > RESOLUTION_HEIGHT ? RESOLUTION_HEIGHT : y);

        w = (w < 0 ? 0 : w);
        w = (w + x > RESOLUTION_WIDTH ? RESOLUTION_WIDTH - x : w);

        h = (h < 0 ? 0 : h);
        h = (h + y > RESOLUTION_HEIGHT ? RESOLUTION_HEIGHT - y : h);

        pipeline.setboxes(new Rect((int) x, (int) y, (int) w, (int) h));

        timestamp = getRuntime();

    }
}
