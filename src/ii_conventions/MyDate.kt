package ii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}

fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(public override val start: MyDate, public override val end: MyDate) : Range<MyDate>, Iterable<MyDate> {
    override fun contains(item: MyDate): Boolean = item >= start && item <= end
    override fun iterator(): Iterator<MyDate> = DateIterator(this)
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        var result = current;
        current = current.nextDay();
        return result;
    }
    override fun hasNext(): Boolean {
        return current <= dateRange.end
    }
}

fun MyDate.plus(interval: TimeInterval) : MyDate = this.addTimeIntervals(interval, 1)
fun MyDate.plus(n: RepeatTimeInterval) : MyDate = this.addTimeIntervals(n.timeInterval, n.number)

class RepeatTimeInterval(val timeInterval: TimeInterval, val number: Int)
fun TimeInterval.times(n: Int) = RepeatTimeInterval(this, n)
