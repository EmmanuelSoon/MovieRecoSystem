package YCEM.MovieRecoSystem.controller;

import java.util.*;

import YCEM.MovieRecoSystem.filter.TrueFilter;
import YCEM.MovieRecoSystem.helper.UserForm;
import YCEM.MovieRecoSystem.helper.UserRating;
import YCEM.MovieRecoSystem.model.Movie;
import YCEM.MovieRecoSystem.model.Rater;
import YCEM.MovieRecoSystem.model.Rating;
import YCEM.MovieRecoSystem.service.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import YCEM.MovieRecoSystem.repo.MovieRepository;
import YCEM.MovieRecoSystem.repo.RaterRepository;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/recommend")
public class RecoController {
    
    @Autowired
    RaterRepository rRepo;
    @Autowired
    SearchEngine searchEngine;
    @Autowired
    MovieRepository mRepo;

    @RequestMapping("/index")
    public String index(){
        // a Welcome Page
        return "ratinglist";
    }

    @RequestMapping("/list")
    public String list(Model model){

        List<Movie> movieList = mRepo.findAll();
        Random rdn = new Random();

        //List of movies for user to rate
        List<UserRating> userRatings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int rdnNum = rdn.nextInt(movieList.size());
            Movie movie = movieList.get(rdnNum);
            movie.addPoster();
            userRatings.add(new UserRating(movie));
        }
        UserForm userForm = new UserForm();
        userForm.setUserRatings(userRatings);
        model.addAttribute("userForm", userForm);
        return "rating-form";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String saveRatings(@ModelAttribute UserForm userForm , BindingResult result, Model model){
        Rater me = new Rater();
        for (UserRating userRating : userForm.getUserRatings()) {
            me.addRating(new Rating(userRating.getMovieid(), userRating.getRating()));
        }
        List<Movie> recommendedList = searchEngine.getRecommendedMovie(me, 10, 10, 2, new TrueFilter());
        /*List<Rater> list = rRepo.findAll();*/
        for (Movie movie : recommendedList ) {
            movie.addPoster();
        }
        model.addAttribute("movies", recommendedList);
        return "ratinglist";
    }


}
