-- Table: public.mes_recipient

-- DROP TABLE public.mes_recipient;

CREATE TABLE public.mes_recipient
(
  id_recipiet integer NOT NULL,
  is_bcc boolean,
  is_cc boolean,
  id_message integer,
  id_message_recipient integer,
  CONSTRAINT mes_recipient_pkey PRIMARY KEY (id_recipiet),
  CONSTRAINT fk7jvut0332634tvqq1qgqo1omp FOREIGN KEY (id_message_recipient)
      REFERENCES public.com_user_data (id_user_data) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkmmabey7f7bq0dosoyislxv2l8 FOREIGN KEY (id_message)
      REFERENCES public.mes_message (id_message) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.mes_recipient
  OWNER TO postgres;
