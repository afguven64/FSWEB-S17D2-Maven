package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DeveloperController{



    public Map<Integer, Developer> developers;
    private Taxable tax;

    @Autowired
    public DeveloperController( Taxable tax) {
        this.tax = tax;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }


    @GetMapping("/developers")
    public List<Developer> getDevelopers() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloper(@PathVariable("id") int id) {

        return developers.get(id);
    }

    @PostMapping("/developers")
    public void addDeveloper(@RequestBody Developer developer) {
        if (developers.containsKey(developer.getId()))
            return;


        double totalSalary = developer.getSalary();

        totalSalary = switch (developer.getExperience()) {
            case JUNIOR -> totalSalary - totalSalary * tax.getSimpleTaxRate() / 100.0;
            case MID -> totalSalary - totalSalary * tax.getMiddleTaxRate() / 100.0;
            case SENIOR -> totalSalary - totalSalary * tax.getUpperTaxRate() / 100.0;
        };

        developer.setSalary(totalSalary);

        developers.put(developer.getId(), developer);

    }

    @PutMapping("developers/{id}")
    public void setDevelopers(@PathVariable("id") int id,
                                   @RequestBody Developer developer) {
        developers.put(id, developer);
    }

    @DeleteMapping("developers/{id}")
    public void deleteDevelopers(@PathVariable("id") int id){
        if (developers.containsKey(id)){
            developers.remove(id);
        }
    }
}
