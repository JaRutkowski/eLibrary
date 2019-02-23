-- Drop table

-- DROP TABLE public.mes_message_type

CREATE TABLE public.mes_message_type (
	id_message_type int4 NOT NULL,
	description varchar(200) NULL,
	name varchar(200) NULL,
	CONSTRAINT mes_message_type_pkey PRIMARY KEY (id_message_type)
)
WITH (
	OIDS=FALSE
) ;
