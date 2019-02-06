package com.korogui.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.korogui.entity.Foo;
import com.korogui.entity.type.Bar;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FooRepositoryIt {

    @Autowired
    private FooRepository repository;

    @Test
    public void findsAllFoo() {
        List<Foo> foos = repository.findAll();

        assertThat(foos, is(empty()));
    }

    @Test
    public void insertsFoo() {
        Foo foo = new Foo();
        foo.setId(new Random().nextInt(100000000));
        foo.setBar(bar("Jon", "Snow"));
        foo.setBars(Arrays.asList(
            bar("James", "Lannister"),
            bar("Sansa", "Stark")
        ));

        Foo actual = repository.save(foo);

        assertThat(actual.getBar().getFirstName(), is("Jon"));
    }

    protected Bar bar(String firstName, String lastName) {
        Bar bar = new Bar();
        bar.setFirstName(firstName);
        bar.setLastName(lastName);
        return bar;
    }
}
