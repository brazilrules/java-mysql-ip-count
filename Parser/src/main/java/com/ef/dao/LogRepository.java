package com.ef.dao;

import com.ef.model.Log;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LogRepository extends CrudRepository<Log, Long>{
    @Procedure(name="find_exceeding_ips")
    List<String> findExceedingIps(@Param("threshold") int threshold, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Procedure(name="find_requests_by_ip")
    List<String> findRequestsByIp(@Param("ip") String ip);
}
