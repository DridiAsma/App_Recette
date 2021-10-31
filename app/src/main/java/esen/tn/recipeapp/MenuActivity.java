package esen.tn.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import esen.tn.recipeapp.Model.Recipe;

public class MenuActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private ArrayList<Recipe> listItem;
    ArrayList<Recipe> searcheItem;
    ListAdapter adapter;
    ListView menuList;
    Recipe listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        myDb = new DatabaseHelper(this);

        listItem = myDb.getAllImg();
        menuList = findViewById(R.id.menuList);
        adapter = new listAdapter(MenuActivity.this,R.layout.custom_list,listItem);
        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView GetText = (TextView) view.findViewById(R.id.TitleItem);
                String text = GetText.getText().toString();
                listId = myDb.getIDbyName(text);
                Toast.makeText(MenuActivity.this, ""+listId.getId(), Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(view.getContext(),DetailActivity.class);
                myIntent.putExtra("id",listId.getId());
                startActivityForResult(myIntent,position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        searcheItem = myDb.getRecipeByName(newText);

                        ListAdapter adapter = new listAdapter(MenuActivity.this,R.layout.custom_list,searcheItem);
                        menuList.setAdapter(adapter);

                        return true;
                    }
                });
                break;
            case R.id.add:
                Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MenuActivity.this,AddActivity.class);
                startActivity(myIntent);
                break;
        }
        return true;
    }

}
