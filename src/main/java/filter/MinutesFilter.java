package filter;


import model.Movie;

public class MinutesFilter implements Filter {
    private int min_mins;
    private int max_mins;

    public MinutesFilter(int min, int max) {
		min_mins = min;
        max_mins = max;
	}

    @Override
	public boolean satisfies(Movie movie) {
		return movie.getMinutes() >= min_mins && movie.getMinutes() <= max_mins;
	}


}
