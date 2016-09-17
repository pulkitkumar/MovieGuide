package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.entities.Movie;
import com.esoxjem.movieguide.entities.Review;
import com.esoxjem.movieguide.entities.Video;
import com.esoxjem.movieguide.favorites.FavoritesInteractor;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author arun
 */
public class MovieDetailsPresenter implements IMovieDetailsPresenter
{
    private WeakReference<IMovieDetailsView> mMovieDetailsView;
    private IMovieDetailsInteractor mMovieDetailsInteractor;
    private IFavoritesInteractor mFavoritesInteractor;

    public MovieDetailsPresenter(IMovieDetailsInteractor movieDetailsInteractor, IFavoritesInteractor favoritesInteractor)
    {
        mMovieDetailsInteractor = movieDetailsInteractor;
        mFavoritesInteractor = favoritesInteractor;
    }

    @Override
    public void setView(IMovieDetailsView view)
    {
        mMovieDetailsView = new WeakReference<>(view);
    }

    @Override
    public void showDetails(Movie movie)
    {
        mMovieDetailsView.get().showDetails(movie);
    }

    @Override
    public Subscription showTrailers(Movie movie)
    {
        return mMovieDetailsInteractor.getTrailers(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Video> videos)
                    {
                        mMovieDetailsView.get().showTrailers(videos);
                    }
                });
    }

    @Override
    public Subscription showReviews(Movie movie)
    {
        return mMovieDetailsInteractor.getReviews(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Review>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Review> reviews)
                    {
                        mMovieDetailsView.get().showReviews(reviews);
                    }
                });
    }

    @Override
    public void showFavoriteButton(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mMovieDetailsView.get().showFavorited();
        } else
        {
            mMovieDetailsView.get().showUnFavorited();
        }
    }

    @Override
    public void onFavoriteClick(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mFavoritesInteractor.unFavorite(movie.getId());
            mMovieDetailsView.get().showUnFavorited();
        } else
        {
            mFavoritesInteractor.setFavorite(movie);
            mMovieDetailsView.get().showFavorited();
        }
    }
}
