package filter;

import model.Movie;

public interface Filter {
	public boolean satisfies(Movie movie);
}
