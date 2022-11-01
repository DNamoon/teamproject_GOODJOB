package com.goodjob.admin.postpaging;

import com.goodjob.post.Post;
import com.goodjob.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 오성훈 22.10.30
 */
@Service
@RequiredArgsConstructor
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> findPostList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
