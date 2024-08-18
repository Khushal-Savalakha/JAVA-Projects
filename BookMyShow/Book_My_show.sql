CREATE TABLE movies_details(
    Movie_ID serial PRIMARY KEY,
    Movie_Title varchar(255),
    Movie_Description text,
    Showtime varchar(255),
    Screen integer,
    Running_Time varchar(255),
    Language varchar(255),
    Price varchar(255),
    Release_Date date,
    Ratings numeric,
    City_Id integer
);
select * from movies_details;
select * from MovieData;

CREATE TABLE cinema_details (
    Cinema_Id serial PRIMARY KEY,
    City_Id INT,
    City_Name VARCHAR(255),
    Cinema_Name VARCHAR(255) NOT NULL,
    Address TEXT
);



-- Insert more data if needed for Cinema_Id 140 and beyond.
select * from cinemaDetails;

DO $$ 
DECLARE
  -- Declare variables
  count INT := 0;
  i INT;
BEGIN
  -- Start a FOR loop
  FOR i IN 1..35
  LOOP
    -- Check if the count is equal to 3
    IF count = 3 THEN
      EXIT; -- Exit the loop if count reaches 3
    END IF;
  raise notice'i has value %',i;
    count := count + 1;

    -- Check another condition to exit the loop (e.g., reaching 35)
    IF i = 35 THEN
      EXIT; -- Exit the loop if i reaches 35
    END IF;
  END LOOP;
END $$;

create table CinemaSeats(sr_no serial,Screen_1 int,Screen_2 int,Screen_3 int,Screen_4 int,Screen_5 int,Screen_6 int);

do
$$
declare 
count int:=0;
begin
for a in 1..30 loop
insert into CinemaSeats(Screen_1,Screen_2,Screen_3,Screen_4,Screen_5,Screen_6) values(count,count,count,count,count,count);
end loop;
end
$$

drop table cinemaseats;
select * from cinemaseats;

alter table CinemaSeats add sr_no serial;

CREATE OR REPLACE FUNCTION Total_seats()
RETURNS INT AS
$$
DECLARE
  seats INT := 0;
  seat_data INT := 1;
  data INT;
BEGIN
  FOR a IN 1..30 LOOP
    SELECT screen_1 INTO data FROM CinemaSeats WHERE sr_no = a;
    IF data = 0 THEN
      seats := seats + 1;
    END IF;
  END LOOP;
  RETURN seats;
END;
$$ LANGUAGE plpgsql;

create or replace procedure Seats_details(seats1 OUT int,seats2 OUT int,seats3 OUT int)
LANGUAGE plpgsql AS  $$
DECLARE
  seats1 INT := 0;
  seats2 INT := 0;
  seats3 INT := 0;
  data1 INT;
  data2 INT;
  data3 INT;
BEGIN
  FOR a IN 1..30 LOOP
    SELECT screen_1 INTO data1 FROM CinemaSeats WHERE sr_no = a;
	SELECT screen_1 INTO data2 FROM CinemaSeats WHERE sr_no = a;
	SELECT screen_1 INTO data3 FROM CinemaSeats WHERE sr_no = a;
    IF data1 = 0 THEN
      seats1 := seats1 + 1;
	  END IF;
	IF data2 = 0 THEN
       seats2 := seats2 + 1;
    END IF;
	IF data3 = 0 THEN
      seats3 := seats3 + 1;
	END IF;
  END LOOP;
END;
$$

call Seats_details
select * from CinemaSeats;
select * from moviedata;
select * from cinemadetails;
select * from cinemadetails where address like '%Kankaria%';
select city_name,cinema_name from cinemadetails where cinema_id in(select cinema_id from cinemadetails where address like '%Raipur%');
select city_id,city_name from cinemadetails where cinema_name='Miraj City Pulse';
select movie_title,movie_description,language,showtime,price,running_time from moviedata where city_id in(select city_id from cinemadetails where cinema_name='Miraj City Pulse');





create table Cinemas as select * from cinemadetails;
drop table cinema;
-- Add a primary key constraint to the existing "Cinemas" table
ALTER TABLE Cinemas
ADD PRIMARY KEY (Cinema_ID);

