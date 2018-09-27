/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename MySqlSlaveStatusCollector
 * @author guguihe
 * @date 2018-09-25 16:00
 */

package org.leoly.lightingrod.guard.mysql;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.leoly.lightingrod.guard.IDataCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-25 16:00
 */
@Service("mySqlSlaveStatusCollector")
public class MySqlSlaveStatusCollector implements IDataCollector<MySqlSlaveStatus> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MySqlSlaveStatus collect() {
        String sql = "show slave status";
        logger.info("Check SQL: {}", sql);
        List<MySqlSlaveStatus> results = jdbcTemplate.query(sql, new RowMapper<MySqlSlaveStatus>() {
            @Override
            public MySqlSlaveStatus mapRow(ResultSet resultSet, int i) throws SQLException {
                MySqlSlaveStatus slaveStatus = new MySqlSlaveStatus();
                slaveStatus.setMasterHost(resultSet.getString("Master_Host"));
                slaveStatus.setMasterLogFile(resultSet.getString("Master_Log_File"));
                slaveStatus.setMasterPort(resultSet.getString("Master_Port"));
                slaveStatus.setMasterServerId(resultSet.getString("Master_Server_Id"));
                slaveStatus.setMasterUser(resultSet.getString("Master_User"));
                slaveStatus.setReadMasterLogPos(resultSet.getString("Read_Master_Log_Pos"));
                slaveStatus.setSlaveIoRunning(resultSet.getString("Slave_IO_Running"));
                slaveStatus.setSlaveSqlRunning(resultSet.getString("Slave_SQL_Running"));
                slaveStatus.setSlaveIoState(resultSet.getString("Slave_IO_State"));
                slaveStatus.setLastIoError(resultSet.getString("Last_IO_Error"));
                slaveStatus.setLastSqlError(resultSet.getString("Last_SQL_Errno"));
                return slaveStatus;
            }
        });

        MySqlSlaveStatus slaveStatus = CollectionUtils.isEmpty(results) ? new MySqlSlaveStatus() : results.get(0);
        String[] props = datasourceUrl.split(":");
        if (ArrayUtils.isNotEmpty(props) && props.length > 2 && props[2].length() > 2) {
            slaveStatus.setServerInfo(props[2].substring(2));
        }

        return slaveStatus;
    }
}
