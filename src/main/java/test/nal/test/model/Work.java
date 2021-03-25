/**
 * 
 */
package test.nal.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Work model 
 * 
 * @author DaiTV 
 *
 */
@Entity
@Table(name = "TBL_WORK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "WORK_NAME", nullable = false)
    private String workName;

    @Column(name = "STARTING_DATE")
    private String StartingDate;

    @Column(name = "ENDING_DATE")
    private String endingDate;

    @Column(name = "STATUS")
    private String status;
}