CREATE TABLE Movies (
    Movie_ID serial PRIMARY KEY,
    Cinema_ID INT,
    Movie_Title VARCHAR(255),
    Movie_Description VARCHAR(255),
    Showtime VARCHAR(255),
    Screen INT,
    Running_Time VARCHAR(255),
    Language VARCHAR(255),
    Price VARCHAR(255),
    Release_Date DATE,
    Ratings FLOAT,
    FOREIGN KEY (Cinema_ID) REFERENCES Cinemas(Cinema_ID)
);
select * from Cinemas;
select * from movies;

do
$$
begin
for a in 1..16 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹ 160', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹ 185', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹ 210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹ 80', '2023-07-14', 8),
    (a, 'The Mummy', 'fantasy action-adventure film', '10:00 AM - 12:00 PM', 5, '110 minutes', 'Hindi', '₹ 230', '2023-09-05', 6.4),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 17..26 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹240', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹310', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹350', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹160', '2023-07-14', 8),
    (a, 'The Mummy', 'fantasy action-adventure film', '10:00 AM - 12:00 PM', 5, '110 minutes', 'Hindi', '₹ 330', '2023-09-05', 6.4),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 27..34 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹140', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹165', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹120', '2023-07-14', 8),
    (a, 'The Mummy', 'fantasy action-adventure film', '10:00 AM - 12:00 PM', 5, '110 minutes', 'Hindi', '₹ 210', '2023-09-05', 6.4),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 35..39 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹160', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹185', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹80', '2023-07-14', 8),
    (a, 'The Mummy', 'fantasy action-adventure film', '10:00 AM - 12:00 PM', 5, '110 minutes', 'Hindi', '₹ 230', '2023-09-05', 6.4),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 40..44 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹ 160', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹ 185', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹ 210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹ 80', '2023-07-14', 8),
    (a, 'The Mummy', 'fantasy action-adventure film', '10:00 AM - 12:00 PM', 5, '110 minutes', 'Hindi', '₹ 230', '2023-09-05', 6.4),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 45..54 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹ 160', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹ 185', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹ 210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹ 80', '2023-07-14', 8),
    (a, 'Bobby Jasoos', 'Hindi-language comedy drama', '8:00 AM - 10:45 AM', 3, '121 minutes', 'Hindi', '₹ 150', '21-09-2023', 7.9),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$

do
$$
begin
for a in 55..139 loop
-- Insert data into the "Movies" table
INSERT INTO Movies (Cinema_ID, Movie_Title, Movie_Description, Showtime, Screen, Running_Time, Language, Price, Release_Date, Ratings)
VALUES
    (a, 'Baahubali: The Beginning', 'epic action film', '7:00 AM - 9:00 AM', 1, '158 minutes', 'Hindi', '₹ 160', '2023-10-09', 8.5),
    (a, 'Dangal', 'biographical sports drama film', '7:00 AM - 10:05 AM', 2, '161 minutes', 'Hindi', '₹ 185', '2023-08-09', 9.5),
    (a, 'Inception', 'science fiction action film', '8:00 AM - 10:45 AM', 3, '148 minutes', 'Hindi', '₹ 210', '2023-09-21', 7.6),
    (a, 'Chaal Jeevi Laiye', 'comedy-drama road film', '9:00 AM - 11:30 AM', 4, '146 minutes', 'Gujarati', '₹ 80', '2023-07-14', 8),
    (a, 'Bobby Jasoos', 'Hindi-language comedy drama', '8:00 AM - 10:45 AM', 3, '121 minutes', 'Hindi', '₹ 150', '21-09-2023', 7.9),
    (a, '1920: Horrors of the Heart', 'horror film', '8:00 AM - 10:15 AM', NULL, '123 minutes', 'Hindi', NULL, '2023-10-10', NULL);
end loop;
end;
$$
	Hindi-language comedy drama	8:00 AM - 10:45 AM	3	121 minutes	Hindi	₹ 150	21-09-2023	7.9


select * from cinema_details;
select * from movies_details;
delete from movies where cinema_id>16;
1]
CREATE OR REPLACE PROCEDURE search_by_area(area_name text)
LANGUAGE PLPGSQL AS $$
DECLARE 
    c_id cinema_details.cinema_id%type;
	c_city_name cinema_details.city_name%type;
    c_cinema_name cinema_details.cinema_name%type;
    c_address cinema_details.address%type;
    cinema_data CURSOR FOR SELECT cinema_id, city_name, cinema_name, address FROM cinema_details WHERE address LIKE area_name;
