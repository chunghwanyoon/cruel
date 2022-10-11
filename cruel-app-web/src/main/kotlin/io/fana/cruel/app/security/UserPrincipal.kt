package io.fana.cruel.app.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    val userId: Long,
    val role: UserRole,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = userId.toString()
    override fun getPassword(): String = ""
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
}

fun fromToken(userId: Long, userRole: UserRole): UserPrincipal =
    UserPrincipal(userId, userRole, listOf(SimpleGrantedAuthority(userRole.role)))
