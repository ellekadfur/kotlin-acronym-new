package com.app.acronyms.data.network.model

import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("sf") var sf: String? = null,
    @SerializedName("lfs") var lfs: List<Lf>? = null
)