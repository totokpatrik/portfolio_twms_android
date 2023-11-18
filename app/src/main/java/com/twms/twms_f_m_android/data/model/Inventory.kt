package com.twms.twms_f_m_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Inventory(
    val id: UUID,
    val palletNumber: String,
    val inventoryLines: List<InventoryLine>,
    val location: Location?,
    val quantity: Int
) : Parcelable
