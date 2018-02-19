package com.ef.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import com.ef.dao.BlockedRepository;
import com.ef.dao.LogRepository;
import com.ef.utils.Constants;
import com.ef.model.Blocked;
import com.ef.model.Log;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ParserController {
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private BlockedRepository blockedRepository;
    
    public List<String> getIpsExceedingThreshold(int threshold, LocalDateTime startDate, String duration) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        
        try (Stream<String> lines = Files.lines(Paths.get("./access"))) {
            lines.forEach((line) -> {
                String[] columns = line.split("|");
                
                Log log = new Log();
                Date date = Date.from(LocalDateTime.parse(columns[0], formatter).toInstant(ZoneOffset.UTC));
                log.setLogDate(date);
                log.setLogIp(columns[1]);
                log.setLogRequest(columns[2]);
                log.setLogStatus(Integer.parseInt(columns[3]));
                log.setLogUserAgent(columns[4]);
                
                logRepository.save(log);
            });
        }
        
        LocalDateTime endDate = null;
        String period = null;
        switch(duration) {
            case Constants.HOURLY:
                endDate = startDate.plus(1, ChronoUnit.HOURS);
                period = "hour";
                break;
            case Constants.DAILY:
                endDate = startDate.plus(1, ChronoUnit.DAYS);
                period = "day";
                break;
        }
        
        List<String> ips = logRepository.findExceedingIps(threshold, Date.from(startDate.toInstant(ZoneOffset.UTC)), Date.from(endDate.toInstant(ZoneOffset.UTC)));
        
        for (String ip : ips) {
            blockedRepository.save(new Blocked(ip, "This IP was blocked because it made more than " + threshold + "requests in a/an " + period));
        }
        
        return ips;
    }
}
