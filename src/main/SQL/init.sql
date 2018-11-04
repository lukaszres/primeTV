-- Table: tb_stats
CREATE SEQUENCE tb_stats_stt_id_seq
  START 1;
CREATE TABLE tb_stats
(
  stt_id         integer                  NOT NULL DEFAULT nextval('tb_stats_stt_id_seq' :: regclass),
  stt_created    timestamp with time zone NOT NULL,
  stt_site       character varying(250),
  stt_ip         character varying(45),
  stt_activity   character varying(45),
  stt_details    character varying(250),
  stt_user_agent character varying(250),
  CONSTRAINT tb_stats_pkey PRIMARY KEY (stt_id)
)
WITH (
OIDS = FALSE
);