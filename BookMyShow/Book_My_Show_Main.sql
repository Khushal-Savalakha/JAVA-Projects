1]--Search Area Name
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

2]--Search movie name 
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

3]
Delete data from movie table
CREATE OR REPLACE FUNCTION insert_deleted_movie_data()
RETURNS TRIGGER 
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO Deleted_Movie_Data VALUES ( OLD.Movie_ID,OLD.cinema_Id,OLD.movie_title,OLD.movie_description,OLD.showtime,OLD.Screen,OLD.Running_Time,OLD.language,OLD.Price,OLD.Release_Date,OLD.Ratings,now());      
    RETURN OLD;
END;
$$ ;
insert into movies_details 
delete from movies_details where movie_id=1000;

-- Create a trigger to activate the insert_deleted_movie_data function
CREATE TRIGGER insert_deleted_movie_trigger
AFTER DELETE ON movies_details
FOR EACH ROW
EXECUTE FUNCTION insert_deleted_movie_data();

--4]to count price of tickets
CREATE OR REPLACE FUNCTION calculate_total_price(num_tickets integer, ticket_price numeric)
RETURNS numeric
LANGUAGE PLPGSQL AS $$
BEGIN
  RETURN num_tickets * ticket_price;
END;
$$;

select calculate_total_price(4,200);

--5]To insert data in Customer table
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

--6]Create a Trigger to store deleted customer data in deleted_customer_data

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

-- Create a trigger that activates on DELETE operations
CREATE TRIGGER customer_delete_trigger
before DELETE ON customer
FOR EACH ROW
EXECUTE FUNCTION log_deleted_customer_data();

--7]create procedure to insert data in Payment table
CREATE OR REPLACE PROCEDURE insert_payment(p_customer_id integer,p_ticket_booked integer,
	p_cinema_id integer,p_movie_id integer,p_screen integer,p_amount numeric)
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO payment (customer_id, ticket_booked, cinema_id, movie_id, screen, amount)
    VALUES (p_customer_id, p_ticket_booked, p_cinema_id, p_movie_id, p_screen, p_amount);
END;
$$;

--8]Retrieve Movies Playing in a Specific City:
SELECT cd.City_Name, md.Movie_Title, md.Showtime
FROM cinema_details cd
JOIN movies_details md ON cd.Cinema_ID = md.Cinema_ID
WHERE cd.City_Name = 'Ahmedabad';

--9]Calculate Total Amount Spent by a Customer:
SELECT customer_id, SUM(amount) AS total_amount_spent
FROM payment
WHERE customer_id = 38
GROUP BY customer_id; 

--10]Retrieve the Top-Rated Movie in Each Cinema:
SELECT cd.Cinema_Name, md.Movie_Title, md.Ratings
FROM cinema_details cd
JOIN movies_details md ON cd.Cinema_ID = md.Cinema_ID
WHERE md.Ratings =(
SELECT MAX(Ratings) FROM movies_details WHERE Cinema_ID = cd.Cinema_ID );
--11]Retrieve the Number of Tickets Booked for Each Movie:
SELECT movie_id, COUNT(*) AS tickets_booked
FROM payment
GROUP BY movie_id;
