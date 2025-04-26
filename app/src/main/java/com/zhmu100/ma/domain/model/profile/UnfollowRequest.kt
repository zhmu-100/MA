package com.zhmu100.ma.domain.model.profile

/**
 * Запрос на отписку одного пользователя от другого.
 *
 * @property followerId ID пользователя, который отписывается.
 * @property followeeId ID пользователя, от которого отписываются.
 */
data class UnfollowRequest(val followerId: String, val followeeId: String)