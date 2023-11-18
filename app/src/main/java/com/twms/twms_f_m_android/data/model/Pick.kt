package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pick(
    val id: Number,
    val pickPalletNumber: String,
    val outboundShipmentStatus: String,
    val quantity: Number,
    val sourceLocation: Location,
    val destinationLocation: Location
) : Parcelable
