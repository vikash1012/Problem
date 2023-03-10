CREATE TABLE public.customer (
                               id serial NOT NULL,
                               email varchar NOT NULL,
                               "password" varchar NOT NULL,
                               CONSTRAINT user_pk PRIMARY KEY (id)
);
