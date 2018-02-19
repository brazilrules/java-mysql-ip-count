package com.ef.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "find_exceeding_ips",
                               procedureName = "find_exceeding_ips",
                               parameters = {
                                   @StoredProcedureParameter(mode = ParameterMode.IN, name = "threshold", type = Integer.class),
                                   @StoredProcedureParameter(mode = ParameterMode.IN, name = "startDate", type = Date.class),
                                   @StoredProcedureParameter(mode = ParameterMode.IN, name = "endDate", type = Date.class)
                               }),
    @NamedStoredProcedureQuery(name = "find_requests_by_ip",
                               procedureName = "find_requests_by_ip",
                               parameters = {
                                   @StoredProcedureParameter(mode = ParameterMode.IN, name = "ip", type = String.class)
                               })
})
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLog;
    
    private Date logDate;
    private String logIp;
    private String logRequest;
    private int logStatus;
    private String logUserAgent;

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getLogRequest() {
        return logRequest;
    }

    public void setLogRequest(String logRequest) {
        this.logRequest = logRequest;
    }

    public int getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(int logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogUserAgent() {
        return logUserAgent;
    }

    public void setLogUserAgent(String logUserAgent) {
        this.logUserAgent = logUserAgent;
    }
}
