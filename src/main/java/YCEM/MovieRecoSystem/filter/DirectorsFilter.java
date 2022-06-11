package filter;


import model.Movie;

public class DirectorsFilter implements Filter{
    private String[] myDirectors;
	
	public DirectorsFilter(String directorString) {
		myDirectors = directorString.split(",");
	}
	
	@Override
	public boolean satisfies(Movie movie) {
        String directors = movie.getDirector();
        for (String director : myDirectors){
            if (directors.contains(director.trim()))
            {
                return true;
            }
        }
        return false;
	}
}
