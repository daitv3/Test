/**
 * 
 */
package test.nal.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.nal.test.common.StatusWork;
import test.nal.test.model.Work;
import test.nal.test.repository.WorkRepository;

/**
 * @author Admin
 *
 */
@RestController
@RequestMapping("TestNAL")
public class WorkController {
    @Autowired
    WorkRepository workRepository;

    /**
     * Get all data work
     * 
     * @return ResponseEntity<List<Work>> result data
     * @throws Exception
     */
    @GetMapping("loadData")
    public ResponseEntity<List<Work>> getAllWork() throws Exception {
        List<Work> works = workRepository.findAll();
        if (works == null || works.size() == 0) {
            throw new Exception("Not exists data");
        }
        return new ResponseEntity<List<Work>>(works, HttpStatus.OK);

    }

    /**
     * get data by pagination and sort by name
     * 
     * @param pageNo   page number
     * @param pageSize size number record
     * @return ResponseEntity<Page<Work>> result data of page
     * @throws Exception
     */
    @GetMapping("loadDataPage")
    public ResponseEntity<Page<Work>> getWorkByPage(@RequestParam int pageNo, @RequestParam int pageSize)
            throws Exception {
        // get info work by page and sort by work name
        Page<Work> works = workRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("workName")));
        // handle when page not exits data
        if (works == null || works.isEmpty()) {
            throw new Exception("Not exists data");
        }

        return new ResponseEntity<Page<Work>>(works, HttpStatus.OK);

    }

    /**
     * Add new work
     * 
     * @param work
     * @return
     * @throws Exception
     */
    @PostMapping("addNew")
    public ResponseEntity<Work> addWork(@RequestBody Work work) throws Exception {
        // validate data
        if (work != null && (work.getWorkName() == null)) {

            throw new Exception("invalid data");
        }

        if (work.getStatus().equals(StatusWork.Planing.getCode())) {
            throw new Exception("Work need add new must be planning");
        }

        // execute update data
        Work result = workRepository.save(work);

        return new ResponseEntity<Work>(result, HttpStatus.OK);
    }

    /**
     * Get info work by work name
     * 
     * @param workName work name
     * @return ResponseEntity<List<Work>> info data
     * @throws Exception
     */
    @GetMapping("loadData/{workName}")
    public ResponseEntity<List<Work>> getDataByName(@PathVariable(value = "workName") String workName)
            throws Exception {

        // Call DAO method get info work
        List<Work> works = workRepository.findByWorkName(workName);

        // handle error message when not exists work
        if (works == null || works.size() == 0) {
            throw new Exception("not exists work");
        }

        return new ResponseEntity<List<Work>>(works, HttpStatus.OK);
    }

    /**
     * Update work by name
     * 
     * @param workName    work name need update
     * @param workUpdData data need update
     * @return ResponseEntity<List<Work>> result update
     * @throws Exception
     */
    @PutMapping("edit/name={workName}")
    public ResponseEntity<List<Work>> editWorkByName(@PathVariable(value = "workName") String workName,
            @RequestBody Work workUpdData) throws Exception {

        // get info work by Name
        List<Work> works = workRepository.findByWorkName(workName);

        // throw error when not exits work
        if (works == null || works.size() == 0) {
            throw new Exception("not exists work");
        }
        works.forEach(item -> {
            item.setStartingDate(workUpdData.getStartingDate());
            item.setEndingDate(workUpdData.getEndingDate());
            item.setStatus(workUpdData.getStatus());
        });

        // update data and return result
        return new ResponseEntity<List<Work>>(workRepository.saveAll(works), HttpStatus.OK);
    }

    /**
     * Update work by ID work
     * 
     * @param id          work id
     * @param workUpdData work data update
     * @return ResponseEntity<Work> status api result
     * @throws Exception
     */
    @PutMapping("edit/{id}")
    public ResponseEntity<Work> editWorkById(@PathVariable(value = "id") int id, @RequestBody Work workUpdData)
            throws Exception {

        // Get work info by ID
        Work work = workRepository.findById(id).orElseThrow(() -> new Exception("not exists work"));
        if (workUpdData.getWorkName() == null || "".equals(workUpdData.getWorkName())) {
            throw new Exception("invalid work name");
        }

        // Setting data need update
        work.setWorkName(workUpdData.getWorkName());
        work.setStartingDate(workUpdData.getStartingDate());
        work.setEndingDate(workUpdData.getEndingDate());
        work.setStatus(workUpdData.getStatus());

        // execute update and return status result
        return new ResponseEntity<Work>(workRepository.save(work), HttpStatus.OK);
    }

    /**
     * Delete Work
     * 
     * @param id Work ID
     * @return Map<String, Boolean> status delete
     * @throws Exception
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteWork(@PathVariable(value = "id") int id) throws Exception {

        // Get work by id and throw error when not exists
        Work work = workRepository.findById(id).orElseThrow(() -> new Exception("not exists work"));

        // execute delete work
        workRepository.delete(work);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
