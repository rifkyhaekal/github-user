package com.example.haekalgithubuserapp.model

import androidx.annotation.DrawableRes

/**
 * Data class for parameter in function MainInfo
 *
 * @param visible boolean data type, used for determine visible or not
 * @param infoText string data type, used for determine what text is displayed in MainInfo
 * @param infoImg integer data type, used for determine what drawbles/ picture displayed in MainInfo
 */
data class MainInfoData(
    var visible: Boolean = false,
    var infoText: String? = null,
    @DrawableRes var infoImg: Int? = -1
)
