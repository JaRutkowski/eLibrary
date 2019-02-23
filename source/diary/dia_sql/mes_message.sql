-- Drop table

-- DROP TABLE public.mes_message

CREATE TABLE public.mes_message (
	id_message int4 NOT NULL,
	content text NULL,
	is_draft bool NULL,
	send_date date NULL,
	title varchar(100) NULL,
	id_message_type int4 NULL,
	id_sender int4 NULL,
	CONSTRAINT mes_message_pkey PRIMARY KEY (id_message),
	CONSTRAINT fk42scy6fgsosjyvlhmacbkd96c FOREIGN KEY (id_message_type) REFERENCES mes_message_type(id_message_type),
	CONSTRAINT fkr6tew0eh3kjx31u5ay916lifx FOREIGN KEY (id_sender) REFERENCES com_user_data(id_user_data)
)
WITH (
	OIDS=FALSE
) ;
