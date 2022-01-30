package com.example.techbook.common

import org.junit.Test


class UtilsTest {

    @Test
    fun `isValidCollegeYear()`() {
        val year = "2020"
        val correctYear = "2020-2024"
        assert(!Utils.isValidCollegeYear(year))
        assert(Utils.isValidCollegeYear(correctYear))
    }
}