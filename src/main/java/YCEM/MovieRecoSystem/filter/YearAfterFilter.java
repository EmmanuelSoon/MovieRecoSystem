package YCEM.MovieRecoSystem.filter;

import YCEM.MovieRecoSystem.model.Movie;

public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	@Override
	public boolean satisfies(Movie movie) {
		return movie.getYearPublished() >= myYear;
	}

}
