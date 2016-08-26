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

import org.HdrHistogram.Histogram;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CollectorInformations
{

    public static enum InformationType
    {
        LATENCY,
        REQUEST,
        MONITORING;
    }

    private InformationType informationType;


    private long totalCount;

    private long minValue;

    private long maxValue;

    private double mean;

    private double stdDeviation;

    private long startTimeStamp;

    private long endTimeStamp;

    public CollectorInformations()
    {
        // no op to help json mapper
    }

    public CollectorInformations( Histogram histogram, InformationType informationType )
    {
        this.informationType = informationType;
        this.totalCount = histogram.getTotalCount();
        this.minValue = TimeUnit.NANOSECONDS.toMillis( histogram.getMinValue() );
        this.maxValue = TimeUnit.NANOSECONDS.toMillis( histogram.getMaxValue() );
        this.mean = TimeUnit.NANOSECONDS.toMillis( Math.round( histogram.getMean() ) );
        this.startTimeStamp = histogram.getStartTimeStamp();
        this.endTimeStamp = histogram.getEndTimeStamp();
        this.stdDeviation = TimeUnit.NANOSECONDS.toMillis( Math.round( histogram.getStdDeviation() ) );
    }

    public long getEndTimeStamp()
    {
        return endTimeStamp;
    }

    public void setEndTimeStamp( long endTimeStamp )
    {
        this.endTimeStamp = endTimeStamp;
    }

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount( long totalCount )
    {
        this.totalCount = totalCount;
    }

    public long getMinValue()
    {
        return minValue;
    }

    public void setMinValue( long minValue )
    {
        this.minValue = minValue;
    }

    public long getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue( long maxValue )
    {
        this.maxValue = maxValue;
    }

    public double getMean()
    {
        return mean;
    }

    public void setMean( double mean )
    {
        this.mean = mean;
    }

    public double getStdDeviation()
    {
        return stdDeviation;
    }

    public void setStdDeviation( double stdDeviation )
    {
        this.stdDeviation = stdDeviation;
    }

    public long getStartTimeStamp()
    {
        return startTimeStamp;
    }

    public void setStartTimeStamp( long startTimeStamp )
    {
        this.startTimeStamp = startTimeStamp;
    }

    public InformationType getInformationType()
    {
        return informationType;
    }

    public void setInformationType( InformationType informationType )
    {
        this.informationType = informationType;
    }

    @Override
    public String toString()
    {
        return toString(false);
    }

    public String toString(boolean ls)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss z" );
        return "CollectorInformations{" + ( ls ?  System.lineSeparator() : "" ) //
            + "informationType=" + informationType + ( ls ?  System.lineSeparator() : "" ) //
            + ",totalCount=" + totalCount + ( ls ?  System.lineSeparator() : "" ) //
            + ", minValue=" + minValue + ( ls ?  System.lineSeparator() : "" ) //
            + ", maxValue=" + maxValue +  ( ls ?  System.lineSeparator() : "" ) //
            + ", mean=" + mean + ( ls ?  System.lineSeparator() : "" ) //
            + ", stdDeviation=" + stdDeviation + ( ls ?  System.lineSeparator() : "" ) //
            + ", startTimeStamp=" + simpleDateFormat.format( startTimeStamp ) + ( ls ?  System.lineSeparator() : "" ) //
            + ", endTimeStamp=" + simpleDateFormat.format(endTimeStamp ) + '}';
    }


    public static String toString( Histogram histogram )
    {
        return "CollectorInformations{" + "totalCount=" + histogram.getTotalCount() //
            + ", minValue=" + histogram.getMinNonZeroValue() //
            + ", maxValue=" + histogram.getMaxValue() //
            + ", mean=" + histogram.getMean() //
            + ", stdDeviation=" + histogram.getStdDeviation() //
            + ", startTimeStamp=" + histogram.getStartTimeStamp() //
            + ", endTimeStamp=" + histogram.getEndTimeStamp() + '}';
    }

}