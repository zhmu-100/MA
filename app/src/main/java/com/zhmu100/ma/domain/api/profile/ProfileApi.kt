package com.zhmu100.ma.domain.api.profile

import com.zhmu100.ma.domain.model.profile.ListFollowersResponse
import com.zhmu100.ma.domain.model.profile.ListFollowingResponse
import com.zhmu100.ma.domain.model.profile.ListProfilesResponse
import com.zhmu100.ma.domain.model.profile.UserProfile

/**
 * API для общения с сервисом профиля
 */
interface ProfileApi {
    /**
     * Получить свой профиль по токену
     */
    suspend fun getMyProfile(): UserProfile
    /**
     * Получить профиль по id
     */
    suspend fun getProfileById(id: String): UserProfile
    /**
     * Получить список из всех профилей с пагинацией
     */
    suspend fun listProfiles(page: Int, pageSize: Int): ListProfilesResponse
    /**
     * Создать новый профиль
     */
    suspend fun createProfile(profile: UserProfile): UserProfile
    /**
     * Обновить данные своего профиля
     */
    suspend fun updateProfile(id: String, profile: UserProfile): UserProfile
    /**
     * Удалить свой профиль
     */
    suspend fun deleteProfile(id: String)
    /**
     * Стать подписчиком другого профиля
     */
    suspend fun follow(followeeId: String)
    /**
     * Перестать подписчиком другого профиля
     */
    suspend fun unfollow(followeeId: String)
    /**
     * Получить список подписчиков профиля с пагинацией
     */
    suspend fun listFollowers(userId: String, page: Int, pageSize: Int): ListFollowersResponse
    /**
     * Получить список подписок профиля с пагинацией
     */
    suspend fun listFollowing(userId: String, page: Int, pageSize: Int): ListFollowingResponse
}