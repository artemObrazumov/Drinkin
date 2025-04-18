package com.artemObrazumov.drinkin.account.domain.models

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)