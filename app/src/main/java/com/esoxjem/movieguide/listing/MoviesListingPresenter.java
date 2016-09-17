package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.entities.Movie;
import com.esoxjem.movieguide.entities.SortingSelectedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author arun
 */
public class MoviesListingPresenter implements IMoviesListingPresenter
{
    private WeakReference<IMoviesListingView> mMoviesView;
    private IMoviesListingInteractor mMoviesInteractor;
    private EventBus eventBus;

    public MoviesListingPresenter(IMoviesListingInteractor interactor, EventBus bus)
    {
        this.mMoviesInteractor = interactor;
        this.eventBus = bus;
    }

    @Override
    public void setView(IMoviesListingView view)
    {
        mMoviesView = new WeakReference<>(view);
    }

    @Override
    public Subscription displayMovies()
    {
        return mMoviesInteractor.fetchMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0()
                {
                    @Override
                    public void call()
                    {
                        mMoviesView.get().loadingStarted();
                    }
                })
                .subscribe(new Subscriber<List<Movie>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mMoviesView.get().loadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies)
                    {
                        mMoviesView.get().showMovies(movies);
                    }
                });
    }

    @Subscribe
    public void onEvent(SortingSelectedEvent event)
    {
        displayMovies();
    }

    @Override
    public void registerForEvents()
    {
        eventBus.register(this);
    }

    @Override
    public void unregisterForEvents()
    {
        eventBus.unregister(this);
    }
}
