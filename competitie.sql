--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-06-05 17:30:42

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
-- TOC entry 834 (class 1247 OID 16414)
-- Name: usertype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.usertype AS ENUM (
    'USER',
    'ADMIN'
);


ALTER TYPE public.usertype OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16396)
-- Name: echipa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.echipa (
    id_echipa bigint NOT NULL,
    nume_echipa text NOT NULL
);


ALTER TABLE public.echipa OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16395)
-- Name: echipa_id_echipa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.echipa_id_echipa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.echipa_id_echipa_seq OWNER TO postgres;

--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 209
-- Name: echipa_id_echipa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.echipa_id_echipa_seq OWNED BY public.echipa.id_echipa;


--
-- TOC entry 214 (class 1259 OID 16420)
-- Name: etapa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.etapa (
    id_etapa bigint NOT NULL,
    denumire text NOT NULL,
    incheiat date NOT NULL
);


ALTER TABLE public.etapa OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16419)
-- Name: etapa_id_etapa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.etapa_id_etapa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.etapa_id_etapa_seq OWNER TO postgres;

--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 213
-- Name: etapa_id_etapa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.etapa_id_etapa_seq OWNED BY public.etapa.id_etapa;


--
-- TOC entry 215 (class 1259 OID 16428)
-- Name: participa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participa (
    id_etapa integer NOT NULL,
    id_persoana integer NOT NULL,
    punctaj integer DEFAULT '-1'::integer NOT NULL
);


ALTER TABLE public.participa OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16405)
-- Name: persoana; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persoana (
    id_persoana bigint NOT NULL,
    id_echipa integer NOT NULL,
    nume text NOT NULL,
    username text NOT NULL,
    rol public.usertype NOT NULL
);


ALTER TABLE public.persoana OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16404)
-- Name: persoana_id_persoana_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persoana_id_persoana_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.persoana_id_persoana_seq OWNER TO postgres;

--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 211
-- Name: persoana_id_persoana_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persoana_id_persoana_seq OWNED BY public.persoana.id_persoana;


--
-- TOC entry 3181 (class 2604 OID 16399)
-- Name: echipa id_echipa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.echipa ALTER COLUMN id_echipa SET DEFAULT nextval('public.echipa_id_echipa_seq'::regclass);


--
-- TOC entry 3183 (class 2604 OID 16423)
-- Name: etapa id_etapa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etapa ALTER COLUMN id_etapa SET DEFAULT nextval('public.etapa_id_etapa_seq'::regclass);


--
-- TOC entry 3182 (class 2604 OID 16408)
-- Name: persoana id_persoana; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana ALTER COLUMN id_persoana SET DEFAULT nextval('public.persoana_id_persoana_seq'::regclass);


--
-- TOC entry 3345 (class 0 OID 16396)
-- Dependencies: 210
-- Data for Name: echipa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.echipa (id_echipa, nume_echipa) FROM stdin;
3	Echipa_3
5	Echipa_5
1	Echipa_1
8	Echipa_8
4	Echipa_4
10	Echipa_10
2	Echipa_2
11	Echipa_Test
9	Echipa_9
7	Echipa_7
0	Echipa_Admin
\.


--
-- TOC entry 3349 (class 0 OID 16420)
-- Dependencies: 214
-- Data for Name: etapa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.etapa (id_etapa, denumire, incheiat) FROM stdin;
5	Etapa_5	2022-02-16
4	Etapa_4	2022-02-03
2	Etapa_2	2022-01-04
1	Etapa_1	2022-04-04
3	Etapa_3	2022-01-28
\.


--
-- TOC entry 3350 (class 0 OID 16428)
-- Dependencies: 215
-- Data for Name: participa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participa (id_etapa, id_persoana, punctaj) FROM stdin;
1	25	4
1	26	5
1	27	1
2	24	6
1	28	9
1	29	9
1	30	9
1	35	10
2	35	7
3	24	4
1	24	3
1	39	12
\.


