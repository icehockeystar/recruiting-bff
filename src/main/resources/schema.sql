create table offer
(
    id uuid not null,
    job_title varchar(256) not null,
    start_date TIMESTAMP WITH TIME ZONE not null,

    primary key(id)
);

create unique index offer_job_title on offer(job_title);

create table job_application
(
    id uuid not null,
    offer_id uuid not null,
    candidate_email varchar(254) not null,
    resume_text varchar(1024) not null,
    status varchar(256) not null,
    primary key(id),
    foreign key(offer_id) references offer(id)
);

create unique index job_application_candidate_email on job_application(candidate_email);
