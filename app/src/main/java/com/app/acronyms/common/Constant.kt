package com.app.acronyms.common

class Constant {
    object ErrorConstant {
        const val GENERIC_ERROR_MESSAGE = "Something Went wrong. Please try again"
    }

    object NetworkConstant{
        const val BASE_URL = "http://www.nactem.ac.uk/"

        object EndPoint{
            const val ACRONYM_SEARCH = "software/acromine/dictionary.py"
        }
    }
}