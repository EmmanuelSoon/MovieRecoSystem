package YCEM.MovieRecoSystem.filter;


import YCEM.MovieRecoSystem.model.Movie;

public class DirectorsFilter implements Filter{
    private String[] myDirectors;
	
	public DirectorsFilter(String directorString) {
		myDirectors = directorString.toLowerCase().split(",");
	}
	
	@Override
	public boolean satisfies(Movie movie) {
        String directors = movie.getDirector().toLowerCase();
        for (String director : myDirectors){
            if (directors.contains(director.trim()))
            {
                return true;
            }
        }
        return false;
	}
}
