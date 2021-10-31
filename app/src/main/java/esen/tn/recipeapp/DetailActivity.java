package esen.tn.recipeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import esen.tn.recipeapp.Model.Recipe;

public class DetailActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    ImageView ViewPhoto;
    TextView ViewTitle,ViewPreparation,ViewIngredient;
    Recipe listItem;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        myDb = new DatabaseHelper(this);
        Toast.makeText(DetailActivity.this, ""+id, Toast.LENGTH_SHORT).show();

        ViewTitle = findViewById(R.id.ViewTitle);
        ViewPreparation = findViewById(R.id.ViewPreparation);
        ViewIngredient = findViewById(R.id.ViewIngredient);
        ViewPhoto = findViewById(R.id.BigImage);

        listItem = myDb.getRowData(id);
        Bitmap bmp = BitmapFactory.decodeByteArray(listItem.getImage(), 0, listItem.getImage().length);
        ViewPhoto.setImageBitmap(bmp);
        ViewTitle.setText(listItem.getTitle());
        ViewIngredient.setText(listItem.getIngredient());
        ViewPreparation.setText(listItem.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder diaBox = AskOption();
                diaBox.show();
                break;
            case R.id.update:
                Toast.makeText(this, "Update selected", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(DetailActivity.this,UpdateActivity.class);
                myIntent2.putExtra("id",id);
                startActivity(myIntent2);
                break;
        }
        return true;
    }


    private AlertDialog.Builder AskOption()
    {
        AlertDialog.Builder myDialogBox = new AlertDialog.Builder(this, R.style.AlertDialog);
        //set message, title, and icon
        myDialogBox.setTitle("Delete");
        myDialogBox.setMessage("Are you sure want to delete this recipe?");
        myDialogBox.setIcon(R.mipmap.trash_delete);

        myDialogBox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Integer deletedRows = myDb.deleteData(Integer.toString(id));
                        if(deletedRows > 0) {
                            Toast.makeText(DetailActivity.this, "Recipe Deleted", Toast.LENGTH_LONG).show();
                            Intent myIntent1 = new Intent(DetailActivity.this,MenuActivity.class);
                            startActivity(myIntent1);
                        }
                    }

                });
        myDialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        return myDialogBox;

    }
}
