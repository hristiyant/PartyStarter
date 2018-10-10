package com.hrisko.partystarter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ContactDetailsActivity extends Activity {

    private static final int CAMERA_REQUEST = 111;
    ImageView mContactPhoto;
    Button mChangePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        mChangePhoto = (Button) findViewById(R.id.btn_change_photo);
        mContactPhoto = (ImageView) findViewById(R.id.iv_contact_photo);


    }

    public void btnChangePhotoClicked(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mContactPhoto.setImageBitmap(photo);

        }
    }

}
