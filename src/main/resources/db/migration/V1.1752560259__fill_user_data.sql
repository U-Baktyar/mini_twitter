INSERT INTO users (login, password, type_user) VALUES
           ('user1', 'pass1', 'PERSON'),
           ('user2', 'pass2', 'PERSON'),
           ('org11',  'pass3', 'ORGANIZATION'),
           ('org22',  'pass4', 'ORGANIZATION');

INSERT INTO person_info (user_id, first_name, last_name, br_data) VALUES
          (1, 'John', 'Doe', '1990-01-01'),
          (2, 'Jane', 'Smith', '1985-05-15');

INSERT INTO organization_info (user_id, organizationname, organizationtype, br_data) VALUES
         (3, 'OpenAI', 'Tech', '2015-12-11'),
         (4, 'SpaceX', 'Aerospace', '2002-05-06');

