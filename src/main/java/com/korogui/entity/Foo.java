package com.korogui.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "FOO")
public class Foo {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "BAR")
    @Type(type = "com.korogui.entity.type.BarType")
    private Bar bar;

    @Column(name = "BARS")
    @Type(type = "com.korogui.entity.type.BarsType")
    private List<Bar> bars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }
}
