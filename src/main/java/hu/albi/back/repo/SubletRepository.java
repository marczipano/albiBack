package hu.albi.back.repo;


import hu.albi.back.model.Sublet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubletRepository extends JpaRepository<Sublet, Integer> {

    Sublet findSubletById(Integer id);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% ORDER BY size DESC", nativeQuery = true)
    List<Sublet> orderSubletBySizeDesc(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% ORDER BY size ASC", nativeQuery = true)
    List<Sublet> orderSubletBySizeAsc(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% ORDER BY price DESC", nativeQuery = true)
    List<Sublet> orderSubletByPriceDesc(@Param("addr") String addr);

    @Query(value = "SELECT * FROM Sublet WHERE address LIKE %:addr% ORDER BY price ASC ", nativeQuery = true)
    List<Sublet> orderSubletByPriceAsc(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% AND garden = 1", nativeQuery = true)
    List<Sublet> findSubletByGarden(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% AND garden = 0", nativeQuery = true)
    List<Sublet> findSubletByNoGarden(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE address LIKE %:addr% ORDER BY id DESC", nativeQuery = true)
    List<Sublet> findSubletByAddress(@Param("addr") String addr);

    @Query(value = "SELECT * FROM sublet WHERE seller_id=:id", nativeQuery = true)
    List<Sublet> findSubletByUser(@Param("id") int id);



    void deleteSubletById(Integer id);
}
