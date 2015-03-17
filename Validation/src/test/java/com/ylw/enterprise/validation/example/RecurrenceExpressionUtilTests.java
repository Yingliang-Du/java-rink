package com.ylw.enterprise.validation.example;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecurrenceExpressionUtilTests {

//	private LoggerTestHelper loggerTestHelper = new LoggerTestHelper(Logger.getLogger(VendorDtoUtils.class));

	  @Test
	  public void recurrenceExpressionOneDay(){
	    String actual = RecurrenceExpressionUtil.getDays(RecurrenceExpressionUtil.SUPPORTED_RECURRENCE_EXPRESSION_START + "MO");
	    String expected = "Mondays";
	    assertEquals(expected, actual);
//	    loggerTestHelper.assertHappyLog();
	  }

	  @Test
	  public void recurrenceExpressionInvalid(){
	    String actual = RecurrenceExpressionUtil.getDays("RRULE:FREQ=MONTHLY;INTERVAL=1;WKST=SU;BYDAY=MO");
	    assertNull(actual);
//	    loggerTestHelper.assertWarnLog();
	  }

	  @Test
	  public void recurrenceExpressionTwoDays(){
	    String actual = RecurrenceExpressionUtil.getDays(RecurrenceExpressionUtil.SUPPORTED_RECURRENCE_EXPRESSION_START + "TU,FR");
	    String expected = "Tuesdays and Fridays";
	    assertEquals(expected, actual);
//	    loggerTestHelper.assertHappyLog();
	  }

	  @Test
	  public void recurrenceExpressionThreeDays(){
	    String actual = RecurrenceExpressionUtil.getDays(RecurrenceExpressionUtil.SUPPORTED_RECURRENCE_EXPRESSION_START + "SU,WE,TH");
	    String expected = "Sundays, Wednesdays and Thursdays";
	    assertEquals(expected, actual);
//	    loggerTestHelper.assertHappyLog();
	  }

	  @Test
	  public void recurrenceExpressionFourDays(){
	    String actual = RecurrenceExpressionUtil.getDays(RecurrenceExpressionUtil.SUPPORTED_RECURRENCE_EXPRESSION_START + "SA,FR,MO,TU,WE");
	    String expected = "Saturdays, Fridays, Mondays, Tuesdays and Wednesdays";
	    assertEquals(expected, actual);
//	    loggerTestHelper.assertHappyLog();
	  }

}
