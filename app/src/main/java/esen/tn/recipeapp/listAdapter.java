package esen.tn.recipeapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import esen.tn.recipeapp.Model.Recipe;

public class listAdapter extends ArrayAdapter<Recipe> {

    private Context mcontext;
    int mresource;

    private static class ViewHolder{
        TextView title;
        ImageView img;
    }

    public listAdapter(Context context, int resource, ArrayList<Recipe>objects) {
        super(context, resource,objects);
        mcontext = context;
        mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        byte[] img = getItem(position).getImage();
        String title = getItem(position).getTitle();
        Recipe recipe = new Recipe(img,title);
        View result;
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(mresource, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.ImageItem);
            holder.title = (TextView)convertView.findViewById(R.id.TitleItem);
            result = convertView;
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
            result = convertView;
        }
        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImage(),0,recipe.getImage().length));
        holder.title.setText(recipe.getTitle());
        return convertView;
    }
}
