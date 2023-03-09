package com.garage.acedetails.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.garage.acedetails.service.GoodsBillService;

@Configuration
@EnableScheduling
public class GoodsBillSchedule {
  @Autowired
  private GoodsBillService goodsBillService;
  
  /* The below cron expression means every monday at 15:35:00
   * Cron template = [sec] [min] [hour] [day of month] [month] [day of week]
   * All value from left to right respectively are second, minute, hour, day of month, month, day of week (no year value)
   * Symbol '*' means all value
   * Symbol '?' means no specific value
   * Symbol '-' is to specify a range example '10-12' in hour value means running from 10h to 12h (10h, 11h, 12h)
   * Symbol ',' is to list the value
   * Symbol '/' is to specific a cycle, example '0/15' or in second field mean running every 15 seconds
   * 
   *  For more information, refer to these documents:
   *  - https://stackjava.com/uncategorized/cron-expression-la-gi-huong-dan-cu-phap-cron-expression.html
   *  - https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
   * */
  @Scheduled(cron = "0 35 15 ? * MON") 
  public void exportGoodsBillToFile() {
    goodsBillService.weeklyGoodsBillStatistics();
  }
}
