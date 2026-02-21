CREATE TABLE IF NOT EXISTS tracks (
    id BIGSERIAL PRIMARY KEY,
    artist_id BIGINT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    audio_url TEXT,
    image_url TEXT,
    funding_goal DECIMAL(19, 2),
    current_funding DECIMAL(19, 2) DEFAULT 0
);