BEGIN
    OPEN cinema_data;
    
    LOOP
        FETCH cinema_data INTO c_id, c_city_name, c_cinema_name, c_address;
        EXIT WHEN NOT FOUND;
        RAISE NOTICE 'Cinema ID: %  ,  City Name: %, Cinema Name: %  , Address: %', c_id, c_city_name, c_cinema_name, c_address;
    END LOOP;
    CLOSE cinema_data;
END;
$$;

CALL search_by_area('%Kankaria%');

select * from cinema_details;
select * from movies_details;
2]
CREATE OR REPLACE FUNCTION search_movie_name(movie_name text)
returns int
LANGUAGE PLPGSQL AS $$
DECLARE 
    m_movie_title movies_details.movie_title%type;
    m_movie_description movies_details.movie_description%type;
    m_running_time movies_details.running_time%type;
    m_language movies_details.language%type;
    m_release_date movies_details.release_date%type;
    m_ratings movies_details.ratings%type;
BEGIN
    SELECT movie_title, movie_description, running_time, language, release_date, ratings
    INTO m_movie_title, m_movie_description, m_running_time, m_language, m_release_date, m_ratings
    FROM movies_details
    WHERE movie_title LIKE '%Dangal%'
    LIMIT 1;

    IF FOUND THEN
        RAISE NOTICE 'Movie Title: %, Description: %, Running time: %, Language: %, Release Date: %, Ratings: %', m_movie_title, m_movie_description, m_running_time, m_language, m_release_date, m_ratings;
    ELSE
        RAISE NOTICE 'Movie not found.';
    END IF;
END;
$$;

select search_movie_name('%Dangal%');

/*DO $$ 
DECLARE 
    m_movie_title movies_details.movie_title%type;
    m_movie_description movies_details.movie_description%type;
    m_running_time movies_details.running_time%type;
    m_language movies_details.language%type;
    m_release_date movies_details.release_date%type;
    m_ratings movies_details.ratings%type;
BEGIN
    SELECT movie_title, movie_description, running_time, language, release_date, ratings
    INTO m_movie_title, m_movie_description, m_running_time, m_language, m_release_date, m_ratings
    FROM movies_details
    WHERE movie_title LIKE '%Dangal%'
    LIMIT 1;

    IF FOUND THEN
        RAISE NOTICE 'Movie Title: %, Description: %, Running time: %, Language: %, Release Date: %, Ratings: %', m_movie_title, m_movie_description, m_running_time, m_language, m_release_date, m_ratings;
    ELSE
        RAISE NOTICE 'Movie not found.';
    END IF;
END $$;
*/
3]-- Create a table to store deleted movie data
CREATE TABLE Deleted_Movie_Data AS
SELECT * FROM movies_details where movie_id=1000;
alter table Deleted_Movie_Data add column date_time timestamp
select * from Deleted_Movie_Data;
Truncate table Deleted_Movie_Data;
-- Create a trigger function to handle row deletions
CREATE OR REPLACE FUNCTION insert_deleted_movie_data()
RETURNS TRIGGER 
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO Deleted_Movie_Data VALUES ( OLD.Movie_ID,OLD.cinema_Id,OLD.movie_title,OLD.movie_description,OLD.showtime,OLD.Screen,OLD.Running_Time,OLD.language,OLD.Price,OLD.Release_Date,OLD.Ratings,now());      
    RETURN OLD;
END;
$$ ;

delete from movies_details where movie_id=1000;

CREATE OR REPLACE VIEW movie_details_view AS
SELECT * FROM movies_details where movie_id=1000;

insert into 
select * from movie_details_view

-- Create a trigger to activate the insert_deleted_movie_data function
CREATE TRIGGER insert_deleted_movie_trigger
AFTER DELETE ON movies_details
FOR EACH ROW
EXECUTE FUNCTION insert_deleted_movie_data();

4]
CREATE OR REPLACE FUNCTION calculate_total_price(num_tickets integer, ticket_price numeric)
RETURNS numeric
LANGUAGE PLPGSQL AS $$
BEGIN
  RETURN num_tickets * ticket_price;
END;
$$;

select calculate_total_price(4,200);

5]

