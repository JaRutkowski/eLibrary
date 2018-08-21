-- Table: public.mes_message

-- DROP TABLE public.mes_message;

CREATE TABLE public.mes_message
(
  id_message integer NOT NULL,
  content character varying(1200),
  is_draft boolean,
  send_date date,
  title character varying(100),
  id_sender integer,
  CONSTRAINT mes_message_pkey PRIMARY KEY (id_message),
  CONSTRAINT fkr6tew0eh3kjx31u5ay916lifx FOREIGN KEY (id_sender)
      REFERENCES public.com_user_data (id_user_data) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.mes_message
  OWNER TO postgres;
