
CREATE TABLE if not exists organizers (
                                          id INT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255)
);

CREATE TABLE if not exists events (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      event_name VARCHAR(255),
                                      organizer_id INT,
                                      FOREIGN KEY (organizer_id) REFERENCES organizers(id)
);

CREATE TABLE if not exists visitors (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255)
);

CREATE TABLE if not exists event_visitors (
                                              event_id INT,
                                              visitor_id INT,
                                              PRIMARY KEY (event_id, visitor_id),
                                              FOREIGN KEY (event_id) REFERENCES events(id),
                                              FOREIGN KEY (visitor_id) REFERENCES visitors(id)
);


INSERT INTO organizers (name) VALUES ('Organizer One');
INSERT INTO organizers (name) VALUES ('Organizer Two');

INSERT INTO events (event_name, organizer_id) VALUES ('Event One', 1);
INSERT INTO events (event_name, organizer_id) VALUES ('Event Two', 2);

INSERT INTO visitors (name) VALUES ('Visitor One');
INSERT INTO visitors (name) VALUES ('Visitor Two');
INSERT INTO visitors (name) VALUES ('Visitor Three');

INSERT INTO event_visitors (event_id, visitor_id) VALUES (1, 1);
INSERT INTO event_visitors (event_id, visitor_id) VALUES (1, 2);
INSERT INTO event_visitors (event_id, visitor_id) VALUES (2, 3);