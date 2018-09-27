/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename MySqlSlaveStatus
 * @author guguihe
 * @date 2018-09-25 16:53
 */

package org.leoly.lightingrod.guard.mysql;

import org.apache.commons.lang3.StringUtils;
import org.leoly.lightingrod.support.gson.GsonUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-25 16:53
 */
public class MySqlLongTransaction implements Serializable {

    private String trxId;
    private String trxState;
    private Date trxStarted;
    private String trxRequestedLockId;
    private String trxWaitStarted;
    private String trxWeight;
    private String trxMysqlThreadId;
    private String trxQuery;
    private String trxTablesInUse;
    private String trxTablesLocked;
    private String trxRowsLocked;
    private String trxIsolationLevel;
    private String serverInfo;
    private Date checkTime;

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getTrxState() {
        return trxState;
    }

    public void setTrxState(String trxState) {
        this.trxState = trxState;
    }

    public Date getTrxStarted() {
        return trxStarted;
    }

    public void setTrxStarted(Date trxStarted) {
        this.trxStarted = trxStarted;
    }

    public String getTrxRequestedLockId() {
        return trxRequestedLockId;
    }

    public void setTrxRequestedLockId(String trxRequestedLockId) {
        this.trxRequestedLockId = trxRequestedLockId;
    }

    public String getTrxWaitStarted() {
        return trxWaitStarted;
    }

    public void setTrxWaitStarted(String trxWaitStarted) {
        this.trxWaitStarted = trxWaitStarted;
    }

    public String getTrxWeight() {
        return trxWeight;
    }

    public void setTrxWeight(String trxWeight) {
        this.trxWeight = trxWeight;
    }

    public String getTrxMysqlThreadId() {
        return trxMysqlThreadId;
    }

    public void setTrxMysqlThreadId(String trxMysqlThreadId) {
        this.trxMysqlThreadId = trxMysqlThreadId;
    }

    public String getTrxQuery() {
        return trxQuery;
    }

    public void setTrxQuery(String trxQuery) {
        this.trxQuery = trxQuery;
    }

    public String getTrxTablesInUse() {
        return trxTablesInUse;
    }

    public void setTrxTablesInUse(String trxTablesInUse) {
        this.trxTablesInUse = trxTablesInUse;
    }

    public String getTrxTablesLocked() {
        return trxTablesLocked;
    }

    public void setTrxTablesLocked(String trxTablesLocked) {
        this.trxTablesLocked = trxTablesLocked;
    }

    public String getTrxRowsLocked() {
        return trxRowsLocked;
    }

    public void setTrxRowsLocked(String trxRowsLocked) {
        this.trxRowsLocked = trxRowsLocked;
    }

    public String getTrxIsolationLevel() {
        return trxIsolationLevel;
    }

    public void setTrxIsolationLevel(String trxIsolationLevel) {
        this.trxIsolationLevel = trxIsolationLevel;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public String toString() {
        return GsonUtils.getGson(true).toJson(this);
    }
}
