package com.ef.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Blocked  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBlocked;
    
    private String blockedIp;
    private String blockedReason;
    
    public Blocked() {
        
    }
    
    public Blocked(String blockedIp, String blockedReason) {
        this.blockedIp = blockedIp;
        this.blockedReason = blockedReason;
    }

    public int getIdBlocked() {
        return idBlocked;
    }

    public void setIdBlocked(int idBlocked) {
        this.idBlocked = idBlocked;
    }

    public String getBlockedIp() {
        return blockedIp;
    }

    public void setBlockedIp(String blockedIp) {
        this.blockedIp = blockedIp;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    public void setBlockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
    }
}
