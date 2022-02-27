package hu.albi.back.controller;


import hu.albi.back.model.Sublet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import hu.albi.back.service.SubletService;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sublets")
public class SubletController {

    private final SubletService subletService;

    public SubletController(SubletService subletService){
        this.subletService = subletService;
    }


    @GetMapping
    public ResponseEntity<List<Sublet>> getAllSublet(){
        List<Sublet> sublets = subletService.getSublets();
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Sublet> getSubletById(@PathVariable("id") Integer id){
        Sublet sublet = subletService.findSubletById(id);
        return new ResponseEntity<>(sublet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sublet> addSublet(@Validated @RequestBody Sublet sublet){
        Sublet newSublet = subletService.addSublet(sublet);
        return new ResponseEntity<>(newSublet, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Sublet> deleteSubletById(@PathVariable("id") Integer id){
        subletService.deleteSublet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
