package com.lkre.web.index;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class IndexBackingTest {
    private List<String> newSelectedGenres;
    private IndexBacking indexBacking;
    private Field selectedGenres;
    private Method selectedGenresStringMethod;


    @Before
    public void setUp() {
        indexBacking = new IndexBacking();
        try {
            selectedGenres = indexBacking.getClass().getDeclaredField("selectedGenres");
            selectedGenresStringMethod = indexBacking.getClass().getDeclaredMethod(
                    "selectedGenresString");
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        selectedGenres.setAccessible(true);
        selectedGenresStringMethod.setAccessible(true);
    }

    @Test
    public void testSelectedGenresStringOneEmptySelectedGenres() throws IllegalAccessException,
            InvocationTargetException {
        newSelectedGenres = new ArrayList<String>() {{
            add("");
        }};
        selectedGenres.set(indexBacking, newSelectedGenres);
        String result = (String) selectedGenresStringMethod.invoke(indexBacking);
        String expectation = "";
        Assert.assertEquals(expectation, result);
    }

    @Test
    public void testSelectedGenresStringOneNoEmptySelectedGenres() throws IllegalAccessException,
            InvocationTargetException {
        newSelectedGenres = new ArrayList<String>() {{
            add("Komedia");
        }};
        selectedGenres.set(indexBacking, newSelectedGenres);
        String result = (String) selectedGenresStringMethod.invoke(indexBacking);
        String expectation = "Komedia";
        Assert.assertEquals(expectation, result);
    }


    @Test
    public void testSelectedGenresString() throws InvocationTargetException,
            IllegalAccessException {
        newSelectedGenres = new ArrayList<String>() {{
            add("Komedia");
            add("Horror SF");
            add("Trzy %");
            add("111;");
            add("");
        }};
        selectedGenres.set(indexBacking, newSelectedGenres);
        String result = (String) selectedGenresStringMethod.invoke(indexBacking);
        String expectation = "Komedia|Horror_SF|Trzy_%|111_:_";
        Assert.assertEquals(expectation, result);
    }
}