package com.twms.twms_f_m_android.data.model

data class Receive(
    val inboundOrderLineId: Number,
    val palletNumber: String?,
    val itemId: Number,
    val quantity: Number
)
