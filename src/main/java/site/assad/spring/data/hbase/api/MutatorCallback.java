package site.assad.spring.data.hbase.api;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * callback for site.assad.spring.data.hbase put delete and update
 *
 * @author Al-assad
 * @since 2019/10/7
 */
public interface MutatorCallback {
    
    /**
     * use mutator api to update put and delete
     *
     * @param mutator
     * @throws Throwable
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}
