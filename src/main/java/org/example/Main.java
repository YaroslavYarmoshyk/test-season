package org.example;

public class Main {
    public static void main(String[] args) {
        final String seasonStart = "15.12";
        final String seasonEnd = "20.01";
        final String analyzeStart = "04.10";
        final String analyzeEnd = "10.12";

        boolean isActive = isActive(seasonStart, seasonEnd, analyzeStart, analyzeEnd);
        System.out.println(isActive);

    }

    private static boolean isActive(final String seasonStart,
                                    final String seasonEnd,
                                    final String analyzeStart,
                                    final String analyseEnd) {
        final boolean isYearChange = isYearChange(seasonStart, seasonEnd);
        if (isYearChange) {
            return isActiveByChangeYear(seasonStart, seasonEnd, analyzeStart, analyseEnd);
        }
        return isActiveBySameYear(seasonStart, seasonEnd, analyzeStart, analyseEnd);
    }

    private static boolean isYearChange(final String seasonStart,
                                        final String seasonEnd) {
        final int startMonth = getMonthNumber(seasonStart);
        final int endMonth = getMonthNumber(seasonEnd);

        return endMonth < startMonth;
    }

    private static boolean isActiveByChangeYear(final String seasonStart,
                                                final String seasonEnd,
                                                final String analyzeStart,
                                                final String analyseEnd) {
        final int startSeasonDay = getDayNumber(seasonStart);
        final int endSeasonDay = getDayNumber(seasonEnd);
        final int startSeasonMonth = getMonthNumber(seasonStart);
        final int endSeasonMonth = getMonthNumber(seasonEnd);

        final int startAnalyzeDay = getDayNumber(analyzeStart);
        final int endAnalyzeDay = getDayNumber(analyseEnd);
        final int startAnalyzeMonth = getMonthNumber(analyzeStart);
        final int endAnalyzeMonth = getMonthNumber(analyseEnd);

        final boolean isAfterStartSeasonDate = isAfterStartSeasonDate(startSeasonDay, startSeasonMonth, startAnalyzeDay, startAnalyzeMonth, endSeasonDay, endSeasonMonth);
        final boolean isBeforeEndSeasonDate = isBeforeEndSeasonDate(startSeasonMonth, endAnalyzeDay, endAnalyzeMonth, endSeasonDay, endSeasonMonth);

        return isAfterStartSeasonDate || isBeforeEndSeasonDate;
    }

    private static boolean isActiveBySameYear(final String seasonStart,
                                              final String seasonEnd,
                                              final String analyzeStart,
                                              final String analyseEnd) {
        final int startSeasonDay = getDayNumber(seasonStart);
        final int endSeasonDay = getDayNumber(seasonEnd);
        final int startSeasonMonth = getMonthNumber(seasonStart);
        final int endSeasonMonth = getMonthNumber(seasonEnd);

        final int startAnalyzeDay = getDayNumber(analyzeStart);
        final int endAnalyzeDay = getDayNumber(analyseEnd);
        final int startAnalyzeMonth = getMonthNumber(analyzeStart);
        final int endAnalyzeMonth = getMonthNumber(analyseEnd);

        boolean isAfterStartSeasonDate = startAnalyzeMonth > startSeasonMonth || isAfterSameMonth(startSeasonDay, startAnalyzeDay);
        boolean isBeforeEndSeasonDate = endAnalyzeMonth < endSeasonMonth || isBeforeSameMonth(endSeasonDay, endAnalyzeDay);

        return isAfterStartSeasonDate || isBeforeEndSeasonDate;
    }

    private static int getDayNumber(final String date) {
        return Integer.parseInt(date.split("\\.")[0]);
    }

    private static int getMonthNumber(final String date) {
        return Integer.parseInt(date.split("\\.")[1]);
    }

    private static boolean isAfterStartSeasonDate(final int startSeasonDay,
                                                  final int startSeasonMonth,
                                                  final int startAnalyzeDay,
                                                  final int startAnalyzeMonth,
                                                  final int endSeasonDay,
                                                  final int endSeasonMonth) {
        if (startSeasonMonth == startAnalyzeMonth) {
            return isAfterSameMonth(startSeasonDay, startAnalyzeDay);
        }
        return startAnalyzeMonth > startSeasonMonth || (startAnalyzeMonth <= endSeasonMonth && startSeasonDay <= endSeasonDay);
    }

    private static boolean isBeforeEndSeasonDate(final int startSeasonMonth,
                                                 final int endAnalyzeDay,
                                                 final int endAnalyzeMonth,
                                                 final int endSeasonDay,
                                                 final int endSeasonMonth) {
        if (endSeasonMonth == endAnalyzeMonth) {
            return isBeforeSameMonth(endSeasonDay, endAnalyzeDay);
        }
        return (endAnalyzeMonth >= startSeasonMonth && endAnalyzeDay <= endSeasonDay) || (endAnalyzeMonth < endSeasonMonth && endAnalyzeDay <= endSeasonDay);
    }

    private static boolean isAfterSameMonth(final int startSeasonDay,
                                            final int startAnalyzeDay) {
        return startAnalyzeDay >= startSeasonDay;
    }

    private static boolean isBeforeSameMonth(final int endSeasonDay,
                                             final int endAnalyzeDay) {
        return endAnalyzeDay <= endSeasonDay;
    }
}