package com.esoxjem.movieguide.entities;

/**
 * Created by pulkitkumar on 17/09/16.
 */
public class SortingSelectedEvent
{
    public static final String DONE = "done";

    private String sortingSelection;

    public SortingSelectedEvent(String sortinSelection)
    {
        this.sortingSelection = sortinSelection;
    }

    public String getSortingSelection()
    {
        return sortingSelection;
    }
}
