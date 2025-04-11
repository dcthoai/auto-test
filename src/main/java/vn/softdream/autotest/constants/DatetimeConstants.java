package vn.softdream.autotest.constants;

@SuppressWarnings("unused")
public interface DatetimeConstants {

    long ONE_SECOND = 1L;
    long ONE_MINUTE = 60 * ONE_SECOND;
    long ONE_HOUR = 60 * ONE_MINUTE;
    long ONE_DAY = 24 * ONE_HOUR;

    interface ZoneID {
        String DEFAULT = "UTC";

        // Asia
        String ASIA_HO_CHI_MINH = "Asia/Ho_Chi_Minh"; // Vietnam (UTC+7)
        String ASIA_TOKYO = "Asia/Tokyo"; // Japan (UTC+9)
        String ASIA_SHANGHAI = "Asia/Shanghai"; // China (UTC+8)
        String ASIA_BANGKOK = "Asia/Bangkok"; // Thailand (UTC+7)
        String ASIA_JAKARTA = "Asia/Jakarta"; // Indonesia (UTC+7)
        String ASIA_DUBAI = "Asia/Dubai"; // UAE (UTC+4)
        String ASIA_CALCUTTA = "Asia/Kolkata"; // India (UTC+5:30)

        // EUROPE
        String EUROPE_LONDON = "Europe/London"; // England (UTC+0, DST +1)
        String EUROPE_PARIS = "Europe/Paris"; // France (UTC+1, DST +2)
        String EUROPE_BERLIN = "Europe/Berlin"; // Germany (UTC+1, DST +2)
        String EUROPE_MOSCOW = "Europe/Moscow"; // Russia (UTC+3)

        // US
        String AMERICA_NEW_YORK = "America/New_York"; // America - East (UTC-5, DST -4)
        String AMERICA_CHICAGO = "America/Chicago"; // America - Center (UTC-6, DST -5)
        String AMERICA_DENVER = "America/Denver"; // America - Mountain (UTC-7, DST -6)
        String AMERICA_LOS_ANGELES = "America/Los_Angeles"; // America - West (UTC-8, DST -7)
        String AMERICA_SAO_PAULO = "America/Sao_Paulo"; // Brazil (UTC-3)
        String AMERICA_MEXICO_CITY = "America/Mexico_City"; // Mexico (UTC-6, DST -5)

        // Australia
        String AUSTRALIA_SYDNEY = "Australia/Sydney"; // Australia (UTC+10, DST +11)
        String AUSTRALIA_PERTH = "Australia/Perth"; // Western Australia (UTC+8)

        // Africa
        String AFRICA_CAIRO = "Africa/Cairo"; // Egypt (UTC+2)
        String AFRICA_JOHANNESBURG = "Africa/Johannesburg"; // South Africa (UTC+2)
    }

    interface Formatter {
        String DEFAULT = "yyyy-MM-dd HH:mm:ss";
        String UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String E_DD_MMM_YYYY = "E, dd MMM yyyy"; // Example: Thu, 13 Feb 2025
        String YYYY_MM_DD_NORMALIZED = "yyyyMMdd";
        String YYYY_MM_DD_HH_MM_SS_NORMALIZED = "yyyyMMddHHmmss";
        String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
        String YYYY_MM_DD_DASH = "yyyy/MM/dd";
        String YYYY_DD_MM_DASH = "yyyy/dd/MM";
        String YYYY_MM_DD_HH_MM_SLASH = "yyyy/MM/dd HH:mm";
        String DD_MM_YYYY_HH_MM_SLASH = "dd/MM/yyyy HH:mm";
        String DD_MM_YYYY_HH_MM_SS_SLASH = "dd/MM/yyyy HH:mm:ss";
        String DD_MM_YYYY_HH_MM_SS_SSS_SLASH = "dd/MM/yyyy HH:mm:ss.SSS";
        String DD_MM_YYYY_DASH = "dd-MM-yyyy";
        String YYYY_MM_DD_SLASH = "yyyy-MM-dd";
        String YYYY_DD_MM_SLASH = "yyyy-dd-MM";
        String YYYY_MM_DD_HH_MM_DASH = "yyyy-MM-dd HH:mm";
        String DD_MM_YYYY_HH_MM_DASH = "dd-MM-yyyy HH:mm";
        String DD_MM_YYYY_HH_MM_SS_DASH = "dd-MM-yyyy HH:mm:ss";
        String DD_MM_YYYY_HH_MM_SS_SSS_DASH = "dd-MM-yyyy HH:mm:ss.SSS";
    }
}
