package YCEM.MovieRecoSystem.model;

import lombok.ToString;

@ToString
public class Result {
    public boolean adult;
    public String backdrop_path;
    public int[] genre_ids;
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public int popularity;
    public String poster_path;
    public String release_date;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
}
