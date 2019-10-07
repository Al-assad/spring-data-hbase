package site.assad.spring.data.hbase.api;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * HBase Data Access exception.
 *
 * @author Costin Leau
 * <p>
 * copy from spring data hadoop site.assad.spring.data.hbase, modified by Al-assad
 * @author Al-assad
 * @since 2019/10/7
 */

public class HbaseSystemException extends UncategorizedDataAccessException {
    
    public HbaseSystemException(Exception cause) {
        super(cause.getMessage(), cause);
    }
    
    public HbaseSystemException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }
}
