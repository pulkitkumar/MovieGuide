package com.esoxjem.movieguide.sorting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.R;

import javax.inject.Inject;

/**
 * @author arun
 */
public class SortingDialogFragment extends DialogFragment implements ISortingDialogView,
        RadioGroup.OnCheckedChangeListener
{
    private RadioGroup mSortingOptionsGroup;
    @Inject ISortingDialogPresenter mSortingDialogPresenter;

    public static SortingDialogFragment newInstance()
    {
        return new SortingDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        BaseApplication.getAppComponent(getContext()).inject(this);
        mSortingDialogPresenter.setView(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.sorting_options, null);
        initViews(dialogView);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.sort_by);
        dialog.show();
        return dialog;
    }

    private void initViews(View dialogView)
    {
        mSortingOptionsGroup = (RadioGroup) dialogView.findViewById(R.id.sorting_group);
        mSortingDialogPresenter.setLastSavedOption();
        mSortingOptionsGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPopularChecked()
    {
        RadioButton popular = (RadioButton) mSortingOptionsGroup.findViewById(R.id.most_popular);
        popular.setChecked(true);
    }

    @Override
    public void setHighestRatedChecked()
    {
        RadioButton highestRated = (RadioButton) mSortingOptionsGroup.findViewById(R.id.highest_rated);
        highestRated.setChecked(true);
    }

    @Override
    public void setFavoritesChecked()
    {
        RadioButton favorites = (RadioButton) mSortingOptionsGroup.findViewById(R.id.favorites);
        favorites.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.most_popular:
                mSortingDialogPresenter.onPopularMoviesSelected();
                break;

            case R.id.highest_rated:
                mSortingDialogPresenter.onHighestRatedMoviesSelected();
                break;

            case R.id.favorites:
                mSortingDialogPresenter.onFavoritesSelected();
                break;
        }
    }

    @Override
    public void dismissDialog()
    {
        dismiss();
    }

}
