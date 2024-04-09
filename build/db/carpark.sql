--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: auto_in_route(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.auto_in_route() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF NEW.auto_id IN (SELECT auto_id FROM journal WHERE time_in IS NULL) THEN
            RAISE EXCEPTION 'The car has not yet returned to the park. Car id: %', NEW.auto_id;
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.auto_in_route() OWNER TO student;

--
-- Name: avg_time_in_routes(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.avg_time_in_routes() RETURNS TABLE(route_name character varying, time_in_minutes numeric)
    LANGUAGE plpgsql
    AS $$
BEGIN
return query(
SELECT name, (SELECT avg(extract(epoch FROM (time_in - time_out))/60) as time FROM journal WHERE route_id = routes.id) FROM routes);
END;
$$;


ALTER FUNCTION public.avg_time_in_routes() OWNER TO student;

--
-- Name: delete_driver(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.delete_driver() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF OLD.id IN (SELECT personnel_id FROM auto) THEN
        RAISE NOTICE 'It is not possible to delete a driver who has cars in the auto table. Driver Id:%', OLD.Id;    
	ROLLBACK;
        END IF;
        RETURN OLD;
    END;
$$;


ALTER FUNCTION public.delete_driver() OWNER TO student;

--
-- Name: delete_driver_with_routes(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.delete_driver_with_routes() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF OLD.id IN (SELECT personnel_id FROM auto WHERE auto.id in (Select distinct auto_id from journal)) THEN
        RAISE NOTICE 'It is not possible to delete a driver who has records in journal. Driver Id:%', OLD.Id;    
	ROLLBACK;
        END IF;
        RETURN OLD;
    END;
$$;


ALTER FUNCTION public.delete_driver_with_routes() OWNER TO student;

--
-- Name: drivers_bonus(integer); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.drivers_bonus(total integer) RETURNS TABLE(driver_id text, bonus_sum numeric)
    LANGUAGE plpgsql
    AS $$
DECLARE
i1 int := 0;
i2 int := 0;
i3 int := 0;
d1 int := 0;
d2 int := 0;
d3 int := 0;
temp record;
rec_drivers CURSOR FOR (WITH t AS (SELECT route_id, auto_personnel.id FROM journal JOIN auto_personnel ON auto_personnel.id IN (SELECT personnel_id FROM auto WHERE journal.auto_id = auto.id) WHERE (time_in-time_out) IN (SELECT min(time_in-time_out) FROM journal GROUP BY route_id)) SELECT id, count(*) FROM t GROUP BY id);
BEGIN
FOR temp IN rec_drivers LOOP
IF temp.count > d3 THEN
    IF temp.count > d2 THEN
        IF temp.count > d1 THEN
            d3 = d2;
            d2 = d1;
            d1 = temp.count;
            i3 = i2;
            i2 = i1;
            i1 = temp.id;
        ELSE
            d3 = d2;
            d2 = temp.count;
            i3 = i2;
            i2 = temp.id;
        END IF;
    ELSE
        d3 = temp.count;
        i3 = temp.id;
    END IF;
END IF;
END LOOP;

RETURN query(
    SELECT unnest(ARRAY[(select last_name|| ' ' ||first_name|| ' ' ||father_name from auto_personnel where id= i1),(select last_name|| ' ' ||first_name|| ' ' ||father_name from auto_personnel where id= i2),(select last_name|| ' ' ||first_name|| ' ' ||father_name from auto_personnel where id= i3)]), unnest(ARRAY[(total*0.5),(total*0.3), (total*0.2)]));
END;
$$;


ALTER FUNCTION public.drivers_bonus(total integer) OWNER TO student;

--
-- Name: fastest_routes_first(integer, integer); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.fastest_routes_first(n1 integer, n2 integer) RETURNS TABLE(route_name character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
return query(
with req as(
 with t as
(select * from journal where route_id in (select route_id from journal where auto_id = n1 intersect select route_id from journal where auto_id = n2) and auto_id in(n1,n2))
select * from journal where (time_in-time_out) in (select min(time_in-time_out) from t group by route_id))
select name from routes where id in (select route_id from req where auto_id = n1));
END;
$$;


ALTER FUNCTION public.fastest_routes_first(n1 integer, n2 integer) OWNER TO student;

--
-- Name: record(character varying); Type: PROCEDURE; Schema: public; Owner: student
--

CREATE PROCEDURE public.record(IN route character varying, OUT rec_time interval, OUT auto_num character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE temp integer;
BEGIN
temp := (SELECT auto_id FROM journal WHERE (time_in-time_out) = (SELECT min(time_in-time_out) FROM journal WHERE route_id = (SELECT id FROM routes WHERE name = route)));
auto_num := (SELECT num FROM auto WHERE id = temp);
rec_time := (SELECT min(time_in-time_out) FROM journal WHERE route_id = (SELECT id FROM routes WHERE name = route));
END;
$$;


ALTER PROCEDURE public.record(IN route character varying, OUT rec_time interval, OUT auto_num character varying) OWNER TO student;

--
-- Name: records_on_routes(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.records_on_routes() RETURNS TABLE(route_name character varying, rec_time interval, auto_num character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
RETURN QUERY(
WITH t AS (SELECT routes.Name, min(journal.time_in-journal.time_out) AS time FROM routes FULL JOIN journal ON journal.route_id = routes.id  GROUP BY name ORDER BY name) SELECT name, time, auto.num FROM t join auto ON auto.id = (SELECT auto_id FROM journal WHERE (time_in-time_out = t.time) AND route_id = (SELECT id FROM routes WHERE name = t.name))
);
END;
$$;


ALTER FUNCTION public.records_on_routes() OWNER TO student;

--
-- Name: update_journal(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.update_journal() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF NEW.time_in < NEW.time_out THEN
            RAISE EXCEPTION 'time_in should be larger then time_out';
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.update_journal() OWNER TO student;

--
-- Name: update_route(); Type: FUNCTION; Schema: public; Owner: student
--

CREATE FUNCTION public.update_route() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    DECLARE temp integer;
    BEGIN
        IF NEW.id IN (SELECT route_id FROM journal) THEN
            INSERT INTO routes (Name) VALUES (OLD.Name) RETURNING id INTO temp;
	    UPDATE journal
	    SET route_id = temp WHERE route_id = OLD.Id;
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.update_route() OWNER TO student;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: auth; Type: TABLE; Schema: public; Owner: student
--

CREATE TABLE public.auth (
    login character varying(50),
    password integer
);


ALTER TABLE public.auth OWNER TO student;

--
-- Name: auto; Type: TABLE; Schema: public; Owner: student
--

CREATE TABLE public.auto (
    id integer NOT NULL,
    num character varying(20),
    color character varying(20),
    mark character varying(20),
    personnel_id integer
);


ALTER TABLE public.auto OWNER TO student;

--
-- Name: auto_id_seq; Type: SEQUENCE; Schema: public; Owner: student
--

CREATE SEQUENCE public.auto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auto_id_seq OWNER TO student;

--
-- Name: auto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: student
--

ALTER SEQUENCE public.auto_id_seq OWNED BY public.auto.id;


--
-- Name: journal; Type: TABLE; Schema: public; Owner: student
--

CREATE TABLE public.journal (
    id integer NOT NULL,
    time_out timestamp(3) without time zone,
    time_in timestamp(3) without time zone,
    auto_id integer,
    route_id integer
);


ALTER TABLE public.journal OWNER TO student;

--
-- Name: routes; Type: TABLE; Schema: public; Owner: student
--

CREATE TABLE public.routes (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE public.routes OWNER TO student;

--
-- Name: auto_on_routes; Type: VIEW; Schema: public; Owner: student
--

CREATE VIEW public.auto_on_routes AS
 SELECT name,
    ( SELECT count(*) AS count
           FROM public.journal
          WHERE ((journal.time_in IS NULL) AND (journal.route_id = routes.id))) AS count
   FROM public.routes;


ALTER VIEW public.auto_on_routes OWNER TO student;

--
-- Name: auto_personnel; Type: TABLE; Schema: public; Owner: student
--

CREATE TABLE public.auto_personnel (
    id integer NOT NULL,
    first_name character varying(20),
    last_name character varying(20),
    father_name character varying(20)
);


ALTER TABLE public.auto_personnel OWNER TO student;

--
-- Name: auto_personnel_id_seq; Type: SEQUENCE; Schema: public; Owner: student
--

CREATE SEQUENCE public.auto_personnel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auto_personnel_id_seq OWNER TO student;

--
-- Name: auto_personnel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: student
--

ALTER SEQUENCE public.auto_personnel_id_seq OWNED BY public.auto_personnel.id;


--
-- Name: drivers; Type: VIEW; Schema: public; Owner: student
--

CREATE VIEW public.drivers AS
 SELECT last_name,
    first_name,
    father_name
   FROM public.auto_personnel;


ALTER VIEW public.drivers OWNER TO student;

--
-- Name: journal_id_seq; Type: SEQUENCE; Schema: public; Owner: student
--

CREATE SEQUENCE public.journal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.journal_id_seq OWNER TO student;

--
-- Name: journal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: student
--

ALTER SEQUENCE public.journal_id_seq OWNED BY public.journal.id;


--
-- Name: routes_id_seq; Type: SEQUENCE; Schema: public; Owner: student
--

CREATE SEQUENCE public.routes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.routes_id_seq OWNER TO student;

--
-- Name: routes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: student
--

ALTER SEQUENCE public.routes_id_seq OWNED BY public.routes.id;


--
-- Name: auto id; Type: DEFAULT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auto ALTER COLUMN id SET DEFAULT nextval('public.auto_id_seq'::regclass);


--
-- Name: auto_personnel id; Type: DEFAULT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auto_personnel ALTER COLUMN id SET DEFAULT nextval('public.auto_personnel_id_seq'::regclass);


--
-- Name: journal id; Type: DEFAULT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.journal ALTER COLUMN id SET DEFAULT nextval('public.journal_id_seq'::regclass);


--
-- Name: routes id; Type: DEFAULT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.routes ALTER COLUMN id SET DEFAULT nextval('public.routes_id_seq'::regclass);


--
-- Data for Name: auth; Type: TABLE DATA; Schema: public; Owner: student
--

COPY public.auth (login, password) FROM stdin;
admin	46792755
admin12345	1450575459
new_admin	365145720
\.


--
-- Data for Name: auto; Type: TABLE DATA; Schema: public; Owner: student
--

COPY public.auto (id, num, color, mark, personnel_id) FROM stdin;
7	г107аз	Синий	Форд	4
5	г105ер	Серый	Мерседес	6
2	т102аз	Голубой	Газель	9
8	к108от	Серый	Мерседес	7
9	х109ам	Чёрный	Газель	7
3	т103ук	Белый	Форд	9
6	р106ад	Жёлтый	Газель	4
1	в101оз	Белый	Мерседес	7
4	в104аз	Белый	Газель	8
\.


--
-- Data for Name: auto_personnel; Type: TABLE DATA; Schema: public; Owner: student
--

COPY public.auto_personnel (id, first_name, last_name, father_name) FROM stdin;
4	Иван	Алексеенко	Сергеевич
6	Алексей	Сергеев	Иванович
7	Илья	Александров	Сергеевич
8	Александр	Ильин	Иванович
9	Илья	Сергеев	Алексеевич
\.


--
-- Data for Name: journal; Type: TABLE DATA; Schema: public; Owner: student
--

COPY public.journal (id, time_out, time_in, auto_id, route_id) FROM stdin;
1	2023-09-29 12:07:12.159	2023-09-29 12:19:42.664	6	1
2	2023-09-29 12:38:02.471	2023-09-30 10:00:03.092	7	1
4	2023-09-29 12:39:56.15	2023-09-30 10:00:03.092	4	2
5	2023-09-29 12:40:38.359	2023-09-30 10:00:03.092	2	3
3	2023-09-29 12:39:13.912	2023-09-30 10:00:03.092	1	3
15	2023-10-06 21:59:53.064	2023-10-06 22:39:53	6	3
19	2023-11-03 00:51:04.311	2023-11-03 02:55:53.388	6	1
17	2023-10-27 19:40:09.402	2023-10-27 19:46:05.18	7	6
18	2023-10-27 19:40:50.618	2023-10-27 19:46:05.18	6	6
25	2023-12-05 11:18:20	2023-12-05 13:19:25	8	3
33	2023-12-09 00:56:07	2023-12-09 01:11:33	2	5
38	2023-12-09 10:17:44	\N	5	1
\.


--
-- Data for Name: routes; Type: TABLE DATA; Schema: public; Owner: student
--

COPY public.routes (id, name) FROM stdin;
1	Маршрут 1
2	Маршрут 2
3	Маршрут 3
6	Маршрут 5
5	Маршрут 6
\.


--
-- Name: auto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: student
--

SELECT pg_catalog.setval('public.auto_id_seq', 20, true);


--
-- Name: auto_personnel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: student
--

SELECT pg_catalog.setval('public.auto_personnel_id_seq', 16, true);


--
-- Name: journal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: student
--

SELECT pg_catalog.setval('public.journal_id_seq', 40, true);


--
-- Name: routes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: student
--

SELECT pg_catalog.setval('public.routes_id_seq', 8, true);


--
-- Name: auth auth_login_key; Type: CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auth
    ADD CONSTRAINT auth_login_key UNIQUE (login);


--
-- Name: auto_personnel auto_personnel_pkey; Type: CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auto_personnel
    ADD CONSTRAINT auto_personnel_pkey PRIMARY KEY (id);


--
-- Name: auto auto_pkey; Type: CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auto
    ADD CONSTRAINT auto_pkey PRIMARY KEY (id);


--
-- Name: journal journal_pkey; Type: CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_pkey PRIMARY KEY (id);


--
-- Name: routes routes_pkey; Type: CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_pkey PRIMARY KEY (id);


--
-- Name: auto_personnel auto_personnel_del; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER auto_personnel_del BEFORE DELETE ON public.auto_personnel FOR EACH ROW EXECUTE FUNCTION public.delete_driver();


--
-- Name: auto_personnel driver_del; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER driver_del BEFORE DELETE ON public.auto_personnel FOR EACH ROW EXECUTE FUNCTION public.delete_driver();


--
-- Name: journal journal_in; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER journal_in BEFORE INSERT ON public.journal FOR EACH ROW EXECUTE FUNCTION public.update_journal();


--
-- Name: journal journal_new; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER journal_new BEFORE INSERT ON public.journal FOR EACH ROW EXECUTE FUNCTION public.auto_in_route();


--
-- Name: journal journal_up; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER journal_up BEFORE UPDATE ON public.journal FOR EACH ROW EXECUTE FUNCTION public.update_journal();


--
-- Name: routes route_up; Type: TRIGGER; Schema: public; Owner: student
--

CREATE TRIGGER route_up BEFORE UPDATE ON public.routes FOR EACH ROW EXECUTE FUNCTION public.update_route();


--
-- Name: auto auto_personnel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.auto
    ADD CONSTRAINT auto_personnel_id_fkey FOREIGN KEY (personnel_id) REFERENCES public.auto_personnel(id);


--
-- Name: journal journal_auto_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_auto_id_fkey FOREIGN KEY (auto_id) REFERENCES public.auto(id);


--
-- Name: journal journal_route_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: student
--

ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_route_id_fkey FOREIGN KEY (route_id) REFERENCES public.routes(id);


--
-- PostgreSQL database dump complete
--

