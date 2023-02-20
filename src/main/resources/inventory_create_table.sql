CREATE TABLE public.inventory (
                                  sku varchar NOT NULL,
                                  id serial4 NOT NULL,
                                  itype varchar NOT NULL,
                                  primary_status varchar NULL DEFAULT 'created'::character varying,
                                  primary_location varchar NOT NULL,
                                  created_at timestamp NOT NULL,
                                  updated_at timestamp NOT NULL,
                                  created_by varchar NOT NULL,
                                  updated_by varchar NOT NULL,
                                  in_attribute json NOT NULL,
                                  cost_price float4 NOT NULL,
                                  selling_price float4 NOT NULL,
                                  secondary_status json NOT NULL,
                                  CONSTRAINT inventory_pkey PRIMARY KEY (id)
);
