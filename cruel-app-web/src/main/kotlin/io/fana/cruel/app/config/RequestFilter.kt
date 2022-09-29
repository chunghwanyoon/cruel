package io.fana.cruel.app.config

import io.fana.cruel.app.common.logger
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/internal/api/*", "/api/v1/*"])
@Component
class RequestFilter : Filter {
    private val log = logger()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val requestWrapper = ContentCachingRequestWrapper((request as HttpServletRequest))
        val responseWrapper = ContentCachingResponseWrapper((response as HttpServletResponse))

        val start = System.currentTimeMillis()
        chain.doFilter(requestWrapper, responseWrapper)
        val end = System.currentTimeMillis()

        val requestBody = getRequestBody(requestWrapper)
        getResponseBody(responseWrapper)

        if (responseWrapper.status >= 400) {
            log.error(
                """{} - {} {} - {}ms {} {}
                    |request: {}
                """.trimMargin(),
                request.method,
                request.requestURI,
                responseWrapper.status,
                end - start,
                request.remoteAddr,
                getHeaders(request),
                requestBody
            )
        } else {
            log.info(
                "{} - {} {} - {}ms {} {}",
                request.method,
                request.requestURI,
                responseWrapper.status,
                end - start,
                request.remoteAddr,
                getHeaders(request),
            )
        }
    }

    private fun getHeaders(request: HttpServletRequest): Map<Any, Any> {
        val headerMap = HashMap<Any, Any>()
        headerMap[USER_AGENT] = request.getHeader(USER_AGENT)
        val clientIp = request.getHeader(FORWARDED_FOR)
            ?: request.getHeader(FORWARDED_FOR_UPPERCASE)
        if (clientIp != null) {
            headerMap[FORWARDED_FOR] = clientIp
        }
        return headerMap
    }

    private fun getRequestBody(request: ContentCachingRequestWrapper): String {
        val wrapper = WebUtils.getNativeRequest(
            request,
            ContentCachingRequestWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                return try {
                    String(buf, 0, buf.size, Charset.defaultCharset())
                } catch (e: UnsupportedEncodingException) {
                    EMPTY_BODY
                }
            }
        }
        return EMPTY_BODY
    }

    @Throws(IOException::class)
    private fun getResponseBody(response: HttpServletResponse): String {
        var payload: String? = null
        val wrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                payload = String(buf, 0, buf.size, Charset.defaultCharset())
                wrapper.copyBodyToResponse()
            }
        }
        return payload ?: EMPTY_BODY
    }

    companion object {
        private const val USER_AGENT = "user-agent"
        private const val FORWARDED_FOR = "X-Forwarded-For"
        private const val FORWARDED_FOR_UPPERCASE = "X-FORWARDED-FOR"
        private const val EMPTY_BODY = " - "
    }
}
