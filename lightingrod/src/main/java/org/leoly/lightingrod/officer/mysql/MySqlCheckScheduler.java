/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename MySqlSlaveStatusCheckScheduler
 * @author guguihe
 * @date 2018-09-25 17:16
 */

package org.leoly.lightingrod.officer.mysql;

import org.leoly.lightingrod.guard.mysql.MySqlLongTransaction;
import org.leoly.lightingrod.guard.mysql.MySqlLongTransactionCollector;
import org.leoly.lightingrod.guard.mysql.MySqlSlaveStatus;
import org.leoly.lightingrod.guard.mysql.MySqlSlaveStatusCollector;
import org.leoly.lightingrod.notifyer.ErrorNotifyer;
import org.leoly.lightingrod.support.LeolyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-25 17:16
 */
@Component
public class MySqlCheckScheduler {

    private Logger logger = LoggerFactory.getLogger("MySqlCheckScheduler-LOG");

    @Autowired
    private ErrorNotifyer smsNotifyer;

    @Autowired
    private MySqlSlaveStatusCollector slaveStatusCollector;

    @Autowired
    private MySqlLongTransactionCollector longTransactionCollector;

    @Value("${lightingrod.longtransaction.maxsize}")
    private int maxTransactionSize;

    @Value("${lightingrod.longtransaction.maxtimeminute}")
    private int maxTransactionTime;

    @Value("${lightingrod.checker}")
    private String checkerDefine;

    @Value("${lightingrod.notifyer}")
    private String notifyerDefine;

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkMySqlSlaveStatus() {
        if (checkerDefine.charAt(0) == '0'){
            return;
        }

        MySqlSlaveStatus slaveStatus = slaveStatusCollector.collect();
        logger.info("Check slave status: {}", slaveStatus);
        // 这两个状态不为ON时，表示同步不正确
        if (MySqlSlaveStatus.FAIL_STATE.equalsIgnoreCase(slaveStatus.getSlaveIoRunning())
                || MySqlSlaveStatus.FAIL_STATE.equalsIgnoreCase(slaveStatus.getSlaveSqlRunning())) {
            logger.info("Check slave fail!");
            String errorMsg = "主从同步异常";
            notifyUser(errorMsg, slaveStatus, slaveStatus.getServerInfo());
        }
    }


    @Scheduled(cron = "0 0/5 * * * *")
    public void checkMySqlLongTransaction() {
        if (checkerDefine.charAt(1) == '0'){
            return;
        }

        if (maxTransactionSize < 1 || maxTransactionTime < 1) {
            logger.warn("请检查lightingrod.longtransaction.maxsize和lightingrod.longtransaction.maxtransactiontime配置是否正确！");
            return;
        }

        List<MySqlLongTransaction> longTransactions = longTransactionCollector.collect();
        if (CollectionUtils.isEmpty(longTransactions)) {
            logger.info("Check long transaction size: 0");
            return;
        }

        logger.info("Check long transaction size: {}", longTransactions.size());
        String serverInfo = longTransactions.get(0).getServerInfo();
        // 检测是否超过了事务堆积最大量
        if (longTransactions.size() > maxTransactionSize) {
            logger.info("Check max transaction size fail!");
            String errorMsg = "事务堆积" + longTransactions.size() + "个";
            notifyUser(errorMsg, longTransactions, serverInfo);
        }

        // 检测是否有长时间执行的事务
        List<MySqlLongTransaction> errorTransactions = new ArrayList<>();
        longTransactions.forEach(longtransaction -> {
            if (LeolyDateUtils.subDate(longtransaction.getCheckTime(), longtransaction.getTrxStarted(), LeolyDateUtils.RETURN_MINUTES) > maxTransactionTime) {
                errorTransactions.add(longtransaction);
            }
        });

        if(!CollectionUtils.isEmpty(errorTransactions)){
            logger.info("Check max transaction time fail!");
            String errorMsg = "事务超时" + errorTransactions.size() + "个";
            notifyUser(errorMsg, longTransactions, serverInfo);
        }
    }

    private void notifyUser(String errorMsg, Object msgs, String serverInfo){
        if (checkerDefine.charAt(0) == '1') {
            smsNotifyer.sendSms(errorMsg, serverInfo);
        }

        if (checkerDefine.charAt(1) == '1') {
            smsNotifyer.sendEmail(msgs, errorMsg);
        }
    }
}
