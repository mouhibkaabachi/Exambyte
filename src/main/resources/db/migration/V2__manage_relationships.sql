CREATE TABLE IF NOT EXISTS exam_students (
exam_id UUID NOT NULL REFERENCES exam(uuid) ON DELETE CASCADE,
student_id UUID NOT NULL REFERENCES users(uuid) ON DELETE CASCADE,
PRIMARY KEY (exam_id, student_id)
    );


CREATE TABLE IF NOT EXISTS exam_organisers (
exam_id UUID NOT NULL REFERENCES exam(uuid) ON DELETE CASCADE,
organiser_id UUID NOT NULL REFERENCES users(uuid) ON DELETE CASCADE,
PRIMARY KEY (exam_id, organiser_id)
    );


CREATE TABLE IF NOT EXISTS exam_questions (
exam_id UUID NOT NULL REFERENCES exam(uuid) ON DELETE CASCADE,
question_id UUID NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
PRIMARY KEY (exam_id, question_id)
    );