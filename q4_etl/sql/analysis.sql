-- Question 4.3 - Create table
DROP TABLE IF EXISTS hn_posts;
CREATE TABLE hn_posts (
  id             BIGINT PRIMARY KEY,
  title          TEXT,
  site           TEXT,
  post_type      VARCHAR(10),
  points         INT,
  comments       INT,
  age_hours      NUMERIC(10,2),
  points_imputed BOOLEAN,
  scraped_at     TIMESTAMP
);

-- Question 4.3 - Import cleaned CSV
-- \copy hn_posts FROM 'hn_posts_clean.csv' WITH (FORMAT csv, HEADER true);

-- Question 4.3 - Verify load
SELECT COUNT(*) AS rows_loaded FROM hn_posts;

-- Question 4.3 - Analysis 1
SELECT site,
       COUNT(*)                  AS posts,
       ROUND(AVG(points), 1)     AS avg_points,
       ROUND(AVG(comments), 1)   AS avg_comments
FROM hn_posts
WHERE post_type = 'story'
GROUP BY site
ORDER BY avg_points DESC
LIMIT 10;

-- Question 4.3 - Analysis 2
SELECT CASE WHEN age_hours < 3  THEN '1) 0-3 hours'
            WHEN age_hours < 12 THEN '2) 3-12 hours'
            ELSE '3) 12+ hours' END                        AS age_band,
       COUNT(*)                                             AS posts,
       ROUND(AVG(points), 1)                                AS avg_points,
       ROUND(AVG(points / NULLIF(age_hours, 0)), 2)         AS points_per_hour,
       ROUND(CORR(points, comments)::numeric, 2)            AS points_comments_corr
FROM hn_posts
WHERE post_type = 'story'
GROUP BY 1
ORDER BY 1;
