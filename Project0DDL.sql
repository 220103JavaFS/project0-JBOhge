-- Drop table

-- DROP TABLE public.account;

CREATE TABLE public.account (
                                account_id int4 NOT NULL DEFAULT nextval('account_id_seq'::regclass),
                                first_name varchar(30) NULL,
                                last_name varchar(30) NULL,
                                username varchar(30) NOT NULL,
                                "password" bytea NOT NULL,
                                access_level int4 NOT NULL,
                                CONSTRAINT account_check CHECK (((access_level > 0) AND (access_level < 4))),
                                CONSTRAINT account_pk PRIMARY KEY (account_id),
                                CONSTRAINT account_un UNIQUE (username)
);

-- Drop table

-- DROP TABLE public.application;

CREATE TABLE public.application (
                                    application_id int4 NOT NULL DEFAULT nextval('application_id_seq'::regclass),
                                    account_id int4 NOT NULL,
                                    credit_score int4 NOT NULL,
                                    debt int4 NOT NULL,
                                    CONSTRAINT application_pk PRIMARY KEY (application_id),
                                    CONSTRAINT application_fk FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Drop table

-- DROP TABLE public.bankaccount;

CREATE TABLE public.bankaccount (
                                    bankaccount_id int4 NOT NULL DEFAULT nextval('bankaccount_id_seq'::regclass),
                                    account_id int4 NOT NULL,
                                    balance numeric(15, 2) NOT NULL,
                                    CONSTRAINT bankaccount_pk PRIMARY KEY (bankaccount_id),
                                    CONSTRAINT bankaccount_fk FOREIGN KEY (account_id) REFERENCES public.account(account_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE OR REPLACE PROCEDURE public.transfer(sender_id integer, receiver_id integer, amount double precision)
 LANGUAGE plpgsql
AS $procedure$
BEGIN
	-- subtracting from the sender account
UPDATE bankaccount SET balance = balance - amount WHERE bankaccount_id = sender_id;

--adding to the receiver account
UPDATE bankaccount SET balance = balance + amount WHERE bankaccount_id = receiver_id;

COMMIT;

END;$procedure$
;

-- DROP SEQUENCE public.account_id_seq;

CREATE SEQUENCE public.account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 10000
	CACHE 1
	NO CYCLE;

-- DROP SEQUENCE public.application_id_seq;

CREATE SEQUENCE public.application_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 20000
	CACHE 1
	NO CYCLE;

-- DROP SEQUENCE public.bankaccount_id_seq;

CREATE SEQUENCE public.bankaccount_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 30000
	CACHE 1
	NO CYCLE;
