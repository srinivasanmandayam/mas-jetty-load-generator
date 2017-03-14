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

package org.mortbay.jetty.load.generator;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.HdrHistogram.Histogram;

public class CollectorInformations
{

    public enum InformationType
    {
        REQUEST,
        MONITORING;
    }

    private InformationType informationType;

    private long totalCount;

    private long minValue;

    private long maxValue;

    private long value50;

    private long value90;

    private double mean;

    private double stdDeviation;

    private long startTimeStamp;

    private long endTimeStamp;

    public CollectorInformations()
    {
        // no op to help json mapper
    }

    public CollectorInformations( Histogram histogram )
    {
        this( histogram, InformationType.REQUEST );
    }

    public CollectorInformations( Histogram histogram, InformationType informationType )
    {
        this.informationType = informationType;
        this.totalCount = histogram.getTotalCount();
        this.minValue = histogram.getMinValue();
        this.maxValue = histogram.getMaxValue();
        this.mean = histogram.getMean();
        this.value50 = histogram.getValueAtPercentile( 50D );
        this.value90 = histogram.getValueAtPercentile( 90D );
        this.startTimeStamp = histogram.getStartTimeStamp();
        this.endTimeStamp = histogram.getEndTimeStamp();
        this.stdDeviation = histogram.getStdDeviation();
    }

    public long getEndTimeStamp()
    {
        return endTimeStamp;
    }

    public void setEndTimeStamp( long endTimeStamp )
    {
        this.endTimeStamp = endTimeStamp;
    }

    public CollectorInformations endTimeStamp( long endTimeStamp )
    {
        this.endTimeStamp = endTimeStamp;
        return this;
    }

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount( long totalCount )
    {
        this.totalCount = totalCount;
    }

    public CollectorInformations totalCount( long totalCount )
    {
        this.totalCount = totalCount;
        return this;
    }

    public long getMinValue()
    {
        return minValue;
    }

    public void setMinValue( long minValue )
    {
        this.minValue = minValue;
    }

    public CollectorInformations minValue( long minValue )
    {
        this.minValue = minValue;
        return this;
    }

    public long getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue( long maxValue )
    {
        this.maxValue = maxValue;
    }

    public CollectorInformations maxValue( long maxValue )
    {
        this.maxValue = maxValue;
        return this;
    }

    public double getMean()
    {
        return mean;
    }

    public void setMean( double mean )
    {
        this.mean = mean;
    }

    public CollectorInformations mean( double mean )
    {
        this.mean = mean;
        return this;
    }

    public double getStdDeviation()
    {
        return stdDeviation;
    }

    public void setStdDeviation( double stdDeviation )
    {
        this.stdDeviation = stdDeviation;
    }

    public CollectorInformations stdDeviation( double stdDeviation )
    {
        this.stdDeviation = stdDeviation;
        return this;
    }

    public long getStartTimeStamp()
    {
        return startTimeStamp;
    }

    public void setStartTimeStamp( long startTimeStamp )
    {
        this.startTimeStamp = startTimeStamp;
    }

    public CollectorInformations startTimeStamp( long startTimeStamp )
    {
        this.startTimeStamp = startTimeStamp;
        return this;
    }

    public InformationType getInformationType()
    {
        return informationType;
    }

    public void setInformationType( InformationType informationType )
    {
        this.informationType = informationType;
    }

    public long getValue50()
    {
        return value50;
    }

    public void setValue50( long value50 )
    {
        this.value50 = value50;
    }

    public CollectorInformations value50( long value50 )
    {
        this.value50 = value50;
        return this;
    }

    public long getValue90()
    {
        return value90;
    }

    public void setValue90( long value90 )
    {
        this.value90 = value90;
    }

    public CollectorInformations value90( long value90 )
    {
        this.value90 = value90;
        return this;
    }

    @Override
    public String toString()
    {
        return toString( false );
    }

    public String toString( boolean ls )
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss z" );
        return "CollectorInformations millis:" + ( ls ? System.lineSeparator() : "" ) //
            + "informationType=" + informationType + ( ls ? System.lineSeparator() : "" ) //
            + ",totalCount=" + totalCount //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", minValue=" + TimeUnit.NANOSECONDS.toMillis( getMinValue() ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", maxValue=" + TimeUnit.NANOSECONDS.toMillis( getMaxValue() ) + ( ls ? System.lineSeparator() : "" ) //
            + ", mean=" + TimeUnit.NANOSECONDS.toMillis( Math.round( getMean() ) ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", stdDeviation=" + TimeUnit.NANOSECONDS.toMillis( Math.round( getStdDeviation() ) ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", value 50%=" + TimeUnit.NANOSECONDS.toMillis( Math.round( getValue50() ) ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", value 90%=" + TimeUnit.NANOSECONDS.toMillis( Math.round( getValue90() ) ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", startTimeStamp=" + simpleDateFormat.format( startTimeStamp ) + ( ls ? System.lineSeparator() : "" ) //
            + ", endTimeStamp=" + simpleDateFormat.format( endTimeStamp );
    }


    public String toStringInNanos( boolean ls )
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss z" );
        return "CollectorInformations nanos:" + ( ls ? System.lineSeparator() : "" ) //
            + "informationType=" + informationType + ( ls ? System.lineSeparator() : "" ) //
            + ",totalCount=" + totalCount //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", minValue=" + getMinValue()  //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", maxValue=" + getMaxValue() + ( ls ? System.lineSeparator() : "" ) //
            + ", mean=" + Math.round( getMean() )  //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", stdDeviation=" + Math.round( getStdDeviation() ) //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", value 50%=" + Math.round( getValue50() )  //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", value 90%=" + Math.round( getValue90() )  //
            + ( ls ? System.lineSeparator() : "" ) //
            + ", startTimeStamp=" + simpleDateFormat.format( startTimeStamp ) + ( ls ? System.lineSeparator() : "" ) //
            + ", endTimeStamp=" + simpleDateFormat.format( endTimeStamp );
    }
}