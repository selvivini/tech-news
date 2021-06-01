package com.technews.controller;

import com.technews.models.Posts;
import com.technews.models.User;
import com.technews.models.Vote;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
    public class PostController {

        @Autowired
        PostRepository repository;

        @Autowired
        VoteRepository voteRepository;

        @Autowired
        UserRepository userRepository;

        @GetMapping("/api/posts")
        public List<Posts> getAllPosts() {
            List<Posts> postList = repository.findAll();
            for (Posts p : postList) {
                p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            }
            return postList;
        }


        @GetMapping("/api/posts/{id}")
        public Posts getPost(@PathVariable Integer id) {
            Posts returnPost = repository.getById(id);
            returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));

            return returnPost;
        }


        @PostMapping("/api/posts")
        @ResponseStatus(HttpStatus.CREATED)
        public Posts addPost(@RequestBody Posts post) {
            repository.save(post);
            return post;
        }


        @PutMapping("/api/posts/{id}")
        public Posts updatePost(@PathVariable int id, @RequestBody Posts post) {
            Posts tempPost = repository.getById(id);
            tempPost.setTitle(post.getTitle());
            return repository.save(tempPost);
        }


        @PutMapping("/api/posts/upvote")
        public String addVote(@RequestBody Vote vote, HttpServletRequest request) {
            String returnValue = "";

            if(request.getSession(false) != null) {
                Posts returnPost = null;

                User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
                vote.setUserId(sessionUser.getId());
                voteRepository.save(vote);

                returnPost = repository.getOne(vote.getPostId());
                returnPost.setVoteCount(voteRepository.countVotesByPostId(vote.getPostId()));

                returnValue = "";
            } else {
                returnValue = "login";
            }

            return returnValue;
        }


        @DeleteMapping("/api/posts/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deletePost(@PathVariable int id) {
            repository.deleteById(id);
        }
    }

