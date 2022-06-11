package controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/recommend")
public class RecoController {
    
    @Autowired
    RecomRepository Rrepo;

    @RequestMapping("/list")
    public String list(Model model){

        return "ratinglist";
    }

    @RequestMapping("/add")
    public String addForm(Model model){
        return "rating-form";
    }

    @RequestMapping("/save")
    public String saveRatings(){

        return "forward:/recommend/list";
    }


}
