package YCEM.MovieRecoSystem.filter;

import YCEM.MovieRecoSystem.model.Movie;

public interface Filter {
	public boolean satisfies(Movie movie);
}
