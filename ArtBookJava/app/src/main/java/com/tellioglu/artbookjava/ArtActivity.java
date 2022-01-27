package com.tellioglu.artbookjava;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tellioglu.artbookjava.databinding.ActivityArtBinding;

import java.io.ByteArrayOutputStream;

public class ArtActivity extends AppCompatActivity {

   private ActivityArtBinding binding;
   private ActivityResultLauncher<Intent> activityResultLauncher;
   private ActivityResultLauncher<String> permissionResultLauncher;
   private SQLiteDatabase database;
    Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        registerLauncher();

        database  = this.openOrCreateDatabase("Pictures",MODE_PRIVATE,null);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.equals("new")){
            binding.nameEditText.setText("");
            binding.artistEditText.setText("");
            binding.yearEditText.setText("");
            binding.imageView.setImageResource(R.drawable.download);
            binding.saveButton.setVisibility(View.VISIBLE);
        }
        else{
            int pictureId = intent.getIntExtra("pictureId",0);
            binding.saveButton.setVisibility(View.INVISIBLE);
            try{
                Cursor cursor = database.rawQuery("SELECT * FROM pictures WHERE id = ?", new String[]{String.valueOf(pictureId)});
                int pictureIx = cursor.getColumnIndex("pictureName");
                int pictureOwnerIx = cursor.getColumnIndex("pictureOwner");
                int yearIx = cursor.getColumnIndex("year");
                int imageIx = cursor.getColumnIndex("image");
                while(cursor.moveToNext()){
                    binding.nameEditText.setText(cursor.getString(pictureIx));
                    binding.artistEditText.setText(cursor.getString(pictureOwnerIx));
                    binding.yearEditText.setText(cursor.getString(yearIx));
                    byte[] bytes = cursor.getBlob(imageIx);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    binding.imageView.setImageBitmap(bitmap);
                }
                cursor.close();


            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    public void save(View view){
        String name = binding.nameEditText.getText().toString();
        String artistName = binding.artistEditText.getText().toString();
        String year = binding.yearEditText.getText().toString();
        Bitmap bitmap = makeSmallerImage(selectedImage, 300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                byte[] byteArray = outputStream.toByteArray();
                try{
                    database.execSQL("CREATE TABLE IF NOT EXISTS pictures( id INTEGER PRIMARY KEY, " +
                            "pictureName VARCHAR, pictureOwner VARCHAR, year VARCHAR, image BLOB)");
                    String sqlString = "INSERT INTO pictures (pictureName,pictureOwner,year,image) VALUES (?, ?, ?, ?)";
                    SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                    sqLiteStatement.bindString(1,name);
                    sqLiteStatement.bindString(2,artistName);
                    sqLiteStatement.bindString(3,year);
                    sqLiteStatement.bindBlob(4,byteArray);
                    sqLiteStatement.execute();

                }
                catch (Exception e){
                    Toast.makeText(ArtActivity.this, "Cant save image", Toast.LENGTH_LONG).show();

                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

    }
    public Bitmap makeSmallerImage(Bitmap bitmap, int maximumSize){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if(bitmapRatio>1){
            width = maximumSize;
            height = (int)(width / bitmapRatio);
        }
        else{
            height = maximumSize;
            width = (int)(height * bitmapRatio);
        }
        return bitmap.createScaledBitmap(bitmap,width,height,true);
    }
    public void selectImage(View view){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //request permission
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }
            else{
                permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            }
        }
        else{
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);

        }
    }
    private void registerLauncher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                  Intent intentFromResult =   result.getData();
                  if(intentFromResult!=null){
                      Uri uriImageData =   intentFromResult.getData();
                      try {
                        //  Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),uriImageData));
                          if(Build.VERSION.SDK_INT >= 28)
                          selectedImage = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),uriImageData));
                          else{
                              selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),uriImageData);
                          }
                          binding.imageView.setImageBitmap(selectedImage);

                      }
                      catch (Exception e){
                          Toast.makeText(ArtActivity.this, "Cant get image", Toast.LENGTH_LONG).show();
                      }


                  }
                }

            }
        });
        permissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    // permession granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);

                }
                else{
                    Toast.makeText(ArtActivity.this, "Permession Denied", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}