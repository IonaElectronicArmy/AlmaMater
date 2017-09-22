package com.iona.framework.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {
	
	//@Scheduled(cron = "0 15 10 15 * ?") CRON example
	/*	  
	  1 Seconds 2 Minutes 3 Hours 4 Day-of-Month 5 Month 6 Day-of-Week 7 Year (optional field)
	 
	 	* * * * * *
		| | | | | | 
		| | | | | +-- Year              (range: 1900-3000)
		| | | | +---- Day of the Week   (range: 1-7, 1 standing for Monday)
		| | | +------ Month of the Year (range: 1-12)
		| | +-------- Day of the Month  (range: 1-31)
		| +---------- Hour              (range: 0-23)
		+------------ Minute            (range: 0-59)
		
	 */
	@Scheduled(fixedDelay = 1000*60*10)
	public void scheduleFixedDelayTask() {
	    System.out.println(
	      "Fixed delay task - " + System.currentTimeMillis() / 1000);
	}
}
