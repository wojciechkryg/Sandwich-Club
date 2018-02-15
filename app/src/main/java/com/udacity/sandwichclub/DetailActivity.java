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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final char NEW_LINE = '\n';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setupOrigin(sandwich.getPlaceOfOrigin());
        setupDescription(sandwich.getDescription());
        setupIngredients(sandwich.getIngredients());
        setupAlsoKnown(sandwich.getAlsoKnownAs());
    }

    private void setupOrigin(String origin) {
        TextView originTv = findViewById(R.id.origin_tv);
        originTv.setText(origin);
    }

    private void setupDescription(String description) {
        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(description);
    }

    private void setupIngredients(List<String> ingredients) {
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        for (String ingredient : ingredients) {
            ingredientsTv.append(ingredient + NEW_LINE);
        }
    }

    private void setupAlsoKnown(List<String> alsoKnownAs) {
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        for (String name : alsoKnownAs) {
            alsoKnownTv.append(name + NEW_LINE);
        }
    }
}
