package com.zhmu100.ma.domain.model.profile

/**
 * Запрос на подписку одного пользователя на другого.
 *
 * @property followerId ID пользователя, который подписывается.
 * @property followeeId ID пользователя, на которого подписываются.
 */
data class FollowRequest(val followerId: String, val followeeId: String)