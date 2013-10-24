package com.zljysoft.SmartDoctor.doctor;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;
import net.sourceforge.zbar.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-21
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class SearchPaientQRCodeActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            SearchPaientQRCodeFragment list = new SearchPaientQRCodeFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class SearchPaientQRCodeFragment  extends Fragment {
        static {
            System.loadLibrary("iconv");
        }
        private Camera mCamera;
        private CameraPreview mPreview;
        private Handler autoFocusHandler;
        ImageScanner scanner;

        private boolean barcodeScanned = false;
        private boolean previewing = true;

        TextView mPatientContentView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.activity_doctor_search_patient_qrcode, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            autoFocusHandler = new Handler();
            mCamera = getCameraInstance();
            /* Instance barcode scanner */
            scanner = new ImageScanner();
            scanner.setConfig(0, Config.X_DENSITY, 3);
            scanner.setConfig(0, Config.Y_DENSITY, 3);

            mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
            FrameLayout preview = (FrameLayout)getActivity().findViewById(R.id.cameraPreview);
            preview.addView(mPreview);

            mPatientContentView = (TextView)getActivity().findViewById(R.id.tv_scanText);

            getActivity().findViewById(R.id.btn_rescan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        mPatientContentView.setText("扫描中...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                        getActivity().findViewById(R.id.ll_buttonslayout).setVisibility(View.INVISIBLE);
                    }
                }
            });
            getActivity().findViewById(R.id.btn_visitpatient).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo
                }
            });
        }
        public void onPause() {
            super.onPause();
            releaseCamera();
        }

        /** A safe way to get an instance of the Camera object. */
        public static Camera getCameraInstance(){
            Camera c = null;
            try {
                c = Camera.open();
            } catch (Exception e){
            }
            return c;
        }

        private void releaseCamera() {
            if (mCamera != null) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.release();
                mCamera = null;
            }
        }

        private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

        Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);

                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();

                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                        mPatientContentView.setText("扫描结果： " + sym.getData());
                        barcodeScanned = true;
                        getActivity().findViewById(R.id.ll_buttonslayout).setVisibility(View.VISIBLE);

                    }
                }
            }
        };

        // Mimic continuous auto-focusing
        Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
    }

}