package com.korogui.entity.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class BarsType implements UserType {

    private static final int SQL_TYPE = Types.ARRAY;

    private static final String DB_OBJECT_TYPE = "BARS_TYP";
    private static final String DB_SINGLE_OBJECT_TYPE = "BAR_TYP";


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

        Array array = (Array) rs.getObject(names[0]);
        if (rs.wasNull()) {
            return Collections.emptyList();
        }

        return Stream.of((Object[]) array.getArray())
            .map(this::mapBar)
            .collect(Collectors.toList());
    }


    private Bar mapBar(Object object)  {
        try {
            Struct struct = ((Struct) object);
            Object[] attributes = struct.getAttributes();

            Bar bar = new Bar();
            bar.setFirstName((String) attributes[0]);
            bar.setLastName((String) attributes[1]);
            return bar;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed mapping bar type", exception);
        }
    }

    @Override
    public void nullSafeSet(
        PreparedStatement st, Object value, int index, SessionImplementor session
    ) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, SQL_TYPE, DB_OBJECT_TYPE);
            return;
        }
        Connection connection = st.getConnection();
        Object values = ((List<Bar>) value).stream()
            .map(bar -> mapStruct(bar, connection))
            .toArray();
        Array array = new ARRAY(ArrayDescriptor.createDescriptor(DB_OBJECT_TYPE, connection), connection, values);
        st.setObject(index, array, SQL_TYPE);

    }

    private Struct mapStruct(Bar bar, Connection connection) {
        try {
            final Object[] values = new Object[] {
                bar.getFirstName(),
                bar.getLastName()
            };

            return new STRUCT(StructDescriptor.createDescriptor(DB_SINGLE_OBJECT_TYPE, connection), connection, values);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map STRUCT bar type", e);
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
