package esen.tn.recipeapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    DatabaseHelper myDb;
    EditText editText_title;
    EditText editText_ingredient;
    EditText editText_how;
    ImageView imgViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btImport = (Button) findViewById(R.id.btImport);
        btImport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            ImageView imgViewPhoto = (ImageView) findViewById(R.id.imgViewPhoto);
            imgViewPhoto.setImageURI(selectedImage);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.validate) {
            myDb = new DatabaseHelper(this);
            editText_title = (EditText)findViewById(R.id.editText_title);
            editText_ingredient = (EditText)findViewById(R.id.editText_ingredient);
            editText_how = (EditText)findViewById(R.id.editText_how);
            imgViewPhoto = (ImageView)findViewById(R.id.imgViewPhoto);
            AddData();
        }
        return true;
    }

   public  void AddData() {

       String title = editText_title.getText().toString();
       String ingredient = editText_ingredient.getText().toString();
       String preparation = editText_how.getText().toString();

       Resources res = getResources();
       Drawable drawable = res.getDrawable(R.mipmap.camera);

       Bitmap photo = ((BitmapDrawable)imgViewPhoto.getDrawable()).getBitmap();
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
       byte[] bArray = bos.toByteArray();

       if (TextUtils.isEmpty(title)){
           editText_title.setError("Input title");
           return;
       }
       if (TextUtils.isEmpty(ingredient)){
           editText_ingredient.setError("Input ingredient");
           return;
       }
       if (TextUtils.isEmpty(preparation)){
           editText_how.setError("Input preparation");
           return;
       }

       if (imgViewPhoto.getDrawable().getConstantState().equals(imgViewPhoto.getResources().getDrawable(R.mipmap.camera).getConstantState())){
           Toast.makeText(this, "NO PICTURE TAKEN. CLICK ON THE RED BUTTON TO CHOOSE A PICTURE.", Toast.LENGTH_LONG).show();
           return;
       }

       boolean isInserted = myDb.insertData(title,bArray, ingredient, preparation);

        if(isInserted == true){
            Toast.makeText(AddActivity.this,"A new recipe has been added",Toast.LENGTH_LONG).show();
            Intent homepage = new Intent(AddActivity.this, MenuActivity.class);
            startActivity(homepage);
        } else
        {
            Toast.makeText(AddActivity.this,"No recipe added",Toast.LENGTH_LONG).show();
        }
    }
}

