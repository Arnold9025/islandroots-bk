-- Add status column to blogs table if it doesn't exist
-- (Hibernate will add it automatically if ddl-auto: update is set, but this is safe)
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='blogs' AND column_name='status') THEN
        ALTER TABLE blogs ADD COLUMN status VARCHAR(20) DEFAULT 'PUBLISHED';
    END IF;
END $$;

-- Insert 3 sample blog posts
INSERT INTO blogs (id, title, content, image_url, author, published_at, status) VALUES 
(gen_random_uuid(), 'The Healing Power of Sea Moss', 'Sea moss, also known as Irish moss, is a type of red algae that is packed with 92 of the 102 minerals our bodies need. It is great for boosting the immune system, improving digestion, and supporting thyroid health.', 'https://images.unsplash.com/photo-1599305090598-fe179d501227', 'Dr. Sebi Inspired', NOW(), 'PUBLISHED'),
(gen_random_uuid(), 'Top 5 Herbs for Energy and Vitality', 'Feeling sluggish? These 5 herbs can help boost your energy naturally: 1. Ashwagandha, 2. Maca Root, 3. Ginseng, 4. Rhodiola, 5. Cordyceps.', 'https://images.unsplash.com/photo-1544367567-0f2fcb009e0b', 'Herbalist Jane', NOW(), 'PUBLISHED'),
(gen_random_uuid(), 'Understanding Detoxification', 'Detoxifying the body is a natural process, but we can support it through clean eating, hydration, and specific herbs like Burdock root and Dandelion root.', 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd', 'Nature Lover', NOW(), 'DRAFT');
