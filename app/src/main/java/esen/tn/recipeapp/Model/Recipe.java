package esen.tn.recipeapp.Model;

public class Recipe {
    public  int id;
    public  String title, ingredient, description;
    public byte[] image;

    public Recipe(){

    }

    public Recipe(int id, String title, byte[] image, String ingredient, String description){
        this.id=id;
        this.title=title;
        this.image=image;
        this.ingredient=ingredient;
        this.description=description;
    }

    public Recipe(byte[] image, String title){
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {return image;}

    public void setImage(byte[] image) {this.image = image; }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
