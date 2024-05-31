CREATE TABLE IF NOT EXISTS veriety_person (
                                              id SERIAL PRIMARY KEY,
                                              veriety VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS status_person (
                                             id SERIAL PRIMARY KEY,
                                             status VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS person (
                                      id SERIAL PRIMARY KEY,
                                      veriety_id BIGINT NOT NULL,
                                      status_id BIGINT NOT NULL,
                                      inn VARCHAR(12) NOT NULL,
                                      type VARCHAR(255) NOT NULL,
                                      shifer VARCHAR(255) NOT NULL,
                                      data DATE NOT NULL,
                                      FOREIGN KEY (veriety_id) REFERENCES veriety_person (id),
                                      FOREIGN KEY (status_id) REFERENCES status_person (id)
);

CREATE TABLE IF NOT EXISTS email_person (
                                            id SERIAL PRIMARY KEY,
                                            person_id BIGINT NOT NULL,
                                            email VARCHAR(255) NOT NULL UNIQUE,
                                            FOREIGN KEY (person_id) REFERENCES person (id)
);

CREATE TABLE IF NOT EXISTS phone_person (
                                            id SERIAL PRIMARY KEY,
                                            person_id BIGINT NOT NULL,
                                            phone VARCHAR(15) NOT NULL UNIQUE,
                                            FOREIGN KEY (person_id) REFERENCES person (id)
);
