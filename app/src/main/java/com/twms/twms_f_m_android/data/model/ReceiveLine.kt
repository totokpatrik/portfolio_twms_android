package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiveLine(
    val inboundOrderName: String,
    val inboundOrderLineId: Number,
    val item: Item,
    val receivedQuantity: Number,
    val expectedQuantity: Number
) : Parcelable
