/**
 * 
 */
package test.nal.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import test.nal.test.model.Work;

/**
 * @author Admin
 *
 */
@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {
    public List<Work> findByWorkName(@Param("workName") String workName);
}
