package YCEM.MovieRecoSystem.service;

public class CSVservice {

    public static void csvInit(){
        CSVHelper helper = new CSVHelper();

        String moviePath = "./src/main/resources/data/ratedmoviesfull.csv";
        String ratingPath = "/src/main/resources/data/ratings.csv";

        helper.csvToMovie(moviePath);
        helper.csvToRater(ratingPath);
        helper.csvToRatings(ratingPath);
    }
}
