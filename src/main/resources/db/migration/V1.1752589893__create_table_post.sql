CREATE TABLE post (
      id BIGSERIAL PRIMARY KEY,
      user_id BIGINT NOT NULL,
      topic VARCHAR(50) NOT NULL,
      text VARCHAR NOT NULL,
      tags VARCHAR,
      create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
      CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);
