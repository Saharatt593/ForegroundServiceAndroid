package th.co.cdg.foregroundserviceandroid.util

class Constants {
    companion object {

        const val HTTP_OK = 200
        const val HTTP_UNAUTHORIZED = 401
        const val HTTP_FORBIDDEN = 403
        const val HTTP_ERROR = 400
        const val HTTP_NOCONTENT = 204
        const val HTTP_INTERNAL_ERROR = 500
        const val SERVER_DOWN = 503

        const val VIEWTYPE_NORMAL = 0x0200
        const val VIEWTYPE_TITLE = 0x0201
        const val VIEWTYPE_DETAIL = 0x0202
        const val VIEWTYPE_EMPTY = 0x0204
        const val VIEWTYPE_OFFLINE = 0x0205
        const val VIEWTYPE_READONLY = 0x0206

        const val WEB_POLICY = "/register/pdpa"
        const val DEVICE = "android"

        const val LIMIT_IMAGE = 100
        const val LIMIT_PDF = 150

        const val TRANSCRIPT = "transcript.jpg"
        const val PHOTO ="stre@m_upload.jpg"
        const val REPORT ="report.pdf"

        const val BLANK = ""

        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

        const val REGEX_EMAIL = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        const val REGEX_NUMBER = "^[0-9]+\$"
        const val REGEX_PASSWORD = "(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+).{7,20}$"

    }
}