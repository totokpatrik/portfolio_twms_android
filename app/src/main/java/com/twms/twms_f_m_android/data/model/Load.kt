package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Load(
    val id: Number,
    val acknowledged: Boolean,
    val outboundShipment: OutboundShipment
) : Parcelable
