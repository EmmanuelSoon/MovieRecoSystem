package Filters;
import models.MovieDatabase;


public class DirectorsFilter implements Filter{
    private String[] myDirectors;
	
	public DirectorsFilter(String directorString) {
		myDirectors = directorString.split(",");
	}
	
	@Override
	public boolean satisfies(String id) {
		boolean check = false;
        String movieDirectors = MovieDatabase.getDirector(id);
        for (String curr_director : myDirectors){
                if (movieDirectors.contains(curr_director.trim()))
                {
                    return true;
                }
        }
        return check;
	}
}
