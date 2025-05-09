package com.example.hackathon.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import com.example.hackathon.model.ImageInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import { Request, Response } from 'express'
import fs from 'fs'
import path from 'path'

@Controller
class HomeController {
    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("message", "Hello, Kotlin + Spring Boot!")
        return "home"
    }

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

    fun getHome(req: Request, res: Response) {
        res.render('home', { message: 'Hello, Node.js + Express!' })
    }

    fun getImages(req: Request, res: Response) {
        try {
            val jsonPath = path.join(__dirname, '../../public/image/images.json')
            val jsonData = fs.readFileSync(jsonPath, 'utf-8')
            val images: List<ImageInfo> = JSON.parse(jsonData)
            res.render('images', { images })
        } catch (error: Exception) {
            console.error('Error reading images:', error)
            res.status(500).render('error')
        }
    }
}