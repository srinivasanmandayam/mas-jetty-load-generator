//
//  ========================================================================
//  Copyright (c) 1995-2017 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.mortbay.jetty.load.generator.store;

import org.mortbay.jetty.load.generator.listeners.LoadResult;

import java.io.Closeable;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface ResultStore
    extends Closeable
{

    /**
     * @param setupData veru generic way of passing some data
     */
    void initialize( Map<String, String> setupData );

    void save( LoadResult loadResult );

    LoadResult get( String loadResultId );

    List<LoadResult> get( List<String> loadResultId );

    void remove( LoadResult loadResult );

    List<LoadResult> find( QueryFiler queryFiler );

    List<LoadResult> findAll();

    String getProviderId();

    boolean isActive( Map<String, String> setupData );

    class QueryFiler
    {
        private String jettyVersion, uuid;

        private Date startDate, endDate;

        public String getJettyVersion()
        {
            return jettyVersion;
        }

        public void setJettyVersion( String jettyVersion )
        {
            this.jettyVersion = jettyVersion;
        }

        public QueryFiler jettyVersion( String jettyVersion )
        {
            this.jettyVersion = jettyVersion;
            return this;
        }

        public Date getStartDate()
        {
            return startDate;
        }

        public void setStartDate( Date startDate )
        {
            this.startDate = startDate;
        }

        public QueryFiler startDate( Date startDate )
        {
            this.startDate = startDate;
            return this;
        }

        public Date getEndDate()
        {
            return endDate;
        }

        public void setEndDate( Date endDate )
        {
            this.endDate = endDate;
        }

        public QueryFiler endDate( Date endDate )
        {
            this.endDate = endDate;
            return this;
        }

        public String getUuid()
        {
            return uuid;
        }

        public void setUuid( String uuid )
        {
            this.uuid = uuid;
        }

        public QueryFiler uuid( String uuid )
        {
            this.uuid = uuid;
            return this;
        }
    }

    static List<ResultStore> getActives( Map<String, String> setupData )
    {
        return StreamSupport.stream( ServiceLoader.load( ResultStore.class ).spliterator(), false ) //
            .filter( resultStore -> resultStore.isActive( setupData ) ) //
            .collect( Collectors.toList() );
    }

    static ResultStore getActiveFromId( String id, Map<String, String> setupData )
    {
        List<ResultStore> resultStores = //
            getActives( setupData ).stream().filter(
                resultStore -> resultStore.getProviderId().equalsIgnoreCase( id ) ) //
                .collect( Collectors.toList() );

        // warning if more than one with same id?
        return resultStores.isEmpty() ? null : resultStores.get( 0 );
    }
}