package com.acadgild.com.session3_assignment3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnUpload;
    static final int IMAGE_UPLOAD_RESULT = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpload = (Button)findViewById(R.id.btnUploadImage);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_UPLOAD_RESULT);
            }
        });
    }

        public void onActivityResult(int reqCode, int resCode, Intent data)
        {
            super.onActivityResult(reqCode,resCode,data);

            try
            {
                if(reqCode==IMAGE_UPLOAD_RESULT && resCode==RESULT_OK && null != data)
                {
                    Uri selectedImage = data.getData();

                    //Get Image from data
                    String[] filePathCol = {MediaStore.Images.Media.DATA};

                    //get the Cursor
                    Cursor c = getContentResolver().query(selectedImage,filePathCol,null, null, null);

                    //Move to first row
                    c.moveToFirst();

                    int colIndex = c.getColumnIndex(filePathCol[0]);
                    imgDecodableString = c.getString(colIndex);
                    c.close();

                    ImageView img1 = (ImageView)findViewById(R.id.imgView1);
                    img1.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    Toast.makeText(this,"Image uploaded successfully", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this,"Image was not selected", Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(this,"Something went wrong while uploading image...", Toast.LENGTH_LONG).show();
            }
        }


}