-- Create a table to store customer data
CREATE TABLE customer (
    customer_id serial PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) unique,
    mobile_no varchar(15) unique,
    login_date_time timestamp
);
drop table customer;
CREATE OR REPLACE PROCEDURE validate_and_insert(f_name varchar(255), l_name varchar(255), gmail varchar(255), contact_no varchar(15))
LANGUAGE plpgsql AS $$
BEGIN
    -- Check if input contains 10 characters or "@gmail.com"
    IF LENGTH(f_name) > 2 AND LENGTH(l_name) > 2 AND gmail LIKE '%@gmail.com%' AND LENGTH(contact_no) = 10 THEN
        INSERT INTO customer(first_name, last_name, email, mobile_no, login_date_time)
        VALUES (f_name, l_name, gmail, contact_no, NOW()); 
    ELSE
        -- Raise a notice with a specific error message
        RAISE NOTICE 'Input does not meet the criteria:';
        IF LENGTH(f_name) <= 2 THEN
            RAISE NOTICE '  - First name should have more than 5 characters.';
        END IF;
        IF LENGTH(l_name) <= 2 THEN
            RAISE NOTICE '  - Last name should have more than 5 characters.';
        END IF;
        IF LENGTH(contact_no) != 10 THEN
            RAISE NOTICE '  - Contact number should have exactly 10 digits.';
        END IF;
        IF gmail NOT LIKE '%@gmail.com%' THEN
            RAISE NOTICE '  - Email should be a Gmail address.';
        END IF;
    END IF;
END;
$$;
select * from customer;
truncate table customer;

select * from customer;
6]
-- Create a table to store deleted customer data
CREATE TABLE deleted_customer_data (
    deleted_id serial PRIMARY KEY,
    customer_id integer,
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255),
    mobile_no varchar(15),
    delete_date timestamp
);

-- Create a trigger function to log deleted data
CREATE OR REPLACE FUNCTION log_deleted_customer_data()
RETURNS TRIGGER 
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO deleted_customer_data (customer_id, first_name, last_name, email, mobile_no, delete_date)
    VALUES (OLD.customer_id, OLD.first_name, OLD.last_name, OLD.email, OLD.mobile_no, NOW());
    RETURN OLD;
END;
$$;

drop function 

-- Create a trigger that activates on DELETE operations
CREATE TRIGGER customer_delete_trigger
before DELETE ON customer
FOR EACH ROW
EXECUTE FUNCTION log_deleted_customer_data();

select * from deleted_customer_data;
truncate table deleted_customer_data;
truncate table customer;
delete from customer where customer_id=28;
select * from customer;

/*CREATE TRIGGER customer_delete_trigger2
before TRUNCATE ON customer
FOR EACH STATEMENT
EXECUTE FUNCTION log_deleted_customer_data();*/

DROP TRIGGER customer_delete_trigger2 ON customer;



select * from customer;
truncate table customer;

