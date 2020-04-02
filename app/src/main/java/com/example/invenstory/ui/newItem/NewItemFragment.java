package com.example.invenstory.ui.newItem;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewItemFragment extends Fragment {

    //These variables are needed by the camera
    private final int REQUEST_CAPTURE_IMAGE = 100;
    private final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";
    private String currentPhotoPath;

    /**
     * This integer represents the code that is used to process a gallery intent
     */
    private final int REQUEST_GALLERY = 123;

    /**
     * This is a Set of filePaths that currently contain the images to be stored
     * in the Item object.
     */
    private Set<String> filePaths;
    //private List<String> filePaths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Home.setFabOff();
        //TODO change Temp page id
        Home.setPageID(-1);
        View root = inflater.inflate(R.layout.fragment_new_item, container, false);

        FloatingActionButton saveButton = (FloatingActionButton) root.findViewById(R.id.saveItem);

        //TODO save Item
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectImage2();
                //startCameraIntent();
            }
        });

        filePaths = new HashSet<String>();
        //filePaths = new ArrayList<String>();
        return root;
    }

    /**
     * This method launches the camera so that the user can take a photo.
     */
    public void startCameraIntent(){
        dispatchTakePictureIntent();
    }

    //The two methods below create the photo and take the photo
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
            if (photoFile!=null) {
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


    /**
     * This method only lets you select one image, but you can use any photos like app
     */
    public void selectImage() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
        pickPictureIntent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        pickPictureIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(pickPictureIntent, REQUEST_GALLERY);
    }

    /**
     * This method only lets you select many images from their
     * gallery to be used when creating a new item.
     */
    private void selectImage2(){
        Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickPictureIntent.setType("image/*");

        //limits the image types the user can select
        String[] mimeTypes = {"image/jpeg", "image/png"};
        pickPictureIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        //lets user select multiple images
        pickPictureIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(pickPictureIntent, REQUEST_GALLERY);
    }

    @Override
    //TODO figure out how to change the resultCode, to see if the action worked.
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_GALLERY) {
            if(data.getClipData()!=null) {
                ClipData imagesInfo = data.getClipData();
                int numPics = imagesInfo.getItemCount();
                for (int i = 0; i < numPics; i++) {
                    Uri imageLoc = imagesInfo.getItemAt(i).getUri();
                    filePaths.add(imageLoc.toString());
                }
            }
            else if(data.getData()!=null){
                Uri imageLoc = data.getData();
                filePaths.add(imageLoc.toString());
            }
            Log.i("Tag", "These are the Uris \n" +filePaths.toString());
        }
        //This will add the current photo path if its valid
        else if(requestCode == REQUEST_CAPTURE_IMAGE){
            Uri imageLoc = Uri.fromFile(new File(currentPhotoPath));
            filePaths.add(imageLoc.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This method sets the image in an imageView to be the image provided by a Uri
     * @param img The imageView you wish to change the picture of
     * @param imageLoc the uri of the image wanted
     */
    private void setPhoto(ImageView img, Uri imageLoc){
        try{
            img.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageLoc));
        }
        catch(FileNotFoundException e){
            Log.i("TAG", "Sorry that file was not found");
        }
        catch(IOException e){
            Log.i("TAG", e.getMessage());
        }
    }
}
