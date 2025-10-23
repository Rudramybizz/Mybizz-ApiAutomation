package smbedition.organization.util;

import org.testng.annotations.DataProvider;
import java.util.Map;
import java.util.HashMap;

public class FiscalYearDataProvider {

    @DataProvider(name = "validFiscalYearData")
    public static Object[][] validFiscalYearData() {
        return new Object[][] {
                // Valid Indian fiscal year
                {
                        createMap(
                                "default_date_format", "DD/MM/YYYY",
                                "timezone_id", "Asia/Kolkata",
                                "maintain_books_of_accounts_from", "01/04/2024",
                                "default_language_id", "en-US",
                                "default_currency_id", "INR",
                                "allow_multi_currency_transactions", false,
                                "manage_exchange_rates", "not_applicable",
                                "allow_decimals", true,
                                "no_of_decimals_allowed", "2",
                                "number_format", "12,34,567.89",
                                "fiscal_year_start_month", "04",
                                "fiscal_year_start_day", "01",
                                "fiscal_year_end_month", "03",
                                "fiscal_year_end_day", "31"
                        )
                },
                // US fiscal year
                {
                        createMap(
                                "default_date_format", "MM/DD/YYYY",
                                "timezone_id", "America/New_York",
                                "maintain_books_of_accounts_from", "01/01/2024",
                                "default_language_id", "en-US",
                                "default_currency_id", "USD",
                                "allow_multi_currency_transactions", true,
                                "manage_exchange_rates", "manual",
                                "allow_decimals", true,
                                "no_of_decimals_allowed", "2",
                                "number_format", "1,234,567.89",
                                "fiscal_year_start_month", "01",
                                "fiscal_year_start_day", "01",
                                "fiscal_year_end_month", "12",
                                "fiscal_year_end_day", "31"
                        )
                },
                // European format
                {
                        createMap(
                                "default_date_format", "DD.MM.YYYY",
                                "timezone_id", "Europe/Berlin",
                                "maintain_books_of_accounts_from", "01.01.2024",
                                "default_language_id", "de-DE",
                                "default_currency_id", "EUR",
                                "allow_multi_currency_transactions", true,
                                "manage_exchange_rates", "automatic",
                                "allow_decimals", true,
                                "no_of_decimals_allowed", "2",
                                "number_format", "1.234.567,89",
                                "fiscal_year_start_month", "01",
                                "fiscal_year_start_day", "01",
                                "fiscal_year_end_month", "12",
                                "fiscal_year_end_day", "31"
                        )
                }
        };
    }

    @DataProvider(name = "invalidFiscalYearData")
    public static Object[][] invalidFiscalYearData() {
        return new Object[][] {
                // Missing required fields
                {createMap("default_date_format", "DD/MM/YYYY")},

                // Invalid date format
                {createMap(
                        "default_date_format", "INVALID_FORMAT",
                        "timezone_id", "Asia/Kolkata",
                        "maintain_books_of_accounts_from", "01/04/2024"
                )},

                // Invalid timezone
                {createMap(
                        "default_date_format", "DD/MM/YYYY",
                        "timezone_id", "Invalid/Timezone",
                        "maintain_books_of_accounts_from", "01/04/2024"
                )},

                // Invalid date
                {createMap(
                        "default_date_format", "DD/MM/YYYY",
                        "timezone_id", "Asia/Kolkata",
                        "maintain_books_of_accounts_from", "32/13/2024" // Invalid date
                )},

                // Invalid currency
                {createMap(
                        "default_date_format", "DD/MM/YYYY",
                        "timezone_id", "Asia/Kolkata",
                        "maintain_books_of_accounts_from", "01/04/2024",
                        "default_currency_id", "INVALID_CURRENCY"
                )},

                // Invalid decimals
                {createMap(
                        "default_date_format", "DD/MM/YYYY",
                        "timezone_id", "Asia/Kolkata",
                        "maintain_books_of_accounts_from", "01/04/2024",
                        "allow_decimals", true,
                        "no_of_decimals_allowed", "5" // Too many decimals
                )},

                // Invalid fiscal year dates
                {createMap(
                        "default_date_format", "DD/MM/YYYY",
                        "timezone_id", "Asia/Kolkata",
                        "maintain_books_of_accounts_from", "01/04/2024",
                        "fiscal_year_start_month", "13", // Invalid month
                        "fiscal_year_start_day", "01",
                        "fiscal_year_end_month", "03",
                        "fiscal_year_end_day", "31"
                )}
        };
    }

    @DataProvider(name = "edgeCaseFiscalYearData")
    public static Object[][] edgeCaseFiscalYearData() {
        return new Object[][] {
                // Minimum decimals
                {
                        createMap(
                                "default_date_format", "DD/MM/YYYY",
                                "timezone_id", "Asia/Kolkata",
                                "maintain_books_of_accounts_from", "01/04/2024",
                                "default_language_id", "en-US",
                                "default_currency_id", "INR",
                                "allow_multi_currency_transactions", false,
                                "manage_exchange_rates", "not_applicable",
                                "allow_decimals", true,
                                "no_of_decimals_allowed", "0",
                                "number_format", "12,34,567",
                                "fiscal_year_start_month", "04",
                                "fiscal_year_start_day", "01",
                                "fiscal_year_end_month", "03",
                                "fiscal_year_end_day", "31"
                        )
                },
                // No decimals allowed
                {
                        createMap(
                                "default_date_format", "DD/MM/YYYY",
                                "timezone_id", "Asia/Kolkata",
                                "maintain_books_of_accounts_from", "01/04/2024",
                                "default_language_id", "en-US",
                                "default_currency_id", "JPY",
                                "allow_multi_currency_transactions", false,
                                "manage_exchange_rates", "not_applicable",
                                "allow_decimals", false,
                                "no_of_decimals_allowed", "0",
                                "number_format", "12,34,567",
                                "fiscal_year_start_month", "04",
                                "fiscal_year_start_day", "01",
                                "fiscal_year_end_month", "03",
                                "fiscal_year_end_day", "31"
                        )
                },
                // Leap year scenario
                {
                        createMap(
                                "default_date_format", "DD/MM/YYYY",
                                "timezone_id", "Asia/Kolkata",
                                "maintain_books_of_accounts_from", "01/04/2024",
                                "default_language_id", "en-US",
                                "default_currency_id", "INR",
                                "allow_multi_currency_transactions", false,
                                "manage_exchange_rates", "not_applicable",
                                "allow_decimals", true,
                                "no_of_decimals_allowed", "2",
                                "number_format", "12,34,567.89",
                                "fiscal_year_start_month", "02",
                                "fiscal_year_start_day", "29",
                                "fiscal_year_end_month", "02",
                                "fiscal_year_end_day", "28"
                        )
                }
        };
    }

    // Helper method to create maps easily
    private static Map<String, Object> createMap(Object... keyValues) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put((String) keyValues[i], keyValues[i + 1]);
        }
        return map;
    }
}