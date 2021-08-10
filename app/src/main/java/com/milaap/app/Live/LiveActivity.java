package com.milaap.app.Live;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.milaap.app.Main.PaymentActivity;
import com.milaap.app.Matched.Matched_Activity;
import com.milaap.app.Utils.CameraPreview;
import com.milaap.app.Utils.TopNavigationViewHelper;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityLiveBinding;

import java.util.ArrayList;

public class LiveActivity extends AppCompatActivity {
    private static final String TAG = "LiveActivity";
    private Context mContext = LiveActivity.this;
    ActivityLiveBinding binding;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private ImageView capture, switchCamera,next,mFlash,mFlashoff,close;
    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    private boolean frontCamera = false;
    public static Bitmap bitmap;
    private RecyclerView mRecycleview;
    ArrayList<String> imgUrls;
    //private List<Camera.Size> mSupportedPreviewSizes;
    //  ZoomControls zoomControls;
    int currentZoomLevel = 0, maxZoomLevel = 0;
    private static  final int FOCUS_AREA_SIZE= 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCompat.requestPermissions(LiveActivity.this,
                new String[]{Manifest.permission.CAMERA}, 123);
        myContext = this;
        mCamera =  Camera.open();
       // int orientation = detectCameraDisplayOrientation(this, new Camera.CameraInfo());
        mCamera.setDisplayOrientation(90);
        mCamera.enableShutterSound(true);
//        Camera.Parameters params = mCamera.getParameters();
//              params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
//// ... set other parameters
//       // camera.setParameters(params);
//        mCamera.setParameters(params);


        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.setFocusableInTouchMode(true);



        cameraPreview.addView(mPreview);
        releaseCamera();
        chooseCamera();
        setupTopNavigationView();
        //setVideo();
        binding.startbtn.setOnClickListener(v -> {
            setVideo();
        });
        binding.videobtn.setOnClickListener(v -> {
            startActivity(new Intent(this, PaymentActivity.class));
        });

    }

    private void setVideo() {

        binding.demoimage.setVisibility(View.GONE);
        binding.vvone.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        binding.vvone.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(false);
            }
        });
        binding.vvone.start();

        binding.vvtwo.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        binding.vvtwo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(false);
            }
        });
        binding.vvtwo.start();


        binding.vvthree.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        binding.vvthree.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(false);
            }
        });
        binding.vvthree.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                binding.demoimage.setVisibility(View.VISIBLE);

            }
        }, 30000);
    }

    public void chooseCamera() {
        //if the camera preview is the front
        Log.d(TAG, "choose camera: camerafront "+cameraFront);
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
               // mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
              //  mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }
    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                frontCamera=false;
                break;

            }

        }
        Log.d(TAG, "find camera: camerafront "+cameraFront);
        Log.d(TAG, "find camera: frontCamera "+frontCamera);
        return cameraId;
    }
    private int findFrontFacingCamera() {

        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                frontCamera=true;
                break;
            }
        }
        Log.d(TAG, "find camera: camerafront "+cameraFront);
        Log.d(TAG, "find camera: frontCamera "+frontCamera);
        return cameraId;

    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
        //  BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        BottomNavigationView tvEx = findViewById(R.id.bottomNavigationView);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
}