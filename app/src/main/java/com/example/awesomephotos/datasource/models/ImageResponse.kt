package com.example.awesomephotos.datasource.models


data class Images(
    val alt_description: String,
    val blur_hash: String,
    val categories: List<String>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<String>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: String,
    val sponsorship: Sponsorship,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int,
    var flag: Boolean = false
)

data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
)

data class LinksX(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class LinksXX(
    val followers: String,
    val following: String,
    val html: String,
    val likes: String,
    val photos: String,
    val portfolio: String,
    val self: String
)

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)

data class ProfileImageX(
    val large: String,
    val medium: String,
    val small: String
)

data class Sponsor(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksX,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
)

data class Sponsorship(
    val impression_urls: List<String>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
)

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class User(
    val accepted_tos: Boolean,
    val bio: String,
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String?,
    val links: LinksXX,
    val location: String,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImageX,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String,
    val updated_at: String,
    val username: String
)