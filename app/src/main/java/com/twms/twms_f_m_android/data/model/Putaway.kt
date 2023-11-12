package com.twms.twms_f_m_android.data.model
data class Putaway(
    val id: Number,
    val destinationLocation: Location?,
    val assignedUser: User?,
    val inventory: Inventory
)
