package YCEM.MovieRecoSystem.filter;


import YCEM.MovieRecoSystem.model.Movie;

public class GenreFilter implements Filter {
    private String myGenre;
	
	public GenreFilter(String genre) {
		myGenre = genre;
	}
	
	@Override
	public boolean satisfies(Movie movie) {
		return movie.getGenres().toLowerCase().contains(myGenre.toLowerCase());
	}


}
