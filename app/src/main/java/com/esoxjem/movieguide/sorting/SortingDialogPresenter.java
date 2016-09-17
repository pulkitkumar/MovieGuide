package com.esoxjem.movieguide.sorting;

import com.esoxjem.movieguide.entities.SortType;
import com.esoxjem.movieguide.entities.SortingSelectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import static com.esoxjem.movieguide.entities.SortingSelectedEvent.DONE;

/**
 * @author arun
 */
public class SortingDialogPresenter implements ISortingDialogPresenter
{
    private WeakReference<ISortingDialogView> sortingDialogView;
    private ISortingDialogInteractor sortingDialogInteractor;
    private EventBus eventBus;

    public SortingDialogPresenter(ISortingDialogInteractor interactor, EventBus eventBus)
    {
        this.sortingDialogInteractor = interactor;
        this.eventBus = eventBus;
    }

    @Override
    public void setView(ISortingDialogView view)
    {
        this.sortingDialogView = new WeakReference<ISortingDialogView>(view);
    }

    @Override
    public void setLastSavedOption()
    {
        int selectedOption = sortingDialogInteractor.getSelectedSortingOption();

        if (selectedOption == SortType.MOST_POPULAR.getValue())
        {
            sortingDialogView.get().setPopularChecked();
        } else if (selectedOption == SortType.HIGHEST_RATED.getValue())
        {
            sortingDialogView.get().setHighestRatedChecked();
        } else
        {
            sortingDialogView.get().setFavoritesChecked();
        }
    }

    @Override
    public void onPopularMoviesSelected()
    {
        sortingDialogInteractor.setSortingOption(SortType.MOST_POPULAR);
        sortingDialogView.get().dismissDialog();
        eventBus.postSticky(new SortingSelectedEvent(DONE));
    }

    @Override
    public void onHighestRatedMoviesSelected()
    {
        sortingDialogInteractor.setSortingOption(SortType.HIGHEST_RATED);
        sortingDialogView.get().dismissDialog();
        eventBus.postSticky(new SortingSelectedEvent(DONE));
    }

    @Override
    public void onFavoritesSelected()
    {
        sortingDialogInteractor.setSortingOption(SortType.FAVORITES);
        sortingDialogView.get().dismissDialog();
        eventBus.postSticky(new SortingSelectedEvent(DONE));
    }
}
