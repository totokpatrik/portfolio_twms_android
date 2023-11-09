package com.twms.twms_f_m_android.data.model

import java.util.Date

data class CustomResponse<T>(
    val pageNumber: Number,
    val pageSize: Number,
    val totalPages: Number,
    val totalRecords: Number,
    val data: T?,
    val success: Boolean,
    val message: String
)