--
-- TOC entry 3347 (class 0 OID 16405)
-- Dependencies: 212
-- Data for Name: persoana; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persoana (id_persoana, id_echipa, nume, username, rol) FROM stdin;
24	5	Nume_Test_1	usernametest1	USER
25	5	Nume_Test_2	usernametest2	USER
26	2	Nume_Test_3	usernametest3	USER
27	2	Nume_Test_4	usernametest4	USER
28	1	Nume_Test_5	1	USER
29	1	Nume_Test_6	2	USER
30	1	Nume_Test_7	3	USER
31	1	Nume_Test_8	4	USER
32	1	Nume_Test_9	5	USER
35	7	Nume_Nou	usernameNou	USER
38	0	Administrator	admin	ADMIN
39	3	Nume_User	username123	USER
40	2	Nume_test	Test123	USER
\.


--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 209
-- Name: echipa_id_echipa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.echipa_id_echipa_seq', 13, true);


--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 213
-- Name: etapa_id_etapa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.etapa_id_etapa_seq', 5, true);


--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 211
-- Name: persoana_id_persoana_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persoana_id_persoana_seq', 40, true);


--
-- TOC entry 3187 (class 2606 OID 24578)
-- Name: echipa echipa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.echipa
    ADD CONSTRAINT echipa_pkey PRIMARY KEY (id_echipa);


--
-- TOC entry 3197 (class 2606 OID 16427)
-- Name: etapa etapa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etapa
    ADD CONSTRAINT etapa_pkey PRIMARY KEY (id_etapa);


--
-- TOC entry 3189 (class 2606 OID 16456)
-- Name: echipa id_echipa_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.echipa
    ADD CONSTRAINT id_echipa_unique UNIQUE (id_echipa);


--
-- TOC entry 3199 (class 2606 OID 16465)
-- Name: etapa id_etapa_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etapa
    ADD CONSTRAINT id_etapa_unique UNIQUE (id_etapa);


--
-- TOC entry 3191 (class 2606 OID 16463)
-- Name: persoana id_persoana_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana
    ADD CONSTRAINT id_persoana_unique UNIQUE (id_persoana);


--
-- TOC entry 3201 (class 2606 OID 16432)
-- Name: participa participa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participa
    ADD CONSTRAINT participa_pkey PRIMARY KEY (id_etapa, id_persoana);


--
-- TOC entry 3193 (class 2606 OID 16412)
-- Name: persoana persoana_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana
    ADD CONSTRAINT persoana_pkey PRIMARY KEY (id_persoana);


--
-- TOC entry 3195 (class 2606 OID 24583)
-- Name: persoana username_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana
    ADD CONSTRAINT username_unique UNIQUE (username);


--
-- TOC entry 3185 (class 1259 OID 24579)
-- Name: echipa_nume_echipa_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX echipa_nume_echipa_uindex ON public.echipa USING btree (nume_echipa);


--
-- TOC entry 3203 (class 2606 OID 24589)
-- Name: participa participa_etapa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participa
    ADD CONSTRAINT participa_etapa_fkey FOREIGN KEY (id_etapa) REFERENCES public.etapa(id_etapa) ON DELETE CASCADE;


--
-- TOC entry 3204 (class 2606 OID 24594)
-- Name: participa participa_persoana_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participa
    ADD CONSTRAINT participa_persoana_fkey FOREIGN KEY (id_persoana) REFERENCES public.persoana(id_persoana) ON DELETE CASCADE;


--
-- TOC entry 3202 (class 2606 OID 24584)
-- Name: persoana persoana_echipa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana
    ADD CONSTRAINT persoana_echipa_fkey FOREIGN KEY (id_echipa) REFERENCES public.echipa(id_echipa) ON DELETE CASCADE;


-- Completed on 2022-06-05 17:30:42

--
-- PostgreSQL database dump complete
--

