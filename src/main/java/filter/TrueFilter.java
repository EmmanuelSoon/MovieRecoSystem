package filter;

import model.Movie;

public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(Movie movie) {
		return true;
	}

}
