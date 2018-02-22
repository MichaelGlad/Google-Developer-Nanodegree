package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich;

    TextView tvMainName;
    TextView tvAlsoKnownAs;
    TextView tvPlaceOfOrigin;
    TextView tvIngredients;
    TextView tvDescription;
    ImageView sandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        sandwichIv = findViewById(R.id.iv_image);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String alsoKnownAsString;
        String ingredientsString;

        tvMainName = findViewById(R.id.tv_name_field);
        tvAlsoKnownAs = findViewById(R.id.tv_also_known_field);
        tvPlaceOfOrigin = findViewById(R.id.tv_place_of_origin_field);
        tvDescription = findViewById(R.id.tv_description_field);
        tvIngredients = findViewById(R.id.tv_ingredients_field);

        alsoKnownAsString = transferListToString(sandwich.getAlsoKnownAs());
        ingredientsString = transferListToString(sandwich.getIngredients());

        if (sandwich.getMainName() != null) {
            tvMainName.setText(sandwich.getMainName());
        }

        if (alsoKnownAsString != null) {
            tvAlsoKnownAs.setText(alsoKnownAsString);
        }

        if (sandwich.getPlaceOfOrigin() != null) {
            tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        if (ingredientsString != null) {
            tvIngredients.setText(ingredientsString);
        }

        if (sandwich.getDescription() != null) {
            tvDescription.setText(sandwich.getDescription());
        }


    }

    // Transfer from list to string
    private String transferListToString(List<String> stringList) {

        StringBuilder permanentString = new StringBuilder();
        if (stringList != null) {

            for (int i = 0; i < stringList.size(); i++) {
                if (i != 0) {
                    permanentString.append(", ");
                }
                permanentString.append(stringList.get(i));
            }
        } else return null;

        return permanentString.toString();
    }
}
