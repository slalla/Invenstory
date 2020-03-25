package com.example.invenstory.ui.newItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invenstory.Home;
import com.example.invenstory.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewItemFragment extends Fragment {

    //These variables are needed by the camera
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    public static final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";
    private String currentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Home.setFabOff();
        //TODO change Temp page id
        Home.setPageID(-1);
        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }

    /**
     * These three functions represent the launching of the camera to take a photo.
     */
    public void startCameraIntent(){
        dispatchTakePictureIntent();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there is a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Set the File object used to save the photo
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Home:", "Exception found when creating the photo save file");
            }

            // Take the picture if the File object was created successfully
            if (null != photoFile) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.invenstory.provider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Calling this method allows us to capture the return code
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(TIME_STAMP_FORMAT).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",   // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
