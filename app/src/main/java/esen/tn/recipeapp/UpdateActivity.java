package esen.tn.recipeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import esen.tn.recipeapp.Model.Recipe;

public class UpdateActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    ImageView UpdateBigImage;
    EditText ViewTitleUpdate,ViewIngredientUpdate,ViewPreparationUpdate;
    int id;
    Recipe ItemForUpdate;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        Toast.makeText(UpdateActivity.this, ""+id, Toast.LENGTH_SHORT).show();

        UpdateBigImage = findViewById(R.id.UpdateBigImage);
        ViewTitleUpdate = findViewById(R.id.ViewTitleUpdate);
        ViewIngredientUpdate = findViewById(R.id.ViewIngredientUpdate);
        ViewPreparationUpdate = findViewById(R.id.ViewPreparationUpdate);

        ItemForUpdate = myDb.getRowData(id);
        Bitmap bmp = BitmapFactory.decodeByteArray(ItemForUpdate.getImage(), 0, ItemForUpdate.getImage().length);
        UpdateBigImage.setImageBitmap(bmp);
        ViewTitleUpdate.setText(ItemForUpdate.getTitle());
        ViewIngredientUpdate.setText(ItemForUpdate.getIngredient());
        ViewPreparationUpdate.setText(ItemForUpdate.getDescription());

        UpdateBigImage.setOnClickListener(new View.OnClickListener() {

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

            UpdateBigImage = findViewById(R.id.UpdateBigImage);
            UpdateBigImage.setImageURI(selectedImage);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm:
                Bitmap photo = ((BitmapDrawable)UpdateBigImage.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] byteArray = bos.toByteArray();
                boolean isUpdate = myDb.updateData(Integer.toString(id),ViewTitleUpdate.getText().toString(),byteArray, ViewIngredientUpdate.getText().toString(),
                        ViewPreparationUpdate.getText().toString());
                if(isUpdate == true)
                {
                    Intent myIntent = new Intent(UpdateActivity.this,DetailActivity.class);
                    startActivity(myIntent);
                }
                break;

            case R.id.cancel:
                Toast.makeText(UpdateActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(UpdateActivity.this,DetailActivity.class);
                startActivity(myIntent);
                break;
        }
        return true;
    }
}
