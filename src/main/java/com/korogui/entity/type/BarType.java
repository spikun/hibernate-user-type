package com.korogui.entity.type;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class BarType implements UserType {

    private static final int SQL_TYPE = Types.STRUCT;

    private static final String DB_OBJECT_TYPE = "BAR_TYP";

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return Bar.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(
        ResultSet rs, String[] names, SessionImplementor session, Object owner
    ) throws HibernateException, SQLException {

        Struct struct = (Struct) rs.getObject(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        Object[] attributes = struct.getAttributes();

        Bar bar = new Bar();
        bar.setFirstName((String) attributes[0]);
        bar.setLastName((String) attributes[1]);
        return bar;
    }

    @Override
    public void nullSafeSet(
        PreparedStatement st, Object value, int index, SessionImplementor session
    ) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, SQL_TYPE, DB_OBJECT_TYPE);
        } else {
            Bar bar = (Bar) value;
            final Object[] values = new Object[] {
                bar.getFirstName(),
                bar.getLastName()
            };
            Connection connection = st.getConnection();
            Struct struct = new STRUCT(StructDescriptor.createDescriptor(DB_OBJECT_TYPE, connection), connection, values);
            st.setObject(index, struct, SQL_TYPE);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
