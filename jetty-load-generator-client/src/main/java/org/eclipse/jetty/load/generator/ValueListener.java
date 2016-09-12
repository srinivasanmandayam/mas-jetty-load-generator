//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
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
package org.eclipse.jetty.load.generator;

/**
 *
 */
public interface ValueListener
{

    /**
     * triggered when the load generator is stopped
     */
    void onLoadGeneratorStop();


    class Values
    {
        private String path;

        /**
         * the latency time in nano seconds
         */
        private long latencyTime;

        private String method;

        private long size;

        private int status;

        public Values()
        {
            // no op
        }

        public String getPath()
        {
            return path;
        }

        public void setPath( String path )
        {
            this.path = path;
        }

        public Values path( String path )
        {
            this.path = path;
            return this;
        }

        public long getLatencyTime()
        {
            return latencyTime;
        }

        public void setLatencyTime( long latencyTime )
        {
            this.latencyTime = latencyTime;
        }

        public Values latencyTime( long latencyTime )
        {
            this.latencyTime = latencyTime;
            return this;
        }

        public String getMethod()
        {
            return method;
        }

        public void setMethod( String method )
        {
            this.method = method;
        }

        public Values method( String method )
        {
            this.method = method;
            return this;
        }

        public long getSize()
        {
            return size;
        }

        public void setSize( long size )
        {
            this.size = size;
        }

        public Values size( long size )
        {
            this.size = size;
            return this;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus( int status )
        {
            this.status = status;
        }

        public Values status( int status )
        {
            this.status = status;
            return this;
        }

        @Override
        public String toString()
        {
            return "Values{" + "path='" + path + '\'' + ", latencyTime=" + latencyTime + ", method='" + method + '\''
                + ", size=" + size + ", status=" + status + '}';
        }
    }

}
