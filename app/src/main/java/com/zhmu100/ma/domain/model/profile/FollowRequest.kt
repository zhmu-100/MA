package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Запрос на подписку одного пользователя на другого.
 *
 * @property followerId ID пользователя, который подписывается.
 * @property followeeId ID пользователя, на которого подписываются.
 */
@Serializable
data class FollowRequest(val followerId: String, val followeeId: String)