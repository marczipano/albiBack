package hu.albi.back.controller;


import hu.albi.back.model.Sublet;
import hu.albi.back.model.SubletInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import hu.albi.back.service.SubletService;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/sublets")
public class SubletController {

    private final SubletService subletService;

    public SubletController(SubletService subletService){
        this.subletService = subletService;
    }


    /*@GetMapping
    public ResponseEntity<List<Sublet>> getAllSublet(){
        List<Sublet> sublets = subletService.getSublets();
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }*/
    /*
    @GetMapping
    public ResponseEntity<List<SubletInfo>> getAllSubletInfo(){
        List<SubletInfo> sublets = subletService.getSubletInfos();
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }*/

    @GetMapping
    public ResponseEntity<List<SubletInfo>> getAllSubletInfo(@RequestParam(required = false, defaultValue = "normal") String o){
        List<SubletInfo> sublets = subletService.getSubletInfos(o);
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<SubletInfo>> findSubletInfoByAddress(@RequestParam(defaultValue = "") String a){
        List<SubletInfo> sublets = subletService.findSubletInfosByAddress(a);
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Sublet> getSubletById(@PathVariable("id") Integer id){
        Sublet sublet = subletService.findSubletById(id);
        return new ResponseEntity<>(sublet, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{user}")
    public ResponseEntity<List<Sublet>> getSubletByUser(@PathVariable("user") Integer userId){
        List<Sublet> sublets = subletService.getSubletsByUser(userId);
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Sublet> addSublet(@Validated @RequestBody Sublet sublet){
        Sublet newSublet = subletService.addSublet(sublet);
        return new ResponseEntity<>(newSublet, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{id}/addImages")
    public ResponseEntity<Sublet> addImagesToSublet(@PathVariable("id") Integer id, @Validated @RequestBody String[] images){
        subletService.addImagesToSublet(id, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Sublet> deleteSubletById(@PathVariable("id") Integer id){
        subletService.deleteSublet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
