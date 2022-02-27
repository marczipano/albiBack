package hu.albi.back.service;

import hu.albi.back.model.Sublet;
import org.springframework.stereotype.Service;
import hu.albi.back.repo.SubletRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class SubletService {

    private static SubletRepository subletRepository;

    public SubletService(SubletRepository subletRepository){
        this.subletRepository = subletRepository;
    }

    public List<Sublet> getSublets() {
        return subletRepository.findAll();
    }

    public Sublet addSublet(Sublet sublet){
        return subletRepository.save(sublet);
    }

    public Sublet findSubletById(Integer id){
        return subletRepository.findSubletById(id);
    }

    public static void deleteSublet(Integer id){
        subletRepository.deleteSubletById(id);
    }

}
