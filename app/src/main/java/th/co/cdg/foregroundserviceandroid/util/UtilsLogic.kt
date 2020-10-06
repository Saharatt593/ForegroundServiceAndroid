package th.co.cdg.foregroundserviceandroid.util

import org.joda.time.DateTime
import org.joda.time.chrono.BuddhistChronology
import org.joda.time.format.DateTimeFormat

import java.util.*
import java.util.regex.Pattern

class UtilsLogic {

    companion object {

        @JvmStatic
        fun xmlEmptyString(data: String?): String? {
            return if (data.isNullOrEmpty() || data.isNullOrBlank()) {
                "-"
            } else {
                data
            }
        }

        @JvmStatic
        fun dateToDate(year: Int, monthOfYear: Int, dayOfMonth: Int): String {
            val df = DateTimeFormat.forPattern("dd/MM/yyyy")
            return df.print(DateTime(year, monthOfYear, dayOfMonth, 0, 0, 0))
        }

        @JvmStatic
        fun convertToDateStringThaiTime(date: Long?): String {
            return if (date != null && date != 0L) {
                val fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
                val dateTime = DateTime(date)
                val dtBuddhist = dateTime.withChronology(BuddhistChronology.getInstance())
                fmt.print(dtBuddhist)
            } else {
                ""
            }
        }

        @JvmStatic
        fun convertToDateStringNoTime(date: Long?): String {
            return if (date != null && date != 0L) {
                val df = DateTimeFormat.forPattern("dd/MM/yyyy")
                df.print(date)
            } else {
                ""
            }
        }

        @JvmStatic
        fun convertToDateStringThai(date: Long?): String {
            return if (date != null && date != 0L) {
                val fmt = DateTimeFormat.forPattern("dd/MM/yyyy")
                val dateTime = DateTime(date)
                val dtBuddhist = dateTime.withChronology(BuddhistChronology.getInstance())
                fmt.print(dtBuddhist)
            } else {
                ""
            }
        }

        @JvmStatic
        fun convertToDateStringThaiTitle(date: Date?): String {
            return if (date != null) {
                val dateTime = DateTime(date)
                val dtBuddhist = dateTime.withChronology(BuddhistChronology.getInstance())
                dtBuddhist.toString("MMMM-yyyy", Locale("th", "TH"))
            } else {
                ""
            }
        }


        @JvmStatic
        fun convertStringToDateStringThaiAndTime(
            date: String?,
            startTime: String,
            endTime: String
        ): String {
            return if (date != null) {
                val dateTime = DateTime(getDateByLong(convertStringToDateLong(date)))
                val dtBuddhist = dateTime.withChronology(BuddhistChronology.getInstance())
                val stringDate = dtBuddhist.toString("dd/MMMM/yyyy", Locale("th", "TH"))
                val startTime = startTime.substring(0, 2) + "." + startTime.substring(2, 4) + " น."
                val endTime = endTime.substring(0, 2) + "." + endTime.substring(2, 4) + " น."

                "วันที่ $stringDate เวลา $startTime - $endTime"
            } else {
                ""
            }
        }


        @JvmStatic
        fun convertStringToDateFormatISO(date: String?): String {
            return if (date != null) {
                val fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
                val format = DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss Z")
                format.print(fmt.parseDateTime(date))
            } else {
                ""
            }
        }

        fun convertStringToDateLong(date: String?): Long {
            return if (date != null) {
                val df = DateTimeFormat.forPattern("dd/MM/yyyy")
                df.parseMillis(date)
            } else {
                0
            }
        }

        @JvmStatic
        fun dateToStirng(date: Date?): String? {
            return if (date != null) {
                val df = DateTimeFormat.forPattern("dd/MM/yyyy")
                df.print(date.time)
            } else ""
        }

        @JvmStatic
        fun convertLongToString(number: Long?): String {
            return number?.toString() ?: ""
        }

        @JvmStatic
        fun convertDoubleToString(number: Double?): String? {
            return if (number != null) {
                String.format("%.2f", number)
            } else {
                ""
            }
        }

        @JvmStatic
        fun numberThousandFormat(data: Long?): String? {
            return if (data != null) {
                String.format("%,d", data)
            } else {
                ""
            }
        }

        @JvmStatic
        fun numberFormat(numberString: String?): String {
            return if (numberString != null) {
                try {
                    String.format("%,.2f", numberString.toFloat())
                } catch (e: java.lang.Exception) {
                    ""
                }
            } else {
                ""
            }

        }

        @JvmStatic
        fun numberFormatToString(numberString: String?): String {
            return if (numberString != null) {
                try {
                    numberString.replace(",", "")
                } catch (e: java.lang.Exception) {
                    ""
                }
            } else {
                ""
            }
        }

        @JvmStatic
        fun getTimeWithColon(data: String?): String {
            return if (data != null && data != "") {
                val hr = data.substring(0, 2)
                val min = data.substring(2)
                "$hr:$min"
            } else {
                ""
            }

        }

        fun checkPID(id: String?): Boolean {
            if (id.isNullOrEmpty() || id.length != 13) {
                return false
            }
            var sum = 0
            for (i in 0..11) {
                sum += Integer.parseInt(id[i].toString()) * (13 - i)
            }
            return id[12] - '0' == (11 - sum % 11) % 10
        }

        fun checkEmail(emailStr: String?): Boolean {
            if (emailStr.isNullOrEmpty()) {
                return false
            }
            val re: Pattern = Pattern.compile(Constants.REGEX_EMAIL, Pattern.CASE_INSENSITIVE)
            return re.matcher(emailStr).find()

        }

        fun checNumber(numberStr: String?): Boolean {
            if (numberStr.isNullOrEmpty()) {
                return false
            }
            val re: Pattern = Pattern.compile(Constants.REGEX_NUMBER, Pattern.CASE_INSENSITIVE)
            return re.matcher(numberStr).find()

        }

        fun getDateByLong(date: Long?): Date {
            return if (date == null) Date() else Date(date)
        }
    }
}