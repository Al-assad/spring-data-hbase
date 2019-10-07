package site.assad.spring.data.hbase.api;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * Callback for mapping rows of a {@link ResultScanner} on a per-row basis.
 * Implementations of this interface perform the actual work of mapping each row to a result object, but don't need to worry about exception handling.
 *
 * @author Costin Leau
 * copy from spring data hadoop site.assad.spring.data.hbase, modified by Al-assad, use the 2.0.0 api
 * @author Al-assad
 * @since 2019/10/7
 */

public interface RowMapper<T> {
    
    T mapRow(Result result, int rowNum) throws Exception;
}
