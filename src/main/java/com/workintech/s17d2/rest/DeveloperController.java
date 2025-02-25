package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.DeveloperFactory;
import com.workintech.s17d2.model.JuniorDeveloper;
import com.workintech.s17d2.model.SeniorDeveloper;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController{



    public Map<Integer, Developer> developers; //testleri geçsin diye public
    private Taxable taxable;

    @Autowired
    public DeveloperController( Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
        this.developers.put(1, new SeniorDeveloper(1, "ahmet", 1000d));
    }


    @GetMapping
    public List<Developer> getDevelopers() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getDeveloper(@PathVariable("id") int id) {
        Developer founded = this.developers.get(id);
        if(founded == null) {
            return new DeveloperResponse(null, HttpStatus.NOT_FOUND, id+ "no exist");
        }
        return new DeveloperResponse(founded, HttpStatus.OK, "Accepted");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse addDeveloper(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);

        if (Objects.nonNull(createdDeveloper)/*developers.containsKey(developer.getId())*/ ) {
            developers.put(createdDeveloper.getId(), createdDeveloper);
        }
        return new DeveloperResponse(createdDeveloper, HttpStatus.CREATED, "Created");


    }

    @PutMapping("/{id}")
    public DeveloperResponse setDevelopers(@PathVariable("id") int id,
                                   @RequestBody Developer developer) {
        developer.setId(id);
        Developer updatedDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        this.developers.put(id, updatedDeveloper);
        return new DeveloperResponse(updatedDeveloper, HttpStatus.OK, "Updated");
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse deleteDevelopers(@PathVariable("id") int id){
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper, HttpStatus.NO_CONTENT, "Deleted");
    }
}
