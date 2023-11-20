package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Putaway(
    val id: Number,
    val destinationLocation: Location?,
    val assignedUser: User?,
    val inventory: Inventory,
    val sourcLocation: Location?
) : Parcelable
