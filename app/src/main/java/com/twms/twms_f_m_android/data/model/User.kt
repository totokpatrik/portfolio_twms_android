package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val userName: String
) : Parcelable
