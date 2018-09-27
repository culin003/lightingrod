/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename MySqlSlaveStatusCollector
 * @author guguihe
 * @date 2018-09-25 16:00
 */

package org.leoly.lightingrod.guard.mysql;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.leoly.lightingrod.guard.IDataCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-25 16:00
 */
@Service("mySqlLongTransactionCollector")
public class MySqlLongTransactionCollector implements IDataCollector<List<MySqlLongTransaction>> {

    private final Logger logger = LoggerFactory.getLogger("MySqlLongTransactionCollector_LOG");

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MySqlLongTransaction> collect() {
        String sql = "SELECT trx_id,trx_isolation_level,trx_mysql_thread_id,trx_query,trx_requested_lock_id,trx_rows_locked," +
                "trx_started,trx_state,trx_tables_in_use,trx_tables_locked,trx_weight,NOW() AS checkTime FROM information_schema.INNODB_TRX  ORDER BY trx_started limit 500";
        logger.info("Check SQL: {}", sql);
        List<MySqlLongTransaction> results = jdbcTemplate.query(sql, new RowMapper<MySqlLongTransaction>() {
            @Override
            public MySqlLongTransaction mapRow(ResultSet resultSet, int i) throws SQLException {
                MySqlLongTransaction longtransaction = new MySqlLongTransaction();
                longtransaction.setTrxId(resultSet.getString("trx_id"));
                longtransaction.setTrxIsolationLevel(resultSet.getString("trx_isolation_level"));
                longtransaction.setTrxMysqlThreadId(resultSet.getString("trx_mysql_thread_id"));
                longtransaction.setTrxQuery(resultSet.getString("trx_query"));
                longtransaction.setTrxRequestedLockId(resultSet.getString("trx_requested_lock_id"));
                longtransaction.setTrxRowsLocked(resultSet.getString("trx_rows_locked"));
                longtransaction.setTrxStarted(resultSet.getTimestamp("trx_started"));
                longtransaction.setTrxState(resultSet.getString("trx_state"));
                longtransaction.setTrxTablesInUse(resultSet.getString("trx_tables_in_use"));
                longtransaction.setTrxTablesLocked(resultSet.getString("trx_tables_locked"));
                longtransaction.setTrxWeight(resultSet.getString("trx_weight"));
                longtransaction.setCheckTime(resultSet.getTimestamp("checkTime"));
                return longtransaction;
            }
        });

        String serverInfo = StringUtils.EMPTY;
        String[] props = datasourceUrl.split(":");
        if (ArrayUtils.isNotEmpty(props) && props.length > 2 && props[2].length() > 2) {
            serverInfo = props[2].substring(2);
        }

        final String info = serverInfo;
        results.forEach(mySqlLongTransaction -> {
            mySqlLongTransaction.setServerInfo(info);
        });

        return results;
    }
}
