package hu.albi.back.controller;


import hu.albi.back.model.Sublet;
import hu.albi.back.model.SubletInfo;
import hu.albi.back.security.services.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<SubletInfo>> getSubletInfosByUser(@PathVariable("user") Integer userId){
        List<SubletInfo> sublets = subletService.getSubletInfosByUser(userId);
        return new ResponseEntity<>(sublets, HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Sublet> addSublet(@Validated @RequestBody Sublet sublet){
        Sublet newSublet = subletService.addSublet(sublet);
        return new ResponseEntity<>(newSublet, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{id}/addImages")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Sublet> addImagesToSublet(@PathVariable("id") Integer id, @Validated @RequestBody String[] images){
        subletService.addImagesToSublet(id, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> deleteSubletById(@PathVariable("id") Integer id, Authentication authentication){
        if(authentication.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN"))){
            SubletService.deleteSubletByAdmin(id);
        }
        else{
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            try {
                SubletService.deleteSubletByUser(id, userDetails.getId().intValue());
            }
            catch (Exception e){
                return new ResponseEntity<>("{\"message\": \"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
