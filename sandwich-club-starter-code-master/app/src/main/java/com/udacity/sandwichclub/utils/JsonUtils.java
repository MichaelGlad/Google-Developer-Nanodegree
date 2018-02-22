package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich;
        ArrayList<String> alsoKnownAsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();
        JSONObject rootJsonObject;
        JSONObject nameJsonObject;
        JSONArray alsoKnownAsJsonArray;
        JSONArray ingredientsJsonArray;

        try {
            //Get root Json object
            rootJsonObject = new JSONObject(json);
            sandwich = new Sandwich();

            //Get name Json object include mainName and alsoKnownAs
            nameJsonObject = rootJsonObject.getJSONObject("name");
            sandwich.setMainName(nameJsonObject.getString("mainName"));

            //Get the strings from Json onject and put it to Sandwich object respectively
            sandwich.setPlaceOfOrigin(rootJsonObject.optString("placeOfOrigin", "No origin place available"));
            sandwich.setDescription(rootJsonObject.optString("description", "No description available"));
            sandwich.setImage(rootJsonObject.getString("image"));

            //Get the list of alsoKnownAs from Json array and put it to Sandwich object respectively
            alsoKnownAsJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
            if (alsoKnownAsJsonArray == null) {
                alsoKnownAsList.add("");
            } else {
                for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
                }
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);

            //Get the list of ingredients from Json array and put it to Sandwich object respectively

            ingredientsJsonArray = rootJsonObject.getJSONArray("ingredients");
            if (ingredientsJsonArray == null || ingredientsJsonArray.get(0) == "") {
                ingredientsList.add("Sorry but we don't have the ingredients");

            } else {
                for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                    ingredientsList.add(ingredientsJsonArray.getString(i));
                }
            }
            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e) {
            return null;
        }

        return sandwich;
    }
}
