package com.dolu.protorakuten.core.domain.model

data class RakutenImage(
    val links: List<String>,
    val size: String? = null,
    val id: Long? = null
)