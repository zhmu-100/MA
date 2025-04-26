package com.zhmu100.ma.domain.api.auth

import com.zhmu100.ma.domain.model.profile.ListFollowersResponse
import com.zhmu100.ma.domain.model.profile.ListFollowingResponse
import com.zhmu100.ma.domain.model.profile.ListProfilesResponse
import com.zhmu100.ma.domain.model.profile.UserProfile

interface ProfileApi {
    suspend fun getMyProfile(): UserProfile
    suspend fun getProfileById(id: String): UserProfile
    suspend fun listProfiles(page: Int, pageSize: Int): ListProfilesResponse
    suspend fun createProfile(profile: UserProfile): UserProfile
    suspend fun updateProfile(id: String, profile: UserProfile): UserProfile
    suspend fun deleteProfile(id: String)
    suspend fun follow(followeeId: String)
    suspend fun unfollow(followeeId: String)
    suspend fun listFollowers(userId: String, page: Int, pageSize: Int): ListFollowersResponse
    suspend fun listFollowing(userId: String, page: Int, pageSize: Int): ListFollowingResponse
}