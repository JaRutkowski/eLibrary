-- Column: template_directory

-- ALTER TABLE public.com_system_properties DROP COLUMN template_directory;

ALTER TABLE public.com_system_properties ADD COLUMN template_directory character varying(80);