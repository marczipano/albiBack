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

    @Query( value = "SELECT * FROM file_info WHERE name = :name ", nativeQuery = true)
    List<FileInfo> findByName(String name);

    @Query( value = "SELECT COUNT(*) FROM file_info WHERE sublet_id NOT IN (SELECT id FROM sublet) ", nativeQuery = true)
    Integer getUnusedCount();

    @Query( value = "SELECT * FROM file_info WHERE sublet_id NOT IN (SELECT id FROM sublet) ", nativeQuery = true)
    List<FileInfo> getUnusedFiles();

    FileInfo findFileInfoById(@Param("id") int id);


    void deleteFileInfoById(Integer id);


}
