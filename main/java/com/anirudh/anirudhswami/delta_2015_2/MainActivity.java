package com.anirudh.anirudhswami.delta_2015_2;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.Collections.*;

public class MainActivity extends AppCompatActivity {

    //String[] names = {"Anirudh","Abishek"};
    String[] names;
    Bitmap[] images;
    String order = "DESC";
    List<String> na = new ArrayList<String>();
    List<Bitmap> im = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Insert my code to put here
        try {
            readContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        names = new  String[na.size()];
        images= new Bitmap[im.size()];
        for(int i=0; i<na.size(); ++i){
            names[i] = na.get(i);

        }
        for(int i=0; i<im.size(); ++i){
            images[i]=im.get(i);
        }

        ListAdapter aniAdapter = new CustomAdapter(MainActivity.this,names,images);
        ListView contList = (ListView) findViewById(R.id.contList);
        contList.setAdapter(aniAdapter);

        ((Button)findViewById(R.id.ser_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numb = ((TextView) findViewById(R.id.ser_txt)).getText().toString();

                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(numb));
                String name = "?";

                ContentResolver contentResolver = getContentResolver();
                Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
                        ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

                try {
                    if (contactLookup != null && contactLookup.getCount() > 0) {
                        contactLookup.moveToNext();
                        name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
                    }
                } finally {
                    if (contactLookup != null) {
                        contactLookup.close();
                    }
                }
                if (name.equals("?")) {
                    Toast.makeText(MainActivity.this, "Contact Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Contact Found: " + name, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((Button)findViewById(R.id.ord)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(na);
                Collections.reverse(im);

                names = new  String[na.size()];
                images= new Bitmap[im.size()];
                for(int i=0; i<na.size(); ++i){
                    names[i] = na.get(i);

                }
                for(int i=0; i<im.size(); ++i){
                    images[i]=im.get(i);
                }

                ListAdapter aniAdapter = new CustomAdapter(MainActivity.this,names,images);
                ListView contList = (ListView) findViewById(R.id.contList);
                contList.setAdapter(aniAdapter);
            }
        });
    }

    public void readContacts() throws IOException {
        ContentResolver cr = getContentResolver();
        //Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);

        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1",
                null,
                "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") "+order);
        //String[] projection = new String[]{ContactsContract.Contacts._ID,
            //    ContactsContract.Contacts.DISPLAY_NAME,
          //      ContactsContract.RawContacts.ACCOUNT_TYPE};
        //Cursor cursor = cr.query(ContactsContract.Data.CONTENT_URI,null,null,null,ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");


        int ind = 0;
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //names[ind] = name;
                //This is for adding images

                Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id));
                InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), my_contact_Uri);
                BufferedInputStream buf = new BufferedInputStream(photo_stream);
                Bitmap my_btmp = BitmapFactory.decodeStream(buf);

                    im.add(my_btmp);
                    na.add(name);

                ind++;
                //buf.close();
                //photo_stream.close();
            }
        }
        cursor.close();
    }


    /*
    public void readContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int ind = 0;
        if (cur.getCount() > 0 ) {
            while (cur.moveToNext() ) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("phone" + phone);
                    }
                    pCur.close();


                    // get email and type

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                    }
                    emailCur.close();

                    // Get note.......
                    String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] noteWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                    Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                    if (noteCur.moveToFirst()) {
                        String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        System.out.println("Note " + note);
                    }
                    noteCur.close();

                    //Get Postal Address....

                    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);
                    while(addrCur.moveToNext()) {
                        String poBox = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        String street = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        String city = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        String state = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        String postalCode = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        String country = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        String type = addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();

                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();

                    // Get Organizations.........

                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();
                }

            }
        }
    }
    */

}
