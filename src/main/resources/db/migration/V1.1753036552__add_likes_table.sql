CREATE TABLE IF NOT EXISTS m2m_user_likes (
      user_id BIGINT NOT NULL,
      post_id BIGINT NOT NULL,
      PRIMARY KEY (user_id, post_id)
);

