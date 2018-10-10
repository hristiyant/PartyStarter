package com.hrisko.partystarter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsListActivity extends Activity {

    ListView mContactsList;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 942;
    Cursor mCursor;
    ArrayList<String> mContacts;
    String newLine = "\n";
    ImageView mContactPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        mContactsList = (ListView) findViewById(R.id.lv_contacts_list);
        mContactPhoto = (ImageView) findViewById(R.id.iv_contact_photo);

        //Method to start the service
        int permissionCheck = ContextCompat.checkSelfPermission(ContactsListActivity.this, Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            showContacts();
        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS
            );
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(
                this,
                R.layout.contacts_list_item,
                R.id.tv_contact_name,
                mContacts
        );

        mContactsList.setAdapter(mAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted -> show contacts
                showContacts();
            } else {
                Toast.makeText(
                        this,
                        "Cannot display contacts without permission",
                        Toast.LENGTH_LONG
                );
            }
        }
    }

    private void showContacts() {

        mCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        );

        mContacts = new ArrayList<>();

        while (mCursor.moveToNext()) {
            String contactName = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            mContacts.add("Name: " + contactName + newLine + "Phone Number: " + phoneNumber);
        }

        mCursor.close();
    }

    public void ivContactPhotoClicked(View view) {
        Intent intent = new Intent(this, ContactDetailsActivity.class);
        startActivity(intent);
    }


}
