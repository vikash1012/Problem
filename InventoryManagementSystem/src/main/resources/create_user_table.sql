CREATE TABLE public.user (
                               id int4 NOT NULL ,
                               email varchar NOT NULL,
                               "password" varchar NOT NULL,
                               CONSTRAINT user_pk PRIMARY KEY (id)
);
