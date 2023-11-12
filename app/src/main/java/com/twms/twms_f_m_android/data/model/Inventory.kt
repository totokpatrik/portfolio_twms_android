package com.twms.twms_f_m_android.data.model

import java.util.UUID

data class Inventory(
    val id: UUID,
    val palletNumber: String,
    val items: List<Item>,
    val location: Location,
    val quantity: Int
)
