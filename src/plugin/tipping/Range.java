package plugin.tipping;

import org.junit.Assert;

/** A very simplistic Range
 * @author Roey Maor
 * @since 9.12.2016
 */

public class Range {
    private int start;
    private int stop;

    public Range(int start, int stop){
        Assert.assertTrue(start<=stop);
        this.start = start;
        this.stop = stop;
    }

    public Range(Range r){
        this.start = r.start;
        this.stop = r.stop;
    }

    public Range merge(Range r){
        if(this.start>r.start || r.stop<this.stop){
            return null;
        }
        return new Range(this.start,r.stop);
    }
}