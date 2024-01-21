package com.greengrow.plantdiary.controller;

import com.greengrow.plantdiary.model.Comment;
import com.greengrow.plantdiary.model.Post;
import com.greengrow.plantdiary.service.CommentService;
import com.greengrow.plantdiary.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private final PostService postService;

    public CommentController(PostService postService) {
        this.postService = postService;
    }

    // 댓글 저장 엔드포인트
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.saveComment(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PostMapping("/comment/{postId}")
    public String addComment(@PathVariable Long postId, Comment comment, RedirectAttributes redirectAttributes) {
        // 여기서 comment 객체는 사용자가 입력한 댓글 내용을 포함합니다.
        // postId를 사용하여 해당 게시글을 찾고 댓글을 해당 게시글에 연결합니다.

        Post post = postService.findPostById(postId);
        comment.setPost(post);
        commentService.saveComment(comment);


        // 리다이렉트 속성에 메시지 추가 (선택 사항)
        redirectAttributes.addFlashAttribute("message", "댓글이 추가되었습니다.");

        // 원래 게시글 페이지로 리다이렉트
        return "redirect:/user/post/" + postId;
    }

}