-- Query 1
CALL validate_and_insert('John', 'Doe', 'john.doe@gmail.com', '1234567890');
-- Query 2
CALL validate_and_insert('Alice', 'Smith', 'alice.smith@gmail.com', '9876543210');
-- Query 3
CALL validate_and_insert('Bob', 'Johnson', 'bob.johnson@gmail.com', '5555555555');
-- Query 4
CALL validate_and_insert('Eva', 'Williams', 'eva.williams@gmail.com', '9999999999');
-- Query 5
CALL validate_and_insert('David', 'Brown', 'david.brown@gmail.com', '8888888888');
-- Query 6
CALL validate_and_insert('Samantha', 'White', 'samantha.white@gmail.com', '7777777777');
-- Query 7
CALL validate_and_insert('Michael', 'Clark', 'michael.clark@gmail.com', '6666666666');
-- Query 8
CALL validate_and_insert('Emily', 'Anderson', 'emily.anderson@gmail.com', '5555555554');
-- Query 9
CALL validate_and_insert('Daniel', 'Young', 'daniel.young@gmail.com', '4444444444');
-- Query 10
CALL validate_and_insert('Olivia', 'Harris', 'olivia.harris@gmail.com', '3333333333');
-- Query 11
CALL validate_and_insert('Sophia', 'Taylor', 'sophia.taylor@gmail.com', '2222222222');
-- Query 12
CALL validate_and_insert('Liam', 'Moore', 'liam.moore@gmail.com', '1111111111');
-- Query 13
CALL validate_and_insert('Emma', 'Wilson', 'emma.wilson@gmail.com', '1234567891'); -- Different mobile number
-- Query 14
CALL validate_and_insert('Mason', 'Jones', 'mason.jones@gmail.com', '9876543211'); -- Different mobile number
-- Query 15
CALL validate_and_insert('Ava', 'Brown', 'ava.brown@gmail.com', '5555555556'); -- Different mobile number
-- Query 16
CALL validate_and_insert('William', 'Davis', 'william.davis@gmail.com', '9999999998'); -- Different mobile number
-- Query 17
CALL validate_and_insert('Sofia', 'Miller', 'sofia.miller@gmail.com', '8888888887'); -- Different mobile number
-- Query 18
CALL validate_and_insert('Lucas', 'Wilson', 'lucas.wilson@gmail.com', '6666666667'); -- Different mobile number
-- Query 19
CALL validate_and_insert('Amelia', 'Martinez', 'amelia.martinez@gmail.com', '7777777776'); -- Different mobile number
-- Query 20
CALL validate_and_insert('Henry', 'Smith', 'henry.smith@gmail.com', '5555555557'); -- Different mobile number
-- Query 21
CALL validate_and_insert('Ella', 'Jones', 'ella.jones@gmail.com', '4444444445'); -- Different mobile number
-- Query 22
CALL validate_and_insert('Benjamin', 'Brown', 'benjamin.brown@gmail.com', '3333333334'); -- Different mobile number
-- Query 23
CALL validate_and_insert('Lily', 'Davis', 'lily.davis@gmail.com', '2222222223'); -- Different mobile number
-- Query 24
CALL validate_and_insert('James', 'Smith', 'james.smith@gmail.com', '1111111112'); -- Different mobile number
-- Query 25
CALL validate_and_insert('Oliver', 'Wilson', 'oliver.wilson@gmail.com', '1234567892'); -- Different mobile number
-- Query 26
CALL validate_and_insert('Sophie', 'Taylor', 'sophie.taylor@gmail.com', '9876543212'); -- Different mobile number
-- Query 27
CALL validate_and_insert('Lucas', 'Johnson', 'lucas.johnson@gmail.com', '5555555558'); -- Different mobile number
-- Query 28
CALL validate_and_insert('William', 'Moore', 'william.moore@gmail.com', '9999999997'); -- Different mobile number
-- Query 29
CALL validate_and_insert('Charlotte', 'Smith', 'charlotte.smith@gmail.com', '8888888886'); -- Different mobile number
-- Query 30
CALL validate_and_insert('Ethan', 'Brown', 'ethan.brown@gmail.com', '6666666668'); -- Different mobile number
-- Continue with additional queries...

create table payment(
);
7]
CREATE TABLE payment (
    payment_id serial PRIMARY KEY,
    customer_id integer REFERENCES customer(customer_id),
    ticket_booked integer,
    cinema_id integer REFERENCES cinema_details(cinema_id),
    movie_id integer REFERENCES movies_details(movie_id),
    screen integer,
    amount numeric
);
ALTER TABLE movies_details
ADD CONSTRAINT movies_details_unique_movie_id UNIQUE (movie_id);

ALTER TABLE cinema_details
ADD CONSTRAINT cinema_details_unique_cinema_id UNIQUE (cinema_id);

select * from movies_details;

CREATE OR REPLACE PROCEDURE insert_payment(p_customer_id integer,p_ticket_booked integer,
	p_cinema_id integer,p_movie_id integer,p_screen integer,p_amount numeric)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO payment (customer_id, ticket_booked, cinema_id, movie_id, screen, amount)
    VALUES (p_customer_id, p_ticket_booked, p_cinema_id, p_movie_id, p_screen, p_amount);
END;
$$;
select * from payment;
select * from customer;
select * from cinema_details;
select * from movies_details;
insert into payment(customer_id, ticket_booked, cinema_id, movie_id, screen, amount) values(37,2,1,91,1,320);
insert into payment(customer_id, ticket_booked, cinema_id, movie_id, screen, amount) values(38,1,1,91,1,160),(39,1,2,97,1,160),(39,3,2,98,2,555),(40,1,7,127,1,160);

do
$$
begin
for a in 41..66 loop
insert into payment(customer_id, ticket_booked, cinema_id, movie_id, screen, amount) values(a,1,1,91,1,160),(a,1,2,97,1,160),(a,3,2,98,2,555),(a,1,7,127,1,160);
end loop;
end;
$$;