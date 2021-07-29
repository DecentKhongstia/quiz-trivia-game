--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.7
-- Dumped by pg_dump version 9.5.7

-- Started on 2021-07-29 09:39:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2152 (class 1262 OID 221545)
-- Dependencies: 2151
-- Name: quiz; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE quiz IS '
';


--
-- TOC entry 8 (class 2615 OID 221546)
-- Name: quiz; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA quiz;


ALTER SCHEMA quiz OWNER TO postgres;

--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 8
-- Name: SCHEMA quiz; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA quiz IS '
';


--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = quiz, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 184 (class 1259 OID 229755)
-- Name: answers; Type: TABLE; Schema: quiz; Owner: postgres
--

CREATE TABLE answers (
    code smallint NOT NULL,
    qcode smallint NOT NULL,
    description character varying(99) NOT NULL,
    flag character varying(1) DEFAULT 'N'::character varying NOT NULL,
    entrydate timestamp without time zone DEFAULT ('now'::text)::timestamp with time zone,
    CONSTRAINT checkqflag CHECK (((flag)::text = ANY (ARRAY[('Y'::character varying)::text, ('N'::character varying)::text])))
);


ALTER TABLE answers OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 229783)
-- Name: gameanswers; Type: TABLE; Schema: quiz; Owner: postgres
--

CREATE TABLE gameanswers (
    code smallint NOT NULL,
    username character varying(40) NOT NULL,
    tally smallint DEFAULT 0 NOT NULL,
    lobbyid character varying(40) NOT NULL,
    flag character varying(1) DEFAULT 'N'::character varying NOT NULL,
    entrydate timestamp without time zone DEFAULT ('now'::text)::timestamp with time zone,
    CONSTRAINT checkqflag CHECK (((flag)::text = ANY (ARRAY[('Y'::character varying)::text, ('N'::character varying)::text])))
);


ALTER TABLE gameanswers OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 229883)
-- Name: gameanswersresponse; Type: TABLE; Schema: quiz; Owner: postgres
--

CREATE TABLE gameanswersresponse (
    id smallint NOT NULL,
    code smallint NOT NULL,
    qcode smallint NOT NULL,
    response character varying(1) NOT NULL,
    entrydate timestamp without time zone DEFAULT ('now'::text)::timestamp with time zone
);


ALTER TABLE gameanswersresponse OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 229741)
-- Name: questions; Type: TABLE; Schema: quiz; Owner: postgres
--

CREATE TABLE questions (
    code smallint NOT NULL,
    description character varying(99) NOT NULL,
    entrydate timestamp without time zone DEFAULT ('now'::text)::timestamp with time zone
);


ALTER TABLE questions OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 221569)
-- Name: userinfos; Type: TABLE; Schema: quiz; Owner: postgres
--

CREATE TABLE userinfos (
    usercode character varying(40) NOT NULL,
    username character varying(99) NOT NULL,
    userpassword character varying(512) NOT NULL,
    entrydate timestamp without time zone DEFAULT ('now'::text)::timestamp with time zone
);


ALTER TABLE userinfos OWNER TO postgres;

--
-- TOC entry 2144 (class 0 OID 229755)
-- Dependencies: 184
-- Data for Name: answers; Type: TABLE DATA; Schema: quiz; Owner: postgres
--

INSERT INTO answers VALUES (1, 1, 'True', 'Y', '2021-07-27 12:30:55.091455');
INSERT INTO answers VALUES (2, 1, 'False', 'N', '2021-07-27 12:31:05.376992');
INSERT INTO answers VALUES (3, 1, 'Not sure', 'N', '2021-07-27 12:31:16.07142');
INSERT INTO answers VALUES (4, 2, 'True', 'N', '2021-07-27 12:32:51.334428');
INSERT INTO answers VALUES (5, 2, 'False', 'Y', '2021-07-27 12:32:58.508151');
INSERT INTO answers VALUES (6, 2, 'Not sure', 'N', '2021-07-27 12:33:07.311129');
INSERT INTO answers VALUES (7, 3, 'Yes', 'Y', '2021-07-27 12:33:22.33875');
INSERT INTO answers VALUES (8, 3, 'No', 'N', '2021-07-27 12:33:30.809509');
INSERT INTO answers VALUES (9, 3, 'Maybe', 'N', '2021-07-27 12:33:40.037782');


--
-- TOC entry 2143 (class 0 OID 229741)
-- Dependencies: 183
-- Data for Name: questions; Type: TABLE DATA; Schema: quiz; Owner: postgres
--

INSERT INTO questions VALUES (1, 'Which is true?', '2021-07-27 12:26:41.577913');
INSERT INTO questions VALUES (2, 'Which is false?', '2021-07-27 12:26:56.065071');
INSERT INTO questions VALUES (3, 'Are you Hungry?', '2021-07-27 12:27:03.665617');


--
-- TOC entry 2016 (class 2606 OID 229762)
-- Name: answers_pkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (code);


--
-- TOC entry 2018 (class 2606 OID 229771)
-- Name: answers_ukey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY answers
    ADD CONSTRAINT answers_ukey UNIQUE (qcode, description);


--
-- TOC entry 2020 (class 2606 OID 229791)
-- Name: gameanswers_pkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY gameanswers
    ADD CONSTRAINT gameanswers_pkey PRIMARY KEY (code);


--
-- TOC entry 2022 (class 2606 OID 229793)
-- Name: gameanswers_ukey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY gameanswers
    ADD CONSTRAINT gameanswers_ukey UNIQUE (username, lobbyid);


--
-- TOC entry 2024 (class 2606 OID 229888)
-- Name: gameanswersresponse_pkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY gameanswersresponse
    ADD CONSTRAINT gameanswersresponse_pkey PRIMARY KEY (id);


--
-- TOC entry 2008 (class 2606 OID 221581)
-- Name: userinfos_pkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY userinfos
    ADD CONSTRAINT userinfos_pkey PRIMARY KEY (usercode);


--
-- TOC entry 2012 (class 2606 OID 229748)
-- Name: userinfos_q_fkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT userinfos_q_fkey UNIQUE (description);


--
-- TOC entry 2014 (class 2606 OID 229746)
-- Name: userinfos_qkey; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY questions
    ADD CONSTRAINT userinfos_qkey PRIMARY KEY (code);


--
-- TOC entry 2010 (class 2606 OID 221579)
-- Name: userinfos_username_key; Type: CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY userinfos
    ADD CONSTRAINT userinfos_username_key UNIQUE (username);


--
-- TOC entry 2025 (class 2606 OID 229765)
-- Name: answers_fkey1; Type: FK CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY answers
    ADD CONSTRAINT answers_fkey1 FOREIGN KEY (qcode) REFERENCES questions(code) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2026 (class 2606 OID 229889)
-- Name: answers_fkey1; Type: FK CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY gameanswersresponse
    ADD CONSTRAINT answers_fkey1 FOREIGN KEY (code) REFERENCES gameanswers(code) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2027 (class 2606 OID 229894)
-- Name: answers_fkey2; Type: FK CONSTRAINT; Schema: quiz; Owner: postgres
--

ALTER TABLE ONLY gameanswersresponse
    ADD CONSTRAINT answers_fkey2 FOREIGN KEY (qcode) REFERENCES questions(code) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2021-07-29 09:39:29

--
-- PostgreSQL database dump complete
--

