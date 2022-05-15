package hu.albi.back.repo;


import hu.albi.back.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<FileInfo, Integer> {

    @Query( value = "SELECT * FROM file_info WHERE sublet_id = :id ", nativeQuery = true)
    List<FileInfo> findFileInfoBySubletId(Integer id);


    FileInfo findFileInfoById(@Param("id") int id);

   /*
    @Query( value = "SELECT * FROM sublet ORDER BY size DESC", nativeQuery = true)
    List<Sublet> orderSubletBySizeDesc();
    */



    void deleteFileInfoById(Integer id);


}
