DROP TABLE IF EXISTS video_card;
DROP TABLE IF EXISTS video_card;
CREATE TABLE IF NOT EXISTS video_card
(
    id           BIGINT       NOT NULL auto_increment,
    href         varchar(250) NOT NULL,
    name         varchar(250) NOT NULL,
    price        REAL       NOT NULL,
    power        REAL       NOT NULL,
    hashrate     REAL       NOT NULL,
    daily_profit REAL       NOT NULL,
    PRIMARY KEY (id)
);


