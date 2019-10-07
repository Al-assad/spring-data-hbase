package site.assad.spring.data.hbase.api;

import org.apache.hadoop.hbase.client.Table;

/**
 * Callback interface for Hbase code. To be used with {@link HbaseTemplate}'s execution methods, often as anonymous classes within a method implementation without
 * having to worry about exception handling.
 *
 * @author Costin Leau
 * <p>
 * copy from spring data hadoop site.assad.spring.data.hbase, modified by Al-assad, use the 2.0.0 api
 * @author Al-assad
 * @since 2019/10/7
 */

public interface TableCallback<T> {
    
    /**
     * Gets called by {@link HbaseTemplate} execute with an active Hbase table. Does need to care about activating or closing down the table.
     *
     * @param table active Hbase table
     * @return a result object, or null if none
     * @throws Throwable thrown by the Hbase API
     */
    T doInTable(Table table) throws Throwable;
}
