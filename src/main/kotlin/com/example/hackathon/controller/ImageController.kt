package com.example.hackathon.controller

import com.example.hackathon.model.ImageInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1")  // API 버전을 명시적으로 지정
class ImageController {

    @GetMapping("/images")
    fun showImages(model: Model): String {
        return try {
            val mapper = jacksonObjectMapper()
            val resource = ClassPathResource("image/images.json")
            val images: List<ImageInfo> = mapper.readValue(resource.inputStream)
            model.addAttribute("images", images)
            "images"
        } catch (e: Exception) {
            e.printStackTrace()
            "error"
        }
    }
} 