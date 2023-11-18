package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InventoryLine (
    val id: Number,
    val item: Item,
    val quantity: Number,
    val pendingQuantity: Number
): Parcelable
