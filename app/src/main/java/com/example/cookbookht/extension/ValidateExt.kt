package com.example.cookbookht.extension

import java.util.regex.Pattern

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "gmail" +
            "(" +
            "\\." +
            "com" +
            ")+"
)

val NO_WHITE_SPACE_PATTERN: Pattern = Pattern.compile(
    "\\A\\w{4,20}\\z"
)

val PASS_HOA_PATTERN: Pattern = Pattern.compile(
    "(?=.*[A-Z])"
)

val PASS_NUMBER_PATTERN: Pattern = Pattern.compile(
    "(?=.*[0-9])"
)
fun CharSequence.noWhiteSpace(): Boolean{
    return NO_WHITE_SPACE_PATTERN.matcher(this).matches()
}

fun CharSequence.passwordHoa(): Boolean{
    return PASS_HOA_PATTERN.matcher(this).matches()
}

fun CharSequence.passwordNumber(): Boolean{
    return PASS_NUMBER_PATTERN.matcher(this).matches()
}
