package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Запрос на подписку одного пользователя на другого.
 *
 * @property follower_id ID пользователя, который подписывается.
 * @property followee_id ID пользователя, на которого подписываются.
 */
@Serializable
data class FollowRequest(
    val follower_id: String,
    val followee_id: String
)