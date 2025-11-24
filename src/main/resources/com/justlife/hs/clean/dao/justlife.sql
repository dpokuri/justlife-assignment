-- This table to store Professional basic information and this can be extended further
CREATE TABLE IF NOT EXISTS test.prof_info
(
    id bigint NOT NULL DEFAULT nextval('test.professional_info_id_seq'::regclass),
    first_name character varying COLLATE pg_catalog."default",
    last_name character varying COLLATE pg_catalog."default",
    phone character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    status smallint DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT professional_info_pkey PRIMARY KEY (id)
)

-- This table to capture professional attendance / availability so that this info will be used in the scheduler job which will update the professional schedules on daily basis
CREATE TABLE IF NOT EXISTS test.prof_attendance
(
    id bigint NOT NULL DEFAULT nextval('test.prof_attendance_id_seq'::regclass),
    date date,
    prof_id bigint,
    status smallint DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT prof_attendance_pkey PRIMARY KEY (id)
)



-- This table to store service basic information
CREATE TABLE IF NOT EXISTS test.service_info
(
    id smallint NOT NULL DEFAULT nextval('test.service_info_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    "desc" character varying COLLATE pg_catalog."default",
    tc character varying COLLATE pg_catalog."default",
    status smallint DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT service_info_pkey PRIMARY KEY (id)
)


-- This table to store vehicle information which will be tagged to Professionals
REATE TABLE IF NOT EXISTS test.vehicle_info
(
    id bigint NOT NULL DEFAULT nextval('test.vehicle_info_id_seq'::regclass),
    reg_num character varying COLLATE pg_catalog."default",
    type character varying COLLATE pg_catalog."default",
    capacity smallint,
    status smallint DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT vehicle_info_pkey PRIMARY KEY (id)
)

-- This table to store vehicle / fleet availability to assign to professionals for a particular route....
CREATE TABLE IF NOT EXISTS test.vehicle_availability
(
    id bigint NOT NULL DEFAULT nextval('test.vehicle_availability_id_seq'::regclass),
    date date,
    vehicle_id bigint,
    status smallint DEFAULT 1,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT vehicle_availability_pkey PRIMARY KEY (id)
)

-- This table to store professional and service mapping if at all system allows one professional can be part of multiple services
CREATE TABLE IF NOT EXISTS test.service_prof_map
(
    id bigint NOT NULL DEFAULT nextval('test.service_prof_map_id_seq'::regclass),
    service_id bigint,
    prof_id bigint,
    status smallint,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT service_prof_map_pkey PRIMARY KEY (id)
)


-- This table to store vehicle-professional tagging to assign to appointments
CREATE TABLE IF NOT EXISTS test.prof_vehicle_map
(
    id bigint NOT NULL DEFAULT nextval('test.prof_vehicle_map_id_seq'::regclass),
    date date,
    prof_id bigint,
    vehicle_id bigint,
    status smallint,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT prof_vehicle_map_pkey PRIMARY KEY (id)
)

-- This table to store professionals daily schedule information. This table will be filled by scheduler jobs everyday midnight by collecting information from vehicle, prefessional availability tables
CREATE TABLE IF NOT EXISTS test.prof_schedule
(
    id bigint NOT NULL DEFAULT nextval('test.prof_availability_id_seq'::regclass),
    prof_id bigint,
    vehicle_id bigint,
    service_id smallint,
    date date,
    start_time time without time zone,
    end_time time without time zone,
    status character varying COLLATE pg_catalog."default",
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT prof_availability_pkey PRIMARY KEY (id)
)

-- This table to store appointment bookings information
CREATE TABLE IF NOT EXISTS test.booking_info
(
    id bigint NOT NULL DEFAULT nextval('test.booking_info_id_seq'::regclass),
    customer_id bigint,
	service_id bigint,
	date date,
	start_time time without time zone,
    end_time time without time zone,
    duration smallint DEFAULT 0,
	prof_count smallint,
    vehicle_id bigint,
    status character varying COLLATE pg_catalog."default",
 	created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT booking_info_pkey PRIMARY KEY (id)
)

-- This table to store booking - professional mapping information as one booking will be assigned to more than one professional
CREATE TABLE IF NOT EXISTS test.booking_prof_map
(
    id bigint NOT NULL DEFAULT nextval('test.prof_booking_map_id_seq'::regclass),
    prof_id bigint,
    booking_id bigint,
    created_on timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp without time zone,
    CONSTRAINT prof_booking_map_pkey PRIMARY KEY (id)
)

