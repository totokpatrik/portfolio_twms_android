package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: Number,
    val name: String,
    val locationType: LocationType?,
    val maximumQuantity: Number,
    val pendingQuantity: Number,
    val currentQuantity: Number,
    val inventories: List<Inventory>?
) : Parcelable
