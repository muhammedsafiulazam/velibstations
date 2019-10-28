package com.muhammedsafiulazam.tests;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

public class RecyclerViewAssertion implements ViewAssertion {
    private final Matcher<Integer> mMatcher;

    public static RecyclerViewAssertion withItemCount(int expectedCount) {
        return withItemCount(is(expectedCount));
    }

    public static RecyclerViewAssertion withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewAssertion(matcher);
    }

    private RecyclerViewAssertion(Matcher<Integer> matcher) {
        this.mMatcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), mMatcher);
    }
}