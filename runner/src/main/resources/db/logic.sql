-- LAST_CHANGE
CREATE FUNCTION saving_update_last_change()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    NEW.last_change = NOW()::timestamp;
    RETURN NEW;
END
$$;

CREATE TRIGGER refresh_last_change
BEFORE UPDATE ON saving
FOR EACH ROW
EXECUTE PROCEDURE saving_update_last_change();

-- CREATED_ON
CREATE FUNCTION saving_init_created_on()
    RETURNS TRIGGER
    LANGUAGE PLPGSQL AS
$$
BEGIN
    NEW.created_on = NOW()::timestamp;
    RETURN NEW;
END
$$;

CREATE TRIGGER init_created_on
BEFORE INSERT ON saving
FOR EACH ROW
EXECUTE PROCEDURE saving_init_created_on();