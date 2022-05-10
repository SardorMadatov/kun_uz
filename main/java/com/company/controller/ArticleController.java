package com.company.controller;

import com.company.dto.ArticleDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto, HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, pId));
    }

    @GetMapping("/public/list")
    public ResponseEntity<?> listByLang(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(articleService.list(page, size));
    }

    @GetMapping("/public/type/{id}")
    public ResponseEntity<?> getByType(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getArticleByType(typeId));
    }


    @GetMapping("/public/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer articleId,
                                     @RequestHeader(name = "Accepted-Language", defaultValue = "uz") LangEnum lang) {
        return ResponseEntity.ok(articleService.getByIdPublished(articleId, lang));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ArticleDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.update(id, dto, pId));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }
    @GetMapping("/{lang}/share/{id}")
    public ResponseEntity<?> shared(@PathVariable("lang") LangEnum lang, @PathVariable("id") Integer id) {

    }
}
