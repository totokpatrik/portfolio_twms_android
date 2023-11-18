package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OutboundShipment(
    val id: Number,
    val name: String,
    val outboundShipmentStatus: String,
    val location: Location
) : Parcelable
