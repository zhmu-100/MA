package com.zhmu100.ma.domain.api.profile

import com.zhmu100.ma.domain.model.profile.ListFollowersResponse
import com.zhmu100.ma.domain.model.profile.ListFollowingResponse
import com.zhmu100.ma.domain.model.profile.ListProfilesResponse
import com.zhmu100.ma.domain.model.profile.UserProfile
import java.util.UUID
import kotlin.math.min

class ProfileApiMock : ProfileApi {
    // Хранилище профилей в памяти
    private val profiles = mutableMapOf<String, UserProfile>()

    // Отношения подписок (followerId to followeeId)
    private val followRelations = mutableSetOf<Pair<String, String>>()

    // Текущий авторизованный пользователь
    private var currentUserId: String? = null

    init {
        // Инициализация тестовыми данными
        val testProfile1 = UserProfile(
            id = "user1",
            name = "Test User 1",
            email = "user1@test.com",
            followerCount = 10,
            followingCount = 5
        )
        val testProfile2 = UserProfile(
            id = "user2",
            name = "Test User 2",
            email = "user2@test.com",
            followerCount = 5,
            followingCount = 10
        )
        profiles[testProfile1.id] = testProfile1
        profiles[testProfile2.id] = testProfile2

        // Тестовые подписки
        followRelations.add("user1" to "user2")
    }

    override suspend fun getMyProfile(): UserProfile {
        return currentUserId?.let { profiles[it] }
            ?: throw IllegalStateException("Not authenticated")
    }

    override suspend fun getProfileById(id: String): UserProfile {
        return profiles[id] ?: throw IllegalArgumentException("Profile not found")
    }

    override suspend fun listProfiles(page: Int, pageSize: Int): ListProfilesResponse {
        val allProfiles = profiles.values.toList()
        val from = (page - 1) * pageSize
        val to = min(from + pageSize, allProfiles.size)

        return ListProfilesResponse(
            profiles = allProfiles.subList(from, to),
            total = allProfiles.size,
            page = page,
            pageSize = pageSize
        )
    }

    override suspend fun createProfile(profile: UserProfile): UserProfile {
        val newId = UUID.randomUUID().toString()
        val newProfile = profile.copy(id = newId)
        profiles[newId] = newProfile
        currentUserId = newId
        return newProfile
    }

    override suspend fun updateProfile(id: String, profile: UserProfile): UserProfile {
        if (!profiles.containsKey(id)) {
            throw IllegalArgumentException("Profile not found")
        }
        val updatedProfile = profile.copy(id = id)
        profiles[id] = updatedProfile
        return updatedProfile
    }

    override suspend fun deleteProfile(id: String) {
        profiles.remove(id)
        // Удаляем все связанные подписки
        followRelations.removeAll { it.first == id || it.second == id }
        if (currentUserId == id) {
            currentUserId = null
        }
    }

    override suspend fun follow(followeeId: String) {
        val followerId = currentUserId ?: throw IllegalStateException("Not authenticated")
        if (!profiles.containsKey(followeeId)) {
            throw IllegalArgumentException("Profile to follow not found")
        }

        followRelations.add(followerId to followeeId)
        updateFollowersCount(followeeId, 1)
        updateFollowingCount(followerId, 1)
    }

    override suspend fun unfollow(followeeId: String) {
        val followerId = currentUserId ?: throw IllegalStateException("Not authenticated")
        if (!profiles.containsKey(followeeId)) {
            throw IllegalArgumentException("Profile to unfollow not found")
        }

        if (followRelations.remove(followerId to followeeId)) {
            updateFollowersCount(followeeId, -1)
            updateFollowingCount(followerId, -1)
        }
    }

    override suspend fun listFollowers(
        userId: String,
        page: Int,
        pageSize: Int
    ): ListFollowersResponse {
        if (!profiles.containsKey(userId)) {
            throw IllegalArgumentException("Profile not found")
        }

        val followers = followRelations
            .filter { it.second == userId }
            .map { it.first }

        val from = (page - 1) * pageSize
        val to = min(from + pageSize, followers.size)

        return ListFollowersResponse(
            followerIds = followers.subList(from, to),
            total = followers.size,
            page = page,
            pageSize = pageSize
        )
    }

    override suspend fun listFollowing(
        userId: String,
        page: Int,
        pageSize: Int
    ): ListFollowingResponse {
        if (!profiles.containsKey(userId)) {
            throw IllegalArgumentException("Profile not found")
        }

        val following = followRelations
            .filter { it.first == userId }
            .map { it.second }

        val from = (page - 1) * pageSize
        val to = min(from + pageSize, following.size)

        return ListFollowingResponse(
            followingIds = following.subList(from, to),
            total = following.size,
            page = page,
            pageSize = pageSize
        )
    }

    private fun updateFollowersCount(userId: String, delta: Int) {
        profiles[userId]?.let { profile ->
            profiles[userId] = profile.copy(followerCount = profile.followerCount + delta)
        }
    }

    private fun updateFollowingCount(userId: String, delta: Int) {
        profiles[userId]?.let { profile ->
            profiles[userId] = profile.copy(followingCount = profile.followingCount + delta)
        }
    }

    // Методы для управления mock-данными (для тестов)
    fun setCurrentUser(id: String?) {
        currentUserId = id?.takeIf { profiles.containsKey(it) }
    }

    fun clearData() {
        profiles.clear()
        followRelations.clear()
        currentUserId = null
    }

    fun addTestProfile(profile: UserProfile) {
        profiles[profile.id] = profile
    }

    fun getFollowRelations(): Set<Pair<String, String>> {
        return followRelations.toSet()
    }
}