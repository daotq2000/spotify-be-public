-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th12 16, 2021 lúc 10:32 AM
-- Phiên bản máy phục vụ: 8.0.27
-- Phiên bản PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `spotify_123`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `albums`
--

CREATE TABLE `albums` (
  `id` int NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `album_time_length` float DEFAULT NULL,
  `download_permission` tinyint(1) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `total_listen` int DEFAULT NULL,
  `genres_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `albums`
--

INSERT INTO `albums` (`id`, `album_name`, `album_time_length`, `download_permission`, `image`, `release_date`, `total_listen`, `genres_id`) VALUES
(1, 'Anh sai rồi', 1008.09, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639644874/g2en4moubz8htv1v5e7t.jpg', '2021-12-16 08:54:35.229000', 0, 1),
(2, 'Anh sẽ yêu em từ phía sau', 939.024, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639645094/d9i65gflvqemti25nlpy.jpg', '2021-12-16 08:58:14.298000', 0, 1),
(3, 'Cuộc gọi nhỡ', 784.67, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639645267/auszoppqicxcpfa7b0qd.jpg', '2021-12-16 09:01:08.240000', 0, 1),
(4, 'Hạ còn vương nắng', 285.672, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639645317/l0fzn20gqddc8vtqxyrz.jpg', '2021-12-16 09:01:58.318000', 0, 2),
(5, 'Yêu thương ngày đó', 662.831, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639645452/ial229c8vx6dtwfmfur3.jpg', '2021-12-16 09:04:12.997000', 0, 1),
(6, 'Người lạ ơi', 445.457, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639645549/pbdxptorqlswc0duo0g4.jpg', '2021-12-16 09:05:49.668000', 0, 1),
(7, 'Thay Lòng', 280.477, 0, 'https://res.cloudinary.com/daotq1/image/upload/v1639646821/pwfkcefqmgtnu5dcpyqa.jpg', '2021-12-16 09:27:01.624000', 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `album_songs`
--

CREATE TABLE `album_songs` (
  `songs_id` int NOT NULL,
  `albums_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `album_songs`
--

INSERT INTO `album_songs` (`songs_id`, `albums_id`) VALUES
(1, 3),
(2, 3),
(3, 3),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 5),
(9, 5),
(10, 5),
(11, 6),
(12, 6),
(13, 2),
(14, 2),
(15, 2),
(17, 2),
(19, 4),
(20, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `artists`
--

CREATE TABLE `artists` (
  `id` int NOT NULL,
  `birth_day` date DEFAULT NULL,
  `country_active` varchar(255) DEFAULT NULL,
  `description` text,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `artists`
--

INSERT INTO `artists` (`id`, `birth_day`, `country_active`, `description`, `full_name`, `gender`, `image`) VALUES
(1, '1994-06-16', 'Việt Nam', 'Sơn Tùng MTP là 1 ca sĩ tài năng', 'Sơn Tùng MTP', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639643540/poa7agk9f6jinmj8sucl.jpg'),
(2, '1996-06-16', 'Việt Nam', 'Anh Tú Perfect', 'Vương Anh Tú', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639642940/fcreim1iok8hxnotuqja.jpg'),
(3, '1996-11-11', 'Việt Nam', '', 'Soobin Hoàng Sơn', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639643023/mwspjrdeyaxyn3p99pfs.jpg'),
(4, '1989-11-11', 'Việt Nam', '', 'Mr.Siro', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639643077/q0ywszjad3x8szdczdvq.jpg'),
(5, '1989-11-11', 'Việt Nam', '', 'Lê Bảo Bình', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639643109/ompmi7a5bfxm6eqny0tw.jpg'),
(6, '1997-11-16', 'Việt Nam', '', 'Orange', b'1', 'https://res.cloudinary.com/daotq1/image/upload/v1639643169/mbxmm8v846x0pduwaawi.jpg'),
(7, '1997-11-08', 'Việt Nam', '', 'DatKa', b'0', 'https://res.cloudinary.com/daotq1/image/upload/v1639643208/tjnxif9h0aljfq1r0lar.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `artist_albums`
--

CREATE TABLE `artist_albums` (
  `artists_id` int NOT NULL,
  `albums_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `artist_albums`
--

INSERT INTO `artist_albums` (`artists_id`, `albums_id`) VALUES
(1, 1),
(2, 3),
(3, 5),
(4, 2),
(6, 6),
(7, 4),
(7, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `artist_songs`
--

CREATE TABLE `artist_songs` (
  `song_id` int NOT NULL,
  `artist_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `artist_songs`
--

INSERT INTO `artist_songs` (`song_id`, `artist_id`) VALUES
(1, 2),
(2, 2),
(3, 2),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 3),
(9, 3),
(10, 3),
(11, 6),
(12, 6),
(13, 4),
(14, 4),
(15, 4),
(16, 1),
(17, 1),
(18, 5),
(19, 7),
(20, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `genres`
--

CREATE TABLE `genres` (
  `id` int NOT NULL,
  `genres_name` varchar(255) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `genres`
--

INSERT INTO `genres` (`id`, `genres_name`, `image`) VALUES
(1, 'Tình Yêu', 'https://res.cloudinary.com/daotq1/image/upload/v1639642066/tidwu1kwntqudqxqfzab.jpg'),
(2, 'Trữ Tình', 'https://res.cloudinary.com/daotq1/image/upload/v1639642093/j4kcmquzhnqtl9pvg2cz.jpg'),
(3, 'EDM', 'https://res.cloudinary.com/daotq1/image/upload/v1639642248/k2tqkx8ny9iadfvxxokt.jpg'),
(4, 'Dancing', 'https://res.cloudinary.com/daotq1/image/upload/v1639642295/milrepu8kepfndm2s8fx.jpg'),
(5, 'Thư  giãn', 'https://res.cloudinary.com/daotq1/image/upload/v1639641316/nrfer6vis3pmiznu6uuu.jpg'),
(6, 'Rock', 'https://res.cloudinary.com/daotq1/image/upload/v1639642345/xv2bmtlva76pqavqoh9i.jpg'),
(7, 'Tình Yêu', 'https://res.cloudinary.com/daotq1/image/upload/v1639641980/yavxvgxlkoyzts6dfjlo.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `history_listens`
--

CREATE TABLE `history_listens` (
  `id` int NOT NULL,
  `count_listen` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `songs_id` int DEFAULT NULL,
  `users_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `play_lists`
--

CREATE TABLE `play_lists` (
  `id` int NOT NULL,
  `favorite_order` int DEFAULT NULL,
  `play_list_name` varchar(255) DEFAULT NULL,
  `users_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `play_list_songs`
--

CREATE TABLE `play_list_songs` (
  `play_list_id` int NOT NULL,
  `songs_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `role_name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `songs`
--

CREATE TABLE `songs` (
  `id` int NOT NULL,
  `count_listen` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `download_permission` bit(1) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `lyrics` text,
  `media_url` varchar(255) DEFAULT NULL,
  `time_play` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `genres_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `songs`
--

INSERT INTO `songs` (`id`, `count_listen`, `description`, `download_permission`, `image`, `lyrics`, `media_url`, `time_play`, `title`, `genres_id`) VALUES
(1, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643315/beidohbwrokfhdm5zzbr.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643316/orfvubjvqbsjomzauszs.mp3', '289.645714', 'Giúp anh trả lời những câu hỏi', 1),
(2, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643430/szvyjorjaywxisfy3w99.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643431/vlj3szjmr45bjilxkoe4.mp3', '225.696', 'Cuộc gọi nhỡ', 1),
(3, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643476/m1nisjroo8rp8afecegt.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643476/np4zdd9lh5vmoxr5ydhi.mp3', '269.328', 'Suốt đời không xứng', 1),
(4, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643587/l7wvy8fk01hzcifteblb.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643587/boiyrmnapunuiiigjxce.mp3', '291.291429', 'Âm thầm bên em', 1),
(5, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643682/b2pa2gjvsmydfsqpnez6.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643683/agdwy0nphmlrz90xa4rt.mp3', '262.19102', 'Anh sai rồi', 1),
(6, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643731/pzdi907eqsafkc9yv670.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643732/hxjtyvb53js83ly1mmut.mp3', '221.570612', 'Như ngày hôm qua', 1),
(7, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643885/mv8ujtpb4cdgwcysnijg.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643886/ryqfg3uoowwtlh8jxbfm.mp3', '233.04', 'Chúng ta không thuộc về nhau', 1),
(8, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639643977/qzetzn7nqc2l6wqeqveb.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639643977/dprlusvxijjz8zsuplh5.mp3', '219.193469', 'Phía sau một cô gái', 1),
(9, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644023/y6ugcrmk0fhkruhqoltv.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644024/aiwelqvloqmm094tnhn3.mp3', '262.608938', 'Yêu thương ngày đó', 1),
(10, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644188/xt6zukv8sskyqg0qd7yj.png', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644189/vqlhjj3mxokpny87srva.mp3', '181.028571', 'Tháng năm', 1),
(11, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644258/kffg7bdg98psgqqz6l3m.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644259/u6xbzap4nc2ttvpvljnf.mp3', '217.312653', 'Người lạ ơi', 1),
(12, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644299/kph7pj7voxhsoao67fhg.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644300/onwbna6bxme2wly062tj.mp3', '228.144', 'Khi em lớn', 1),
(13, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644398/bjdxfok2svzs1lcxyfyj.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644398/f5a3jwhikogykkm9qiwg.mp3', '245.70775', 'Vô hình trong tim em', 1),
(14, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644443/mrwntrjv16cjhkc7saoi.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644444/sif4dqf24jozh2pceaug.mp3', '211.2', 'Em gái mưa', 1),
(15, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644500/oozaac0g1z5psyk054ye.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644500/smlh2nlqr4t4t1eydths.mp3', '272.457143', 'Tìm được nhau khó thế nào', 1),
(16, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644554/qqxtw64ajjxagn5cjlhe.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644555/fz3ldbjhx0vbuy7kiezd.mp3', '298.788571', 'Day dứt nỗi đau', 1),
(17, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644601/p5sbi21fcx7lq8thoicm.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644602/q2p7obaa88qnwvfepkc0.mp3', '209.658776', 'Anh sẽ mạnh mẽ để yêu em', 1),
(18, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644697/s2yffn1azxpv0osehfpk.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644697/gb48ay01dtagkm2u5isi.mp3', '345.077551', 'Sai cách yêu', 1),
(19, 1, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639644768/rhkl8smown3vayou8zku.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639644769/dr9wmspumzaomgqgv9mx.mp3', '285.672', 'Hạ còn vương nắng', 2),
(20, 0, NULL, NULL, 'https://res.cloudinary.com/daotq1/image/upload/v1639646782/aurfho2waqtn9l4bb4pi.jpg', '', 'https://res.cloudinary.com/daotq1/video/upload/v1639646782/btmzkl3yfasghej0a41q.mp3', '280.476735', 'Thay lòng', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `active_status` int DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `roles_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `active_status`, `avatar`, `birth_day`, `email`, `first_name`, `gender`, `last_name`, `password`, `phone_number`, `username`, `roles_id`) VALUES
(1, 1, NULL, '2000-12-10', 'daotq1@fsoft.com.vn', 'Dao', b'0', 'Quang Tran', '$2a$10$m50spRMlb6SUZupGkTGBKuRGBSFQoEFUaZfoEV5gMGPthUvv.1Ksa', '0374550289', 'daotq1', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_files`
--

CREATE TABLE `user_files` (
  `id` int NOT NULL,
  `file_name` varchar(500) DEFAULT NULL,
  `file_size` float DEFAULT NULL,
  `users_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `albums`
--
ALTER TABLE `albums`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc807g9wahqlt9so0e3ycnfrfl` (`genres_id`);

--
-- Chỉ mục cho bảng `album_songs`
--
ALTER TABLE `album_songs`
  ADD PRIMARY KEY (`albums_id`,`songs_id`),
  ADD KEY `FKb6tiocd1a6oywi5uiv74xsejo` (`songs_id`);

--
-- Chỉ mục cho bảng `artists`
--
ALTER TABLE `artists`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `artist_albums`
--
ALTER TABLE `artist_albums`
  ADD PRIMARY KEY (`albums_id`,`artists_id`),
  ADD KEY `FKfmmualu3hfcrkmjmtsagp3fyg` (`artists_id`);

--
-- Chỉ mục cho bảng `artist_songs`
--
ALTER TABLE `artist_songs`
  ADD PRIMARY KEY (`artist_id`,`song_id`),
  ADD KEY `FKghy77yc4orfkkx2s4rw5xrrvc` (`song_id`);

--
-- Chỉ mục cho bảng `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `history_listens`
--
ALTER TABLE `history_listens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcauoaxpecc6enfvq52lmcqykn` (`songs_id`),
  ADD KEY `FKrvs7k21isotgl43klbwr8dqrj` (`users_id`);

--
-- Chỉ mục cho bảng `play_lists`
--
ALTER TABLE `play_lists`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKikrr3ymjp4ift7679cpx0d5il` (`users_id`);

--
-- Chỉ mục cho bảng `play_list_songs`
--
ALTER TABLE `play_list_songs`
  ADD PRIMARY KEY (`play_list_id`,`songs_id`),
  ADD KEY `FKnycdf7k62sev9avlrj9buhibi` (`songs_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtjnptsjlp0p1u7ufn15ite4ar` (`genres_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbgvg7xuekkcqmpvi3tgkxk85j` (`roles_id`);

--
-- Chỉ mục cho bảng `user_files`
--
ALTER TABLE `user_files`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK15kex35wybpqy3dmcga7hk92h` (`users_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `albums`
--
ALTER TABLE `albums`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `artists`
--
ALTER TABLE `artists`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `history_listens`
--
ALTER TABLE `history_listens`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `play_lists`
--
ALTER TABLE `play_lists`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `songs`
--
ALTER TABLE `songs`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `user_files`
--
ALTER TABLE `user_files`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `albums`
--
ALTER TABLE `albums`
  ADD CONSTRAINT `FKc807g9wahqlt9so0e3ycnfrfl` FOREIGN KEY (`genres_id`) REFERENCES `genres` (`id`);

--
-- Các ràng buộc cho bảng `album_songs`
--
ALTER TABLE `album_songs`
  ADD CONSTRAINT `FKb6tiocd1a6oywi5uiv74xsejo` FOREIGN KEY (`songs_id`) REFERENCES `songs` (`id`),
  ADD CONSTRAINT `FKc1509byyo0ln7h65fafx2bcqp` FOREIGN KEY (`albums_id`) REFERENCES `albums` (`id`);

--
-- Các ràng buộc cho bảng `artist_albums`
--
ALTER TABLE `artist_albums`
  ADD CONSTRAINT `FKfmmualu3hfcrkmjmtsagp3fyg` FOREIGN KEY (`artists_id`) REFERENCES `artists` (`id`),
  ADD CONSTRAINT `FKta24krbs0pre47h56bouogipr` FOREIGN KEY (`albums_id`) REFERENCES `albums` (`id`);

--
-- Các ràng buộc cho bảng `artist_songs`
--
ALTER TABLE `artist_songs`
  ADD CONSTRAINT `FK5efi80orcsmiqe37okfe11sif` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`),
  ADD CONSTRAINT `FKghy77yc4orfkkx2s4rw5xrrvc` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`);

--
-- Các ràng buộc cho bảng `history_listens`
--
ALTER TABLE `history_listens`
  ADD CONSTRAINT `FKcauoaxpecc6enfvq52lmcqykn` FOREIGN KEY (`songs_id`) REFERENCES `songs` (`id`),
  ADD CONSTRAINT `FKrvs7k21isotgl43klbwr8dqrj` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `play_lists`
--
ALTER TABLE `play_lists`
  ADD CONSTRAINT `FKikrr3ymjp4ift7679cpx0d5il` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `play_list_songs`
--
ALTER TABLE `play_list_songs`
  ADD CONSTRAINT `FKhmoeouscsabnvqm5mvr3uiyqk` FOREIGN KEY (`play_list_id`) REFERENCES `play_lists` (`id`),
  ADD CONSTRAINT `FKnycdf7k62sev9avlrj9buhibi` FOREIGN KEY (`songs_id`) REFERENCES `songs` (`id`);

--
-- Các ràng buộc cho bảng `songs`
--
ALTER TABLE `songs`
  ADD CONSTRAINT `FKtjnptsjlp0p1u7ufn15ite4ar` FOREIGN KEY (`genres_id`) REFERENCES `genres` (`id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKbgvg7xuekkcqmpvi3tgkxk85j` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`);

--
-- Các ràng buộc cho bảng `user_files`
--
ALTER TABLE `user_files`
  ADD CONSTRAINT `FK15kex35wybpqy3dmcga7hk92h` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
