package com.example.invenstory.ui.send;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.invenstory.Home;
import com.example.invenstory.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    /**
     * This is the carouselView that is in the fragment
     * https://github.com/sayyam/carouselview documentation found here
     */
    private CarouselView carouselView;

    /**
     * This is a sample set of images from Sean's local device. This will not work
     * Unless the same files exist on a users device. To find file paths on your device,
     * uncomment out selectImages on the NewItemFragment and click the button
     * Filepaths will be printed in a log message.
     */
    private String[] sampleImages={"/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200127-WA0000.jpg",
            "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200130-WA0000.jpg",
            "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200128-WA0002.jpeg",
            "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200126-WA0000.jpg",
            "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200129-WA0000.jpg",
            "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20200128-WA0000.jpeg"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home.setFabOff();
        Home.setPageID(2);

        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);

        carouselView = root.findViewById(R.id.carouselView);

        //Sets the images in the view
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                File imgFile = new  File(sampleImages[position]);

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }
            }
        };
        //Sets the number of pages on the carousel view.
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}