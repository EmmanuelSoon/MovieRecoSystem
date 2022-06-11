package Filters;

import models.MovieDatabase;

public class MinutesFilter implements Filter {
    private int min_mins;
    private int max_mins;

    public MinutesFilter(int min, int max) {
		min_mins = min;
        max_mins = max;
	}

    @Override
	public boolean satisfies(String id) {
		return MovieDatabase.getMinutes(id) >= min_mins && MovieDatabase.getMinutes(id) <= max_mins;
	}


}
