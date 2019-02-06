package com.korogui.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.korogui.entity.Foo;

public interface FooRepository extends JpaRepository<Foo, Long> {

}
