package com.lkre.web.index;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class IndexBackingTest {
    private final String GENRES_COOKIES = "userSelectedGenres";
    private List<String> newSelectedGenres;
    private String newSelectedGenresString;
    private IndexBacking indexBacking;
    private Field selectedGenres;
    private Method selectedGenresStringMethod;
    private Method prepareGenreListMethod;

    @Before
    public void setUp() {
        indexBacking = new IndexBacking();
        try {
            selectedGenres = indexBacking.getClass().getDeclaredField("selectedGenres");
            selectedGenresStringMethod = indexBacking.getClass().getDeclaredMethod(
                    "selectedGenresString");
            prepareGenreListMethod = indexBacking.getClass().getDeclaredMethod("prepareGenreList"
                    , Cookie.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        selectedGenres.setAccessible(true);
        selectedGenresStringMethod.setAccessible(true);
        prepareGenreListMethod.setAccessible(true);
        newSelectedGenres = new ArrayList<String>() {{
            add("Komedia");
            add("Horror SF");
            add("Trzy %");
            add("111;");
            add("");
        }};
        newSelectedGenresString = "Komedia|Horror_SF|Trzy_%|111_:_";
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
        selectedGenres.set(indexBacking, newSelectedGenres);
        String result = (String) selectedGenresStringMethod.invoke(indexBacking);
        String expectation = newSelectedGenresString;
        Assert.assertEquals(expectation, result);
    }

    @Test
    public void testPrepareGenreList() throws InvocationTargetException, IllegalAccessException {
        newSelectedGenres = new ArrayList<String>() {{
            add("Komedia");
            add("Horror SF");
            add("Trzy %");
            add("111;");
        }};
        Cookie cookie = new Cookie(GENRES_COOKIES, newSelectedGenresString);
        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) prepareGenreListMethod.invoke(indexBacking, cookie);
        List<String> expect = this.newSelectedGenres;
        Assert.assertThat(result, is(expect));
    }
}