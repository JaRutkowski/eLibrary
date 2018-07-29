-- Drop table

-- DROP TABLE public.mes_recipient

CREATE TABLE public.mes_recipient (
	is_bcc bool NULL,
	is_cc bool NULL,
	id_message int4 NOT NULL,
	id_recipient int4 NULL,
	CONSTRAINT mes_recipient_pkey PRIMARY KEY (id_message),
	CONSTRAINT fkmmabey7f7bq0dosoyislxv2l8 FOREIGN KEY (id_message) REFERENCES mes_message(id_message),
	CONSTRAINT fkq6618i2msk1lvs8lfepu75o9m FOREIGN KEY (id_recipient) REFERENCES com_user_data(id_user_data)
)
WITH (
	OIDS=FALSE
) ;
