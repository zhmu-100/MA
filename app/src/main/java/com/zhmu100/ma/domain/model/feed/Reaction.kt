package com.zhmu100.ma.domain.model.feed

import kotlinx.serialization.Serializable

@Serializable
enum class Reaction {
    REACTION_UNSPECIFIED,
    REACTION_LIKE,
    REACTION_LOVE,
    REACTION_HAHA,
    REACTION_WOW,
    REACTION_SAD,
    REACTION_ANGRY
}