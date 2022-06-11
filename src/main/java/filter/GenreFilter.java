package Filters;

import models.MovieDatabase;

public class GenreFilter implements Filter {
    private String myGenre;
	
	public GenreFilter(String genre) {
		myGenre = genre;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getGenres(id).toLowerCase().contains(myGenre.toLowerCase());
	}


}
