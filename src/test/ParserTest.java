package test;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;

import helper.Parser;

public class ParserTest {

    @Before
    public void setUp() throws Exception {
    }

    public void testPatternAddEvent(String s, String name, String start, String end) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ADD_EVENT);
        assertEquals(name, result.group(Parser.TAG_NAME));
        assertEquals(start, result.group(Parser.TAG_START));
        assertEquals(end,  result.group(Parser.TAG_END));
    }
    
    public void testPatternAddTask(String s, String name, String due) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ADD_TASK);
        assertEquals(name, result.group(Parser.TAG_NAME));
        assertEquals(due, result.group(Parser.TAG_DATE));
    }
    
    public void testPatternProject(String s, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_PROJECT);
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternAddKeywordToProject(String s, String keyword, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ADD_KEYWORD_TO_PROJECT);
        assertEquals(keyword, result.group(Parser.TAG_KEYWORD));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternAddIndexToProject(String s, String index, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ADD_INDEX_TO_PROJECT);
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternAddProgress(String s, String progress, String index, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ADD_PROGRESS);
        assertEquals(progress, result.group(Parser.TAG_PROGRESS));
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternDeleteIndexFromProject(String s, String index, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_DELETE_INDEX_FROM_PROJECT);
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternDeleteProgress(String s, String index, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_DELETE_PROGRESS);
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternEditNameByKey(String s, String keyword, String field, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EDIT_NAME_BY_KEY);
        assertEquals(keyword, result.group(Parser.TAG_KEYWORD));
        assertEquals(field, result.group(Parser.TAG_FIELD));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternEditDateTimeByKey(String s, String keyword, String field, String date) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EDIT_DATE_TIME_BY_KEY);
        assertEquals(keyword, result.group(Parser.TAG_KEYWORD));
        assertEquals(field, result.group(Parser.TAG_FIELD));
        assertEquals(date, result.group(Parser.TAG_DATE));
    }
    
    public void testPatternEditNameByIndex(String s, String index, String field, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EDIT_NAME_BY_INDEX);
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(field, result.group(Parser.TAG_FIELD));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternEditDateTimeByIndex(String s, String index, String field, String date) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX);
        assertEquals(index, result.group(Parser.TAG_INDEX));
        assertEquals(field, result.group(Parser.TAG_FIELD));
        assertEquals(date, result.group(Parser.TAG_DATE));
    }
    
    public void testPatternEditProject(String s, String name, String field, String value) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EDIT_PROJECT);
        assertEquals(value, result.group(Parser.TAG_VALUE));
        assertEquals(field, result.group(Parser.TAG_FIELD));
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternName(String s, String name) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_NAME);
        assertEquals(name, result.group(Parser.TAG_NAME));
    }
    
    public void testPatternInteger(String s, String index) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_INTEGER);
        assertEquals(index, result.group(Parser.TAG_INDEX));
    }
    
    public void testPatternAny(String s, String value) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_ANY);
        assertEquals(value, result.group(Parser.TAG_VALUE));
    }
    
    public void testPatternEmpty(String s) {
        Matcher result = Parser.matchRegex(s, Parser.PATTERN_EMPTY);
        assertNotNull(result);
    }
    
    
    @Test
    public void testParser() {
        testPatternAddEvent("\"sleep yo\" on 15 Sep 2015 10:00 to 23 Sep 2016",
                "sleep yo", "15 Sep 2015 10:00", "23 Sep 2016");
        
        testPatternAddTask("\"sleep yo\" on 15 Sep 2015 10:00",
                "sleep yo", "15 Sep 2015 10:00");
        
        testPatternProject("project \"sleep and eat\" ",
                "sleep and eat");
        
        testPatternAddKeywordToProject("\"eat again\" to \"project ini\"",
                "eat again", "project ini");

        testPatternAddIndexToProject("2348 to \"project ini\"",
                "2348", "project ini");
        
        testPatternAddProgress("progress \"sleep \" 2348 to \"project ini\"",
                "sleep ", "2348", "project ini");
        
        testPatternDeleteIndexFromProject("898 from \"lewat gan\"",
                "898", "lewat gan");
        
        testPatternDeleteProgress("progress 898 from \"lewat gan\"",
                "898", "lewat gan");
        
        testPatternEditNameByKey(" \"sleep\"  name  \"sleep again\"",
                "sleep", "name", "sleep again");
        
        testPatternEditDateTimeByKey(" \"sleep\"  start  15 Sep 2015",
                "sleep", "start", "15 Sep 2015");
        
        testPatternEditNameByIndex(" 332  name  \"sleep again\"",
                "332", "name", "sleep again");
        
        testPatternEditDateTimeByIndex(" 282  start  15 Sep 2015",
                "282", "start", "15 Sep 2015");
        
        testPatternEditProject("project \"sleep\"  name  \"sleep again\"",
                "sleep", "name", "sleep again");
        
        testPatternName("    \" sleep and eat  \"   ",
                " sleep and eat  ");
        
        testPatternInteger("     343434     ",
                "343434");
        
        testPatternAny("asd8asd",
                "asd8asd");
        
        testPatternEmpty("     ");
        
        
        
    }

}
