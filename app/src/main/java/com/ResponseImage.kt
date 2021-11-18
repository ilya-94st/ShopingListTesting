package com

data class ResponseImage(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)