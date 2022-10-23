package com.pearlbailey.commontools.web

import com.pearlbailey.commontools.web.model.CreatedResourceResponse
import org.springframework.web.client.RestTemplate

abstract class AbstractWebService(private val baseUrl: String, open val restTemplate: RestTemplate) {

    abstract fun getResourceName(): String

    inline fun <reified T> getResourceById(id: Int) = restTemplate.getForObject(getUrl(id), T::class.java)

    fun createResource(createResourceRequest: Any) =
        restTemplate.postForObject(baseUrl, createResourceRequest, CreatedResourceResponse::class.java)!!

    inline fun <reified T> updateResource(id: Int, patchTeacherDto: Any) =
        restTemplate.patchForObject(getUrl(id), patchTeacherDto, T::class.java)

    inline fun <reified T> getAllResources() = restTemplate.getForObject(getUrlForResource(), Array<T>::class.java)

    fun getUrlForResource() = "$baseUrl/${getResourceName()}"

    fun getUrl(id: Int): String = "${getUrlForResource()}/$id"

}