package io.fana.cruel.app.security

import io.fana.cruel.app.common.logger
import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.AuthenticationFailedException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserArgumentResolver : HandlerMethodArgumentResolver {
    private val log = logger()

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.parameterType == LoginUser::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): LoginUser {
        try {
            return extractLoginUserFromHeader(webRequest)
        } catch (exception: Exception) {
            log.warn(exception.message)
            throw AuthenticationFailedException(ErrorCode.UNAUTHORIZED_USER)
        }
    }

    private fun extractLoginUserFromHeader(webRequest: NativeWebRequest): LoginUser =
        LoginUser(
            id = extractLoginUserInfoFromHeader(webRequest, LoginUserAuthHeader.USER_ID).toLong(),
            role = UserRole.from(extractLoginUserInfoFromHeader(webRequest, LoginUserAuthHeader.USER_ROLE))
        )

    private fun extractLoginUserInfoFromHeader(webRequest: NativeWebRequest, header: LoginUserAuthHeader): String =
        webRequest.getHeader(header.key)
            ?: throw AuthenticationFailedException(
                ErrorCode.UNAUTHORIZED_USER,
                "${LoginUserAuthHeader.USER_ID.key} header not found"
            )
}
