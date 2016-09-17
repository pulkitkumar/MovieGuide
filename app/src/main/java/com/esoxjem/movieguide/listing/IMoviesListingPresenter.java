package com.esoxjem.movieguide.listing;

import rx.Subscription;

/**
 * @author arun
 */
public interface IMoviesListingPresenter
{
    Subscription displayMovies();
    void setView(IMoviesListingView view);
    void registerForEvents();
    void unregisterForEvents();
}
