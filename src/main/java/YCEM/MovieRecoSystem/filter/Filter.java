package YCEM.MovieRecoSystem.filter;

import YCEM.MovieRecoSystem.model.Movie;
import lombok.Builder;

public interface Filter {
	public boolean satisfies(Movie movie);
}
