-- PostgreSQL Database Schema for Interview Questions
-- All definitions in US English

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Categories table to store question categories
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Difficulty levels as an enumeration type
CREATE TYPE difficulty_level AS ENUM ('junior', 'intermediate', 'senior');

-- Questions table to store interview questions
CREATE TABLE questions (
    id UUID PRIMARY KEY,
    question_text TEXT NOT NULL,
    explanation TEXT,
    difficulty difficulty_level NOT NULL,
    topic TEXT, 
    category_id UUID NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Answers table to store possible answers for each question
CREATE TABLE answers (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    answer_text TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Users table compatible with Spring Security
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE, 
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Authorities table for Spring Security roles
CREATE TABLE authorities (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    authority VARCHAR(50) NOT NULL, 
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, authority)
);

-- Questionnaires table to store generated questionnaires
CREATE TABLE questionnaires (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_by UUID REFERENCES users(id) ON DELETE SET NULL,
    question_count INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Questionnaire filters table to store the filters used to generate a questionnaire
CREATE TABLE questionnaire_filters (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    questionnaire_id UUID NOT NULL REFERENCES questionnaires(id) ON DELETE CASCADE,
    filter_type VARCHAR(50) NOT NULL, 
    filter_value TEXT NOT NULL, 
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(questionnaire_id, filter_type, filter_value)
);

-- Questionnaire questions table to store the questions included in each questionnaire
CREATE TABLE questionnaire_questions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    questionnaire_id UUID NOT NULL REFERENCES questionnaires(id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    question_order INTEGER NOT NULL, 
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(questionnaire_id, question_id)
);

-- User questionnaire sessions table to track user progress on questionnaires
CREATE TABLE user_questionnaire_sessions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    questionnaire_id UUID NOT NULL REFERENCES questionnaires(id) ON DELETE CASCADE,
    start_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP WITH TIME ZONE,
    is_completed BOOLEAN DEFAULT FALSE,
    score DECIMAL(5,2), 
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, questionnaire_id, start_time)
);

-- User answers table to store user responses to questions
CREATE TABLE user_answers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    session_id UUID NOT NULL REFERENCES user_questionnaire_sessions(id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    answer_id UUID REFERENCES answers(id) ON DELETE SET NULL, 
    is_correct BOOLEAN, 
    answer_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, 
    time_taken_seconds INTEGER, 
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(session_id, question_id)
);

-- User answer history table to track all attempts at answering questions
CREATE TABLE user_answer_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    answer_id UUID REFERENCES answers(id) ON DELETE SET NULL,
    session_id UUID REFERENCES user_questionnaire_sessions(id) ON DELETE CASCADE,
    is_correct BOOLEAN,
    answer_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    time_taken_seconds INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster lookups
CREATE INDEX idx_questions_category ON questions(category_id);
CREATE INDEX idx_questions_difficulty ON questions(difficulty);
CREATE INDEX idx_answers_question ON answers(question_id);
CREATE INDEX idx_authorities_user ON authorities(user_id);
CREATE INDEX idx_questionnaire_questions_questionnaire ON questionnaire_questions(questionnaire_id);
CREATE INDEX idx_questionnaire_questions_question ON questionnaire_questions(question_id);
CREATE INDEX idx_user_questionnaire_sessions_user ON user_questionnaire_sessions(user_id);
CREATE INDEX idx_user_questionnaire_sessions_questionnaire ON user_questionnaire_sessions(questionnaire_id);
CREATE INDEX idx_user_answers_session ON user_answers(session_id);
CREATE INDEX idx_user_answers_question ON user_answers(question_id);
CREATE INDEX idx_user_answer_history_user ON user_answer_history(user_id);
CREATE INDEX idx_user_answer_history_question ON user_answer_history(question_id);
CREATE INDEX idx_questionnaire_filters_questionnaire ON questionnaire_filters(questionnaire_id);

-- Function to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers to automatically update the updated_at column
CREATE TRIGGER update_categories_modtime
BEFORE UPDATE ON categories
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_questions_modtime
BEFORE UPDATE ON questions
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_answers_modtime
BEFORE UPDATE ON answers
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_users_modtime
BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_authorities_modtime
BEFORE UPDATE ON authorities
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_questionnaires_modtime
BEFORE UPDATE ON questionnaires
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_questionnaire_filters_modtime
BEFORE UPDATE ON questionnaire_filters
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_questionnaire_questions_modtime
BEFORE UPDATE ON questionnaire_questions
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_user_questionnaire_sessions_modtime
BEFORE UPDATE ON user_questionnaire_sessions
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_user_answers_modtime
BEFORE UPDATE ON user_answers
FOR EACH ROW EXECUTE FUNCTION update_modified_column();

-- Comments on how to use this schema
COMMENT ON TABLE categories IS 'Stores the main categories of interview questions';
COMMENT ON TABLE questions IS 'Stores individual interview questions';
COMMENT ON TABLE answers IS 'Stores possible answers for each question';
COMMENT ON TABLE users IS 'Stores user information compatible with Spring Security';
COMMENT ON TABLE authorities IS 'Stores user roles for Spring Security';
COMMENT ON TABLE questionnaires IS 'Stores generated questionnaires';
COMMENT ON TABLE questionnaire_filters IS 'Stores filters used to generate questionnaires';
COMMENT ON TABLE questionnaire_questions IS 'Maps questions to questionnaires';
COMMENT ON TABLE user_questionnaire_sessions IS 'Tracks user progress on questionnaires';
COMMENT ON TABLE user_answers IS 'Stores user responses to questions';
COMMENT ON TABLE user_answer_history IS 'Tracks all user attempts at answering questions';
