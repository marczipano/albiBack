package hu.albi.back.repo;


import hu.albi.back.model.Sublet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SubletRepository extends JpaRepository<Sublet, Integer> {

    Sublet findSubletById(Integer id);
    void deleteSubletById(Integer id);

}
