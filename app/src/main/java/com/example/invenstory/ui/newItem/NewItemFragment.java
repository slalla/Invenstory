package com.example.invenstory.ui.newItem;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.example.invenstory.model.FileUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.invenstory.Home.setPageID;

/**
 * This class represents the Fragment that will be used to create
 * new Items to be added to collections
 */
public class NewItemFragment extends Fragment {

    /**
     * This integer represents the code that is used to process a picture taking intent
     * This value is currently temporary and may be updated in the future
     */
    private final int REQUEST_CAPTURE_IMAGE = 100;
    /**
     * This string represents unique date format that will be used for creating file names
     */
    private final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";
    /**
     * This string represents the filepath of the most current picture taken using the picture
     * taking intent.
     */
    private String currentPhotoPath;

    /**
     * This integer represents the code that is used to process a gallery intent
     * This value is currently temporary and may be updated in the future
     */
    private final int REQUEST_GALLERY = 123;

    /**
     * This is a Set of filePaths that currently contain the images to be stored
     * in the Item object.
     */
    private Set<String> filePaths;

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
                //selectImages();
                //startCameraIntent();
            }
        });

        filePaths = new HashSet<String>();
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
    private void selectImages(){
        Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickPictureIntent.setType("image/*");

        //limits the image types the user can select
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        pickPictureIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        //lets user select multiple images
        pickPictureIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(pickPictureIntent, REQUEST_GALLERY);
    }


    /**
     * This method is called after the startActvityForResult method is called.
     * This method currently acts on REQEUST_GALLERY and REQUEST_IMAGE_CAPTURE
     * It finds filepaths for images and saves them into the filePaths Set
     * @param requestCode the request being processed
     * @param resultCode the result of the process
     * @param data the intent this process is related to
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_GALLERY) {
            //multiple images are selected
            if(data.getClipData()!=null) {
                ClipData imagesInfo = data.getClipData();
                int numPics = imagesInfo.getItemCount();
                for (int i = 0; i < numPics; i++) {
                    //Gets the uri of the current image
                    Uri imageLoc = imagesInfo.getItemAt(i).getUri();
                    Log.i("", "Here is a path " + pathFinder(imageLoc));

                    //changes the uri to a filepath
                    String path = pathFinder(imageLoc);
                    File imgFile = new File(path);
                    //makes sure the file exists
                    if(imgFile.exists()){
                        filePaths.add(imgFile.getAbsolutePath());
                    }
                    //shows an error if the file doesn't exist
                    else{
                        Toast.makeText(getActivity(), "Sorry that image couldn't be found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //Single image is selected
            else if(data.getData()!=null){
                Uri imageLoc = data.getData();
                filePaths.add(imageLoc.toString());
                Log.i("", "Here is a path " + pathFinder(imageLoc));
                //changes the uri to a filepath
                String path = pathFinder(imageLoc);
                File imgFile = new File(path);
                //makes sure the file exists
                if(imgFile.exists()){
                    filePaths.add(imgFile.getAbsolutePath());
                }
                //shows an error if the file doesn't exist
                else{
                    Toast.makeText(getActivity(), "Sorry that image couldn't be found", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //This will add the current photo path if its valid
        else if(requestCode == REQUEST_CAPTURE_IMAGE){
            Uri imageLoc = Uri.fromFile(new File(currentPhotoPath));
            Log.i("", "Here is a path " + pathFinder(imageLoc));
            //changes the uri to a filepath
            String path = pathFinder(imageLoc);
            File imgFile = new File(path);
            //makes sure the file exists
            if(imgFile.exists()){
                filePaths.add(imgFile.getAbsolutePath());
            }
            //shows an error if the file doesn't exist
            else{
                Toast.makeText(getActivity(), "Sorry that image couldn't be found", Toast.LENGTH_SHORT).show();
            }
        }

        Log.i("Tag", "These are the filePaths \n" +filePaths.toString());
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This method sets the image in an ImageView to be the image provided by a Uri
     * @param img The ImageView you wish to change the picture of
     * @param imageLoc the uri of the image wanted
     */
    private void setPhoto(ImageView img, Uri imageLoc){
        try{
            img.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageLoc));
        }
        catch(FileNotFoundException e){
            Log.i("TAG", "Sorry that file was not found");
            Toast.makeText(getContext(), "That picture could not be found", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e){
            Log.i("TAG", e.getMessage());
        }
    }

    //TODO make this boolean so if the resource exists it will return true or false.
    /**
     * This method sets the image in an imageView to be the image provided at the location given
     * @param img the ImageView where the picture will be loaded
     * @param location the absolute file of the image resource
     */
    private void setPhoto(ImageView img, String location){
        File imgFile = new  File(location);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }
    }

    /**
     * This method finds the absolute file path of an image on and android device given a uri
     * This method makes use of the FileUtils class
     * @param uri the uri resource where the image is located
     * @return the file path of the requested image
     */
    private String pathFinder(Uri uri){
        FileUtils fileUtils = new FileUtils(getActivity());
        return fileUtils.getPath(uri);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyboard(getActivity());
    }
}
