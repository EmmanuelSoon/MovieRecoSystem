package YCEM.MovieRecoSystem.controller;

import java.util.*;

import YCEM.MovieRecoSystem.helper.UserRating;
import YCEM.MovieRecoSystem.model.Movie;
import YCEM.MovieRecoSystem.service.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import YCEM.MovieRecoSystem.repo.MovieRepository;
import YCEM.MovieRecoSystem.repo.RaterRepository;


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
        return "index";
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
        model.addAttribute("userRatings", userRatings);
        return "rating-form";
    }

    @RequestMapping("/submit")
    public String saveRatings(@ModelAttribute List<UserRating> list ,Model model){
        return "";
    }


}
