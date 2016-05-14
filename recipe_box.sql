--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE categories (
    id integer NOT NULL,
    category_name character varying
);


ALTER TABLE categories OWNER TO "Guest";

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE categories_id_seq OWNER TO "Guest";

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;


--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE ingredients (
    id integer NOT NULL,
    ingredient_description character varying
);


ALTER TABLE ingredients OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ingredients_id_seq OWNER TO "Guest";

--
-- Name: ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ingredients_id_seq OWNED BY ingredients.id;


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes (
    id integer NOT NULL,
    recipe_name character varying,
    rating integer,
    image character varying
);


ALTER TABLE recipes OWNER TO "Guest";

--
-- Name: recipes_categories; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes_categories (
    id integer NOT NULL,
    recipe_id integer,
    category_id integer
);


ALTER TABLE recipes_categories OWNER TO "Guest";

--
-- Name: recipes_categories_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_categories_id_seq OWNER TO "Guest";

--
-- Name: recipes_categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_categories_id_seq OWNED BY recipes_categories.id;


--
-- Name: recipes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_id_seq OWNER TO "Guest";

--
-- Name: recipes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_id_seq OWNED BY recipes.id;


--
-- Name: recipes_ingredients; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE recipes_ingredients (
    id integer NOT NULL,
    recipe_id integer,
    ingredient_id integer
);


ALTER TABLE recipes_ingredients OWNER TO "Guest";

--
-- Name: recipes_ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipes_ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipes_ingredients_id_seq OWNER TO "Guest";

--
-- Name: recipes_ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipes_ingredients_id_seq OWNED BY recipes_ingredients.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients ALTER COLUMN id SET DEFAULT nextval('ingredients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes ALTER COLUMN id SET DEFAULT nextval('recipes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_categories ALTER COLUMN id SET DEFAULT nextval('recipes_categories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipes_ingredients ALTER COLUMN id SET DEFAULT nextval('recipes_ingredients_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY categories (id, category_name) FROM stdin;
5	bar food
7	American
8	fast food
9	persian
4	mexican
10	new category
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('categories_id_seq', 10, true);


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients (id, ingredient_description) FROM stdin;
2	tomato
5	cheese
7	ketchup
8	pickles
6	bon
9	tomato
10	milk
11	tomato
\.


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_id_seq', 11, true);


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes (id, recipe_name, rating, image) FROM stdin;
8	french fries	3	\N
11	Tacos	3	\N
12	chili	3	\N
13	tomato soup	3	\N
14	enchillada	3	\N
16	updated something	0	\N
17	new recipe	3	\N
10	Burrito	3	http://www.tacobueno.com/media/1381/beefbob.png?quality=65
\.


--
-- Data for Name: recipes_categories; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes_categories (id, recipe_id, category_id) FROM stdin;
10	13	9
11	11	9
12	10	9
13	12	4
14	17	5
15	10	5
\.


--
-- Name: recipes_categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_categories_id_seq', 15, true);


--
-- Name: recipes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_id_seq', 17, true);


--
-- Data for Name: recipes_ingredients; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipes_ingredients (id, recipe_id, ingredient_id) FROM stdin;
13	17	9
14	10	10
15	10	11
\.


--
-- Name: recipes_ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipes_ingredients_id_seq', 15, true);


--
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: recipes_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipes_categories
    ADD CONSTRAINT recipes_categories_pkey PRIMARY KEY (id);


--
-- Name: recipes_ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipes_ingredients
    ADD CONSTRAINT recipes_ingredients_pkey PRIMARY KEY (id);


--
-- Name: recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

