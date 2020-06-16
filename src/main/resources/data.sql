INSERT INTO role (name) VALUES
('UNCONFIRMEDUSER'),
('USER'),
('ADMIN');

INSERT INTO users (email, username, password, role_id) VALUES
('admin@messageApp.com','Admin','$2y$12$8LGseeE.VlxJo0NyGNZHM.xJAcmtLht6y5mmRIFp/xOxAn1aMg362',3),
('lol@xd.com','Adam33', '$2y$12$8LGseeE.VlxJo0NyGNZHM.xJAcmtLht6y5mmRIFp/xOxAn1aMg362', 2);

