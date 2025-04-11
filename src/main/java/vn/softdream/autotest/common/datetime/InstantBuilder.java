package vn.softdream.autotest.common.datetime;

import vn.softdream.autotest.constants.DatetimeConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

@SuppressWarnings("unused")
public class InstantBuilder {

    private final ZoneId currentZoneID;
    private Instant instanceInstant;

    public InstantBuilder(Instant instant) {
        this(instant, DatetimeConstants.ZoneID.DEFAULT);
    }

    public InstantBuilder(Instant instant, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceInstant = instant;
    }

    public InstantBuilder(LocalDateTime localDateTime) {
        this(localDateTime, DatetimeConstants.ZoneID.DEFAULT);
    }

    public InstantBuilder(LocalDateTime localDateTime, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceInstant = localDateTime.atZone(this.currentZoneID).toInstant();
    }

    public InstantBuilder(String dateTime, String formatter) {
        this(dateTime, formatter, DatetimeConstants.ZoneID.DEFAULT);
    }

    public InstantBuilder(String dateTime, String formatter, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceInstant = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(formatter))
            .atZone(this.currentZoneID)
            .toInstant();
    }

    public InstantBuilder plusYears(int yearsToAdd) {
        this.instanceInstant = ZonedDateTime.ofInstant(instanceInstant, this.currentZoneID)
            .plusYears(yearsToAdd)
            .toInstant();
        return this;
    }

    public InstantBuilder plusMonths(int monthsToAdd) {
        this.instanceInstant = ZonedDateTime.ofInstant(instanceInstant, this.currentZoneID)
            .plusMonths(monthsToAdd)
            .toInstant();
        return this;
    }

    public InstantBuilder plusDays(int daysToAdd) {
        this.instanceInstant = this.instanceInstant.plusSeconds(daysToAdd * DatetimeConstants.ONE_DAY);
        return this;
    }

    public InstantBuilder plusHours(int hoursToAdd) {
        this.instanceInstant = this.instanceInstant.plusSeconds(hoursToAdd * DatetimeConstants.ONE_HOUR);
        return this;
    }

    public InstantBuilder plusMinutes(int minutesToAdd) {
        this.instanceInstant = this.instanceInstant.plusSeconds(minutesToAdd * DatetimeConstants.ONE_MINUTE);
        return this;
    }

    public InstantBuilder plusSeconds(int secondsToAdd) {
        this.instanceInstant = this.instanceInstant.plusSeconds(secondsToAdd);
        return this;
    }

    private ZonedDateTime getZonedDateTime() {
        return instanceInstant.atZone(this.currentZoneID);
    }

    public String getMonthStr() {
        return getZonedDateTime().getMonth().name();
    }

    public String getDayOfWeekStr() {
        return getZonedDateTime().getDayOfWeek().name();
    }

    public int getYear() {
        return getZonedDateTime().getYear();
    }

    public int getDayOfYear() {
        return getZonedDateTime().getDayOfYear();
    }

    public int getDaysOfYear() {
        return Year.of(getYear()).length();
    }

    public int getMonth() {
        return getZonedDateTime().getMonthValue();
    }

    public int getDayOfMonth() {
        return getZonedDateTime().getDayOfMonth();
    }

    public int getDaysOfMonth() {
        return getZonedDateTime().getMonth().length(Year.isLeap(getYear()));
    }

    public int getWeekOfYear() {
        return getZonedDateTime().get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }

    public int getWeeksOfYear() {
        return getDaysOfYear() / 7;
    }

    public int getWeekOfMonth() {
        return getZonedDateTime().get(WeekFields.of(Locale.getDefault()).weekOfMonth());
    }

    public int getDayOfWeek() {
        return getZonedDateTime().getDayOfWeek().getValue();
    }

    public int getHour() {
        return getZonedDateTime().getHour();
    }

    public int getMinute() {
        return getZonedDateTime().getMinute();
    }

    public int getSecond() {
        return getZonedDateTime().getSecond();
    }

    public int getMilliSecond() {
        return getZonedDateTime().getNano() / 1_000_000;
    }

    public boolean isBefore(Instant instantToCompare) {
        return this.instanceInstant.isBefore(instantToCompare);
    }

    public boolean isAfter(Instant instantToCompare) {
        return this.instanceInstant.isAfter(instantToCompare);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(instanceInstant, this.currentZoneID);
    }

    public LocalDateTime toLocalDateTime(String zoneID) {
        return LocalDateTime.ofInstant(instanceInstant, ZoneId.of(zoneID));
    }

    @Override
    public String toString() {
        return this.toString(DatetimeConstants.Formatter.DEFAULT);
    }

    public String toString(String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter).withZone(this.currentZoneID);
        return dateTimeFormatter.format(instanceInstant);
    }

    public String toStringWithZoneID(String zoneID) {
        return toStringWithZoneID(zoneID, DatetimeConstants.Formatter.DEFAULT);
    }

    public String toStringWithZoneID(String zoneID, String formatter) {
        ZoneId zone = ZoneId.of(zoneID);
        return DateTimeFormatter.ofPattern(formatter).format(instanceInstant.atZone(zone));
    }

    public Instant getInstance() {
        return this.instanceInstant;
    }
}
