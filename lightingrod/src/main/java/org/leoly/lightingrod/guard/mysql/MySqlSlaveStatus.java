/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename MySqlSlaveStatus
 * @author guguihe
 * @date 2018-09-25 16:53
 */

package org.leoly.lightingrod.guard.mysql;

import org.leoly.lightingrod.support.gson.GsonUtils;
import org.springframework.core.style.DefaultToStringStyler;
import org.springframework.core.style.ToStringCreator;
import org.springframework.core.style.ValueStyler;

import java.io.Serializable;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-25 16:53
 */
public class MySqlSlaveStatus implements Serializable {

    public static final String FAIL_STATE = "NO";

    private String slaveIoState;
    private String masterHost;
    private String masterUser;
    private String masterPort;
    private String masterLogFile;
    private String readMasterLogPos;
    private String slaveIoRunning;
    private String slaveSqlRunning;
    private String masterServerId;
    private String lastIoError;
    private String lastSqlError;
    private String serverInfo;

    public String getSlaveIoState() {
        return slaveIoState;
    }

    public void setSlaveIoState(String slaveIoState) {
        this.slaveIoState = slaveIoState;
    }

    public String getMasterHost() {
        return masterHost;
    }

    public void setMasterHost(String masterHost) {
        this.masterHost = masterHost;
    }

    public String getMasterUser() {
        return masterUser;
    }

    public void setMasterUser(String masterUser) {
        this.masterUser = masterUser;
    }

    public String getMasterPort() {
        return masterPort;
    }

    public void setMasterPort(String masterPort) {
        this.masterPort = masterPort;
    }

    public String getMasterLogFile() {
        return masterLogFile;
    }

    public void setMasterLogFile(String masterLogFile) {
        this.masterLogFile = masterLogFile;
    }

    public String getReadMasterLogPos() {
        return readMasterLogPos;
    }

    public void setReadMasterLogPos(String readMasterLogPos) {
        this.readMasterLogPos = readMasterLogPos;
    }

    public String getSlaveIoRunning() {
        return slaveIoRunning;
    }

    public void setSlaveIoRunning(String slaveIoRunning) {
        this.slaveIoRunning = slaveIoRunning;
    }

    public String getSlaveSqlRunning() {
        return slaveSqlRunning;
    }

    public void setSlaveSqlRunning(String slaveSqlRunning) {
        this.slaveSqlRunning = slaveSqlRunning;
    }

    public String getMasterServerId() {
        return masterServerId;
    }

    public void setMasterServerId(String masterServerId) {
        this.masterServerId = masterServerId;
    }

    public String getLastIoError() {
        return lastIoError;
    }

    public void setLastIoError(String lastIoError) {
        this.lastIoError = lastIoError;
    }

    public String getLastSqlError() {
        return lastSqlError;
    }

    public void setLastSqlError(String lastSqlError) {
        this.lastSqlError = lastSqlError;
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
