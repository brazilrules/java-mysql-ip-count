/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef;

import com.ef.controller.ParserController;
import java.time.format.DateTimeFormatter;
import com.ef.utils.Constants;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author leo_k
 */
public class Parser implements CommandLineRunner {

    @Value("${startDate}")
    private String startDateArg;
    @Value("${duration}")
    private String duration;
    @Value("${threshold}")
    private String thresholdArg;
    
    
    @Override
    public void run(String... args) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(startDateArg, formatter);
            
            int limit = 0;
            switch (duration) {
                case Constants.HOURLY:
                    limit = 200;
                    break;
                
                case Constants.DAILY:
                    limit = 500;
                    break;
                    
                default:
                    throw new Exception("Argument duration should be" + Constants.HOURLY + " or " + Constants.DAILY);
            }
            
            int threshold = Integer.parseInt(thresholdArg);
            
            ParserController controller = new ParserController();
            List<String> ips = controller.getIpsExceedingThreshold(threshold, startDate, duration);
            
            for (String ip : ips) {
                System.out.println("The IP number " + ip + " has made more than " + threshold + " requests in the time period.");
            }
            
        } catch (NumberFormatException ex) {
            System.err.println("Argument threshold should be an integer");
        } catch (NullPointerException ex) {
            if (startDateArg == null) {
                System.err.println("Argument startDate must be provided");
            }
            
            if (duration == null) {
                System.err.println("Argument duration must be provided");
            }
            
            if (thresholdArg == null) {
                System.err.println("Argument threshold must be provided");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Parser.class, args);
    }
    
}
