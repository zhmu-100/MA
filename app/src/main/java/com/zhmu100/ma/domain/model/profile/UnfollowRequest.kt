package com.zhmu100.ma.domain.model.profile

import kotlinx.serialization.Serializable

/**
 * Запрос на отписку одного пользователя от другого.
 *
 * @property followerId ID пользователя, который отписывается.
 * @property followeeId ID пользователя, от которого отписываются.
 */
@Serializable
data class UnfollowRequest(val followerId: String, val followeeId: String)