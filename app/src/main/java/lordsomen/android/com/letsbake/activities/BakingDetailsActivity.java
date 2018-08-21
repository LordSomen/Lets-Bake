package lordsomen.android.com.letsbake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.adapters.ViewPagerAdapter;
import lordsomen.android.com.letsbake.fragments.IngredientsFragment;
import lordsomen.android.com.letsbake.fragments.RecipeFragment;
import lordsomen.android.com.letsbake.pojos.BakingData;

public class BakingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.baking_details_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.baking_details_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_baking_details)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);
        ButterKnife.bind(this);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        RecipeFragment recipeFragment = new RecipeFragment();
        viewPagerAdapter.addFragment(ingredientsFragment, "Ingredients");
        viewPagerAdapter.addFragment(recipeFragment, "Recipes");
        Intent intent = getIntent();
        if (intent.hasExtra(BakingData.BAKINGDATA)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ingredientsFragment.setArguments(bundle);
                recipeFragment.setArguments(bundle);
            }
        }
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
