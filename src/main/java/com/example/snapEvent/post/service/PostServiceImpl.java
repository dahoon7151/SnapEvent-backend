package com.example.snapEvent.post.service;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.post.dto.PostDto;
import com.example.snapEvent.post.dto.PostListDto;
import com.example.snapEvent.post.repository.PostRepository;
import com.example.snapEvent.common.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Transactional
    @Override
    public Page<Post> sortPostlist(int page, int postCount, String order) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (order.equals("recent")) {
            sorts.add(Sort.Order.desc("createdBy"));
        } else if (order.equals("old")) {
            sorts.add(Sort.Order.asc("createdBy"));
        } else if (order.equals("comment")) {
                sorts.add(Sort.Order.desc("comment"));
        } else if (order.equals("like")) {
            sorts.add(Sort.Order.desc("like"));
        }
        Pageable pageable = PageRequest.of(page, postCount, Sort.by(sorts));

        return this.postRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void writePost(Member member, PostDto postDto) {
        Post post = postRepository.save(postDto.toEntity(member));
        log.info("글 작성 완료 {}", post.getId());
    }
}
