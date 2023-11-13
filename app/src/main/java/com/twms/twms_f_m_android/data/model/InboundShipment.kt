package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InboundShipment(
    val id: Number,
    val name: String,
    val inboundShipmentStatus: String,
    val location: Location?,
) : Parcelable
