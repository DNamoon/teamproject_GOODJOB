package com.goodjob.admin.postpaging;

import com.goodjob.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 오성훈 22.10.30
 */
public interface AdminPostService {

    Page<Post> findPostList(Pageable pageable);
}
