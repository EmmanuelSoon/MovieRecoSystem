package YCEM.MovieRecoSystem.filter;
import YCEM.MovieRecoSystem.model.Movie;

import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;
    
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(Movie movie) {
        for(Filter f : filters) {
            if (! f.satisfies(movie)) {
                return false;
            }
        }
        return true;
    }

}
